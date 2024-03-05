package br.unitins.sas;

import org.apache.log4j.Logger;

import javax.crypto.Cipher;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Alice
 */
public class Client {

    private static final Logger logger = Logger.getLogger(Client.class);

    public static void main(String[] args) throws Exception {
        String host = "localhost";
        int port = 6666;
        Socket socket = new Socket(host, port);

        // Gera o par de chaves
        RSAKeyPairGenerator keyPairGenerator = new RSAKeyPairGenerator();

        // Envia a chave pública para o servidor
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        oos.writeObject(keyPairGenerator.getPublicKey());

        // Recebe a mensagem criptografada
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        byte[] mensagemCriptografada = (byte[]) ois.readObject();

        // Descriptografa a mensagem
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, keyPairGenerator.getPrivateKey());
        byte[] mensagemDescriptografada = cipher.doFinal(mensagemCriptografada);
        logger.info("Mensagem recebida do servidor: " + new String(mensagemDescriptografada));

        ois.close();
        oos.close();
        socket.close();
    }
}
