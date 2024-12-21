import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// Clase principal
public class VerificadorDeContrasena {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        mostrarRequisitos();
        System.out.print("Ingrese la cantidad de contraseñas a verificar: ");
        int cantidad = leerNumeroPositivo(scanner);

        List<String> contrasenas = leerContrasenas(scanner, cantidad);

        System.out.println("\n=== Iniciando verificación ===");
        ExecutorService executorService = Executors.newFixedThreadPool(cantidad);

        for (String contrasena : contrasenas) {
            executorService.execute(new VerificadorContrasena(contrasena));
        }

        executorService.shutdown();
        scanner.close();
    }

    // Mostrar los requisitos de las contraseñas
    private static void mostrarRequisitos() {
        System.out.println("=== Requisitos de la contraseña ===");
        System.out.println("- Mínimo 8 caracteres.");
        System.out.println("- Al menos un carácter especial (!@#$%^&*(),.?/:{}|<>).");
        System.out.println("- Al menos 2 letras mayúsculas.");
        System.out.println("- Al menos 3 letras minúsculas.");
        System.out.println("- Al menos 1 número.\n");
    }

    // Leer un número positivo desde la entrada
    private static int leerNumeroPositivo(Scanner scanner) {
        while (!scanner.hasNextInt()) {
            System.out.print("Por favor, ingrese un número válido: ");
            scanner.next();
        }
        int numero = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea
        return Math.max(numero, 1);
    }

    // Leer contraseñas desde la entrada
    private static List<String> leerContrasenas(Scanner scanner, int cantidad) {
        List<String> contrasenas = new ArrayList<>();
        for (int i = 0; i < cantidad; i++) {
            System.out.print("Ingrese la contraseña #" + (i + 1) + ": ");
            contrasenas.add(scanner.nextLine());
        }
        return contrasenas;
    }
}
