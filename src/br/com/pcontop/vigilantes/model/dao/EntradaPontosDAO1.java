package br.com.pcontop.vigilantes.model.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import br.com.pcontop.vigilantes.model.bean.EntradaPontos;
import com.google.inject.Inject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Paulo
 * Date: 26/03/12
 * Time: 23:22
 * Objeto de leitura e gravação de uma EntradaPontos na tabela ENTRADA_PONTOS no SQLite.
 */
public class EntradaPontosDAO1 extends SQLiteOpenHelper implements EntradaPontosDAO {

    private static final String TABELA = "ENTRADA_PONTOS";
    private static final int VERSAO=2;
    private static final SimpleDateFormat iso8601Format = new SimpleDateFormat("yyyy-MM-dd");
    private String[] COLS = {"id", "nome", "data_insercao", "pontos", "quantidade"};

    @Inject
    public EntradaPontosDAO1(Context context) {
        super(context, TABELA, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // TODO - Ajustar depois. Mantido somente para debug.
        String ddl = "CREATE TABLE " + TABELA +
                "( id INTEGER PRIMARY KEY, " +
                "nome TEXT NOT NULL, " +
                "data_insercao TEXT NOT NULL, " +
                "pontos NUMBER, " +
                "quantidade NUMBER " +
                ");";
        sqLiteDatabase.execSQL(ddl);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int versaoAnterior, int versaoAtual) {
    }

    private void inserir(EntradaPontos entradaPontos){
        ContentValues values;
        long id =  entradaPontos.getId();
        if (id==-1){
            id = getMaxId()+1;
        }
        entradaPontos.setId(id);
        values = getContentValues(entradaPontos);
        getWritableDatabase().insert(TABELA, null, values);
    }

    @Override
    public long getMaxId(){
        long id =1;
        Cursor c = getWritableDatabase().query(TABELA, new String[]{"max(id)"},null, null, null,null,null);
        if (c.moveToNext()){
            id = c.getLong(0);
        }
        c.close();
        return id;
    }

    @Override
    public List<EntradaPontos> getLista(Date date){
        ArrayList<EntradaPontos> entradas = new ArrayList<EntradaPontos>();
        String dateFormatada = formateData(date);
        Cursor c = getWritableDatabase().query(TABELA, COLS,"substr(data_insercao,1,10)=?",new String[]{dateFormatada},null,null,null);
        while (c.moveToNext()){
            try {
                EntradaPontos entrada = getEntradaPontos(c);
                entradas.add(entrada);

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        c.close();
        return entradas;
    }

    private EntradaPontos getEntradaPontos(Cursor c) throws ParseException {
        EntradaPontos entrada = new EntradaPontos();
        entrada.setId(c.getLong(0));
        entrada.setNome(c.getString(1));
        entrada.setDataInsercao(new Date(parseData(c.getString(2)).getTime()));
        entrada.setPontos(c.getInt(3));
        entrada.setQuantidade(c.getInt(4));
        return entrada;
    }

    @Override
    public void removaEntrada(EntradaPontos entradaPontos){
        getWritableDatabase().delete(TABELA, "id=?", new String[]{entradaPontos.getId()+ ""});
    }

    private void editarEntrada(EntradaPontos entradaPontos){
        getWritableDatabase().update(TABELA,getContentValues(entradaPontos),"id=?", new String[]{entradaPontos.getId()+""});
    }

    private ContentValues getContentValues(EntradaPontos entradaPontos){
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", entradaPontos.getId());
        contentValues.put("nome", entradaPontos.getNome());
        contentValues.put("quantidade", entradaPontos.getQuantidade());
        contentValues.put("pontos", entradaPontos.getPontos());
        contentValues.put("data_insercao", formateData(entradaPontos.getDataInsercao()));
        return contentValues;
    }

    @Override
    public void insiraOuAtualize(EntradaPontos entradaPontos){
        if (entradaPontos.getId()>=0 && existeEntradaPontos(entradaPontos)){
            editarEntrada(entradaPontos);
        }   else {
            inserir(entradaPontos);
        }
    }

    private boolean existeEntradaPontos(EntradaPontos entradaPontos) {
        boolean resultado=false;
        Cursor c = getWritableDatabase().query(TABELA, COLS,"id = ?",new String[]{String.valueOf(entradaPontos.getId())},null,null,null);
        while (c.moveToNext()){
            resultado=true;
        }
        c.close();
        return resultado;
    }

    public static String formateData(Date data){
        return iso8601Format.format(data);
    }


    public ArrayList<EntradaPontos> pesquiseEntradasComNome(String nomeEntrada) {
        ArrayList<EntradaPontos> entradas = new ArrayList<EntradaPontos>();
        Cursor c = getWritableDatabase().query(TABELA, COLS,"nome = ?",new String[]{nomeEntrada},null,null,null);
        while (c.moveToNext()){
            try {
                EntradaPontos entrada = getEntradaPontos(c);
                entradas.add(entrada);

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        c.close();
        return entradas;

    }


    public static Date parseData(String data) throws ParseException {
        return iso8601Format.parse(data);
    }

    /**
     * Lista todas as entradas existentes.
     * @return  Arraylist contendo as entradas.
     */
    public ArrayList<EntradaPontos> pesquiseTodasEntradas() {
        ArrayList<EntradaPontos> entradas = new ArrayList<EntradaPontos>();
        Cursor c = getWritableDatabase().query(TABELA, COLS,null,null,null,null,null);
        while (c.moveToNext()){
            try {
                EntradaPontos entrada = getEntradaPontos(c);
                entradas.add(entrada);

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        c.close();
        return entradas;

    }

    public void removaTabela(){
        String ddl = "drop table " + TABELA + ";";
        getWritableDatabase().execSQL(ddl);
    }


}
