package br.com.pcontop.vigilantes.model;

import android.util.Log;
import br.com.pcontop.vigilantes.control.DataAtual;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;


/**
 * Created with IntelliJ IDEA.
 * User: Paulo
 * Date: 12/05/12
 * Time: 19:11
 * Implementação dos métodos do modelo de pontos e datas. Acessa objetos de bases de dados.
 */
public class ModeloData implements MetodosData {
    private LimitePontosSQL limitePontosSQL;
    private EntradaPontosSQL entradaPontosSQL;
    private DiaSemanaReuniaoSQL diaSemanaReuniaoSQL;

    public ModeloData(DiaSemanaReuniaoSQL diaSemanaReuniaoSQL, EntradaPontosSQL entradaPontosSQL, LimitePontosSQL limitePontosSQL){
        this.diaSemanaReuniaoSQL = diaSemanaReuniaoSQL;
        this.entradaPontosSQL = entradaPontosSQL;
        this.limitePontosSQL = limitePontosSQL;
    }

    public Date getDataAtual(){
        return DataAtual.getDataAtual();
    }

    public boolean isDataAtual(Date data){
        Date dataAtual = getDataAtual();
        boolean resultado;
        resultado = compareAnoMesDia(data, dataAtual);
        return resultado;
    }

    public List<EntradaPontos> getEntradasPontosData(Date data){
        List<EntradaPontos> entradasPontos = entradaPontosSQL.getLista(data);
        entradaPontosSQL.close();
        return entradasPontos;
    }

    public void deletarEntrada(EntradaPontos entradaPontos) {
        entradaPontosSQL.removaEntrada(entradaPontos);
        entradaPontosSQL.close();
    }

    public void insiraOuAtualize(EntradaPontos entradaPontos) {
        entradaPontosSQL.inserirOuEditar(entradaPontos);
        entradaPontosSQL.close();
    }

    public void definaLimitePontos(LimitePontos limitePontos) throws SemanaOcupadaException, ParseException {
        insiraOuAtualize(limitePontos);
    }


    public boolean isDataEmPeriodoLimiteAlteravel(Date data) {
        try {
            Date dataAtual = getDataAtual();
            Date dataUltimaReuniao = getDataReuniaoAnterior(dataAtual);
            Date dataProximaReuniao = getDataProximaReuniao(dataAtual);
            if (dataProximaReuniao==null || dataUltimaReuniao ==null){
                return false;
            }
            if (!compareAnoMesDia(dataAtual, dataProximaReuniao)) {
                if (data.compareTo(dataUltimaReuniao)>0 && data.compareTo(dataProximaReuniao)<=0){
                    if (!isAntesUltimoLimitePontos(data)){
                        return true;
                    }
                }
            } else {
                Date dataReuniaoDepoisProxima = getDataProximaReuniaoMaisDias(dataAtual, 7);
                if (dataReuniaoDepoisProxima==null) {
                    return false;
                }
                if (data.compareTo(dataProximaReuniao)==0){
                    return true;
                }
                if (data.compareTo(dataProximaReuniao)>0 && data.compareTo(dataReuniaoDepoisProxima)<=0){
                    if (!isAntesUltimoLimitePontos(data)){
                        return true;
                    }
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean isAntesUltimoLimitePontos(Date data) throws ParseException {
        LimitePontos limitePontos = getUltimoLimitePontos();
        if (limitePontos!=null){
            if (limitePontos.getDataInicio().after(data)) {
                return true;
            }
        }
        return false;
    }

    private LimitePontos getUltimoLimitePontos() throws ParseException {
        return limitePontosSQL.getUltimoLimitePontos();
    }

    private void insiraOuAtualize(LimitePontos limitePontos) throws SemanaOcupadaException, ParseException {
        limitePontosSQL.insiraOuAtualizePontos(limitePontos);
        Log.v("ControleCaderno", "Adicionando limite:" + limitePontos);
    }

    public LimitePontos getLimitePontos(Date data) throws ParseException {
        LimitePontos limitePontos;
        limitePontos = limitePontosSQL.getLimitePontos(data);
        return limitePontos;
    }

    public DiaSemanaReuniao getDiaSemanaReuniao(Date data) throws ParseException {
        DiaSemanaReuniao diaSemana = diaSemanaReuniaoSQL.getDiaSemanaValido(data);
        diaSemanaReuniaoSQL.close();
        return diaSemana;
    }

    /*
    public int getDiaSemana(Date data){
        Calendar calendar = getCalendar(data);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }
    */

    public void definaDiaSemanaReuniao(int diaSemana, Date data) throws ParseException {
        //Pega validade: se existir, vai para a próxima reunião. Se não, vai para a anterior.
        Date diaInicioValidade;
        if (getDiaSemanaReuniao(data)!=null){
            diaInicioValidade = getDataProximaReuniao(data, diaSemana);
        }else {
            diaInicioValidade = getDataReuniaoAnterior(data, diaSemana);
        }
        //Ajusta data fim do Limite Pontos
        LimitePontos limitePontos = getLimitePontos(data);
        if (limitePontos!=null){
            limitePontos.setDataFim(diaInicioValidade);
            try {
                definaLimitePontos(limitePontos);
            } catch (SemanaOcupadaException e) {
                e.printStackTrace();
            }
        }
        DiaSemanaReuniao diaSemanaReuniao = new DiaSemanaReuniao();
        diaSemanaReuniao.setInicioValidade(getDataAtual());
        diaSemanaReuniao.setDiaSemana(diaSemana);
        insiraOuAtualize(diaSemanaReuniao);
    }

    /*
    private void apagueDiasReuniaoComValidadeApos(Date diaInicioValidade) {
        diaSemanaReuniaoSQL.apagueEntradasComValidadeApos(diaInicioValidade);
    }

    private Date getProximoDia(Date data){
        Calendar calendar = getCalendar(data);
        calendar.add(Calendar.DATE, 1);
        return calendar.getTime();
    }
    */

    public void insiraOuAtualize(DiaSemanaReuniao diaSemanaReuniao) throws ParseException {
        diaSemanaReuniaoSQL.inserirOuAtualizar(diaSemanaReuniao);
    }

    public long getPontosExtras(Date data){
        long pontosExtras=0;
        try {
            LimitePontos limitePontos = getLimitePontos(data);
            long pontosMaximos = getPontosMaximosDiaContandoExtras(data);
            pontosExtras = pontosMaximos - limitePontos.getPontosDia();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return pontosExtras;
    }

    public Date getDataReuniaoAnterior(Date data) throws ParseException {
        DiaSemanaReuniao diaSemanaReuniao =  getDiaSemanaReuniao(data);
        if (diaSemanaReuniao==null) {
            return null;
        }
        int diaReuniao = diaSemanaReuniao.getDiaSemana();
        return getDataReuniaoAnterior(data, diaReuniao);
    }

    private Date getDataReuniaoAnterior(Date data, int diaReuniao) {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(data);
        int diaSemanaData = calendar.get(Calendar.DAY_OF_WEEK);
        int diferencaDias = diaReuniao < diaSemanaData? diaSemanaData-diaReuniao : 7-(diaReuniao-diaSemanaData);
        calendar.add(Calendar.DATE, -diferencaDias);
        return calendar.getTime();
    }

    public Date getDataReuniaoAnteriorMaisDias(Date data, int dias) throws ParseException {
        Date dataRetorno = getDataReuniaoAnterior(data);
        dataRetorno = adicioneDiasAData(dataRetorno, dias);
        return dataRetorno;
    }

    public Date adicioneDiasAData(Date data, int dias) {
        Date dataResultado = data;
        if (dataResultado!=null)  {
            Calendar calendar = getCalendar(dataResultado);
            calendar.add(Calendar.DATE,dias);
            dataResultado = calendar.getTime();
        }
        return dataResultado;

    }

    public Date getDataProximaReuniao(Date data) throws ParseException {
        DiaSemanaReuniao diaSemanaReuniao =  getDiaSemanaReuniao(data);
        if (diaSemanaReuniao==null) {
            return null;
        }
        int diaReuniao = diaSemanaReuniao.getDiaSemana();
        return getDataProximaReuniao(data, diaReuniao);
    }

    private Date getDataProximaReuniao(Date data, int diaReuniao) {
        GregorianCalendar calendar = getCalendar(data);
        int diaSemanaData = calendar.get(Calendar.DAY_OF_WEEK);
        int diferencaDias = diaReuniao >= diaSemanaData? diaReuniao-diaSemanaData : 7-(diaSemanaData-diaReuniao);
        calendar.add(Calendar.DATE, diferencaDias);
        return calendar.getTime();
    }

    public Date getDataProximaReuniaoMaisDias(Date data, int dias) throws ParseException {
        Date dataSaida = getDataProximaReuniao(data);
        dataSaida= adicioneDiasAData(dataSaida, dias);
        return dataSaida;
    }

    public boolean isDataProximaReuniao(Date data) {
        Date dataProximaReuniao = null;
        boolean b = false;
        try {
            dataProximaReuniao = getDataProximaReuniao(data);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (dataProximaReuniao!=null){
            b = compareAnoMesDia(data, dataProximaReuniao);
        }
        return b;
    }

    public LimitePontos getNovoLimitePontos(Date data) throws ParseException {
        LimitePontos limitePontos = new LimitePontos();
        //Se for no dia da reunião, defina o limite para a próxima semana
        if (isDataProximaReuniao(data)){
            limitePontos.setDataInicio(getDataProximaReuniaoMaisDias(data, 1));
            limitePontos.setDataFim(getDataProximaReuniaoMaisDias(data, 7));
        } else {
            limitePontos.setDataInicio(getDataReuniaoAnteriorMaisDias(data, 1));
            limitePontos.setDataFim(getDataProximaReuniao(data));
        }
        LimitePontos limitePontosAnterior = getLimitePontosAnterior(data);
        if (limitePontosAnterior!=null){
            limitePontos.setPontosDia(limitePontosAnterior.getPontosDia());
            limitePontos.setPontosLivreSemana(limitePontosAnterior.getPontosLivreSemana());
        }
        return limitePontos;
    }

    private LimitePontos getLimitePontosAnterior(Date data) {
        LimitePontos limitePontos=null;
        try {
            limitePontos = limitePontosSQL.getLimitePontosAnterior(data);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return limitePontos;
    }

    public long getPontosMaximosDiaContandoExtras(Date data) {
        long pontosMaximos=0;
        long pontosExtrasConsumidos = 0;
        try {
            LimitePontos limitePontos = getLimitePontos(data);
            long pontosExtras = limitePontos.getPontosLivreSemana();
            Date dataInicio = limitePontos.getDataInicio();
            Calendar calendar = getCalendar(dataInicio);
            while (!compareAnoMesDia(calendar.getTime(), data)){
                pontosExtrasConsumidos+=pontosExtrasConsumidosData(calendar.getTime());
                calendar.add(Calendar.DATE, 1);
            }
            pontosExtras-=pontosExtrasConsumidos;
            pontosExtras=pontosExtras<0?0:pontosExtras;
            pontosMaximos = limitePontos.getPontosDia() + pontosExtras;

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return pontosMaximos;
    }

    private long pontosExtrasConsumidosData(Date data) {
        long pontosExtrasConsumidos =0;
        try {
            LimitePontos limitePontos = getLimitePontos(data);
            long limitePontosDia = limitePontos.getPontosDia();
            limitePontosDia=limitePontosDia<0?0:limitePontosDia;
            long pontosDia = (long)getPontosDia(data);
            pontosExtrasConsumidos = pontosDia - limitePontosDia;
            pontosExtrasConsumidos = pontosExtrasConsumidos<0?0:pontosExtrasConsumidos;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return pontosExtrasConsumidos;
    }

    public GregorianCalendar getCalendar(Date date){
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTime(date);
        return gregorianCalendar;
    }

    public double getPontosDia(Date data) {
        List<EntradaPontos> entradasPontoDia= getEntradasPontosData(data);
        double pontos = 0;
        for (EntradaPontos entradaPontos: entradasPontoDia){
            pontos+= entradaPontos.getPontosMultiplicados();
        }
        return pontos;
    }

    public boolean isInicioLimite(Date data){
        try {
            LimitePontos limitePontos = getLimitePontos(data);
            return compareAnoMesDia(limitePontos.getDataInicio(),data);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isFinalLimite(Date data){
        try {
            LimitePontos limitePontos = getLimitePontos(data);
            return compareAnoMesDia(limitePontos.getDataFim(),data);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean compareAnoMesDia(Date data1, Date data2) {
        if (data1.getYear()!=data2.getYear()) {
            return false;
        }
        if (data1.getMonth()!=data2.getMonth()){
            return false;
        }
        return data1.getDate() == data2.getDate();
    }

    /**
     * Retorna o dia da reunião (1-7) do último dia do mês, ou 1 (domingo), caso não exista.
     * @param data Data do mês a ser procurado.
     * @return Codigo 1-7 do dia de reunião do último dia do mês.
     */
    public int getDiaSemanaReuniaoMes(Date data){
        int retorno = 1;
        GregorianCalendar calendar = getCalendar(data);
        calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
        try {
            DiaSemanaReuniao diaSemanaReuniao = getDiaSemanaReuniao(calendar.getTime());
            if (diaSemanaReuniao!=null){
                retorno = diaSemanaReuniao.getDiaSemana();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return retorno;
    }

    public void atualizeEntradaPontos(EntradaPontos entradaPontos) {
        insiraOuAtualize(entradaPontos);
    }

}
