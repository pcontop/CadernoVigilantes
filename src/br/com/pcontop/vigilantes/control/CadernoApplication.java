package br.com.pcontop.vigilantes.control;

import com.google.inject.Module;
import roboguice.application.RoboApplication;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Paulo
 * Date: 03/06/12
 * Time: 14:20
 *  Override do objeto de aplicação inical do roboguice. Dá início ao mapeamento de injeções.
 */
public class CadernoApplication extends RoboApplication {
    @Override
    protected void addApplicationModules(List<Module> modules) {
        modules.add(new ModuloCaderno());
    }

}
