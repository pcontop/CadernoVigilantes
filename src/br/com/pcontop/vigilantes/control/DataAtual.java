package br.com.pcontop.vigilantes.control;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created with IntelliJ IDEA.
 * User: Paulo
 * Date: 12/05/12
 * Time: 16:35
 * Esta classe define a data atual do sistema. Normalmente, ela é igual a data atual, mas ela possui métodos para se
 * forçar a data, durante testes da aplicação.
 *
 */
public class DataAtual {
    private static Date dataAtual;

    private static Date getDataAtualFlat(){
        Calendar calendar = GregorianCalendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int date1 = calendar.get(Calendar.DAY_OF_MONTH);
        calendar = new GregorianCalendar(year, month, date1);
        Date time = calendar.getTime();
        return time;
    }

    public static Date getDataAtual(){
        if (dataAtual==null){
            return getDataAtualFlat();
        }
        return dataAtual;
    }

    public static void forceDataAtual(Date dataAtual){
        DataAtual.dataAtual=dataAtual;
    }
}
