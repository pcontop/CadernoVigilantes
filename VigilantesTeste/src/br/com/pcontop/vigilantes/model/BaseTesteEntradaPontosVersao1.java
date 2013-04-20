package br.com.pcontop.vigilantes.model;

import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import br.com.pcontop.vigilantes.model.bean.EntradaPontos;
import br.com.pcontop.vigilantes.view.PaginaDia;

import java.util.List;

/**
 * Limpe base antes de executar teste. Teste de conformidade do DAO de Entrada Pontos, versão 1. Injete o DAO versão 1 no modulo.
 * User: Paulo
 * Date: 17/04/13
 * Time: 11:12
 * To change this template use File | Settings | File Templates.
 */
public class BaseTesteEntradaPontosVersao1 extends ActivityInstrumentationTestCase2<PaginaDia> {
    private BaseTesteEntradaPontos baseTesteEntradaPontos;

    public BaseTesteEntradaPontosVersao1() {
        super("br.com.pcontop.vigilantes", PaginaDia.class);
    }

    @Override
    public void setUp() throws Exception{
        super.setUp();
        baseTesteEntradaPontos = new BaseTesteEntradaPontos(getActivity());

    }

    @UiThreadTest
    public void testEntradaPontosRegressaoInsere(){
        baseTesteEntradaPontos.insiraEntradasBase();
        List<EntradaPontos> entradas = baseTesteEntradaPontos.controleCaderno.getTodasEntradasPontos();
        assertNotNull(entradas);
        assertEquals(entradas.size(), baseTesteEntradaPontos.criaEntradas.crieEntradasPontos().size());
    }

    @UiThreadTest
    public void testEntradaPontosRegressaoInsereOuAtualiza(){
        List<EntradaPontos> entradas = baseTesteEntradaPontos.controleCaderno.getTodasEntradasPontos();
        long quantidade = entradas.size();
        baseTesteEntradaPontos.insiraEntradasBase();
        long quantidade2 = baseTesteEntradaPontos.controleCaderno.getTodasEntradasPontos().size();
        assertEquals(quantidade, quantidade2);
    }

    @UiThreadTest
    public void testTiposCamposVersao(){
        List<EntradaPontos> entradasBase = baseTesteEntradaPontos.controleCaderno.getTodasEntradasPontos();
        for (EntradaPontos entradaBase:entradasBase){
            EntradaPontos entradaLista = baseTesteEntradaPontos.criaEntradas.getEntradaPontos(entradaBase);
            double quantidadeArredondada;
            double pontosArredondados;
            quantidadeArredondada = Math.floor(entradaLista.getQuantidade());
            pontosArredondados=Math.floor(entradaLista.getPontos());
            if (entradaLista.getQuantidade()!=entradaBase.getQuantidade()){
                if (quantidadeArredondada==entradaLista.getQuantidade()){
                    assertTrue(false);
                    return;
                }
            }
            if (entradaLista.getPontos()!=entradaBase.getPontos()){
                if (pontosArredondados==entradaLista.getPontos()){
                    assertTrue(false);
                    return;
                }
            }
        }
    }

}
