package br.com.pcontop.vigilantes.model.dao;

import br.com.pcontop.vigilantes.shared.bean.DiaSemanaReuniao;

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

    void insiraOuAtualize(DiaSemanaReuniao diaSemanaReuniao) throws ParseException;

    void apagueEntradasComValidadeApos(Date diaInicioValidade);

    void close();

}
