package br.com.pcontop.vigilantes.control.validacao;


import android.widget.TextView;
import br.com.pcontop.vigilantes.view.Toaster;

/**
 * Created with IntelliJ IDEA.
 * User: Paulo
 * Date: 10/04/13
 * Time: 21:04
 * To change this template use File | Settings | File Templates.
 */
public class ValidacaoCampoTextoNaoVazio implements ValidacaoCampo {
    private TextView view;
    private int codigoMensagem;
    private Toaster toaster;

    public ValidacaoCampoTextoNaoVazio(TextView view, int codigoMensagem, Toaster toaster){
        this.view=view;
        this.codigoMensagem=codigoMensagem;
        this.toaster = toaster;
    }

    @Override
    public boolean valide() {
        if (view.getText().toString().trim().equals("")){
            toaster.toast(codigoMensagem);
            view.requestFocus();
            return false;
        }
        return true;
    }
}
