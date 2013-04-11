package br.com.pcontop.vigilantes.control;

import br.com.pcontop.vigilantes.model.*;
import roboguice.config.AbstractAndroidModule;

/**
 * Created with IntelliJ IDEA.
 * User: Paulo
 * Date: 31/05/12
 * Time: 16:25
 * Mapeia as injeções de dependência do projeto.
 */
public class ModuloCaderno extends AbstractAndroidModule {
    @Override
    protected void configure() {
        bind(LimitePontosDAO.class).to(LimitePontosDAO1.class);
        bind(LimitePontosSQL.class).to(LimitePontosProxy.class);
        bind(EntradaPontosDAO.class).to(EntradaPontosDAO3.class);
        bind(EntradaPontosSQL.class).to(EntradaPontosProxy.class);
        bind(DiaSemanaReuniaoSQL.class).to(DiaSemanaReuniaoDAO1.class);
        bind(ControleCaderno.class).asEagerSingleton();
        bind(ComportamentoPaginaDia.class).to(ComportamentoPaginaDiaCelular.class);
        bind(ComportamentoPaginaMes.class).to(ComportamentoPaginaMesCelular.class);
    }
}
