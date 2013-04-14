package br.com.pcontop.vigilantes.control.comportamento;

import android.app.Activity;
import android.view.View;
import br.com.pcontop.vigilantes.control.ControleCaderno;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Paulo
 * Date: 27/05/12
 * Time: 20:58
 * Define os métodos de compartamento de uma PaginaMes. O comportamento define as transições entre telas,
 * e tratamento de gestos.
 */
public interface ComportamentoPaginaMes{
    void vaParaMesSeguinte();
    void vaParaMesAnterior();
    void vaParaDia(Date data);
    void setOnTouchListenerFor(View view);
    void inicializePaginaMes(ControleCaderno controleCaderno, Activity activity, Date data);

}
