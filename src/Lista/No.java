package Lista;

public class No {

    private String palavra; // Lista do tipo String
    private No proxNo; // será utilizado para ir ao proximo nó
    private String elemento; //elemento que adiciona elemento em cada nó da lista


    // Criando um objeto vazio do tipo No
    public No(){
        palavra = " ";
        proxNo = null;
    }

    /* Abaixo teremos os metodos gets e setes para obter acesso aos atributos da classe */
    public String getPalavra(){
        return palavra;
    }

    public void setPalavra( String palav){
        palavra = palav;

    }

    //Obtém o próximo nó da lsita
    public No getNo(){
        return proxNo;

    }
    //Define a referência para o próximo nó da lista
    public void setNo(No prox){
        proxNo = prox;
    }

    public No(String elem){
        elemento = elem;
        proxNo = null;
    }

    public String getElemento(){
        return elemento;
    }


}