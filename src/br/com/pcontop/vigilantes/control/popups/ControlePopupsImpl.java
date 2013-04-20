package br.com.pcontop.vigilantes.control.popups;

import br.com.pcontop.vigilantes.R;
import br.com.pcontop.vigilantes.control.ControleCaderno;
import br.com.pcontop.vigilantes.model.MetodosData;
import br.com.pcontop.vigilantes.view.MostreSobre;
import br.com.pcontop.vigilantes.view.PaginaSistema;
import com.google.inject.Inject;

import java.util.Date;

/**
 * Implementação dos étodos de abertura de pop-ups.
 * User: Paulo
 * Date: 19/04/13
 * Time: 20:35
 */
public class ControlePopupsImpl implements ControlePopups {
    MetodosData metodosData;

    @Inject
    public ControlePopupsImpl(MetodosData metodosData){
        this.metodosData = metodosData;
    }

    @Override
    public void mostreSobre(PaginaSistema paginaSistema, ControleCaderno controleCaderno) {
        MostreSobre mostreSobre = new MostreSobre(paginaSistema, paginaSistema, controleCaderno);
        mostreSobre.executar();
    }

    @Override
    public void busqueDiaSemanaReuniao(PaginaSistema paginaSistema, ControleCaderno controleCaderno) {
        BusqueDiaSemanaReuniao busqueDiaSemanaReuniao = new BusqueDiaSemanaReuniao(paginaSistema, paginaSistema, metodosData.getDataAtual(), controleCaderno);
        busqueDiaSemanaReuniao.executar();
    }

    @Override
    public void busqueDiaSemanaReuniao(PaginaSistema paginaSistema, int diaSemana, ControleCaderno controleCaderno) {
        BusqueDiaSemanaReuniao busqueDiaSemanaReuniao = new BusqueDiaSemanaReuniao(paginaSistema, paginaSistema, metodosData.getDataAtual(), diaSemana, controleCaderno);
        busqueDiaSemanaReuniao.executar();
    }

    @Override
    public void busqueLimitePontos(PaginaSistema paginaSistema, Date data, ControleCaderno controleCaderno) {
        if (metodosData.isDataEmPeriodoLimiteAlteravel(data)) {
            BusqueLimitesPontos busqueLimitesPontos = new BusqueLimitesPontos(paginaSistema, paginaSistema, data, controleCaderno);
            busqueLimitesPontos.executar();
        } else {
            controleCaderno.toast(paginaSistema.getString(R.string.data_invalida));
        }
    }

    @Override
    public void busqueDataAtual(PaginaSistema paginaSistema, ControleCaderno controleCaderno) {
        BusqueDataAtual busqueDataAtual = new BusqueDataAtual(paginaSistema, paginaSistema, controleCaderno);
        busqueDataAtual.executar();
    }
}
