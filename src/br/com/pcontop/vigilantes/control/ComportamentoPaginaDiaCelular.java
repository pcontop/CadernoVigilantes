package br.com.pcontop.vigilantes.control;

import android.app.Activity;
import android.content.Intent;
import android.view.MotionEvent;
import android.view.View;
import br.com.pcontop.vigilantes.R;
import br.com.pcontop.vigilantes.view.PaginaDia;
import br.com.pcontop.vigilantes.view.PaginaMes;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created with IntelliJ IDEA.
 * User: Paulo
 * Date: 27/05/12
 * Time: 20:40
 * Define os comportamentos de troca de tela e gestos para a tela de PaginaDia, para o celular.
 */
public class ComportamentoPaginaDiaCelular implements ComportamentoPaginaDia, View.OnTouchListener {
    private Activity activity;
    private ControleCaderno controleCaderno;
    private Date data;
    private float downXValue=0;
    private final int MIN_SLIDE=100;

    @Override
    public void inicializePaginaDia (ControleCaderno controleCaderno, Activity activity, Date data){
       this.controleCaderno = controleCaderno;
       this.activity = activity;
       this.data = data;
   }

    @Override
    public void vaParaDiaSeguinte() {
        GregorianCalendar calendar = controleCaderno.getCalendar(data);
        calendar.add(Calendar.DATE,1);
        Intent intent = new Intent(activity, PaginaDia.class);
        intent.putExtra("dia", calendar.getTime());
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.desliza_direita_centro, R.anim.desliza_centro_esquerda);
    }

    @Override
    public void vaParaDiaAnterior() {
        GregorianCalendar calendar = controleCaderno.getCalendar(data);
        calendar.add(Calendar.DATE,-1);
        Intent intent = new Intent(activity, PaginaDia.class);
        intent.putExtra("dia", calendar.getTime());
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.desliza_esquerda_centro, R.anim.desliza_centro_direita);
    }

    @Override
    public void vaParaMes() {
        Intent intent = new Intent(activity, PaginaMes.class);
        intent.putExtra("mes", data);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.desliza_cima_centro, R.anim.desliza_centro_baixo);
    }

    @Override
    public boolean onTouch(View arg0, MotionEvent arg1) {
        switch (arg1.getAction()){
            case MotionEvent.ACTION_DOWN:
            {
                // store the X value when the user's finger was pressed down
                downXValue = arg1.getX();
                break;
            }

            case MotionEvent.ACTION_UP:
            {
                // Get the X value when the user released his/her finger
                float currentX = arg1.getX();
                float currentY = arg1.getY();

                // going backwards: pushing stuff to the right
                if ( currentX - downXValue > MIN_SLIDE)
                {
                    vaParaDiaAnterior();
                }

                // going forwards: pushing stuff to the left
                if (downXValue - currentX > MIN_SLIDE)
                {
                    vaParaDiaSeguinte();
                }


            }
        }
        return false;
    }

    @Override
    public void adicioneComportamentoTouch(ArrayList<View> views) {
        for (View view:views){
            view.setOnTouchListener(this);
        }
    }

}
