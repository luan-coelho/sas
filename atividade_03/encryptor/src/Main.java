import java.util.Scanner;

public class Main {

    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        Encryptor hc = new Encryptor();
        String textoClaro = "TEErertet";
        String textoCifrado = hc.cifrar(textoClaro);

        System.out.println("=== RESULTADO ===");
        System.out.println("Texto Cifrado: " + textoCifrado);
    }
}
