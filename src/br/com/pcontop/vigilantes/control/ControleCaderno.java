package br.com.pcontop.vigilantes.control;

import android.content.Context;
import android.graphics.Color;
import android.widget.Toast;
import br.com.pcontop.vigilantes.R;
import br.com.pcontop.vigilantes.control.arquivos.NomesDeArquivos;
import br.com.pcontop.vigilantes.control.popups.BusqueDataAtual;
import br.com.pcontop.vigilantes.control.popups.BusqueDiaSemanaReuniao;
import br.com.pcontop.vigilantes.control.popups.BusqueLimitesPontos;
import br.com.pcontop.vigilantes.model.MetodosDados;
import br.com.pcontop.vigilantes.model.MetodosData;
import br.com.pcontop.vigilantes.model.MetodosString;
import br.com.pcontop.vigilantes.model.SemanaOcupadaException;
import br.com.pcontop.vigilantes.model.bean.DiaSemanaReuniao;
import br.com.pcontop.vigilantes.model.bean.EntradaPontos;
import br.com.pcontop.vigilantes.model.bean.LimitePontos;
import br.com.pcontop.vigilantes.model.excel.CrieExcel;
import br.com.pcontop.vigilantes.model.excel.ImpossivelCriarDiretorioException;
import br.com.pcontop.vigilantes.view.MostreSobre;
import br.com.pcontop.vigilantes.view.PaginaSistema;
import com.google.inject.Inject;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Paulo
 * Date: 16/04/12
 * Time: 21:05
 * Classe de controle, que define o acesso aos métodos de dados que definem as regras de negócio.
 */
public class ControleCaderno {

    @Inject
    CrieExcel crieExcel;

    @Inject
    NomesDeArquivos nomesDeArquivos;

    @Inject
    Context context;

    @Inject
    MetodosData metodosData;

    @Inject
    MetodosString metodosString;

    @Inject
    MetodosDados metodosDados;

    @Inject
    public ControleCaderno(){

    }

    public void toast(String mensagem){
        Toast.makeText(context, mensagem, Toast.LENGTH_SHORT).show();
    }

    public void toast(int resourceId){
        toast(context.getText(resourceId).toString());
    }

    public int getCorData(Date data){
        LimitePontos limitePontos;
        long pontosMaximosDia;
        try {
            limitePontos = metodosDados.getLimitePontos(data);
        } catch (ParseException e) {
            return 0;
        }
        double pontosDia = metodosData.getPontosDia(data);
        if (limitePontos==null){
            return Color.WHITE;
        }
        pontosMaximosDia = metodosData.getPontosMaximosDiaContandoExtras(data);
        if (pontosDia>limitePontos.getPontosDia() && pontosDia <= pontosMaximosDia) {
            return Color.YELLOW;
        }

        if (pontosDia>limitePontos.getPontosDia() && pontosDia > pontosMaximosDia) {
            return Color.RED;
        }

        if (pontosDia <= limitePontos.getPontosDia()){
            return Color.GREEN;
        }
        return 0;
    }

    public void mostreSobre(PaginaSistema paginaSistema) {
        MostreSobre mostreSobre =new MostreSobre(paginaSistema, paginaSistema, this);
        mostreSobre.executar();
    }

    public void busqueDiaSemanaReuniao(PaginaSistema paginaSistema) {
        BusqueDiaSemanaReuniao busqueDiaSemanaReuniao = new BusqueDiaSemanaReuniao(paginaSistema, paginaSistema, metodosData.getDataAtual(), this);
        busqueDiaSemanaReuniao.executar();
    }

    public void busqueDiaSemanaReuniao(PaginaSistema paginaSistema, int diaSemana) {
        BusqueDiaSemanaReuniao busqueDiaSemanaReuniao = new BusqueDiaSemanaReuniao(paginaSistema, paginaSistema, metodosData.getDataAtual(), diaSemana, this);
        busqueDiaSemanaReuniao.executar();
    }

    public void busqueLimitePontos(PaginaSistema paginaSistema, Date data) {
        if (metodosData.isDataEmPeriodoLimiteAlteravel(data)){
            BusqueLimitesPontos busqueLimitesPontos = new BusqueLimitesPontos(paginaSistema, paginaSistema, data, this);
            busqueLimitesPontos.executar();
        } else {
            toast(paginaSistema.getString(R.string.data_invalida));
        }
    }

    public void busqueDataAtual(PaginaSistema paginaSistema) {
        BusqueDataAtual busqueDataAtual = new BusqueDataAtual(paginaSistema,  paginaSistema, this);
        busqueDataAtual.executar();
    }

    public void definaDiaAtual(Date time) {
        DataAtual.forceDataAtual(time);
    }

    public void chequeSeDiaSemanaReuniaoEstaDefinido(PaginaSistema paginaSistema) {
        try {
            DiaSemanaReuniao diaSemanaReuniao = metodosDados.getDiaSemanaReuniao(metodosData.getDataAtual());
            if (diaSemanaReuniao ==null){
                busqueDiaSemanaReuniao(paginaSistema);
            }
        } catch (ParseException e) {
            toast(R.string.erro_abertura_dia_semana);
            e.printStackTrace();
        }
    }

    /**
     * Verifica o limite de pontos para uma data, ou retorna um objeto vazio caso contrário.
     */
    public LimitePontos getLimitePontosOuNovo(Date dataDestino) {
        LimitePontos limitePontos=null;
        try {
            limitePontos = metodosDados.getLimitePontos(dataDestino);
            if (limitePontos==null){
                limitePontos = metodosData.getNovoLimitePontos(dataDestino);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return limitePontos;
    }

    public LimitePontos getLimiteSemanaAtualOuProxima(Date dataDestino) {
        if (metodosData.isDataProximaReuniao(dataDestino)){
            dataDestino = metodosData.adicioneDiasAData(dataDestino, 1);
        }
        return getLimitePontosOuNovo(dataDestino);
    }

    public EntradaPontos pesquiseEntradaPontosPorNome(String entrada) {
        EntradaPontos entradaEscolhida= null;
        List<EntradaPontos> entradasPontos = metodosDados.pesquiseEntradasPontosPorNome(entrada);
        for (EntradaPontos entradaPontos: entradasPontos){
            if (entradaEscolhida==null || entradaEscolhida.getId() > entradaPontos.getId()){
                entradaEscolhida = entradaPontos;
            }
        }
        return entradaEscolhida;
    }

    public void exporteExcel(List<EntradaPontos> entradas){
        try {
            crieExcel.criePlanilha(entradas, nomesDeArquivos.nomeArquivoPadrao());
        } catch (IOException e) {
            toast(R.string.erro_geracao_planilha);
        } catch (ImpossivelCriarDiretorioException e) {
            toast(R.string.erro_acesso_diretorio);
        }
        String inicioMensagem = context.getText(R.string.excel_gerado).toString();
        toast(inicioMensagem + getUltimaPlanilhaCriada().getAbsolutePath());
    }

    public void exporteExcelEntradasPontos(){
        exporteExcel(metodosDados.pesquiseTodasEntradas());
    }

    public File getUltimaPlanilhaCriada(){
        return crieExcel.getUltimaPlanilha();
    }

    public void definaLimitePontos(LimitePontos limitePontosRef) throws SemanaOcupadaException, ParseException {
        metodosDados.insiraOuAtualizeLimitePontos(limitePontosRef);
    }

    public boolean isDataProximaReuniao(Date dataDestino) {
        return metodosData.isDataProximaReuniao(dataDestino);
    }

    public String[] pesquiseNomesEntradasPontos() {
        return metodosString.pesquiseNomesEntradasPontos();
    }

    public void deletarEntrada(EntradaPontos entradaPontos) {
        metodosDados.deletarEntrada(entradaPontos);
    }

    public List<EntradaPontos> getEntradasPontosData(Date dataCaderno) {
        return metodosDados.getEntradasPontosData(dataCaderno);
    }

    public LimitePontos getLimitePontos(Date dataCaderno) throws ParseException {
        return metodosDados.getLimitePontos(dataCaderno);
    }

    public long getPontosExtras(Date dataCaderno) {
        return metodosData.getPontosExtras(dataCaderno);
    }

    public boolean isDataEmPeriodoLimiteAlteravel(Date dataCaderno) {
        return metodosData.isDataEmPeriodoLimiteAlteravel(dataCaderno);
    }

    public double getPontosDia(Date dataCaderno) {
        return metodosData.getPontosDia(dataCaderno);
    }

    public void atualizeEntradaPontos(EntradaPontos entradaPontos) {
        metodosDados.insiraOuAtualize(entradaPontos);
    }

    public GregorianCalendar getCalendar(Date mes) {
        return metodosData.getCalendar(mes);
    }

    public DiaSemanaReuniao getDiaSemanaReuniao(Date data) throws ParseException {
        return metodosDados.getDiaSemanaReuniao(data);
    }

    public void definaDiaSemanaReuniao(int i, Date data) throws ParseException {
        metodosData.definaDiaSemanaReuniao(i, data);
    }

    public int getDiaSemanaReuniaoMes(Date mes) {
        return metodosData.getDiaSemanaReuniaoMes(mes);
    }

    public boolean isInicioLimite(Date dataApresentada) {
        return metodosData.isInicioLimite(dataApresentada);
    }

    public boolean isFinalLimite(Date dataApresentada) {
        return metodosData.isFinalLimite(dataApresentada);
    }

    public boolean isDataAtual(Date dataApresentada) {
        return metodosData.isDataAtual(dataApresentada);
    }

    public Date getDataAtual() {
        return metodosData.getDataAtual();
    }

    public MetodosDados getMetodosDados(){
        return metodosDados;
    }

    public Context getContext(){
        return context;
    }
}
