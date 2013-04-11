package br.com.pcontop.vigilantes.model;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created with IntelliJ IDEA.
 * User: Paulo
 * Date: 20/10/12
 * Time: 23:59
 * To change this template use File | Settings | File Templates.
 */
public class ModeloString implements MetodosString {
    private DiaSemanaReuniaoSQL diaSemanaReuniaoSQL;
    private EntradaPontosSQL entradaPontosSQL;
    private LimitePontosSQL limitePontosSQL;

    public ModeloString(DiaSemanaReuniaoSQL diaSemanaReuniaoSQL, EntradaPontosSQL entradaPontosSQL, LimitePontosSQL limitePontosSQL) {
        this.diaSemanaReuniaoSQL = diaSemanaReuniaoSQL;
        this.entradaPontosSQL = entradaPontosSQL;
        this.limitePontosSQL = limitePontosSQL;

    }

    @Override
    public String[] pesquiseNomesEntradasPontos() {
        ArrayList<EntradaPontos> entradas = pesquiseTodasEntradas();
        HashSet<String> setNomesEstradas = new HashSet<String>();
        String[] nomesEntradas = new String[1];
        for (EntradaPontos entrada:entradas){
           setNomesEstradas.add(entrada.getNome());
        }
        return setNomesEstradas.toArray(nomesEntradas);
    }

    @Override
    public ArrayList<EntradaPontos> pesquiseEntradasPontosPorNome(String filtroEntrada) {
        ArrayList<EntradaPontos> entradas = entradaPontosSQL.pesquiseEntradasComNome(filtroEntrada);
        return entradas;
    }

    public ArrayList<EntradaPontos> pesquiseTodasEntradas(){
        ArrayList<EntradaPontos> entradas = entradaPontosSQL.pesquiseTodasEntradas();
        return entradas;
    }

}
