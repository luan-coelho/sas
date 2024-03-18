package br.unitins.sas;

import java.io.*;
import java.util.Base64;

public class RSAKeyPairGenerator {
    public static void main(String[] args) throws IOException {
        executeCommand("openssl genpkey -algorithm RSA -out bob_private_key.pem -pkeyopt rsa_keygen_bits:1024");
        executeCommand("openssl rsa -pubout -in bob_private_key.pem -out bob_public_key.pem");

        executeCommand("openssl genpkey -algorithm RSA -out alice_private_key.pem -pkeyopt rsa_keygen_bits:1024");
        executeCommand("openssl rsa -pubout -in alice_private_key.pem -out alice_public_key.pem");

        System.out.println("\n=== Chaves RSA geradas com sucesso! ===");
    }

    public static String encrypt(String message, String publicKeyPath) throws Exception {
        String command = "echo \"" + message + "\" | openssl rsautl -encrypt -pubin -inkey " + publicKeyPath + " | base64";
        return executeCommand(command);
    }

    public static String decrypt(String base64EncryptedMessage, String privateKeyPath) throws Exception {
        // Decodifica a mensagem criptografada de Base64 para bytes
        byte[] encryptedMessageBytes = Base64.getDecoder().decode(base64EncryptedMessage);

        // Salva a mensagem criptografada em um arquivo temporário, pois pkeyutl lê a entrada de um arquivo
        File tempEncryptedFile = File.createTempFile("encrypted", ".bin");
        try (FileOutputStream fos = new FileOutputStream(tempEncryptedFile)) {
            fos.write(encryptedMessageBytes);
        }

        // Constrói o comando usando pkeyutl para descriptografar
        String command = String.format("openssl pkeyutl -decrypt -in %s -inkey %s",
                tempEncryptedFile.getAbsolutePath(), privateKeyPath
        );
        String s = executeCommand(command);

        // Apaga o arquivo temporário
        tempEncryptedFile.delete();

        return s;
    }

    /**
     * Executa um comando no terminal
     *
     * @param command Comando
     * @return Saída do comando
     * @throws IOException
     */
    private static String executeCommand(String command) throws IOException {
        ProcessBuilder builder = new ProcessBuilder();
        builder.command("sh", "-c", command);
        Process process = builder.start();

        InputStream is = process.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));

        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        return sb.toString();
    }
}