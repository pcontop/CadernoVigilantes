package br.com.pcontop.vigilantes.control.comportamento;

import android.app.Activity;
import android.view.View;
import br.com.pcontop.vigilantes.control.ControleCaderno;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Paulo
 * Date: 27/05/12
 * Time: 20:37
 * Define os métodos de compartamento de uma PaginaDia. O comportamento define as transições entre telas, e tratamento
 * para gestos.
 */
public interface ComportamentoPaginaDia {
    void vaParaDiaSeguinte();
    void vaParaDiaAnterior();
    void vaParaMes();
    void adicioneComportamentoTouch(ArrayList<View> listaViews);
    void inicializePaginaDia(ControleCaderno controleCaderno, Activity activity, Date data);
}
