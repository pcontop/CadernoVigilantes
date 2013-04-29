package br.com.pcontop.vigilantes.model.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import br.com.pcontop.vigilantes.control.ContextoGlobal;
import br.com.pcontop.vigilantes.model.SemanaOcupadaException;
import br.com.pcontop.vigilantes.shared.bean.LimitePontos;
import com.google.inject.Inject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Paulo
 * Date: 02/04/12
 * Time: 21:26
 * Métodos de acesso a tabela LIMITE_PONTOS do SQLLite, que possui as informações para a informação encapsulada no
 * objeto LimitePontos. Permite gravar, atualizar, ler e remover linhas da tabela.
 *
 */
public class LimitePontosDAO1 extends SQLiteOpenHelper implements LimitePontosDAO {
    
    private final static String TABELA = "LimitePontos";
    private final static int VERSAO=1;
    private final static String [] campos = {"dataInicio", "dataFim", "pontos_dia", "pontos_livres" };
    private static final SimpleDateFormat iso8601Format = new SimpleDateFormat("yyyy-MM-dd");

    @Inject
    public LimitePontosDAO1(Context context) {
        super(context, TABELA, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String ddl = "CREATE TABLE " + TABELA +
                "( dataInicio String , " +
                "dataFim String, " +
                "pontos_dia INTEGER NOT NULL, " +
                "pontos_livres INTEGER, " +
                "PRIMARY KEY (dataInicio)" +
                ");";
        sqLiteDatabase.execSQL(ddl);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //Faz nada.
    }
    
    private ContentValues getContentValues(LimitePontos limitePontos){
        ContentValues contentValues = new ContentValues();
        contentValues.put(campos[0], formateData(limitePontos.getDataInicio()));
        contentValues.put(campos[1], formateData(limitePontos.getDataFim()));
        contentValues.put(campos[2], limitePontos.getPontosDia());
        contentValues.put(campos[3], limitePontos.getPontosLivreSemana());
        return contentValues;
    }
    
    @Override
    public LimitePontos getLimitePontos(Date data) throws ParseException {
        if (ContextoGlobal.debugMode){
            listaLimitesPontos();
        }
        LimitePontos limitePontos = null;
        if (data==null){
            return null;
        }
        Cursor c = getReadableDatabase().query(
                TABELA,
                campos,
                "dataInicio<=? and dataFim>=?",
                new String[]{formateData(data),formateData(data)},
                null, null, null);
        if (c.moveToNext()) {
            limitePontos = getLimitePontos(c);
        }
        c.close();
        return limitePontos;
    }

    private LimitePontos getLimitePontos(Cursor c) throws ParseException {
        LimitePontos limitePontos = new LimitePontos();
        limitePontos.setDataInicio(parseData(c.getString(0)));
        limitePontos.setDataFim(parseData(c.getString(1)));
        limitePontos.setPontosDia(c.getInt(2));
        limitePontos.setPontosLivreSemana(c.getInt(3));
        return limitePontos;
    }

    public LimitePontos getUltimoLimitePontos() throws ParseException {
        LimitePontos limitePontos = null;
        Cursor c = getReadableDatabase().query(
                TABELA,
                campos,
                null,
                null,
                null, null, "dataInicio desc");
        if (c.moveToNext()) {
            limitePontos = getLimitePontos(c);
        }
        c.close();
        return limitePontos;
    }

    public void listaLimitesPontos() throws ParseException {
        LimitePontos limitePontos;
        Cursor c = getReadableDatabase().query(
                TABELA,
                campos,
                null,
                null,
                null, null, null);
        int contador=0;
        while (c.moveToNext()) {
            limitePontos = getLimitePontos(c);
            System.out.println("LimitePontosDAO1.listaLimitesPontos() - " + contador + ": " + limitePontos);
            contador++;
        }
        c.close();

    }

    @Override
    public LimitePontos getLimitePontos(LimitePontos limitePontos) throws ParseException {
        return getLimitePontos(limitePontos.getDataInicio()) ;
    }
    
    @Override
    public void insiraOuAtualizePontos(LimitePontos limitePontos) throws SemanaOcupadaException, ParseException {
        if (getLimitePontos(limitePontos)!=null){
            atualizePontos(limitePontos);
        } else {
           insiraPontos(limitePontos); 
        }
    }

    @Override
    public LimitePontos getLimitePontosAnterior(Date data) throws ParseException {
        LimitePontos limitePontos = null;
        if (data==null){
            return null;
        }
        Cursor c = getReadableDatabase().query(
                TABELA,
                campos,
                "dataInicio<?",
                new String[]{formateData(data)},
                null, null, "dataInicio desc");
        if (c.moveToNext()) {
            limitePontos = getLimitePontos(c);
        }
        c.close();
        return limitePontos;

    }

    private void insiraPontos(LimitePontos limitePontos) throws SemanaOcupadaException, ParseException {
        if (getLimitePontos(limitePontos)!=null){
            throw new SemanaOcupadaException();
        }
        getWritableDatabase().insert(TABELA,null, getContentValues(limitePontos));
    }

    private void atualizePontos(LimitePontos limitePontos) {
        getWritableDatabase().update(
                TABELA,
                getContentValues(limitePontos),
                "dataInicio=?",
                new String[]{formateData(limitePontos.getDataInicio())});
        if (ContextoGlobal.debugMode){
            try {
                listaLimitesPontos();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    public static String formateData(Date data){
        return iso8601Format.format(data);
    }

    public static Date parseData(String data) throws ParseException {
        return iso8601Format.parse(data);
    }

}
