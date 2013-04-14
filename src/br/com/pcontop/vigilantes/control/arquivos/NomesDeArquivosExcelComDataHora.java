package br.com.pcontop.vigilantes.control.arquivos;

import android.content.Context;
import br.com.pcontop.vigilantes.R;
import br.com.pcontop.vigilantes.control.ConversaoLocale;
import com.google.inject.Inject;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Paulo
 * Date: 13/04/13
 * Time: 20:21
 * To change this template use File | Settings | File Templates.
 */
public class NomesDeArquivosExcelComDataHora implements NomesDeArquivos {

    private Context context;

    private final String SUFIXO = ".xls";
    private final String SEPARADOR = "-";

    @Inject
    public NomesDeArquivosExcelComDataHora(Context context){
        this.context = context;
    }

    @Override
    public String nomeArquivoPadrao() {
        String prefixo = context.getText(R.string.nome_arquivo_excel).toString();
        String dataHora = ConversaoLocale.convertaDataHora(new Date()).replace("/","").replace(":","").replace(" ","");
        return prefixo + SEPARADOR + dataHora + SUFIXO;
    }
}
