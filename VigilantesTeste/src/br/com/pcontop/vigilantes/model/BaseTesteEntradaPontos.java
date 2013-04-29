package br.com.pcontop.vigilantes.model;

import android.content.Context;
import br.com.pcontop.vigilantes.control.ControleCaderno;
import br.com.pcontop.vigilantes.shared.bean.EntradaPontos;
import br.com.pcontop.vigilantes.view.PaginaDia;

import java.util.List;

public class BaseTesteEntradaPontos {
    PaginaDia paginaDia;
    ControleCaderno controleCaderno;
    Context context;
    CriaEntradas criaEntradas;
    MetodosDados metodosDados;

    public BaseTesteEntradaPontos(PaginaDia paginaDia) {
        this.paginaDia = paginaDia;
        inicializeObjetos();
    }

    private void inicializeObjetos() {
        controleCaderno = paginaDia.getControleCaderno();
        context = paginaDia.getBaseContext();
        metodosDados = controleCaderno.getMetodosDados();
        criaEntradas = new CriaEntradas();
    }

    void insiraEntradasBase() {
        List<EntradaPontos> entradas = criaEntradas.crieEntradasPontos();
        insiraEntradasBase(entradas);
    }

    void insiraEntradasBase(List<EntradaPontos> entradas) {
        for (EntradaPontos entradaPontos : entradas) {
            controleCaderno.insiraOuAtualizeEntradaPontos(entradaPontos);
        }
    }

    public boolean compare(EntradaPontos entradaCache, EntradaPontos entradaPontos) {
        if (entradaCache.getPontos() != entradaPontos.getPontos()) {
            return false;
        }
        if (entradaCache.getQuantidade() != entradaPontos.getQuantidade()) {
            return false;
        }
        if (entradaCache.getId() != entradaPontos.getId()) {
            return false;
        }
        if (!entradaCache.getDataInsercao().equals(entradaPontos.getDataInsercao())) {
            return false;
        }
        if (entradaCache.getPontosMultiplicados() != entradaPontos.getPontosMultiplicados()) {
            return false;
        }
        return true;
    }
}