package br.com.pcontop.vigilantes.control.arquivos;

import android.content.Context;
import br.com.pcontop.vigilantes.R;
import com.google.inject.Inject;

/**
 * Created with IntelliJ IDEA.
 * User: Paulo
 * Date: 13/04/13
 * Time: 20:21
 * To change this template use File | Settings | File Templates.
 */
public class NomesDeArquivosExcel implements NomesDeArquivos {

    private Context context;

    private final String SUFIXO = ".xls";

    @Inject
    public NomesDeArquivosExcel(Context context){
        this.context = context;
    }

    @Override
    public String nomeArquivoPadrao() {
        String prefixo = context.getText(R.string.nome_arquivo_excel).toString();
        return prefixo + SUFIXO;
    }
}
