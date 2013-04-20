package br.com.pcontop.vigilantes.model;

import br.com.pcontop.vigilantes.model.bean.DiaSemanaReuniao;
import br.com.pcontop.vigilantes.model.bean.EntradaPontos;
import br.com.pcontop.vigilantes.model.bean.LimitePontos;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * Métodos de acesso a base.
 * User: Paulo
 * Date: 14/04/13
 * Time: 11:20
 */
public interface MetodosDados {

    List<EntradaPontos> getEntradasPontosData(Date dia);

    void deletarEntrada(EntradaPontos entradaPontos);

    void insiraOuAtualizeLimitePontos(LimitePontos limitePontos) throws SemanaOcupadaException, ParseException;

    LimitePontos getLimitePontos(Date data) throws ParseException;

    DiaSemanaReuniao getDiaSemanaReuniao(Date data) throws ParseException;

    LimitePontos getUltimoLimitePontos() throws ParseException;

    void insiraOuAtualize(DiaSemanaReuniao diaSemanaReuniao) throws ParseException;

    LimitePontos getLimitePontosAnterior(Date data);

    void insiraOuAtualize(EntradaPontos entradaPontos);

    List<EntradaPontos> pesquiseEntradasPontosPorNome(String entrada);

    List<EntradaPontos> getTodasEntradas();

}
