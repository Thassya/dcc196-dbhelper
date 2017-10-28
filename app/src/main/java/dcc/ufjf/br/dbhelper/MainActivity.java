package dcc.ufjf.br.dbhelper;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView textSaida;
    Button btnInsere;
    Button btnLista;

    BibliotecaDBHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        helper = new BibliotecaDBHelper(getApplicationContext());

        textSaida = (TextView) findViewById(R.id.txtSaida);
        btnInsere = (Button) findViewById(R.id.btnInsere);
        btnLista = (Button) findViewById(R.id.btnLista);

        btnInsere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    SQLiteDatabase db = helper.getWritableDatabase();
                    ContentValues valores = new ContentValues();
                    valores.put(BibliotecaContract.Livro.COLUMN_NAME_TITULO, "Deu a louca no Império");
                    valores.put(BibliotecaContract.Livro.COLUMN_NAME_AUTOR, "JRR Lucas");
                    valores.put(BibliotecaContract.Livro.COLUMN_NAME_ANO, 1956);
                    //null.. id ja e gerado automaticamente. nao preciso fazer nd aqui.
                    db.insert(BibliotecaContract.Livro.TABLE_NAME, null, valores);
                }
                catch (Exception e){
                    Log.e("Biblio", e.getLocalizedMessage());
                    Log.e("Biblio", e.getStackTrace().toString());
                }
            }
        });

        btnLista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db = helper.getReadableDatabase();
                String[] campos = {
                        BibliotecaContract.Livro._ID,
                        BibliotecaContract.Livro.COLUMN_NAME_TITULO,
                        BibliotecaContract.Livro.COLUMN_NAME_AUTOR,
                        BibliotecaContract.Livro.COLUMN_NAME_ANO,
                };

                String where = BibliotecaContract.Livro.COLUMN_NAME_ANO +  " < ? ";
                String[] argumentos = {"1999"};
                String ord = BibliotecaContract.Livro.COLUMN_NAME_EDITORA + " DESC";

                Cursor resultado = db.query(BibliotecaContract.Livro.TABLE_NAME, campos, where, argumentos, null, null, ord);

                resultado.moveToFirst();
                int idxID = resultado.getColumnIndexOrThrow(BibliotecaContract.Livro._ID); //para assegurar. Sabemos que é zero. Nao precisa revirar o código se eu mudar..
                int idxTitulo = resultado.getColumnIndexOrThrow(BibliotecaContract.Livro.COLUMN_NAME_TITULO); //para assegurar. Sabemos que é zero. Nao precisa revirar o código se eu mudar..

                textSaida.setText(String.format("%d: %s", resultado.getInt(idxID), resultado.getString(idxTitulo)));
            }
        });
    }
}
