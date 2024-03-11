import java.util.Scanner;

//Playfair
public class Decifrador {

    private static final Scanner scanner = new Scanner(System.in);
    private static char[][] tabelaCaracteres;
    private static Ponto[] posicoes;

    public static void main(String[] args) {
        String chave = "queromeformarlogo";
        System.out.println("Insira o texto cifrado a ser decifrado:");
        String textoCifrado = scanner.nextLine().toUpperCase().replaceAll("[^A-Z]", "");
        System.out.println("==== RESULTADO ===");
        System.out.println("Texto decifrado: " + decifrar(textoCifrado, chave));
    }

    public static String decifrar(String texto, String chave) {
        criarTabela(chave);

        StringBuilder textoDecifrado = new StringBuilder();
        char[] textoPreparado = texto.toCharArray();

        for (int i = 0; i < textoPreparado.length; i += 2) {
            char a = textoPreparado[i];
            char b = textoPreparado[i + 1];
            int linha1 = posicoes[a - 'A'].x;
            int linha2 = posicoes[b - 'A'].x;
            int coluna1 = posicoes[a - 'A'].y;
            int coluna2 = posicoes[b - 'A'].y;

            if (linha1 == linha2) {
                coluna1 = (coluna1 + 4) % 5;
                coluna2 = (coluna2 + 4) % 5;
            } else if (coluna1 == coluna2) {
                linha1 = (linha1 + 4) % 5;
                linha2 = (linha2 + 4) % 5;
            } else {
                int temp = coluna1;
                coluna1 = coluna2;
                coluna2 = temp;
            }

            textoDecifrado.append(tabelaCaracteres[linha1][coluna1]);
            textoDecifrado.append(tabelaCaracteres[linha2][coluna2]);
        }

        return textoDecifrado.toString();
    }

    private static void criarTabela(String chave) {
        tabelaCaracteres = new char[5][5];
        posicoes = new Ponto[26];

        String s = prepararChave(chave) + "ABCDEFGHIKLMNOPQRSTUVWXYZ";

        int[] flag = new int[26];
        int x = 0, y = 0;

        for (char c : s.toCharArray()) {
            if (flag[c - 'A'] == 0) {
                tabelaCaracteres[x][y] = c;
                posicoes[c - 'A'] = new Ponto(x, y);
                if (++y == 5) {
                    if (++x == 5) break;
                    y = 0;
                }
                flag[c - 'A'] = 1;
            }
        }
    }

    private static String prepararChave(String chave) {
        chave = chave.toUpperCase().replaceAll("[^A-Z]", "");
        return chave.replaceAll("J", "I");
    }
}

