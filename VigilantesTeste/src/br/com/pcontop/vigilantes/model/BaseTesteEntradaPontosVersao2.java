package br.com.pcontop.vigilantes.model;

import android.content.Context;
import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import br.com.pcontop.vigilantes.control.ControleCaderno;
import br.com.pcontop.vigilantes.model.bean.EntradaPontos;
import br.com.pcontop.vigilantes.view.PaginaDia;

import java.util.List;

/**
 * Teste de conformidade do DAO de Entrada Pontos, versão 2. Injete o DAO versão 2 no modulo.
 * User: Paulo
 * Date: 17/04/13
 * Time: 11:12
 */
public class BaseTesteEntradaPontosVersao2 extends ActivityInstrumentationTestCase2<PaginaDia> {
    PaginaDia paginaDia;
    ControleCaderno controleCaderno;
    Context context;
    CriaEntradasTeste criaEntradasTeste;
    MetodosDados metodosDados;

    public BaseTesteEntradaPontosVersao2() {
        super("br.com.pcontop.vigilantes", PaginaDia.class);
    }

    @Override
    public void setUp() throws Exception{
        super.setUp();
        inicializeObjetos();

    }

    private void inicializeObjetos() {
        paginaDia = getActivity();
        controleCaderno = paginaDia.getControleCaderno();
        context = paginaDia.getBaseContext();
        metodosDados = controleCaderno.getMetodosDados();
        criaEntradasTeste = new CriaEntradasTeste();
    }

    private void insiraEntradasBase(){
        List<EntradaPontos> entradas = criaEntradasTeste.crieEntradasPontos();
        insiraEntradasBase(entradas);
    }

    private void insiraEntradasBase(List<EntradaPontos> entradas){
        for (EntradaPontos entradaPontos:entradas){
            controleCaderno.insiraOuAtualizeEntradaPontos(entradaPontos);
        }
    }

    /**
     * Pode ser usado para tanto v1->v2, quanto 0->v2.
     */
    @UiThreadTest
    public void testEntradaPontosRegressaoInsere(){
        //entradas = metodosDados.pesquiseTodasEntradas();
        insiraEntradasBase();
        List<EntradaPontos> entradas = controleCaderno.getTodasEntradasPontos();
        assertNotNull(entradas);
        assertEquals(entradas.size(), criaEntradasTeste.crieEntradasPontos().size());
    }

    /**
     * Deve manter a mesma quantidade de registros, preservando os Ids.
     */
    @UiThreadTest
    public void testEntradaPontosRegressaoInsereOuAtualiza(){
        List<EntradaPontos> entradas = controleCaderno.getTodasEntradasPontos();
        long quantidade = entradas.size();
        insiraEntradasBase();
        long quantidade2 = controleCaderno.getTodasEntradasPontos().size();
        assertEquals(quantidade, quantidade2);
    }

    /**
     * Verifica se os dados foram mantidos, com o arrendodamento de quantidade.
     */
    @UiThreadTest
    public void testTiposCamposVersao(){
       List<EntradaPontos> entradasBase = controleCaderno.getTodasEntradasPontos();
       for (EntradaPontos entradaBase:entradasBase){
           EntradaPontos entradaLista = criaEntradasTeste.getEntradaPontos(entradaBase);
           double quantidadeArredondada;
           //double pontosArredondados;
           quantidadeArredondada = Math.floor(entradaLista.getQuantidade());
           //pontosArredondados=Math.floor(entradaLista.getPontos());
           if (entradaLista.getQuantidade()!=entradaBase.getQuantidade()){
                if (quantidadeArredondada==entradaLista.getQuantidade()){
                    assertTrue(false);
                    return;
                }
           }
           if (entradaLista.getPontos()!=entradaBase.getPontos()){
               assertTrue(false);
               return;
           }
       }
    }




}