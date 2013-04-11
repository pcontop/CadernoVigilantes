package br.com.pcontop.vigilantes.control;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.DatePicker;
import br.com.pcontop.vigilantes.R;
import br.com.pcontop.vigilantes.view.Observer;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created with IntelliJ IDEA.
 * User: Paulo
 * Date: 12/05/12
 * Time: 16:03
 * Pop-up que busca a Data Atual com o usuário. Usado em modo de debug para definir manualmente a data, para testes.
 * Após a ativação, grava a informação no singleton DataAtual e ativa o Observer passado.
 */
public class BusqueDataAtual extends MostreAlerta {

    public BusqueDataAtual(Context context, Observer observer, ControleCaderno controleCaderno){
        super(context, observer, controleCaderno);
    }

    @Override
    public void executar() {
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        final DatePicker datePicker = new DatePicker(context);
        alert.setTitle(context.getText(R.string.selecione_data));
        alert.setView(datePicker);
        alert.setPositiveButton(context.getText(R.string.definir),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        Calendar calendar = new GregorianCalendar(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth());
                        controleCaderno.definaDiaAtual(calendar.getTime());
                        observer.activate();
                    }
                });

        alert.show();

    }
}
