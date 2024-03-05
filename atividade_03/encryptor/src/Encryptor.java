public class Encryptor {

    int[][] chave = {{17, 17, 5}, {21, 18, 21}, {2, 2, 19}}; // Exemplo de chave 3x3
    int[][] inversaChave = {{4, 9, 15}, {15, 17, 6}, {24, 0, 17}}; // Inversa da chave, necessário para descriptografar

    // Método para cifrar
    public String cifrar(String textoClaro) {
        int[] vetorTexto = textoParaNumeros(textoClaro.toUpperCase());
        StringBuilder textoCifrado = new StringBuilder();
        for (int i = 0; i < vetorTexto.length; i += 3) {
            int[] bloco = {vetorTexto[i], vetorTexto[i + 1], vetorTexto[i + 2]};
            int[] blocoCifrado = multiplicaMatrizBloco(bloco, chave);
            for (int num : blocoCifrado) {
                textoCifrado.append((char) (num + 65));
            }
        }
        return textoCifrado.toString();
    }

    // Método para converter texto em números
    private int[] textoParaNumeros(String texto) {
        int[] numeros = new int[texto.length()];
        for (int i = 0; i < texto.length(); i++) {
            numeros[i] = texto.charAt(i) - 'A';
        }
        return numeros;
    }

    // Método para multiplicar a matriz da chave pelo bloco de texto
    private int[] multiplicaMatrizBloco(int[] bloco, int[][] chave) {
        int[] resultado = new int[bloco.length];
        for (int i = 0; i < chave.length; i++) {
            for (int j = 0; j < bloco.length; j++) {
                resultado[i] += chave[i][j] * bloco[j];
            }
            resultado[i] %= 26; // Modulo 26
        }
        return resultado;
    }
}
