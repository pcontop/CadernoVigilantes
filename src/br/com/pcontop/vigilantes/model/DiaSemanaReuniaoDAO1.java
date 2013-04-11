package br.com.pcontop.vigilantes.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import br.com.pcontop.vigilantes.control.ContextoGlobal;
import com.google.inject.Inject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Paulo
 * Date: 25/04/12
 * Time: 22:38
 * Objeto que acessa os dados de DiaSemanaReuniao na tabela DIA_SEMANA da base SQLite.
 */
public class DiaSemanaReuniaoDAO1 extends SQLiteOpenHelper implements DiaSemanaReuniaoDAO {
    private static final String TABELA = "DIA_SEMANA";
    private static final int VERSAO=1;
    private static final SimpleDateFormat iso8601Format = new SimpleDateFormat("yyyy-MM-dd");
    private String[] COLS = {"inicioValidade", "diaSemana"};

    @Inject
    public DiaSemanaReuniaoDAO1(Context context) {
        super(context, TABELA, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String ddl = "CREATE TABLE " + TABELA +
                "( inicioValidade TEXT PRIMARY KEY, " +
                "diaSemana INTEGER NOT NULL" +
                ");";
        sqLiteDatabase.execSQL(ddl);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //Para depois.
    }

    public DiaSemanaReuniao getDiaSemana() throws ParseException {
        DiaSemanaReuniao diaSemana = null;
        Cursor c = getWritableDatabase().query(TABELA,COLS,null,null,null,null,"inicioValidade desc");
        if (c.moveToNext()){
            diaSemana = getDiaSemana(c);
        }
        return diaSemana;
    }

    @Override
    public DiaSemanaReuniao getDiaSemanaValido(Date data) throws ParseException {
        DiaSemanaReuniao diaSemana = null;
        if (ContextoGlobal.debugMode){
            listaTabela();
        }
        String dataFormatada = formateData(data);
        Cursor c = getWritableDatabase().query(TABELA,COLS,"inicioValidade <= ?",new String[]{dataFormatada},null,null,"inicioValidade desc");
        if (c.moveToNext()){
            diaSemana = getDiaSemana(c);
        }
        return diaSemana;
    }

    private DiaSemanaReuniao getDiaSemanaIgual(Date data) throws ParseException {
        DiaSemanaReuniao diaSemana = null;
        String dataFormatada = formateData(data);
        Cursor c = getWritableDatabase().query(TABELA,COLS,"inicioValidade = ?",new String[]{dataFormatada},null,null,"inicioValidade desc");
        if (c.moveToNext()){
            diaSemana = getDiaSemana(c);
        }
        return diaSemana;
    }

    private void listaTabela() throws ParseException {
        DiaSemanaReuniao diaSemana = null;
        Cursor c = getWritableDatabase().query(TABELA,COLS,null,null,null,null,"inicioValidade");
        int contador=0;
        while (c.moveToNext()){
            diaSemana = getDiaSemana(c);
            System.out.println("DiaSemanaReuniaoDAO1.ListaTabela() - " + contador + ": " + diaSemana);
            contador++;
        }
    }

    private void inserir(DiaSemanaReuniao diaSemana){
        ContentValues values = getContentValues(diaSemana);
        getWritableDatabase().insert(TABELA, null, values);
    }

    private void atualizar(DiaSemanaReuniao diaSemanaReuniao){
        String dataValidadeFormatada = formateData(diaSemanaReuniao.getInicioValidade());
        getWritableDatabase().update(TABELA,getContentValues(diaSemanaReuniao),"inicioValidade=?", new String[]{dataValidadeFormatada});
    }


    @Override
    public void inserirOuAtualizar(DiaSemanaReuniao diaSemanaReuniao) throws ParseException {
        DiaSemanaReuniao diaSemanaReuniaoExistente = getDiaSemanaIgual(diaSemanaReuniao.getInicioValidade());
        if (diaSemanaReuniaoExistente!=null){
            atualizar(diaSemanaReuniao);
        } else {
            inserir(diaSemanaReuniao);
        }
    }

    private ContentValues getContentValues(DiaSemanaReuniao diaSemana) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("inicioValidade", formateData(diaSemana.getInicioValidade()));
        contentValues.put("diaSemana", diaSemana.getDiaSemana());
        return contentValues;
    }

    private DiaSemanaReuniao getDiaSemana(Cursor c) throws ParseException {
        DiaSemanaReuniao diaSemana = new DiaSemanaReuniao();
        diaSemana.setInicioValidade(parseData(c.getString(0)));
        diaSemana.setDiaSemana(c.getInt(1));
        return diaSemana;
    }

    @Override
    public void apagueEntradasComValidadeApos(Date diaInicioValidade) {
        String dataValidadeFormatada = formateData(diaInicioValidade);
        getWritableDatabase().delete(TABELA,"inicioValidade>?", new String[]{dataValidadeFormatada});
    }

    public static String formateData(Date data){
        return iso8601Format.format(data);
    }

    public static Date parseData(String data) throws ParseException {
        return iso8601Format.parse(data);
    }
}
