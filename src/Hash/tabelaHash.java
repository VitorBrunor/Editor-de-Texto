package Hash;
import java.util.LinkedList;
import Lista.Encadeada;
import Lista.No;
import java.io.FileWriter;
import java.io.IOException;

public class tabelaHash {

    private static final int TAMANHO_TABELA = 40000;
    private Encadeada[] tabela;

    public tabelaHash() {
        tabela = new Encadeada[TAMANHO_TABELA];
        for (int i = 0; i < TAMANHO_TABELA; i++) {
            tabela[i] = new Encadeada();
        }
    }


    private int calcularIndice(String palavra) {
        int hashCode = calcularHashCode(palavra);
        return hashCode % TAMANHO_TABELA;
    }

    private int calcularHashCode(String palavra) {
        int hashCode = 0;
        for (char c : palavra.toCharArray()) {
            hashCode += (int) c;
        }
        return hashCode;
    }

    public void adicionar(String palavra) {
        int indice = calcularIndice(palavra);
        tabela[indice].adicionar(palavra);
        salvarPalavraNoArquivo(palavra);
    }

    public void remover(String palavra) {
        int indice = calcularIndice(palavra);
        tabela[indice].exluirElemento(palavra);
    }

    public boolean buscar(String palavra) {
        int indice = calcularIndice(palavra);
        Encadeada EncadeadaEncadeada = tabela[indice];
        No atual = EncadeadaEncadeada.getCabeca();
        while (atual != null) {
            if (atual.getPalavra().equals(palavra)) {
                return true;
            }
            atual = atual.getNo();
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < TAMANHO_TABELA; i++) {
            sb.append("Posição ").append(i).append(": ").append(tabela[i].toString()).append("\n");
        }
        return sb.toString();
    }


    public String[] getPalavras() {
        LinkedList<String> palavras = new LinkedList<>();

        for (int i = 0; i < TAMANHO_TABELA; i++) {
            Encadeada encadeada = tabela[i];
            No atual = encadeada.getCabeca();

            while (atual != null) {
                palavras.add(atual.getPalavra());
                atual = atual.getNo();
            }
        }

        return palavras.toArray(new String[0]);
    }

    private void salvarPalavraNoArquivo(String palavra) {
        try {
            FileWriter arquivo = new FileWriter("dicionario.txt", true);
            arquivo.write(palavra + "\n");
            arquivo.close();
        } catch (IOException e) {
            System.out.println("Erro ao salvar a palavra no arquivo.");
            e.printStackTrace();
        }
    }

}