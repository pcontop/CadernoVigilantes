package br.com.pcontop.vigilantes.model;

import android.database.sqlite.SQLiteDatabase;

import java.text.ParseException;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Paulo
 * Date: 10/05/12
 * Time: 22:10
 * Definição dos métodos de busca, alteração e remoção de DiaSemanaReuniao na base SQLite.
 */
public interface DiaSemanaReuniaoSQL {

    DiaSemanaReuniao getDiaSemanaValido(Date data) throws ParseException;

    void inserirOuAtualizar(DiaSemanaReuniao diaSemanaReuniao) throws ParseException;

    void apagueEntradasComValidadeApos(Date diaInicioValidade);

    void close();

}
