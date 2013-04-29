package br.com.pcontop.vigilantes.view;

import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import br.com.pcontop.vigilantes.R;
import br.com.pcontop.vigilantes.shared.bean.EntradaPontos;

/**
 * This is a simple framework for a test of an Application.  See
 * {@link android.test.ApplicationTestCase ApplicationTestCase} for more information on
 * how to write and extend Application tests.
 * <p/>
 * To run this test, you can type:
 * adb shell am instrument -w \
 * -e class br.com.pcontop.vigilantes.view.PaginaDiaTest \
 * br.com.pcontop.vigilantes.tests/android.test.InstrumentationTestRunner
 */
public class PaginaDiaTest extends ActivityInstrumentationTestCase2<PaginaDia> {
    EditText entrada;
    EditText quantidade;
    EditText pontos;
    ImageView botaoAdicionar;
    PaginaDia paginaDia;
    ListView listaCaderno;
    MenuItem menuItemExportar;

    public PaginaDiaTest() {
        super("br.com.pcontop.vigilantes", PaginaDia.class);
    }

    @Override
    public void setUp() throws Exception{
        super.setUp();
        paginaDia = this.getActivity();
        entrada = (EditText) paginaDia.findViewById(R.id.caderno_entrada);
        quantidade=(EditText) paginaDia.findViewById(R.id.caderno_quantidade);
        pontos=(EditText) paginaDia.findViewById(R.id.caderno_pontos);
        botaoAdicionar =(ImageView) paginaDia.findViewById(R.id.caderno_adicionar);
        listaCaderno = (ListView) paginaDia.findViewById(R.id.caderno_lista_dia);
        menuItemExportar = (MenuItem) paginaDia.findViewById(R.id.exportar_excel);

    }

    @UiThreadTest
    public void testAsserts(){
        assertNotNull(entrada);
        assertNotNull(quantidade);
        assertNotNull(pontos);
        assertNotNull(botaoAdicionar);
        assertNotNull(listaCaderno);
    }

    @UiThreadTest
    public void testInsert(){
        String valorEntrada = "Pizza";
        entrada.setText(valorEntrada);
        assertEquals(entrada.getText().toString(), valorEntrada);
        String valorQuantidade = "1";
        quantidade.setText(valorQuantidade);
        assertEquals(quantidade.getText().toString(), valorQuantidade);
        String valorPontos="20";
        pontos.setText(valorPontos);
        assertEquals(pontos.getText().toString(), valorPontos);
        int tamanhoAnterior = listaCaderno.getAdapter().getCount();
        botaoAdicionar.performClick();
        int tamanhoAtual = listaCaderno.getAdapter().getCount();
        assertEquals(tamanhoAnterior+1, tamanhoAtual);
        EntradaPontos entradaPontos = (EntradaPontos) listaCaderno.getAdapter().getItem(tamanhoAtual-1);
        assertEquals(entradaPontos.getNome(), valorEntrada);
        assertEquals(entradaPontos.getQuantidade(), Double.parseDouble(valorQuantidade));
        assertEquals(entradaPontos.getPontos(), Double.parseDouble(valorPontos));
    }

    /*@UiThreadTest
    public void testExportExcel(){
        paginaDia.openOptionsMenu();
        //menuItemExportar.

        assertNotNull(paginaDia.getControleCaderno().getUltimaPlanilhaCriada());
        assertTrue(paginaDia.getControleCaderno().getUltimaPlanilhaCriada().exists());


    }
    */



}
