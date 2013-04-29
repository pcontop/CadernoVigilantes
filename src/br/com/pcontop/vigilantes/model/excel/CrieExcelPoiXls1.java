package br.com.pcontop.vigilantes.model.excel;

import br.com.pcontop.vigilantes.control.ConversaoLocale;
import br.com.pcontop.vigilantes.shared.bean.EntradaPontos;
import com.google.inject.Inject;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Paulo
 * Date: 11/04/13
 * Time: 21:10
 * To change this template use File | Settings | File Templates.
 */
public class CrieExcelPoiXls1 implements CrieExcel {
    private Workbook workbook;
    private File ultimaPlanilha;

    //TODO - Internationalization.

    private DiretoriosArquivos diretoriosArquivos;

    @Inject
    public CrieExcelPoiXls1(DiretoriosArquivos diretoriosArquivos){
        this.diretoriosArquivos = diretoriosArquivos;
    }

    @Override
    public void criePlanilha(List<EntradaPontos> entradas, String fileName) throws IOException, ImpossivelCriarDiretorioException {
        crieWorkbook();
        HashMap<String, List<EntradaPontos>> entradasPorData = getEntradasPorData(entradas);
        adicioneEntradas(entradasPorData);
        gravePlanilha(fileName);
    }

    private HashMap<String, List<EntradaPontos>> getEntradasPorData(List<EntradaPontos> entradas) {
        HashMap<String, List<EntradaPontos>> entradasPorData = new HashMap<String, List<EntradaPontos>>();
        for (EntradaPontos entradaPontos:entradas){
            adicioneEntrada(entradasPorData, entradaPontos);
        }
        return entradasPorData;
    }

    private void adicioneEntrada(HashMap<String, List<EntradaPontos>> entradasPorData, EntradaPontos entradaPontos) {
        String dataEntrada = ConversaoLocale.converta(entradaPontos.getDataInsercao());
        List<EntradaPontos> listaEntradasData;
        listaEntradasData = entradasPorData.get(dataEntrada);
        if (listaEntradasData==null){
            listaEntradasData=new ArrayList<EntradaPontos>();
            entradasPorData.put(dataEntrada, listaEntradasData);
        }
        listaEntradasData.add(entradaPontos);
    }

    private void adicioneEntradas(HashMap<String, List<EntradaPontos>> entradasPorData) {
        List<EntradaPontos> entradasData;
        List<String> datas =new ArrayList<String>(entradasPorData.keySet());
        Collections.sort(datas, new ComparatorDataPorFormato());

        for (String data:datas){
            entradasData = entradasPorData.get(data);
            Sheet dataSheet = workbook.createSheet(data.replace("/","-"));
            adicioneEntradas(dataSheet, entradasData);
        }

    }

    private void adicioneCabecalho(Sheet dataSheet) {
        CellStyle cellStyle = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        cellStyle.setFont(font);
        Row row = dataSheet.createRow(0);

        Cell cellNome = row.createCell(0);
        cellNome.setCellValue("Nome");
        cellNome.setCellStyle(cellStyle);
        Cell cellPontosUnitarios = row.createCell(1);
        cellPontosUnitarios.setCellValue("Pts Unit.");
        cellPontosUnitarios.setCellStyle(cellStyle);
        Cell cellQuantidade = row.createCell(2);
        cellQuantidade.setCellValue("Qtd.");
        cellQuantidade.setCellStyle(cellStyle);
        Cell cellPontosMultiplicados = row.createCell(3);
        cellPontosMultiplicados.setCellValue("Pts. Mult.");
        cellPontosMultiplicados.setCellStyle(cellStyle);
    }

    private void adicioneEntradas(Sheet dataSheet, List<EntradaPontos> entradasData) {
        adicioneCabecalho(dataSheet);
        int numeroLinha=1;
        Row row;
        Collections.sort(entradasData, new ComparatorEntradaPontosPorIdCrescente());
        for (EntradaPontos entradaPontos: entradasData){
            row = dataSheet.createRow(numeroLinha);
            adicioneEntrada(row, entradaPontos);
            numeroLinha++;
        }
    }

    private void adicioneEntrada(Row row, EntradaPontos entradaPontos) {
        Cell celulaNomeEntrada = row.createCell(0);
        celulaNomeEntrada.setCellValue(entradaPontos.getNome());
        Cell celulaPontosUnitariosEntrada = row.createCell(1);
        celulaPontosUnitariosEntrada.setCellValue(ConversaoLocale.converta(entradaPontos.getPontos()));
        Cell celulaQuantidade = row.createCell(2);
        celulaQuantidade.setCellValue(ConversaoLocale.converta(entradaPontos.getQuantidade()));
        Cell celulaPontosMultiplicados = row.createCell(3);
        celulaPontosMultiplicados.setCellValue(ConversaoLocale.converta(entradaPontos.getPontosMultiplicados()));
    }

    private void gravePlanilha(String fileName) throws IOException, ImpossivelCriarDiretorioException {
        ultimaPlanilha = new File(diretoriosArquivos.getDiretorioGravacaoArquivo(), fileName);
        FileOutputStream fileOutputStream = new FileOutputStream(ultimaPlanilha);
        workbook.write(fileOutputStream);
        fileOutputStream.flush();
        fileOutputStream.close();
    }

    public void crieWorkbook(){
        this.workbook = new HSSFWorkbook();
    }

    public File getUltimaPlanilha(){
        return ultimaPlanilha;
    }

}
