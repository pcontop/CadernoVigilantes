package br.com.pcontop.vigilantes.control.popups;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import br.com.pcontop.vigilantes.R;
import br.com.pcontop.vigilantes.control.ControleCaderno;
import br.com.pcontop.vigilantes.shared.bean.DiaSemanaReuniao;
import br.com.pcontop.vigilantes.view.Observer;

import java.text.ParseException;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Paulo
 * Date: 06/05/12
 * Time: 11:51
 * Pop-up para que o usuário defina o dia de semana da reunião, a partir da data corrente.
 * Depois do dia de semana ser definido, ele é gravado usando métodos do controller e o Observer passado é ativado.
 */
public class BusqueDiaSemanaReuniao extends MostreAlerta {
    private Date data;
    private int diaSemana=-1;

    public BusqueDiaSemanaReuniao(Context context, Observer observer, Date data, int diaSemana, ControleCaderno controleCaderno){
        super(context, observer, controleCaderno);
        this.data = data;
        this.diaSemana = diaSemana;
    }

    public BusqueDiaSemanaReuniao(Context context, Observer observer, Date data, ControleCaderno controleCaderno){
        super(context, observer, controleCaderno);
        this.data = data;
    }

    public void executar() {
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        final Spinner input = new Spinner(context);
        String[] dias = context.getResources().getStringArray(R.array.dias_semana);
        ArrayAdapter<String> diasAdapter = new ArrayAdapter<String>(context,android.R.layout.simple_spinner_item ,dias);
        input.setAdapter(diasAdapter);
        alert.setTitle(context.getText(R.string.selecione_dia));
        alert.setView(input);
        int diaSemanaSelecionado=0;
        if (diaSemana==-1){
            try {
                DiaSemanaReuniao diaSemanaReuniao = controleCaderno.getDiaSemanaReuniao(data);
                if (diaSemanaReuniao!=null){
                    diaSemanaSelecionado = diaSemanaReuniao.getDiaSemana()-1;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            diaSemanaSelecionado=diaSemana-1;
        }

        input.setSelection(diaSemanaSelecionado);

        alert.setPositiveButton(context.getText(R.string.definir),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        int diaReuniao = (int)input.getSelectedItemId();
                        //toast("diaSemanaReuniao definida para " + diaReuniao);
                        try {
                            controleCaderno.definaDiaSemanaReuniao(diaReuniao + 1, data);
                            controleCaderno.toast(context.getText(R.string.adicionar_limite).toString());
                            observer.activate();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                });

        alert.show();

    }
}
