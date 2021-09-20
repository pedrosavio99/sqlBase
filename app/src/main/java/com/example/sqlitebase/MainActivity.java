package com.example.sqlitebase;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Service;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText editCodigo,editNome,editGenero;
    Button btnSalvar,btnExcluir;
    ListView listaCliente;

    //referenciando o banco ja criado
    BancoDados db = new BancoDados(this);

    //variavel para guardar a lista dos elemntos da tabela
    ArrayAdapter<String> adapter; //montagem da lista
    ArrayList<String> arrayList;  //preencher a ui

    InputMethodManager imm; //isso é pra tirar o teclado quando o user parar de digitar


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editCodigo = (EditText)findViewById(R.id.cod_);
        editNome = (EditText)findViewById(R.id.nome_);
        editGenero = (EditText)findViewById(R.id.genero_);

        btnExcluir = (Button)findViewById(R.id.excluir);
        btnSalvar = (Button)findViewById(R.id.salvar);

        imm = (InputMethodManager) this.getSystemService(Service.INPUT_METHOD_SERVICE); //  a implmeentação ta la em baixo

        listaCliente = (ListView)findViewById(R.id.lista);

        listarClientes();


        //criar a função pra pegar os item do banco e mostrar no textview.
        listaCliente.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String conteudo = (String) listaCliente.getItemAtPosition(position);

                //Toast.makeText(MainActivity.this, "Selecionado: " + conteudo, Toast.LENGTH_LONG).show();
                String codigo = conteudo.substring(0,conteudo.indexOf("-"));
                Cliente cliente = db.selecionarCliente(Integer.parseInt(codigo));

                editCodigo.setText(String.valueOf(cliente.getCodigo()));
                editNome.setText(cliente.getNome());
                editGenero.setText(cliente.getGenero());
            }
        });


        //botao salvar vem aqui
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String codigo = editCodigo.getText().toString();
                String nome = editNome.getText().toString();
                String genero = editGenero.getText().toString();

                if(nome.isEmpty()){
                    editNome.setError("Campo Obrigatório");
                    //Toast.makeText(MainActivity.this, "Complete os campos", Toast.LENGTH_LONG).show();

                } else if (codigo.isEmpty()){
                        //inserir dados na tabela
                        db.addCliente(new Cliente(nome,genero));
                        Toast.makeText(MainActivity.this, "salvo com sucesso", Toast.LENGTH_LONG).show();
                        listarClientes();
                        esconderTeclado();
                         limpaCampos();
                    } else {
                        //atualizar dados na tabela
                        db.atualizarCliente(new Cliente(Integer.parseInt(codigo),nome,genero));
                        Toast.makeText(MainActivity.this, "Atualizado com sucesso", Toast.LENGTH_LONG).show();
                        listarClientes();
                        esconderTeclado();
                        limpaCampos();



                    }
            }
        });


        //botão excluir
        btnExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String codigo = editCodigo.getText().toString();

                if (codigo.isEmpty()){
                    Toast.makeText(MainActivity.this, "não encontrado", Toast.LENGTH_LONG).show();
                }else {
                    Cliente cliente = new Cliente();
                    //perceba que esse parse int é so por conta que o codigo é um int
                    cliente.setCodigo(Integer.parseInt(codigo));
                    db.apagarCliente(cliente);
                    Toast.makeText(MainActivity.this, "Excluido com sucesso", Toast.LENGTH_LONG).show();
                    listarClientes();
                    limpaCampos();
                }

            }
        });






        //testando as funções da tabela
       // add um cliente
        //db.addCliente(new Cliente("Pedro Savio","Cavalo"));


        //como apagar, defina o objeto e defina o codigo depois apague com a função
       // Cliente cliente = new Cliente();
        //cliente.setCodigo(3);
        //db.apagarCliente(cliente);

        //Toast.makeText(MainActivity.this, "salvo com sucesso", Toast.LENGTH_LONG).show();

        //buscar um cliente

        //instanciando um cliente


        //escolhendo o cliente epelo codigo
       // Cliente cliente = db.selecionarCliente(1);
            //so pra ver se selecionou msm
        //Log.d("Cliente selecionado", "Codigo: " + cliente.getCodigo() + "Nome: "+ cliente.getNome() + "genero: " + cliente.getGenero());


        //função atualizar elementos dentro da tabela
      /*Cliente cliente = new Cliente();
        cliente.setCodigo(1);
        cliente.setNome("Pedro Deus Supremo pokemom 2000");
        cliente.setGenero("BestaInfernal");

        db.atualizarCliente(cliente);
        Log.d("Cliente selecionado", "Codigo: " + cliente.getCodigo() + " Nome: "+ cliente.getNome() + " genero: " + cliente.getGenero());
        */


    }

    //essa é a função que vai chamar a listagem do banco de dados
    public void listarClientes(){

        List<Cliente> clientes = db.listaTodosClientes();
        arrayList = new ArrayList<String>();//aqui sempre recebe um novo
        adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1,arrayList);
        //referencia a lista do ui
        listaCliente.setAdapter(adapter);

        for(Cliente c : clientes){
               //Log.d("Lista","\nID: "+ c.getCodigo()+ " Nome: " + c.getNome());

            //aqui eu so intanciei o codigo e o neme, mas é so concatenar o genero que ele aparece tbm
            arrayList.add(c.getCodigo() + "-" + c.getNome());
            adapter.notifyDataSetChanged();
        }

    }

    void limpaCampos(){
        editCodigo.setText("");
        editNome.setText("");
        editGenero.setText("");

        editNome.requestFocus();
    }


    void esconderTeclado(){
        //precisa referenciar um edittext qualquer
        imm.hideSoftInputFromWindow(editNome.getWindowToken(),0);
    }

}