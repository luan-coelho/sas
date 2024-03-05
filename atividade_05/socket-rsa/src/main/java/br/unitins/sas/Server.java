package br.unitins.sas;

import org.apache.log4j.Logger;

import javax.crypto.Cipher;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.PublicKey;

/**
 * Bob
 */
public class Server {

    private static final Logger logger = Logger.getLogger(Server.class);

    public static void main(String[] args) throws Exception {
        int port = 6666;
        ServerSocket serverSocket = new ServerSocket(port);
        logger.info("Servidor iniciado e esperando por conexões...");

        Socket socket = serverSocket.accept();
        logger.info("Cliente conectado.");

        // Recebe a chave pública do cliente
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        PublicKey publicKey = (PublicKey) ois.readObject();

        // Mensagem para enviar
        String mensagem = "Olá, Alice!";

        // Criptografa a mensagem
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] mensagemCriptografada = cipher.doFinal(mensagem.getBytes());

        // Envia a mensagem criptografada
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        oos.writeObject(mensagemCriptografada);

        ois.close();
        oos.close();
        socket.close();
        serverSocket.close();
    }
}