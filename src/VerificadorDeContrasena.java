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

    // Clase para verificar contraseñas en hilos
    static class VerificadorContrasena implements Runnable {
        private final String contrasena;

        public VerificadorContrasena(String contrasena) {
            this.contrasena = contrasena;
        }

        @Override
        public void run() {
            System.out.println("\nHilo verificando contraseña: " + contrasena);
            List<String> errores = validarContrasena(contrasena);

            if (errores.isEmpty()) {
                System.out.println("Contraseña válida: " + contrasena);
            } else {
                System.out.println("Contraseña inválida: " + contrasena);
                System.out.println("Requisitos no cumplidos:");
                errores.forEach(error -> System.out.println("- " + error));
            }
        }

        // Validar contraseña y devolver los errores
        private List<String> validarContrasena(String contrasena) {
            List<String> errores = new ArrayList<>();

            if (contrasena.length() < 8) {
                errores.add("Debe tener al menos 8 caracteres.");
            }
            if (!contrasena.matches(".*[!@#$%^&*(),.?/:{}|<>].*")) {
                errores.add("Debe contener al menos un carácter especial.");
            }
            if (!contrasena.matches(".*[A-Z].*[A-Z].*")) {
                errores.add("Debe contener al menos dos letras mayúsculas.");
            }
            if (!contrasena.matches(".*[a-z].*[a-z].*[a-z].*")) {
                errores.add("Debe contener al menos tres letras minúsculas.");
            }
            if (!contrasena.matches(".*\\d.*")) {
                errores.add("Debe contener al menos un número.");
            }

            return errores;
        }
    }
}
