package br.com.pcontop.vigilantes.model;

import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import br.com.pcontop.vigilantes.shared.bean.EntradaPontos;
import br.com.pcontop.vigilantes.view.PaginaDia;

import java.util.Date;
import java.util.List;

/**
 * Teste de conformidade do DAO de Entrada Pontos, versão 3. Injete o DAO versão 3 no modulo.
 * User: Paulo
 * Date: 17/04/13
 * Time: 11:12
 */
public class BaseTesteEntradaPontosVersao3 extends ActivityInstrumentationTestCase2<PaginaDia> {
    private BaseTesteEntradaPontos baseTesteEntradaPontos;

    public BaseTesteEntradaPontosVersao3() {
        super("br.com.pcontop.vigilantes", PaginaDia.class);
    }

    @Override
    public void setUp() throws Exception{
        super.setUp();
        baseTesteEntradaPontos = new BaseTesteEntradaPontos(getActivity());

    }

    /**
     * Pode ser usado para tanto v2->v3, quanto v1->v3, quanto 0->v3.
     */
    @UiThreadTest
    public void test01EntradaPontosRegressaoInsere(){
        baseTesteEntradaPontos.insiraEntradasBase();
        List<EntradaPontos> entradas = baseTesteEntradaPontos.controleCaderno.getTodasEntradasPontos();
        assertNotNull(entradas);
        assertEquals(entradas.size(), baseTesteEntradaPontos.criaEntradas.crieEntradasPontos().size());
    }

    /**
     * Deve manter a mesma quantidade de registros, preservando os Ids.
     */
    @UiThreadTest
    public void test02EntradaPontosRegressaoInsereOuAtualiza(){
        List<EntradaPontos> entradas = baseTesteEntradaPontos.controleCaderno.getTodasEntradasPontos();
        long quantidade = entradas.size();
        baseTesteEntradaPontos.insiraEntradasBase();
        long quantidade2 = baseTesteEntradaPontos.controleCaderno.getTodasEntradasPontos().size();
        assertEquals(quantidade, quantidade2);
    }

    /**
     * Verifica se os dados foram mantidos, com o arrendodamento de quantidade.
     */
    @UiThreadTest
    public void test03TiposCamposVersao(){
        List<EntradaPontos> entradasBase = baseTesteEntradaPontos.controleCaderno.getTodasEntradasPontos();
        for (EntradaPontos entradaBase:entradasBase){
            EntradaPontos entradaLista = baseTesteEntradaPontos.criaEntradas.getEntradaPontos(entradaBase);
            if (entradaLista.getQuantidade()!=entradaBase.getQuantidade()){
                assertTrue(false);
                return;
            }
            if (entradaLista.getPontos()!=entradaBase.getPontos()){
                assertTrue(false);
                return;
            }
        }
        assertTrue(true);
    }

    @UiThreadTest
    public void test04CachePersiste(){
        EntradaPontos entradaPontos = baseTesteEntradaPontos.criaEntradas.crieEntradaPontosTardia();
        baseTesteEntradaPontos.metodosDados.insiraOuAtualize(entradaPontos);
        List<EntradaPontos> entradasCache = baseTesteEntradaPontos.metodosDados.getEntradasPontosData(new Date());
        EntradaPontos entradaCache = baseTesteEntradaPontos.criaEntradas.getEntradaPontos(entradaPontos, entradasCache);
        assertTrue(baseTesteEntradaPontos.compare(entradaCache, entradaPontos));
    }

}
