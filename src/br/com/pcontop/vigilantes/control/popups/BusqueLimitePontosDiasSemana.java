package br.com.pcontop.vigilantes.control.popups;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import br.com.pcontop.vigilantes.R;
import br.com.pcontop.vigilantes.control.ControleCaderno;
import br.com.pcontop.vigilantes.control.ConversaoLocale;
import br.com.pcontop.vigilantes.model.SemanaOcupadaException;
import br.com.pcontop.vigilantes.model.bean.LimitePontos;
import br.com.pcontop.vigilantes.view.NumberPicker;
import br.com.pcontop.vigilantes.view.Observer;

import java.text.ParseException;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Paulo
 * Date: 28/04/12
 * Time: 19:10
 * Pop-up que busca com o usuário o limite de pontos por dia da semana atual do usuário. Após a definição, grava a
 * informação usando métodos do controlador e ativa o Observer passado.
 */
public class BusqueLimitePontosDiasSemana extends MostreAlerta {
    private Date dataDestino;

    public BusqueLimitePontosDiasSemana(Context context, Observer observer, Date dataDestino, ControleCaderno controleCaderno){
        super(context, observer, controleCaderno);
        this.dataDestino = dataDestino;
    }

    public void executar() {
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        final NumberPicker inputMeta = new NumberPicker(context);

        alert.setTitle(R.string.definir_meta);
        alert.setView(inputMeta);
        LimitePontos limitePontos = controleCaderno.getLimiteSemanaAtualOuProxima(dataDestino);
        final LimitePontos limitePontosRef = limitePontos;
        final Date dataInicio = limitePontos.getDataInicio();
        int pontosDia = limitePontos.getPontosDia();
        if (pontosDia!=-1){
            //inputMeta.setText(pontosDia + "");
            inputMeta.setCurrent(pontosDia);
        }
        alert.setPositiveButton(context.getText(R.string.definir),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        Integer valorDiaAtual = inputMeta.getCurrent();
                        limitePontosRef.setPontosDia(valorDiaAtual);
                        try {
                            try {
                                controleCaderno.definaLimitePontos(limitePontosRef);
                                if (dataInicio.after(controleCaderno.getDataAtual())){
                                    String textoData = context.getText(R.string.semana_adiada) + ConversaoLocale.converta(dataInicio);
                                    controleCaderno.toast(textoData);
                                }
                            } catch (ParseException e) {
                                e.printStackTrace();
                                return;
                            }
                            observer.activate();
                        } catch (SemanaOcupadaException e) {
                            e.printStackTrace();
                        }
                    }
                });

        AlertDialog dialog = alert.create();
        dialog.show();
        dialog.getWindow().setLayout(300, 600);
    }

}
