package br.com.pcontop.vigilantes.model;

import android.content.Context;
import br.com.pcontop.vigilantes.model.DiaSemanaReuniao;
import br.com.pcontop.vigilantes.model.EntradaPontos;
import br.com.pcontop.vigilantes.model.LimitePontos;
import br.com.pcontop.vigilantes.model.SemanaOcupadaException;

import java.text.ParseException;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Paulo
 * Date: 12/05/12
 * Time: 20:00
 * Define os métodos necessários para se lidar com os dados de pontuação por data. Retorna objetos de EntradaPontos, LimitePontos, DiaSemanaReuniao.
 */
public interface MetodosData {

    Date getDataAtual();

    boolean isDataAtual(Date data);

    List<EntradaPontos> getEntradasPontosData(Date dia);

    void deletarEntrada(EntradaPontos entradaPontos);

    void definaLimitePontos(LimitePontos limitePontos) throws SemanaOcupadaException, ParseException;

    void atualizeEntradaPontos(EntradaPontos entradaPontos);

    boolean isDataEmPeriodoLimiteAlteravel(Date data);

    LimitePontos getLimitePontos(Date data) throws ParseException;

    DiaSemanaReuniao getDiaSemanaReuniao(Date data) throws ParseException;

    void definaDiaSemanaReuniao(int diaSemana, Date data) throws ParseException;

    long getPontosExtras(Date data);

    GregorianCalendar getCalendar(Date date);

    double getPontosDia(Date data);

    boolean isInicioLimite(Date data);

    boolean isFinalLimite(Date data);

    int getDiaSemanaReuniaoMes(Date data);

    boolean isDataProximaReuniao(Date data);

    Date adicioneDiasAData(Date data, int dias);

    long getPontosMaximosDiaContandoExtras(Date data);

    LimitePontos getNovoLimitePontos(Date data) throws ParseException;

}
