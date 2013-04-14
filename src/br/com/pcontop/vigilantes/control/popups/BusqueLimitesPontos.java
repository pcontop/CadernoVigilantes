package br.com.pcontop.vigilantes.control.popups;

import android.content.Context;
import br.com.pcontop.vigilantes.control.ControleCaderno;
import br.com.pcontop.vigilantes.view.Observer;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Paulo
 * Date: 05/05/12
 * Time: 15:17
 * Abre um pop-up para o usuário definir os limites de pontos para a semana atual (limite diário e pontos livres).
 * Os valores iniciais serão os da semana anterior.
 * Será aberto um pop-up para o limite diário, e outro para o limite de pontos livres, e finalmente o Observer passado para
 * o objeto será ativado.
 */
public class BusqueLimitesPontos extends MostreAlerta {
    Date dataDestino;

    public BusqueLimitesPontos(Context context, Observer observer, Date dataDestino, ControleCaderno controleCaderno){
        super(context, observer, controleCaderno);
        this.dataDestino = dataDestino;
    }

    public void executar() {
        BusqueLimitePontosExtrasSemana limiteExtrasSemana = new BusqueLimitePontosExtrasSemana(context, observer, dataDestino, controleCaderno);
        BusqueLimitePontosDiasSemana limiteDias = new BusqueLimitePontosDiasSemana(context, limiteExtrasSemana, dataDestino, controleCaderno);
        limiteDias.executar();
    }

}
