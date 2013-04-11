package br.com.pcontop.vigilantes.control;

import android.content.Context;

/**
 * Created with IntelliJ IDEA.
 * User: Paulo
 * Date: 12/05/12
 * Time: 15:18
 * Objeto que contém variáveis globais.
 */
public class ContextoGlobal {
    public static boolean debugMode=false;
    private static Context context;

    @Deprecated
    public static Context getContext() {
        return context;
    }
    @Deprecated
    public static void setContext(Context context) {
        ContextoGlobal.context = context;
    }
}
