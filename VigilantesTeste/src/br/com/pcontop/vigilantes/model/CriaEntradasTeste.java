package br.com.pcontop.vigilantes.model;

import br.com.pcontop.vigilantes.model.bean.EntradaPontos;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Paulo
 * Date: 17/04/13
 * Time: 14:53
 * To change this template use File | Settings | File Templates.
 */
public class CriaEntradasTeste {

    private Date parseDate(String date){
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        try {
            return df.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            return null;
        }
    }

    private List<EntradaPontos> listaEntradas;


    public List<EntradaPontos> crieEntradasPontos(){
        if (listaEntradas==null){

            Date date = parseDate("15-08-1974");
            listaEntradas = new ArrayList<EntradaPontos>();
            EntradaPontos entradaPontos = new EntradaPontos();
            entradaPontos.setDataInsercao(date);
            entradaPontos.setNome("Feijão");
            entradaPontos.setQuantidade(1.1);
            entradaPontos.setPontos(2.0);
            entradaPontos.setId(0);
            listaEntradas.add(entradaPontos);

            entradaPontos = new EntradaPontos();
            entradaPontos.setDataInsercao(date);
            entradaPontos.setNome("Arroz");
            entradaPontos.setQuantidade(3.5);
            entradaPontos.setPontos(1.1);
            listaEntradas.add(entradaPontos);
            entradaPontos.setId(1);

            date = parseDate("15-09-1974");

            entradaPontos = new EntradaPontos();
            entradaPontos.setDataInsercao(date);
            entradaPontos.setNome("Feijão");
            entradaPontos.setQuantidade(3.1);
            entradaPontos.setPontos(2.0);
            entradaPontos.setId(3);
            listaEntradas.add(entradaPontos);

            entradaPontos = new EntradaPontos();
            entradaPontos.setDataInsercao(date);
            entradaPontos.setNome("Arroz");
            entradaPontos.setQuantidade(4.5);
            entradaPontos.setPontos(1.1);
            listaEntradas.add(entradaPontos);
            entradaPontos.setId(2);

            date = parseDate("14-08-1974");

            entradaPontos = new EntradaPontos();
            entradaPontos.setDataInsercao(date);
            entradaPontos.setNome("Feijão");
            entradaPontos.setQuantidade(3.1);
            entradaPontos.setPontos(2.0);
            entradaPontos.setId(4);
            listaEntradas.add(entradaPontos);

            entradaPontos = new EntradaPontos();
            entradaPontos.setDataInsercao(date);
            entradaPontos.setNome("Arroz");
            entradaPontos.setQuantidade(4.5);
            entradaPontos.setPontos(1.1);
            listaEntradas.add(entradaPontos);
            entradaPontos.setId(5);
        }

        return listaEntradas;
    }

    public EntradaPontos getEntradaPontos(long id){
        for (EntradaPontos entradaPontos: crieEntradasPontos()){
            if (entradaPontos.getId()==id){
                return entradaPontos;
            }
        }
        return null;
    }

    public EntradaPontos getEntradaPontos(EntradaPontos entradaPontos){
        return getEntradaPontos(entradaPontos.getId());
    }

}
