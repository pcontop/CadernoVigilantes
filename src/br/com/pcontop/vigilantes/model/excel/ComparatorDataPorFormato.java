package br.com.pcontop.vigilantes.model.excel;

import android.util.Log;
import br.com.pcontop.vigilantes.control.ConversaoLocale;

import java.text.ParseException;
import java.util.Comparator;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Paulo
 * Date: 13/04/13
 * Time: 14:44
 * To change this template use File | Settings | File Templates.
 */
public class ComparatorDataPorFormato implements Comparator<String> {
    @Override
    public int compare(String s, String s2) {
        Date date1;
        Date date2;
        try {
            date1 = ConversaoLocale.parseData(s);
            date2 = ConversaoLocale.parseData(s2);
        } catch (ParseException e) {
            Log.e(getClass().getName(),"Erro na conversão de data", e);
            return 0;
        }
        return date1.compareTo(date2);
    }
}
