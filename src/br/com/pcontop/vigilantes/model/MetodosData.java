package br.com.pcontop.vigilantes.model;

import br.com.pcontop.vigilantes.model.bean.LimitePontos;

import java.text.ParseException;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created with IntelliJ IDEA.
 * User: Paulo
 * Date: 12/05/12
 * Time: 20:00
 * Define os métodos necessários para se lidar com os dados de pontuação por data. Retorna objetos de EntradaPontos, LimitePontos, DiaSemanaReuniao.
 */
public interface  MetodosData {

    Date getDataAtual();

    boolean isDataAtual(Date data);

    boolean isDataEmPeriodoLimiteAlteravel(Date data);

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
