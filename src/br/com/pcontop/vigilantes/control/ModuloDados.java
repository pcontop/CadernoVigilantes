package br.com.pcontop.vigilantes.control;

import android.content.Context;
import br.com.pcontop.vigilantes.model.*;
import com.google.inject.AbstractModule;

/**
 * Created with IntelliJ IDEA.
 * User: Paulo
 * Date: 31/05/12
 * Time: 12:30
 * Classe antiga. Mapeava antigas injeções de interface, não será mais utilizada. Está sendo mantida apenas para referência.
 */
@Deprecated
public class ModuloDados extends AbstractModule {

    @Override
    protected void configure() {
        bind(ComportamentoPaginaDia.class).to(ComportamentoPaginaDiaCelular.class);
        bind(Context.class).toInstance(ContextoGlobal.getContext());
        bind(LimitePontosSQL.class).to(LimitePontosProxy.class);
        bind(EntradaPontosSQL.class).to(EntradaPontosProxy.class);
        bind(DiaSemanaReuniaoSQL.class).to(DiaSemanaReuniaoDAO1.class);
        bind(MetodosData.class).to(ModeloData.class);
    }
}
