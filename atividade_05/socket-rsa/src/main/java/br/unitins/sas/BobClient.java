package br.unitins.sas;

import java.io.*;
import java.net.Socket;

import static br.unitins.sas.RSAKeyPairGenerator.*;

public class BobClient {
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("localhost", 5000);
        System.out.println("Conectado a Alice!");

        BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        String line;
        while (true) {
            // Lê uma mensagem do terminal para enviar a Alice
            System.out.print("Digite sua mensagem para Alice: ");
            String messageToAlice = reader.readLine();
            // Criptografa a mensagem com a chave pública de Alice
            String encryptedMessageToAlice = encrypt(messageToAlice, "alice_public_key.pem");
            output.println(encryptedMessageToAlice);

            // Se a mensagem for "tchau", encerra a comunicação
            if ("tchau".equalsIgnoreCase(messageToAlice)) {
                System.out.println("Bob encerrou a conexão.");
                break;
            }

            // Espera pela resposta de Alice
            line = input.readLine();
            System.out.println("Mensagem criptografada recebida de Alice: " + line);
            // Descriptografa a mensagem usando a chave privada de Bob
            String decryptedMessage = decrypt(line, "bob_private_key.pem");
            System.out.println("Mensagem descriptografada: " + decryptedMessage);
        }

        socket.close();
    }

    // Métodos encrypt e decrypt similares aos da classe AliceServer
}