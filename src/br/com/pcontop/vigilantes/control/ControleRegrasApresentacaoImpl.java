package br.com.pcontop.vigilantes.control;

import android.content.Context;
import android.graphics.Color;
import android.widget.Toast;
import br.com.pcontop.vigilantes.R;
import br.com.pcontop.vigilantes.control.arquivos.NomesDeArquivos;
import br.com.pcontop.vigilantes.control.popups.ControlePopups;
import br.com.pcontop.vigilantes.model.MetodosDados;
import br.com.pcontop.vigilantes.model.MetodosData;
import br.com.pcontop.vigilantes.model.MetodosString;
import br.com.pcontop.vigilantes.shared.bean.DiaSemanaReuniao;
import br.com.pcontop.vigilantes.shared.bean.EntradaPontos;
import br.com.pcontop.vigilantes.shared.bean.LimitePontos;
import br.com.pcontop.vigilantes.model.excel.CrieExcel;
import br.com.pcontop.vigilantes.model.excel.ImpossivelCriarDiretorioException;
import br.com.pcontop.vigilantes.view.PaginaSistema;
import com.google.inject.Inject;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * Implementação dos métodos de Regras de apresentação.
 * @author Paulo Bruno Contopoulos
 */
public class ControleRegrasApresentacaoImpl implements ControleRegrasApresentacao {

    CrieExcel crieExcel;
    NomesDeArquivos nomesDeArquivos;
    Context context;
    MetodosData metodosData;
    MetodosString metodosString;
    MetodosDados metodosDados;

    @Inject
    public ControleRegrasApresentacaoImpl(CrieExcel crieExcel,
                                          NomesDeArquivos nomesDeArquivos,
                                          MetodosDados metodosDados,
                                          MetodosData metodosData,
                                          MetodosString metodosString,
                                          Context context){
        this.crieExcel = crieExcel;
        this.nomesDeArquivos = nomesDeArquivos;
        this.metodosDados = metodosDados;
        this.metodosData = metodosData;
        this.metodosString = metodosString;
        this.context = context;
    }

    @Override
    public void toast(String mensagem) {
        Toast.makeText(context, mensagem, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void toast(int resourceId) {
        toast(context.getText(resourceId).toString());
    }

    @Override
    public int getCorData(Date data) {
        LimitePontos limitePontos;
        long pontosMaximosDia;
        try {
            limitePontos = metodosDados.getLimitePontos(data);
        } catch (ParseException e) {
            return 0;
        }
        double pontosDia = metodosData.getPontosDia(data);
        if (limitePontos == null) {
            return Color.WHITE;
        }
        pontosMaximosDia = metodosData.getPontosMaximosDiaContandoExtras(data);
        if (pontosDia > limitePontos.getPontosDia() && pontosDia <= pontosMaximosDia) {
            return Color.YELLOW;
        }

        if (pontosDia > limitePontos.getPontosDia() && pontosDia > pontosMaximosDia) {
            return Color.RED;
        }

        if (pontosDia <= limitePontos.getPontosDia()) {
            return Color.GREEN;
        }
        return 0;
    }

    @Override
    public void definaDiaAtual(Date time) {
        DataAtual.forceDataAtual(time);
    }

    @Override
    public void chequeSeDiaSemanaReuniaoEstaDefinido(PaginaSistema paginaSistema, ControlePopups controlePopups, ControleCaderno controleCaderno) {
        try {
            DiaSemanaReuniao diaSemanaReuniao = metodosDados.getDiaSemanaReuniao(metodosData.getDataAtual());
            if (diaSemanaReuniao ==null){
                controlePopups.busqueDiaSemanaReuniao(paginaSistema, controleCaderno);
            }
        } catch (ParseException e) {
            toast(R.string.erro_abertura_dia_semana);
            e.printStackTrace();
        }
    }

    /**
     * Verifica o limite de pontos para uma data, ou retorna um objeto vazio caso contrário.
     */
    @Override
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

    @Override
    public LimitePontos getLimiteSemanaAtualOuProxima(Date dataDestino) {
        if (metodosData.isDataProximaReuniao(dataDestino)){
            dataDestino = metodosData.adicioneDiasAData(dataDestino, 1);
        }
        return getLimitePontosOuNovo(dataDestino);
    }

    @Override
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

    @Override
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

    @Override
    public File getUltimaPlanilhaCriada(){
        return crieExcel.getUltimaPlanilha();
    }

    @Override
    public Context getContext(){
        return context;
    }

}