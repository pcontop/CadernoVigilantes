package br.com.pcontop.vigilantes.control;

import java.text.DecimalFormat;
import java.text.ParseException;
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
        String texto;
        String language = Locale.getDefault().getDisplayLanguage();
        DecimalFormat df = new DecimalFormat("#.#");
        texto = df.format(numero);
        if (language.equals("português")){
             texto = texto.replace(".",",");
        }
        return texto;
    }

    private static SimpleDateFormat getDateFormat(){
        String language = Locale.getDefault().getDisplayLanguage();
        SimpleDateFormat df;
        if (language.equals("português")){
            df = new SimpleDateFormat("dd/MM/yyyy");
        } else {
            df = new SimpleDateFormat("MM/dd/yyyy");
        }
        return df;
    }

    private static SimpleDateFormat getDateTimeFormat(){
        String language = Locale.getDefault().getDisplayLanguage();
        SimpleDateFormat df;
        if (language.equals("português")){
            df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        } else {
            df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        }
        return df;
    }


    public static String converta(Date data){
        String texto;
        SimpleDateFormat df = getDateFormat();
        texto = df.format(data);
        return texto;
    }

    public static Date parseData(String data) throws ParseException {
        SimpleDateFormat df = getDateFormat();
        return df.parse(data);
    }

    public static String convertaDataHora(Date date) {
        return getDateTimeFormat().format(date);
    }
}
