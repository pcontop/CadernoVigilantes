package br.com.pcontop.vigilantes.model.excel;

import br.com.pcontop.vigilantes.model.bean.EntradaPontos;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Cria excel para exportação.
 * User: Paulo
 * Date: 11/04/13
 * Time: 21:08
 */
public interface CrieExcel {
    public void criePlanilha(List<EntradaPontos> entradas, String fileName) throws IOException, ImpossivelCriarDiretorioException;
    public File getUltimaPlanilha();
}
