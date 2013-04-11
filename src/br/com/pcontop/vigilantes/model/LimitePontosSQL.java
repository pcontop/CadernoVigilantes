package br.com.pcontop.vigilantes.model;

import android.content.Context;

import java.text.ParseException;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Paulo
 * Date: 10/05/12
 * Time: 22:12
 * Define os métodos de acesso as classes de armazenamento de LimitePontos.
 */
public interface LimitePontosSQL {

    LimitePontos getLimitePontos(Date data) throws ParseException;

    LimitePontos getLimitePontos(LimitePontos limitePontos) throws ParseException;

    LimitePontos getUltimoLimitePontos() throws ParseException;

    void insiraOuAtualizePontos(LimitePontos limitePontos) throws SemanaOcupadaException, ParseException;

    void close();

    LimitePontos getLimitePontosAnterior(Date data) throws ParseException;
}
