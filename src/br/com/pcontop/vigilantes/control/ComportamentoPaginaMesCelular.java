package br.com.pcontop.vigilantes.control;

import android.app.Activity;
import android.content.Intent;
import android.view.MotionEvent;
import android.view.View;
import br.com.pcontop.vigilantes.R;
import br.com.pcontop.vigilantes.view.PaginaDia;
import br.com.pcontop.vigilantes.view.PaginaMes;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created with IntelliJ IDEA.
 * User: Paulo
 * Date: 27/05/12
 * Time: 20:59
 * Define os comportamentos de troca de tela e gestos para a tela de PaginaMes, para o celular.
 */
public class ComportamentoPaginaMesCelular implements ComportamentoPaginaMes, View.OnTouchListener {
    private Activity activity;
    private ControleCaderno controleCaderno;
    private Date mes;
    private float downXValue=0;
    private final int MIN_SLIDE=100;

    @Override
    public void vaParaMesSeguinte() {
        GregorianCalendar calendar = controleCaderno.getCalendar(mes);
        calendar.add(Calendar.MONTH,1);
        Intent intent = new Intent(activity, PaginaMes.class);
        intent.putExtra("mes", calendar.getTime());
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.desliza_direita_centro, R.anim.desliza_centro_esquerda);
    }

    @Override
    public void vaParaMesAnterior() {
        GregorianCalendar calendar = controleCaderno.getCalendar(mes);
        calendar.add(Calendar.MONTH,-1);
        Intent intent = new Intent(activity, PaginaMes.class);
        intent.putExtra("mes", calendar.getTime());
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.desliza_esquerda_centro, R.anim.desliza_centro_direita);
    }

    @Override
    public void vaParaDia(Date data) {
        Intent intent = new Intent(activity, PaginaDia.class);
        intent.putExtra("dia", data);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.desliza_baixo_centro, R.anim.desliza_centro_cima);
    }

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

                // going backwards: pushing stuff to the right
                if ( currentX - downXValue > MIN_SLIDE)
                {
                    vaParaMesAnterior();
                }

                // going forwards: pushing stuff to the left
                if (downXValue - currentX > MIN_SLIDE)
                {
                    vaParaMesSeguinte();
                }

            }
        }
        return false;
    }

    public void setOnTouchListenerFor(View view){
        view.setOnTouchListener(this);
    }

    @Override
    public void inicializePaginaMes(ControleCaderno controleCaderno, Activity activity, Date data) {
        this.controleCaderno = controleCaderno;
        this.activity = activity;
        this.mes = data;
    }

}
