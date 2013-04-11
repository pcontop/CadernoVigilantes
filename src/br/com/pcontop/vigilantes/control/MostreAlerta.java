package br.com.pcontop.vigilantes.control;

import android.content.Context;
import br.com.pcontop.vigilantes.control.ControleCaderno;
import br.com.pcontop.vigilantes.control.FabricaControleCaderno;
import br.com.pcontop.vigilantes.view.Observer;

/**
 * Created with IntelliJ IDEA.
 * User: Paulo
 * Date: 12/05/12
 * Time: 16:06
 * Template para os pop-ups que serão utilizados nas telas. Segue o padrão observer. Cada pop-up é recebe um observer
 * que é ativado em algum momento (normalmente no final de processo), e cada pop-up é um Observer, que permite o
 * encadeamento de pop-ups.
 */
public abstract class MostreAlerta implements Observer {
    protected Context context;
    protected Observer observer;
    protected ControleCaderno controleCaderno;

    public MostreAlerta(Context context, Observer observer, ControleCaderno controleCaderno) {
        this.context = context;
        this.observer = observer;
        this.controleCaderno = controleCaderno;
        //this.controleCaderno = new FabricaControleCaderno(context).get();
    }

    public abstract void executar();

    @Override
    public void activate() {
        executar();
    }
}
