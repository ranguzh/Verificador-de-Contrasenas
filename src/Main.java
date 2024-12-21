import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// Clase para verificar contraseñas en hilos
class VerificadorContrasena implements Runnable {
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
