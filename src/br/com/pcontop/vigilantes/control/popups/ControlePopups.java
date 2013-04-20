package br.com.pcontop.vigilantes.control.popups;

import br.com.pcontop.vigilantes.control.ControleCaderno;
import br.com.pcontop.vigilantes.view.PaginaSistema;

import java.util.Date;

/**
 * Define os métodos de abertura de Popups.
 * User: Paulo
 * Date: 19/04/13
 * Time: 20:46
 */
public interface ControlePopups {
    void mostreSobre(PaginaSistema paginaSistema, ControleCaderno controleCaderno);

    void busqueDiaSemanaReuniao(PaginaSistema paginaSistema, ControleCaderno controleCaderno);

    void busqueDiaSemanaReuniao(PaginaSistema paginaSistema, int diaSemana, ControleCaderno controleCaderno);

    void busqueLimitePontos(PaginaSistema paginaSistema, Date data, ControleCaderno controleCaderno);

    void busqueDataAtual(PaginaSistema paginaSistema, ControleCaderno controleCaderno);
}
