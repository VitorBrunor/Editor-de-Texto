package Lista;

import java.util.Locale;
import java.util.Scanner;
import Hash.tabelaHash;

public class Main {

    public static void Main (String[] args) {

        Locale.setDefault(Locale.US);
        Scanner sc = new Scanner(System.in);

        Encadeada lista1 = new Encadeada();

        // Criar uma instância da tabela hash
        tabelaHash tabela = new tabelaHash();

        // Adicionar elementos
        tabela.adicionar("apple");
        tabela.adicionar("banana");
        tabela.adicionar("orange");
        tabela.adicionar("grape");
        tabela.adicionar("watermelon");

        // Exibir a tabela hash
        System.out.println("Tabela Hash:");
        System.out.println(tabela);

        // Verificar se uma palavra está presente na tabela
        String palavra1 = "banana";
        String palavra2 = "pineapple";
        System.out.println("A palavra '" + palavra1 + "' está presente na tabela? " + tabela.buscar(palavra1));
        System.out.println("A palavra '" + palavra2 + "' está presente na tabela? " + tabela.buscar(palavra2));

        // Remover um elemento da tabela
        tabela.remover("orange");

        // Exibir a tabela hash atualizada
        System.out.println("Tabela Hash após remover 'orange':");
        System.out.println(tabela);
    }

    public static void showMenu() {
        System.out.println("1 - Inserir elemento na lista");
        System.out.println("2 - Mostrar lista");
        System.out.println("3 - Mostrar o tamanho da lista");
        System.out.println("4 - excluir da lista");
        System.out.println("5 - Sair");
        System.out.println("  ");
    }
}