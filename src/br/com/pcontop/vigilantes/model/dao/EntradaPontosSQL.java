package br.com.pcontop.vigilantes.model.dao;

import br.com.pcontop.vigilantes.model.bean.EntradaPontos;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Paulo
 * Date: 10/05/12
 * Time: 22:11
 * Define os métodos de acesso a informações de base de EntradaPontos.
 */
public interface EntradaPontosSQL {

    long getMaxId();

    List<EntradaPontos> getLista(Date date);

    void removaEntrada(EntradaPontos entradaPontos);

    void insiraOuAtualize(EntradaPontos entradaPontos);

    ArrayList<EntradaPontos> pesquiseEntradasComNome(String nomeEntrada);

    ArrayList<EntradaPontos> pesquiseTodasEntradas();

    void close();

}
