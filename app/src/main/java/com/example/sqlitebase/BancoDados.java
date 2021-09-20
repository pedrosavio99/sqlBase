package com.example.sqlitebase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

//crianção e manutenção do banco
public class BancoDados extends SQLiteOpenHelper {


    //variaveis dinamicas.
    private static final int VERSAO_BANCO = 1;
    private static final String BANCO_CLIENTE = "Banco_dados_Clientes"; //nome do banco



    //criação da tabela E SUAS COLUNAS
    private static final String TABELA_CLIENTE = "tabela_Clientes";
    private static final String COLUNA_CODIGO = "codigo";
    private static final String COLUNA_NOME = "nome";
    private static final String COLUNA_GENERO = "genero";



    //ESQEUELETO CRIADO SO FALTA A PARTE DAS COLUNAS
    public BancoDados(@Nullable Context context) {
        super(context, BANCO_CLIENTE, null, VERSAO_BANCO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //precisamos criar uma query pra da inicio ao banco, usando as variaveis que a gente ja tinha

        String QUERY_COLUNA = "CREATE TABLE " + TABELA_CLIENTE + "(" +
                COLUNA_CODIGO + " INTEGER PRIMARY KEY, " + COLUNA_NOME + " TEXT,"
                + COLUNA_GENERO + " TEXT)";

        //esse codigo cria de fato o nosso banco
        db.execSQL(QUERY_COLUNA);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            //esse metodo serve para quando vc por exemplo precisa adicionar uma coluna a um
        //banco ja existente.
    }

    //=====================================================================================

    /*  as opções de adicionar remover listar essas coisas ficarão aqui em baixo  */

    //criamos a classe modelo cliente do objeto Cliente
    void addCliente(Cliente cliente) {
        //vamos pegar os dados vindos do obejto Cliente, usando referencia ao banco ja ciando

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues(); //variavel pra armazenar as informaçãos do obj

        values.put(COLUNA_NOME, cliente.getNome());
        values.put(COLUNA_GENERO, cliente.getGenero());

        //agr vamos adicionar ao banco, ou melhor diretamente na tabela criada.
        db.insert(TABELA_CLIENTE,null,values);
        db.close();

    }


    void apagarCliente(Cliente cliente){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABELA_CLIENTE,COLUNA_CODIGO + " = ?", new String[] { String.valueOf(cliente.getCodigo())});
        db.close();
    }

    Cliente selecionarCliente(int codigo){
        //observe que em selecionar vc quer ler e não escrever na tabela
        SQLiteDatabase db = this.getReadableDatabase();

        //para ler um determido elemento da tabela vc precisa criar um cursor

        Cursor cursor = db.query(TABELA_CLIENTE, new String[]{COLUNA_CODIGO,COLUNA_NOME,COLUNA_GENERO}
        ,COLUNA_CODIGO + " = ?",new String[] {String.valueOf(codigo)},
                null, null,null,null );
        //verificar se o cursosr n esta nulo
        if(cursor != null) {
            //mover para a primeira posição
            cursor.moveToFirst();
            //pronto agr ta selecionado, precisamos retornar a informação

        }
        //pegando pelo indice, no 0 tem o codigo, no 1 tem o nome e no 2 tem o genero
        Cliente cliente = new Cliente(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1),cursor.getString(2));
        return cliente;
    }


    //atualizar cliente

    void atualizarCliente(Cliente cliente) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues(); //variavel pra armazenar as informaçãos do obj
        values.put(COLUNA_NOME, cliente.getNome());
        values.put(COLUNA_GENERO, cliente.getGenero());

        //com os valores pegos e setandos em values é so atualizaar a posição no db

        db.update(TABELA_CLIENTE,values, COLUNA_CODIGO + " = ?",
                new String[] { String.valueOf(cliente.getCodigo()) });

    }

    //CRIAR A LISTA PRA SER EXIBIDA
    public List<Cliente> listaTodosClientes(){
        List<Cliente> listaClientes = new ArrayList<Cliente>();
        String query = "SELECT * FROM " +  TABELA_CLIENTE;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(query,null);

        if(c.moveToFirst()) {
            do {
                Cliente cliente = new Cliente();
                cliente.setCodigo(Integer.parseInt(c.getString(0)));
                cliente.setNome(c.getString(1));
                cliente.setGenero(c.getString(2));

                listaClientes.add(cliente);
            } while (c.moveToNext());
        }

        return listaClientes;
    }
}
