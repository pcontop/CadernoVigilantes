package br.com.pcontop.vigilantes.view;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created with IntelliJ IDEA.
 * User: Paulo
 * Date: 11/04/13
 * Time: 01:55
 * To change this template use File | Settings | File Templates.
 */
public class ConversaoLocale {
    public static String converta(Double numero){
        String texto="";
        String language = Locale.getDefault().getDisplayLanguage();
        DecimalFormat df = new DecimalFormat("#.#");
        texto = df.format(numero);
        if (language.equals("português")){
             texto = texto.replace(".",",");
        }
        return texto;
    }

    public static String converta(Date data){
        String texto="";
        String language = Locale.getDefault().getDisplayLanguage();
        SimpleDateFormat df;
        if (language.equals("português")){
            df = new SimpleDateFormat("dd/MM/yyyy");
        } else {
            df = new SimpleDateFormat("MM/dd/yyyy");
        }
        texto = df.format(data);
        return texto;
    }

}
