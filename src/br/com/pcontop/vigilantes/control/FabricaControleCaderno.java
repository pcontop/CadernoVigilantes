package br.com.pcontop.vigilantes.control;

import android.content.Context;
import br.com.pcontop.vigilantes.model.DiaSemanaReuniaoSQL;
import br.com.pcontop.vigilantes.model.EntradaPontosSQL;
import br.com.pcontop.vigilantes.model.LimitePontosSQL;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Provides;

/**
 * Created with IntelliJ IDEA.
 * User: Paulo
 * Date: 12/05/12
 * Time: 14:34
 * Fábrica do Controle de Caderno, um singleton. É um provedor para o roboguice.
 */
@Deprecated
public class FabricaControleCaderno implements Provider<ControleCaderno>{
    private static ControleCaderno controleCaderno;
    private static DiaSemanaReuniaoSQL diaSemanaReuniaoSQL;
    private static EntradaPontosSQL entradaPontosSQL;
    private static LimitePontosSQL limitePontosSQL;

    //private Context context;

    @Inject
    public FabricaControleCaderno (Provider<Context> providerContext, DiaSemanaReuniaoSQL diaSemanaReuniaoSQL, EntradaPontosSQL entradaPontosSQL, LimitePontosSQL limitePontosSQL){
        //this.context = providerContext.get();
        this.diaSemanaReuniaoSQL = diaSemanaReuniaoSQL;
        this.entradaPontosSQL = entradaPontosSQL;
        this.limitePontosSQL = limitePontosSQL;
    }

    /*
    public FabricaControleCaderno (Context context){
        this.context = context;
    }
    */

    @Provides
    public ControleCaderno get(){
        //if (controleCaderno==null) {
            controleCaderno = new ControleCaderno(diaSemanaReuniaoSQL, entradaPontosSQL, limitePontosSQL);
        //}
        return controleCaderno;
    }

}
