package br.com.pcontop.vigilantes.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import br.com.pcontop.vigilantes.R;
import br.com.pcontop.vigilantes.control.ControleCaderno;
import br.com.pcontop.vigilantes.control.MostreAlerta;

/**
 * Created with IntelliJ IDEA.
 * User: Paulo
 * Date: 12/05/12
 * Time: 12:46
 * Apresenta um pop-up com o Sobre do aplicativo. Mostra informações do criador, versão.
 */
public class MostreSobre extends MostreAlerta {

    public MostreSobre(Context context, Observer observer, ControleCaderno controleCaderno){
        super(context, observer, controleCaderno);
    }

    @Override
    public void executar(){
        AlertDialog alertDialog;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.sobre,
                null);
        TextView viewVersao = (TextView) layout.findViewById(R.id.sobre_versao);
        String versao="";
        try {
            versao = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        viewVersao.setText(versao);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(layout);
        alertDialog = builder.create();
        alertDialog.show();
    }
}
