package br.com.pcontop.vigilantes.view;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import br.com.pcontop.vigilantes.R;
import br.com.pcontop.vigilantes.model.EntradaPontos;

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
    ImageButton botaoAdicionar;
    Activity mActivity;
    ListView listaCaderno;

    public PaginaDiaTest() {
        super("br.com.pcontop.vigilantes", PaginaDia.class);
    }

    @Override
    public void setUp() throws Exception{
        super.setUp();
        mActivity = this.getActivity();
        entrada = (EditText) mActivity.findViewById(R.id.caderno_entrada);
        quantidade=(EditText)mActivity.findViewById(R.id.caderno_quantidade);
        pontos=(EditText)mActivity.findViewById(R.id.caderno_pontos);
        botaoAdicionar =(ImageButton)mActivity.findViewById(R.id.caderno_adicionar);
        listaCaderno = (ListView) mActivity.findViewById(R.id.caderno_lista_dia);
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
        assertEquals(entradaPontos.getQuantidade(), Integer.parseInt(valorQuantidade));
        assertEquals(entradaPontos.getPontos(), Integer.parseInt(valorPontos));
    }

}
