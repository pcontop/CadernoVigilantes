package br.com.pcontop.vigilantes.model;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Paulo
 * Date: 12/05/12
 * Time: 20:02
 *  Adaptador, contendo acesso aos métodos de regras de negócio. No momento, apenas implementa os métodos da interface
 *  do objeto de modelo de datas, mas poderá adicionar métodos de outras interfaces, conforme necessário.
 *  A associação a um método específico é realizada por injeção (roboguice).
 */
public class MetodosAdapter implements MetodosData, MetodosString {

    protected MetodosData metodosData;
    protected MetodosString metodosString;


    public MetodosAdapter(DiaSemanaReuniaoSQL diaSemanaReuniaoSQL, EntradaPontosSQL entradaPontosSQL, LimitePontosSQL limitePontosSQL){
        //Injector injector = Guice.createInjector(new ModuloDados());
        //metodosData = injector.getInstance(MetodosData.class);
        //Removido contexto global - injeção deve resolver. ContextoGlobal.setContext(context);
        metodosData = new ModeloData(diaSemanaReuniaoSQL, entradaPontosSQL, limitePontosSQL);
        metodosString = new ModeloString(diaSemanaReuniaoSQL, entradaPontosSQL, limitePontosSQL);
    }

    public Date getDataAtual(){
        return metodosData.getDataAtual();
    }

    @Override
    public boolean isDataAtual(Date data){
        return metodosData.isDataAtual(data);
    }

    @Override
    public List<EntradaPontos> getEntradasPontosData(Date dia){
        return metodosData.getEntradasPontosData(dia);
    }


    @Override
    public void deletarEntrada(EntradaPontos entradaPontos) {
        metodosData.deletarEntrada(entradaPontos);
    }

    @Override
    public void definaLimitePontos(LimitePontos limitePontos) throws SemanaOcupadaException, ParseException {
        metodosData.definaLimitePontos(limitePontos);
    }

    @Override
    public void atualizeEntradaPontos(EntradaPontos entradaPontos) {
        metodosData.atualizeEntradaPontos(entradaPontos);
    }

    @Override
    public boolean isDataEmPeriodoLimiteAlteravel(Date data) {
        return metodosData.isDataEmPeriodoLimiteAlteravel(data);
    }

    @Override
    public LimitePontos getLimitePontos(Date data) throws ParseException {
        return metodosData.getLimitePontos(data);
    }

    @Override
    public DiaSemanaReuniao getDiaSemanaReuniao(Date data) throws ParseException {
        return metodosData.getDiaSemanaReuniao(data);
    }

    @Override
    public void definaDiaSemanaReuniao(int diaSemana, Date data) throws ParseException {
        metodosData.definaDiaSemanaReuniao(diaSemana, data);
    }

    @Override
    public long getPontosExtras(Date data){
        return metodosData.getPontosExtras(data);
    }

    @Override
    public GregorianCalendar getCalendar(Date date){
        return metodosData.getCalendar(date);
    }

    @Override
    public double getPontosDia(Date data) {
        return metodosData.getPontosDia(data);
    }

    @Override
    public boolean isInicioLimite(Date data){
        return metodosData.isInicioLimite(data);
    }

    @Override
    public boolean isFinalLimite(Date data){
        return metodosData.isFinalLimite(data);
    }

    @Override
    public int getDiaSemanaReuniaoMes(Date data){
        return metodosData.getDiaSemanaReuniaoMes(data);
    }

    @Override
    public boolean isDataProximaReuniao(Date data) {
        return metodosData.isDataProximaReuniao(data);
    }

    @Override
    public Date adicioneDiasAData(Date data, int dias) {
        return metodosData.adicioneDiasAData(data, dias);
    }

    @Override
    public long getPontosMaximosDiaContandoExtras(Date data) {
        return metodosData.getPontosMaximosDiaContandoExtras(data);
    }

    @Override
    public LimitePontos getNovoLimitePontos(Date data) throws ParseException {
        return metodosData.getNovoLimitePontos(data);
    }

    @Override
    public String[] pesquiseNomesEntradasPontos() {
        return metodosString.pesquiseNomesEntradasPontos();
    }

    @Override
    public ArrayList<EntradaPontos> pesquiseEntradasPontosPorNome(String entrada) {
        return metodosString.pesquiseEntradasPontosPorNome(entrada);
    }


}
