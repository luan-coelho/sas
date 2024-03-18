package br.unitins.sas;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import static br.unitins.sas.RSAKeyPairGenerator.decrypt;
import static br.unitins.sas.RSAKeyPairGenerator.encrypt;

public class AliceServer {
    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(5000);
        System.out.println("Alice esperando conexões na porta 5000...");

        Socket socket = serverSocket.accept();
        System.out.println("Conexão de Bob recebida!");

        BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter output = new PrintWriter(socket.getOutputStream(), true);

        String line;
        while ((line = input.readLine()) != null) {
            System.out.println("Mensagem criptografada recebida de Bob: " + line);
            // Descriptografa a mensagem usando a chave privada de Alice
            String decryptedMessage = decrypt(line, "alice_private_key.pem");
            System.out.println("Mensagem descriptografada: " + decryptedMessage);

            // Se a mensagem for "tchau", encerra a comunicação
            if ("tchau".equalsIgnoreCase(decryptedMessage)) {
                System.out.println("Alice encerrou a conexão.");
                break;
            }

            // Lê uma mensagem do terminal para enviar a Bob
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Digite sua mensagem para Bob: ");
            String messageToBob = reader.readLine();
            // Criptografa a mensagem com a chave pública de Bob
            String encryptedMessageToBob = encrypt(messageToBob, "bob_public_key.pem");
            output.println(encryptedMessageToBob);
        }

        socket.close();
        serverSocket.close();
    }
}
