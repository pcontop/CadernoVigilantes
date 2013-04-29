package br.com.pcontop.vigilantes.control;

import android.content.Context;
import br.com.pcontop.vigilantes.control.popups.ControlePopups;
import br.com.pcontop.vigilantes.shared.bean.EntradaPontos;
import br.com.pcontop.vigilantes.shared.bean.LimitePontos;
import br.com.pcontop.vigilantes.view.PaginaSistema;

import java.io.File;
import java.util.Date;
import java.util.List;

/**
 * Definição dos métodos de regras de apresentação. Recebem um pedido da View e verificam nos métodos de regras de negócios, retornando a view
 * resultados e comandos.
 * User: Paulo
 * Date: 19/04/13
 * Time: 20:50
 */
public interface ControleRegrasApresentacao {
    void toast(String mensagem);

    void toast(int resourceId);

    int getCorData(Date data);

    void definaDiaAtual(Date time);

    void chequeSeDiaSemanaReuniaoEstaDefinido(PaginaSistema paginaSistema, ControlePopups controlePopups, ControleCaderno controleCaderno);

    LimitePontos getLimitePontosOuNovo(Date dataDestino);

    LimitePontos getLimiteSemanaAtualOuProxima(Date dataDestino);

    EntradaPontos pesquiseEntradaPontosPorNome(String entrada);

    void exporteExcel(List<EntradaPontos> entradas);

    File getUltimaPlanilhaCriada();

    Context getContext();
}
