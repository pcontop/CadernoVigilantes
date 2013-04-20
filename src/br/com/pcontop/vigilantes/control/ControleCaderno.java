package br.com.pcontop.vigilantes.control;

import android.content.Context;
import br.com.pcontop.vigilantes.control.popups.ControlePopups;
import br.com.pcontop.vigilantes.model.MetodosDados;
import br.com.pcontop.vigilantes.model.MetodosData;
import br.com.pcontop.vigilantes.model.MetodosString;
import br.com.pcontop.vigilantes.model.SemanaOcupadaException;
import br.com.pcontop.vigilantes.model.bean.DiaSemanaReuniao;
import br.com.pcontop.vigilantes.model.bean.EntradaPontos;
import br.com.pcontop.vigilantes.model.bean.LimitePontos;
import br.com.pcontop.vigilantes.view.PaginaSistema;
import com.google.inject.Inject;

import java.io.File;
import java.text.ParseException;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Classe de controle, que serve de facade aos métodos de regras de negócio, e para as regras de apresentação e popup.
 * para as activities.
 * User: Paulo
 * Date: 16/04/12
 * Time: 21:05
 */
public class ControleCaderno {

    private ControleRegrasApresentacao controleRegrasApresentacao;
    private ControlePopups controlePopups;
    private MetodosData metodosData;
    private MetodosString metodosString;
    private MetodosDados metodosDados;

    @Inject
    public ControleCaderno(
                           MetodosDados metodosDados,
                           MetodosData metodosData,
                           MetodosString metodosString,
                           ControleRegrasApresentacao controleRegrasApresentacao,
                           ControlePopups controlePopups){
        this.metodosDados = metodosDados;
        this.metodosData = metodosData;
        this.metodosString = metodosString;
        this.controleRegrasApresentacao = controleRegrasApresentacao;
        this.controlePopups = controlePopups;
    }

    public void toast(String mensagem){
        controleRegrasApresentacao.toast(mensagem);
    }

    public int getCorData(Date data){

        return controleRegrasApresentacao.getCorData(data);
    }

    public void mostreSobre(PaginaSistema paginaSistema) {
        controlePopups.mostreSobre(paginaSistema, this);
    }

    public void busqueDiaSemanaReuniao(PaginaSistema paginaSistema) {
        controlePopups.busqueDiaSemanaReuniao(paginaSistema, this);
    }

    public void busqueDiaSemanaReuniao(PaginaSistema paginaSistema, int diaSemana) {
        controlePopups.busqueDiaSemanaReuniao(paginaSistema, diaSemana, this);
    }

    public void busqueLimitePontos(PaginaSistema paginaSistema, Date data) {
        controlePopups.busqueLimitePontos(paginaSistema, data, this);
    }

    public void busqueDataAtual(PaginaSistema paginaSistema) {
        controlePopups.busqueDataAtual(paginaSistema, this);
    }

    public void definaDiaAtual(Date time) {
        controleRegrasApresentacao.definaDiaAtual(time);
    }

    public void chequeSeDiaSemanaReuniaoEstaDefinido(PaginaSistema paginaSistema) {
        controleRegrasApresentacao.chequeSeDiaSemanaReuniaoEstaDefinido(paginaSistema, controlePopups, this);
    }

    public LimitePontos getLimiteSemanaAtualOuProxima(Date dataDestino) {
        return controleRegrasApresentacao.getLimiteSemanaAtualOuProxima(dataDestino);
    }

    public EntradaPontos pesquiseEntradaPontosPorNome(String entrada) {
        return controleRegrasApresentacao.pesquiseEntradaPontosPorNome(entrada);
    }

    public void exporteExcel(List<EntradaPontos> entradas){
        controleRegrasApresentacao.exporteExcel(entradas);
    }

    public void exporteExcelEntradasPontos(){
        exporteExcel(metodosDados.getTodasEntradas());
    }

    public File getUltimaPlanilhaCriada(){
        return controleRegrasApresentacao.getUltimaPlanilhaCriada();
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

    public void insiraOuAtualizeEntradaPontos(EntradaPontos entradaPontos) {
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
        return controleRegrasApresentacao.getContext();
    }

    public List<EntradaPontos> getTodasEntradasPontos(){
        return metodosDados.getTodasEntradas();
    }
}
