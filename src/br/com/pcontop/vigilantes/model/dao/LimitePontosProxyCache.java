package br.com.pcontop.vigilantes.model.dao;

import br.com.pcontop.vigilantes.model.SemanaOcupadaException;
import br.com.pcontop.vigilantes.model.bean.LimitePontos;
import com.google.inject.Inject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: Paulo
 * Date: 10/05/12
 * Time: 22:13
 * Classe de cache para os limites de pontos. 100 limites são guardados, sendo que limites adicionados depois disto irão
 * ocupar o lugar de limites mais antigos.
 */
public class LimitePontosProxyCache implements LimitePontosSQL {

    private ArrayList<String> ordemEntrada = new ArrayList<String>();
    private HashMap<String, LimitePontos> cacheLimites = new HashMap<String, LimitePontos>();
    private final static int tamanhoCache=100;
    private LimitePontosDAO limitePontosDAO;

    @Inject
    public LimitePontosProxyCache(LimitePontosDAO limitePontosDAO){
        this.limitePontosDAO = limitePontosDAO;
    }

    private void adicioneCache(LimitePontos limitePontos) {
        if (limitePontos!=null && !estaNoCache(limitePontos)){
            String dataChave = getDataChave(limitePontos.getDataInicio());
            if (ordemEntrada.size()>tamanhoCache){
                String chave = ordemEntrada.get(0);
                cacheLimites.remove(chave);
                ordemEntrada.remove(chave);
            }
            cacheLimites.put(dataChave, limitePontos);
            ordemEntrada.add(dataChave);
        }
    }

    private boolean estaNoCache(LimitePontos limitePontos) {
        return cacheLimites.values().contains(limitePontos);
    }

    private LimitePontos getLimitePontosCache(LimitePontos limitePontos){
        String dataChave = null;
        if (limitePontos!=null){
            dataChave = getDataChave(limitePontos.getDataInicio());
        }
        return cacheLimites.get(dataChave);
    }

    private LimitePontos getLimitePontosCache(Date data){
        return cacheLimites.get(getDataChave(data));
    }


    @Override
    public LimitePontos getLimitePontos(Date data) throws ParseException {
        LimitePontos limitePontos = getLimitePontosCache(data);
        if (limitePontos!=null){
            return limitePontos;
        }
        limitePontos = limitePontosDAO.getLimitePontos(data);
        adicioneCache(limitePontos);
        return limitePontos;
    }

    private String getDataChave(Date data) {
        if (data!=null){
            return LimitePontosDAO1.formateData(data);
        } else {
            return null;
        }
    }

    @Override
    public LimitePontos getLimitePontos(LimitePontos limitePontos) throws ParseException {
        LimitePontos limitePontosRetorno = getLimitePontosCache(limitePontos);
        if (limitePontosRetorno!=null){
            return limitePontosRetorno;
        }
        limitePontosRetorno = limitePontosDAO.getLimitePontos(limitePontos);
        adicioneCache(limitePontosRetorno);
        return limitePontosRetorno;
    }

    @Override
    public LimitePontos getUltimoLimitePontos() throws ParseException {
        return limitePontosDAO.getUltimoLimitePontos();
    }

    @Override
    public void insiraOuAtualizePontos(LimitePontos limitePontos) throws SemanaOcupadaException, ParseException {
        adicioneCache(limitePontos);
        limitePontosDAO.insiraOuAtualizePontos(limitePontos);
    }

    @Override
    public void close() {
        //Nada.
    }

    @Override
    public LimitePontos getLimitePontosAnterior(Date data) throws ParseException {
        return limitePontosDAO.getLimitePontosAnterior(data);
    }

}
