package br.com.pcontop.vigilantes.model.dao;

import br.com.pcontop.vigilantes.shared.bean.EntradaPontos;
import com.google.inject.Inject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Paulo
 * Date: 10/05/12
 * Time: 22:45
 * Proxy que realiza um cache de EntradaPontos. Até 100 registros consultados e alterados são mantidos em memória no objeto,
 * e após este limite, objetos mais recentes substituem objetos mais antigos.
 */
public class EntradaPontosProxyCache implements EntradaPontosSQL {

    private ArrayList<String> ordemEntrada = new ArrayList<String>();
    private HashMap<String, List<EntradaPontos>> cacheEntradas = new HashMap<String, List<EntradaPontos>>();
    private final static int tamanhoCache=100;
    private EntradaPontosDAO entradaPontosDAO;

    @Inject
    public EntradaPontosProxyCache(EntradaPontosDAO entradaPontosDAO){
        this.entradaPontosDAO = entradaPontosDAO;
    }

    private void adicioneCache(EntradaPontos entradaPontos) {
        if (entradaPontos!=null && !estaNoCache(entradaPontos)){
            Date data = entradaPontos.getDataInsercao();
            String dataChave = formateData(data);

            if (ordemEntrada.size()>tamanhoCache){
                String chave = ordemEntrada.get(0);
                cacheEntradas.remove(chave);
                ordemEntrada.remove(chave);
            }
            List<EntradaPontos> listaEntradaPontos = getListaCache(data);
            if (listaEntradaPontos==null){
                listaEntradaPontos = new ArrayList<EntradaPontos>();
                cacheEntradas.put(dataChave, listaEntradaPontos);
            }
            removaEntradaAnteriorSeExistir(listaEntradaPontos, entradaPontos);
            listaEntradaPontos.add(entradaPontos);
            if (!ordemEntrada.contains(dataChave)){
                ordemEntrada.add(dataChave);
            }
        }
    }

    private void removaEntradaAnteriorSeExistir(List<EntradaPontos> listaEntradaPontos, EntradaPontos entradaPontos) {
        for (EntradaPontos entradaPontosLista:listaEntradaPontos){
            if (entradaPontos.getId()==entradaPontosLista.getId()){
                listaEntradaPontos.remove(entradaPontosLista);
                return;
            }
        }
    }

    private String formateData(Date data) {
        return EntradaPontosDAO2.formateData(data);
    }

    private List<EntradaPontos> getListaCache(Date data) {
        String dataChave = formateData(data);
        return cacheEntradas.get(dataChave);
    }

    private boolean estaNoCache(EntradaPontos entradaPontos){
        List<EntradaPontos> list = getListaCache(entradaPontos.getDataInsercao());
        return list != null && list.contains(entradaPontos);
    }

    /*
    private EntradaPontos getEntradaPontosCache(Long id){
        for (List<EntradaPontos> list: cacheEntradas.values()){
            for (EntradaPontos entradaPontos:list){
                if (entradaPontos.getId()==id){
                    return entradaPontos;
                }
            }
        }
        return null;
    }
    */

    @Override
    public long getMaxId() {
        return entradaPontosDAO.getMaxId();
    }

    @Override
    public List<EntradaPontos> getLista(Date date) {
        List<EntradaPontos> lista = getListaCache(date);
        if (lista==null){
            lista = entradaPontosDAO.getLista(date);
        }
        adicioneCache(date, lista);
        return lista;
    }

    private void adicioneCache(Date date, List<EntradaPontos> lista) {
        String dataChave = formateData(date);
        cacheEntradas.put(dataChave, lista);
    }

    @Override
    public void removaEntrada(EntradaPontos entradaPontos) {
        removaCache(entradaPontos);
        entradaPontosDAO.removaEntrada(entradaPontos);
    }

    private void removaCache(EntradaPontos entradaPontos) {
        Date data = entradaPontos.getDataInsercao();
        List<EntradaPontos> list = getListaCache(data);
        if (list!=null){
            list.remove(entradaPontos);
        }
    }

    @Override
    public void insiraOuAtualize(EntradaPontos entradaPontos) {
        adicioneCache(entradaPontos);
        entradaPontosDAO.insiraOuAtualize(entradaPontos);
    }

    @Override
    public ArrayList<EntradaPontos> pesquiseEntradasComNome(String nomeEntrada) {
        return entradaPontosDAO.pesquiseEntradasComNome(nomeEntrada);
    }

    @Override
    public ArrayList<EntradaPontos> pesquiseTodasEntradas() {
        return entradaPontosDAO.pesquiseTodasEntradas();
    }


    @Override
    public void close() {
        //Nada
    }

    public void removaTabela(){
        entradaPontosDAO.removaTabela();
        cacheEntradas.clear();
    }
}
