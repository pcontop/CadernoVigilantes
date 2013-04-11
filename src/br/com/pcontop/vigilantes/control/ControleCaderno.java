package br.com.pcontop.vigilantes.control;

import android.content.Context;
import android.graphics.Color;
import android.widget.Toast;
import br.com.pcontop.vigilantes.R;
import br.com.pcontop.vigilantes.model.*;
import br.com.pcontop.vigilantes.view.MostreSobre;
import br.com.pcontop.vigilantes.view.PaginaSistema;
import br.com.pcontop.vigilantes.view.Toaster;
import com.google.inject.Inject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Paulo
 * Date: 16/04/12
 * Time: 21:05
 * Classe de controle, que define o acesso aos métodos de dados que definem as regras de negócio.
 */
public class ControleCaderno extends MetodosAdapter {


    @Inject
    protected ControleCaderno(DiaSemanaReuniaoSQL diaSemanaReuniaoSQL, EntradaPontosSQL entradaPontosSQL, LimitePontosSQL limitePontosSQL){
        super(diaSemanaReuniaoSQL, entradaPontosSQL, limitePontosSQL);
    }

    public void toast(String mensagem, Context context){
        Toast.makeText(context, mensagem, Toast.LENGTH_SHORT).show();
    }

    public int getCorData(Date data){
        LimitePontos limitePontos;
        long pontosMaximosDia;
        try {
            limitePontos = getLimitePontos(data);
        } catch (ParseException e) {
            return 0;
        }
        double pontosDia = getPontosDia(data);
        if (limitePontos==null){
            return Color.WHITE;
        }
        pontosMaximosDia = getPontosMaximosDiaContandoExtras(data);
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
        BusqueDiaSemanaReuniao busqueDiaSemanaReuniao = new BusqueDiaSemanaReuniao(paginaSistema, paginaSistema, getDataAtual(), this);
        busqueDiaSemanaReuniao.executar();
    }

    public void busqueDiaSemanaReuniao(PaginaSistema paginaSistema, int diaSemana) {
        BusqueDiaSemanaReuniao busqueDiaSemanaReuniao = new BusqueDiaSemanaReuniao(paginaSistema, paginaSistema, getDataAtual(), diaSemana, this);
        busqueDiaSemanaReuniao.executar();
    }

    public void busqueLimitePontos(PaginaSistema paginaSistema, Date data) {
        if (isDataEmPeriodoLimiteAlteravel(data)){
            BusqueLimitesPontos busqueLimitesPontos = new BusqueLimitesPontos(paginaSistema, paginaSistema, data, this);
            busqueLimitesPontos.executar();
        } else {
            toast(paginaSistema.getString(R.string.data_invalida), paginaSistema);
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
            DiaSemanaReuniao diaSemanaReuniao = getDiaSemanaReuniao(getDataAtual());
            if (diaSemanaReuniao ==null){
                busqueDiaSemanaReuniao(paginaSistema);
            }
        } catch (ParseException e) {
            toast("Erro na abertura do dia da semana da reuniao.", paginaSistema);
            e.printStackTrace();
        }
    }

    /**
     * Verifica o limite de pontos para uma data, ou retorna um objeto vazio caso contrário.
     */
    public LimitePontos getLimitePontosOuNovo(Date dataDestino) {
        LimitePontos limitePontos=null;
        try {
            limitePontos = getLimitePontos(dataDestino);
            if (limitePontos==null){
                limitePontos = getNovoLimitePontos(dataDestino);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return limitePontos;
    }

    public LimitePontos getLimiteSemanaAtualOuProxima(Date dataDestino) {
        if (isDataProximaReuniao(dataDestino)){
            dataDestino = adicioneDiasAData(dataDestino,1);
        }
        return getLimitePontosOuNovo(dataDestino);
    }

    public EntradaPontos pesquiseEntradaPontosPorNome(String entrada) {
        EntradaPontos entradaEscolhida= null;
        ArrayList<EntradaPontos> entradasPontos = pesquiseEntradasPontosPorNome(entrada);
        for (EntradaPontos entradaPontos: entradasPontos){
            if (entradaEscolhida==null || entradaEscolhida.getId() > entradaPontos.getId()){
                entradaEscolhida = entradaPontos;
            }
        }
        return entradaEscolhida;
    }

}
