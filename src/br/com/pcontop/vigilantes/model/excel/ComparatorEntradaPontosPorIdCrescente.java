package br.com.pcontop.vigilantes.model.excel;

import br.com.pcontop.vigilantes.shared.bean.EntradaPontos;

import java.util.Comparator;

/**
 * Created with IntelliJ IDEA.
 * User: Paulo
 * Date: 13/04/13
 * Time: 14:17
 * To change this template use File | Settings | File Templates.
 */
public class ComparatorEntradaPontosPorIdCrescente implements Comparator<EntradaPontos> {

    @Override
    public int compare(EntradaPontos entradaPontos, EntradaPontos entradaPontos2) {
        return ((Long)entradaPontos.getId()).compareTo(entradaPontos2.getId()) ;
    }
}
