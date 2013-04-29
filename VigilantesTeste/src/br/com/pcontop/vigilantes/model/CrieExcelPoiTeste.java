package br.com.pcontop.vigilantes.model;

import android.os.Environment;
import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.util.Log;
import br.com.pcontop.vigilantes.control.ControleCaderno;
import br.com.pcontop.vigilantes.shared.bean.EntradaPontos;
import br.com.pcontop.vigilantes.model.excel.CrieExcelPoiXls1;
import br.com.pcontop.vigilantes.model.excel.DiretoriosArquivosAndroid;
import br.com.pcontop.vigilantes.model.excel.ImpossivelCriarDiretorioException;
import br.com.pcontop.vigilantes.view.PaginaDia;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Paulo
 * Date: 12/04/13
 * Time: 01:32
 */
public class CrieExcelPoiTeste extends ActivityInstrumentationTestCase2<PaginaDia> {
    String nomeArquivo = "CadernoVigilantes.xls";
    CrieExcelPoiXls1 crieExcel;
    ControleCaderno controleCaderno;
    MetodosDados metodosDados;
    List<EntradaPontos> entradas;
    CriaEntradas criaEntradas;

    public CrieExcelPoiTeste() {
        super("br.com.pcontop.vigilantes", PaginaDia.class);
    }

    @Override
    public void setUp() throws Exception{
        super.setUp();
        inicializeObjetos();
        insiraEntradasBase();
    }

    private void insiraEntradasBase() {
        for (EntradaPontos entradaPontos: entradas) {
            metodosDados.insiraOuAtualize(entradaPontos);
        }
    }

    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

    public void inicializeObjetos(){
        controleCaderno = getActivity().getControleCaderno();
        crieExcel = new CrieExcelPoiXls1(new DiretoriosArquivosAndroid(controleCaderno.getContext()));
        metodosDados = controleCaderno.getMetodosDados();
        criaEntradas = new CriaEntradas();
        entradas = criaEntradas.crieEntradasPontos();
        //Log.v("CrieExcelPoiTeste Estado de gravação no externo: " , isExternalStorageWritable()+ "");

    }


    @UiThreadTest
    public void testCriacaoPoi(){

        try {
            assertTrue(isExternalStorageWritable());
            crieExcel.criePlanilha(entradas, nomeArquivo);
            File ultimaPlanilha = crieExcel.getUltimaPlanilha();

            assertNotNull(ultimaPlanilha);
            assert (ultimaPlanilha.exists());
            Log.v("testCriacaoPoi. Arquivo criado em: ", ultimaPlanilha.getAbsolutePath());
            Log.v("testCriacaoPoi. Arquivo criado com tamanho: ", ""+ ultimaPlanilha.getTotalSpace());
        } catch (IOException e) {
            e.printStackTrace();
            assertTrue(false);
        } catch (ImpossivelCriarDiretorioException e) {
            e.printStackTrace();
            assertTrue(false);
        }
    }

    @UiThreadTest
    public void testeBindCriacaoPoi(){
        controleCaderno.exporteExcel(entradas);
        File ultimaPlanilha = controleCaderno.getUltimaPlanilhaCriada();
        assertNotNull(ultimaPlanilha);
        assert (ultimaPlanilha.exists());
        Log.v("testeBindCriacaoPoi. Arquivo criado em: ", ultimaPlanilha.getAbsolutePath());
        Log.v("testeBindCriacaoPoi. Arquivo criado com tamnho: ", ""+ ultimaPlanilha.getTotalSpace());

    }

    @UiThreadTest
    public void testCriacaoBindComBase(){
        assertTrue(isExternalStorageWritable());
        List<EntradaPontos> entradasBase = metodosDados.getTodasEntradas();
        assertNotNull(entradasBase);
        assertTrue(entradasBase.size()>0);

        controleCaderno.exporteExcel(entradasBase);
        File ultimaPlanilha = controleCaderno.getUltimaPlanilhaCriada();
        assertNotNull(ultimaPlanilha);
        assert (ultimaPlanilha.exists());
        Log.v("testCriacaoPoiBindComBase. Arquivo criado em: ", ultimaPlanilha.getAbsolutePath());
        Log.v("testCriacaoPoiBindComBase. Arquivo criado com tamnho: ", "" + ultimaPlanilha.getTotalSpace());

    }

    @UiThreadTest
    public void testCriacaoControllerTotal(){
        controleCaderno.exporteExcelEntradasPontos();
        File ultimaPlanilha = controleCaderno.getUltimaPlanilhaCriada();
        assertNotNull(ultimaPlanilha);
        assert (ultimaPlanilha.exists());
        Log.v("testCriacaoPoiBindComBase. Arquivo criado em: ", ultimaPlanilha.getAbsolutePath());
        Log.v("testCriacaoPoiBindComBase. Arquivo criado com tamnho: ", ""+ ultimaPlanilha.getTotalSpace());
    }

}
