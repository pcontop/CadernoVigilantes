package br.com.pcontop.vigilantes.model;

import br.com.pcontop.vigilantes.model.bean.DiaSemanaReuniao;
import br.com.pcontop.vigilantes.model.bean.EntradaPontos;
import br.com.pcontop.vigilantes.model.bean.LimitePontos;
import br.com.pcontop.vigilantes.model.dao.DiaSemanaReuniaoSQL;
import br.com.pcontop.vigilantes.model.dao.EntradaPontosSQL;
import br.com.pcontop.vigilantes.model.dao.LimitePontosSQL;
import com.google.inject.Inject;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * Facade para os métodos de acesso a dados dos DAOs.
 */
public class ModeloDados implements MetodosDados {
    
    private LimitePontosSQL limitePontosSQL;
    private EntradaPontosSQL entradaPontosSQL;
    private DiaSemanaReuniaoSQL diaSemanaReuniaoSQL;
    
    @Inject
    public ModeloDados(LimitePontosSQL limitePontosSQL
                       , EntradaPontosSQL entradaPontosSQL
                       , DiaSemanaReuniaoSQL diaSemanaReuniaoSQL){
        this.limitePontosSQL = limitePontosSQL;
        this.entradaPontosSQL = entradaPontosSQL;
        this.diaSemanaReuniaoSQL = diaSemanaReuniaoSQL;
    }

    public void deletarEntrada(EntradaPontos entradaPontos) {
        entradaPontosSQL.removaEntrada(entradaPontos);
        entradaPontosSQL.close();
    }

    public void insiraOuAtualize(EntradaPontos entradaPontos) {
        entradaPontosSQL.insiraOuAtualize(entradaPontos);
        entradaPontosSQL.close();
    }

    public void insiraOuAtualizeLimitePontos(LimitePontos limitePontos) throws SemanaOcupadaException, ParseException {
        limitePontosSQL.insiraOuAtualizePontos(limitePontos);
        //Log.v("ControleCaderno", "Adicionando limite:" + limitePontos);
    }

    public DiaSemanaReuniao getDiaSemanaReuniao(Date data) throws ParseException {
        DiaSemanaReuniao diaSemana = diaSemanaReuniaoSQL.getDiaSemanaValido(data);
        diaSemanaReuniaoSQL.close();
        return diaSemana;
    }

    public void insiraOuAtualize(DiaSemanaReuniao diaSemanaReuniao) throws ParseException {
        diaSemanaReuniaoSQL.insiraOuAtualize(diaSemanaReuniao);
    }

    public List<EntradaPontos> getEntradasPontosData(Date data){
        List<EntradaPontos> entradasPontos = entradaPontosSQL.getLista(data);
        entradaPontosSQL.close();
        return entradasPontos;
    }

    public LimitePontos getUltimoLimitePontos() throws ParseException {
        return limitePontosSQL.getUltimoLimitePontos();
    }

    public LimitePontos getLimitePontos(Date data) throws ParseException {
        LimitePontos limitePontos;
        limitePontos = limitePontosSQL.getLimitePontos(data);
        return limitePontos;
    }

    public LimitePontos getLimitePontosAnterior(Date data) {
        LimitePontos limitePontos=null;
        try {
            limitePontos = limitePontosSQL.getLimitePontosAnterior(data);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return limitePontos;
    }

    public List<EntradaPontos> pesquiseEntradasPontosPorNome(String filtroEntrada) {
        return entradaPontosSQL.pesquiseEntradasComNome(filtroEntrada);
    }

    public List<EntradaPontos> getTodasEntradas(){
        return entradaPontosSQL.pesquiseTodasEntradas();
    }

    public void removaTabelaEntradaPontos(){
        entradaPontosSQL.removaTabela();
    }


}