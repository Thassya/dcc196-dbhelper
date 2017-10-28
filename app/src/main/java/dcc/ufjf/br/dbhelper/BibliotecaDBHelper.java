package dcc.ufjf.br.dbhelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by thassya on 28/10/17.
 */

public class BibliotecaDBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "biblioteca.db";

    public BibliotecaDBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(BibliotecaContract.Livro.SQL_CREATE_LIVRO);
        Log.i("BIBLIO", "Tabela Criada! v" + DATABASE_VERSION);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int antigo, int novo) {
        //versão nova? É tudo noovo! não quero saber de nada antigo. DROP!
        sqLiteDatabase.execSQL(BibliotecaContract.Livro.SQL_DROP_LIVRO);

        Log.i("BIBLIO", "Tabela atualizada de v" + antigo + " para v" + novo);
        onCreate(sqLiteDatabase);

    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //se o cara reverter o app, você vai lá e reseta tudo
        Log.i("BIBLIO", "Tabela revertida de v" + oldVersion + " para v" + newVersion);
        onUpgrade(db,oldVersion,newVersion);
    }


}
