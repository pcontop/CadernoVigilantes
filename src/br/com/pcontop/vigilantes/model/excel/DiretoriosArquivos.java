package br.com.pcontop.vigilantes.model.excel;

import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: Paulo
 * Date: 12/04/13
 * Time: 19:47
 * To change this template use File | Settings | File Templates.
 */
public interface DiretoriosArquivos {
    public File getDiretorioGravacaoArquivo() throws ImpossivelCriarDiretorioException;
}
