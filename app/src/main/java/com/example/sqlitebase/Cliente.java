package com.example.sqlitebase;
//essa é a clasee de transição do iu pro db, sim, isso é um objeto!!!!
public class Cliente {



    //observe que são as variaveis que vc add no ui
    int codigo;
    String nome;
    String genero;


    //criando um contrutor para podermos instanciar em determinadas partes

    public Cliente(){
        //isso serve para podermos instaciarmos essa classe em outras de nosso projeto e podermos transitar os dados

    }



    //esse contrutor é pra fzer o update do banco de dados, para quando for alerado alguma coisa
    public Cliente(int _codigo, String _nome, String _genero){

        //observe q estou pegando as pastes do objeto e instanciando com os paremetros desse construtor
        this.codigo = _codigo;
        this.nome = _nome;
        this.genero = _genero;
    }


    //esse é o construtor para inserir dados ao banco
    public Cliente( String _nome, String _genero){

        //observe que o codigo não foi adicionado pois o codigo não sera gerado em buscas
        this.nome = _nome;
        this.genero = _genero;
    }
//===================================================================================

    //essa parte eu construi clianco Botaodireito - generate - gatter and setters e selecionei todas as minha variaveis
    //esse banco é criado com sql Helper
    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }
}
