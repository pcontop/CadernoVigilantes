package br.com.pcontop.vigilantes.model;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Paulo
 * Date: 20/10/12
 * Time: 23:56
 * To change this template use File | Settings | File Templates.
 */
public interface MetodosString {

    String[] pesquiseNomesEntradasPontos();
    ArrayList<EntradaPontos> pesquiseEntradasPontosPorNome(String entrada);
}
