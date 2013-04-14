package br.com.pcontop.vigilantes.model;

import br.com.pcontop.vigilantes.model.bean.EntradaPontos;
import com.google.inject.Inject;

import java.util.HashSet;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Paulo
 * Date: 20/10/12
 * Time: 23:59
 * To change this template use File | Settings | File Templates.
 */
public class ModeloString implements MetodosString {
    @Inject
    private MetodosDados metodosDados;

    @Inject
    public ModeloString() {
    }

    @Override
    public String[] pesquiseNomesEntradasPontos() {
        List<EntradaPontos> entradas = metodosDados.pesquiseTodasEntradas();
        HashSet<String> setNomesEstradas = new HashSet<String>();
        String[] nomesEntradas = new String[1];
        for (EntradaPontos entrada:entradas){
           setNomesEstradas.add(entrada.getNome());
        }
        return setNomesEstradas.toArray(nomesEntradas);
    }

}
