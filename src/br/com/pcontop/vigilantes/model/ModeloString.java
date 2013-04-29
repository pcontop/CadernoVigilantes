package br.com.pcontop.vigilantes.model;

import br.com.pcontop.vigilantes.shared.bean.EntradaPontos;
import com.google.inject.Inject;

import java.util.HashSet;
import java.util.List;

/**
 * Implementação dos métodos de pesquisa por String.
 * User: Paulo
 * Date: 20/10/12
 * Time: 23:59
 */
public class ModeloString implements MetodosString {

    private MetodosDados metodosDados;

    @Inject
    public ModeloString(MetodosDados metodosDados) {
        this.metodosDados = metodosDados;
    }

    @Override
    public String[] pesquiseNomesEntradasPontos() {
        List<EntradaPontos> entradas = metodosDados.getTodasEntradas();
        HashSet<String> setNomesEstradas = new HashSet<String>();
        String[] nomesEntradas = new String[1];
        for (EntradaPontos entrada:entradas){
           setNomesEstradas.add(entrada.getNome());
        }
        return setNomesEstradas.toArray(nomesEntradas);
    }

}
