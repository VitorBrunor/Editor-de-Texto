package Lista;

import java.util.Locale;
import java.util.Scanner;
import org.w3c.dom.Node;
import Lista.No;

public class Encadeada {

    private No cabeca; // cabeça do nó
    private String palavra;
    private int tamanho;
    private int valor;


    // aqui vamos instanciar uma Encadeada vazia

    public Encadeada() {
        palavra = " ";
        cabeca = new No();

    }

    public String getPalavra() {
        return palavra;
    }

    public No getCabeca(){
        return cabeca;
    }

    public void adicionar(String palavra) {
        No NovoNo = new No(); // instancia um nó Novo em branco
        NovoNo.setPalavra(palavra);
        NovoNo.setNo(cabeca);
        cabeca = NovoNo;
    }

    // Método para saber o tamanho da Encadeada
    public int getTamanho() {

        No atual = cabeca;
        int contador = 0;

        while (atual != null) {
            contador++;
            atual = atual.getNo();
        }

        return contador;
    }

    public int getValor(){
        return valor;
    }

    // Método para definir o valor de um elemento na Encadeada
    public void setValor(int posicao, int valor) {
        No atual = cabeca;
        int contador = 0;

        while (atual != null && contador < posicao) {
            atual = atual.getNo();
            contador++;
        }

        if (atual != null) {
            atual.setNo(atual);
        }
    }

    // Método para exluir um elemento da Encadeada
    public void exluirElemento(String elemento) {
        No atual = cabeca;
        No anterior = cabeca;

        // Aqui vamos implementar um método para caso o elemento a ser excluido seja o
        // primeiro da Encadeada

        for (int i = 0; i < this.getTamanho(); i++) {

            if (this.getTamanho() == 1) {

                // atual = atual.getNo();
                anterior = atual; // para apontar o nó anterior ao nó que queremos exluir.
                atual = atual.getNo(); // aqui é para apontar o próximo elemento caso nao seja o que desejamos excluir
                /*
                 * anterior.setNo(atual.getNo());
                 * atual = atual.setNo();
                 * break;// como encontramos o valor acima aqui vamos parar.
                 */
            }

        }

        // Aqui eprcorre a lsita para encontrar o elemento
        while (atual != null && (atual.getPalavra() == null || !atual.getPalavra().equals(elemento))) {
            anterior = atual;
            atual = atual.getNo();
        }

        // Se o elemento for encontrado
        if (atual != null) {
            anterior.setNo(atual.getNo());

        }
    }

    // Aqui é uma função para exiNor a Encadeada
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("");

        No p = cabeca;
        while (p != null) {
            sb.append(p.getPalavra() + "\n");
            p = p.getNo();
        }
        sb.append("");

        return sb.toString();
    }

}