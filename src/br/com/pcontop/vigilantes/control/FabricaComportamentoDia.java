package br.com.pcontop.vigilantes.control;

import br.com.pcontop.vigilantes.view.PaginaDia;
import com.google.inject.Provides;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Paulo
 * Date: 27/05/12
 * Time: 20:48
 * Cria um comporamento dia. Subetituído pela injeção usando Roboguice.
 */
@Deprecated
public class FabricaComportamentoDia {

    @Provides
    public static ComportamentoPaginaDia getComportamento(ControleCaderno controleCaderno, PaginaDia paginaDia, Date dataCaderno) {
        ComportamentoPaginaDia comportamentoPaginaDia = new ComportamentoPaginaDiaCelular();
        comportamentoPaginaDia.inicializePaginaDia(controleCaderno, paginaDia, dataCaderno);
        return comportamentoPaginaDia;
    }
}
