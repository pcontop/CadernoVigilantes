package br.com.pcontop.vigilantes.control.modulo;

import br.com.pcontop.vigilantes.control.ControleCaderno;
import br.com.pcontop.vigilantes.control.ControleRegrasApresentacao;
import br.com.pcontop.vigilantes.control.ControleRegrasApresentacaoImpl;
import br.com.pcontop.vigilantes.control.arquivos.NomesDeArquivos;
import br.com.pcontop.vigilantes.control.arquivos.NomesDeArquivosExcel;
import br.com.pcontop.vigilantes.control.comportamento.ComportamentoPaginaDia;
import br.com.pcontop.vigilantes.control.comportamento.ComportamentoPaginaDiaCelular;
import br.com.pcontop.vigilantes.control.comportamento.ComportamentoPaginaMes;
import br.com.pcontop.vigilantes.control.comportamento.ComportamentoPaginaMesCelular;
import br.com.pcontop.vigilantes.control.popups.ControlePopups;
import br.com.pcontop.vigilantes.control.popups.ControlePopupsImpl;
import br.com.pcontop.vigilantes.model.*;
import br.com.pcontop.vigilantes.model.dao.*;
import br.com.pcontop.vigilantes.model.excel.CrieExcel;
import br.com.pcontop.vigilantes.model.excel.CrieExcelPoiXls1;
import br.com.pcontop.vigilantes.model.excel.DiretoriosArquivos;
import br.com.pcontop.vigilantes.model.excel.DiretoriosArquivosAndroid;
import roboguice.config.AbstractAndroidModule;

/**
 * Modulo de amarração para o Roboguice. Define quais injeções serão realizadas.
 * User: Paulo
 * Date: 31/05/12
 * Time: 16:25
 */
public class ModuloCaderno extends AbstractAndroidModule {
    @Override
    protected void configure() {
        bind(LimitePontosDAO.class).to(LimitePontosDAO1.class);
        bind(LimitePontosSQL.class).to(LimitePontosProxyCache.class).asEagerSingleton();
        bind(EntradaPontosDAO.class).to(EntradaPontosDAO3.class);
        bind(EntradaPontosSQL.class).to(EntradaPontosProxyCache.class).asEagerSingleton();
        bind(DiaSemanaReuniaoSQL.class).to(DiaSemanaReuniaoDAO1.class).asEagerSingleton();
        bind(ControleCaderno.class).asEagerSingleton();
        bind(CrieExcel.class).to(CrieExcelPoiXls1.class);
        bind(NomesDeArquivos.class).to(NomesDeArquivosExcel.class);
        bind(DiretoriosArquivos.class).to(DiretoriosArquivosAndroid.class);
        bind(ComportamentoPaginaDia.class).to(ComportamentoPaginaDiaCelular.class);
        bind(ComportamentoPaginaMes.class).to(ComportamentoPaginaMesCelular.class);
        bind(MetodosString.class).to(ModeloString.class).asEagerSingleton();
        bind(MetodosData.class).to(ModeloData.class).asEagerSingleton();
        bind(MetodosDados.class).to(ModeloDados.class).asEagerSingleton();
        bind(ControlePopups.class).to(ControlePopupsImpl.class);
        bind(ControleRegrasApresentacao.class).to(ControleRegrasApresentacaoImpl.class);
    }
}
