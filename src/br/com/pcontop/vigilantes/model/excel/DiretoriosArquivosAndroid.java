package br.com.pcontop.vigilantes.model.excel;

import android.content.Context;
import android.os.Environment;
import br.com.pcontop.vigilantes.R;
import com.google.inject.Inject;

import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: Paulo
 * Date: 12/04/13
 * Time: 16:44
 * To change this template use File | Settings | File Templates.
 */
public class DiretoriosArquivosAndroid implements DiretoriosArquivos {

    private final Context context;

    @Inject
    public DiretoriosArquivosAndroid(Context context){
        this.context=context;
    }

    public File getDiretorioGravacaoArquivo() throws ImpossivelCriarDiretorioException {
        String nomeAplicacao = context.getText(R.string.nome_arquivo_excel).toString();
        String dirPath = Environment.getExternalStorageDirectory().toString() + "/download/" + nomeAplicacao;
        File root = new File(dirPath);
        if (!root.exists()&&!root.mkdirs()){
            throw new ImpossivelCriarDiretorioException(root.getAbsolutePath());
        }
        return root;
    }
}
