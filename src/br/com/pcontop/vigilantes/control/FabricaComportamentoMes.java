package br.com.pcontop.vigilantes.control;

import android.app.Activity;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Paulo
 * Date: 27/05/12
 * Time: 21:03
 * Criação do comportamento mês. Substituído pela injeção roboguice.
 */
@Deprecated
public class FabricaComportamentoMes {
    public static ComportamentoPaginaMes getComportamentoPaginaMes(ControleCaderno controleCaderno, Activity activity, Date mes) {
        ComportamentoPaginaMes comportamentoPaginaMes = new ComportamentoPaginaMesCelular();
        comportamentoPaginaMes.inicializePaginaMes(controleCaderno, activity, mes);
        return comportamentoPaginaMes;
    }
}
