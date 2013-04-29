package br.com.pcontop.vigilantes.view;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import br.com.pcontop.vigilantes.R;
import br.com.pcontop.vigilantes.control.ConversaoLocale;
import br.com.pcontop.vigilantes.shared.bean.EntradaPontos;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Paulo
 * Date: 31/03/12
 * Time: 19:14
 * Define o adapter para a lista de EntradaPontos do dia que será apresentada. O layout básico está definido no xml
 * R.layout.pagina_dia_lista, e o nome, a quantidade de unidades e os pontos por unidade são apresentados, uma EntradaPontos por dia.
 */
public class ListaCadernoDiaAdapter extends BaseAdapter {

    private Activity activity;
    private List<EntradaPontos> entradas;

    public ListaCadernoDiaAdapter(Activity activity, List<EntradaPontos> entradas) {
        this.activity = activity;
        this.entradas = entradas;
    }
    
    @Override
    public int getCount() {
        return entradas.size(); 
    }

    @Override
    public Object getItem(int i) {
        return entradas.get(i);  
    }

    @Override
    public long getItemId(int i) {
        return entradas.get(i).getId(); 
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LinearLayout linearLayout = (LinearLayout)activity.getLayoutInflater().inflate(R.layout.pagina_dia_lista, null);
        EntradaPontos entradaPontos = entradas.get(i);

        TextView nome = (TextView) linearLayout.findViewById(R.id.caderno_lista_entrada);
        nome.setText(entradaPontos.getNome());
        
        TextView quantidade = (TextView) linearLayout.findViewById(R.id.caderno_quantidade);
        quantidade.setText(entradaPontos.getQuantidade()+ "");

        TextView pontos = (TextView) linearLayout.findViewById(R.id.caderno_lista_pontos);
        TextView pontosMultiplicados = (TextView) linearLayout.findViewById(R.id.caderno_lista_pontos_multiplicados);

        pontos.setText(ConversaoLocale.converta(entradaPontos.getPontos()));
        quantidade.setText(ConversaoLocale.converta(entradaPontos.getQuantidade()));
        pontosMultiplicados.setText(ConversaoLocale.converta(entradaPontos.getPontosMultiplicados()));

        if (i%2==0){
            nome.setBackgroundResource(R.drawable.caderno_entradas_brancas);
            quantidade.setBackgroundResource(R.drawable.caderno_entradas_brancas);
            pontos.setBackgroundResource(R.drawable.caderno_entradas_brancas);
            pontosMultiplicados.setBackgroundResource(R.drawable.caderno_entradas_brancas);
        } else {
            nome.setBackgroundResource(R.drawable.caderno_entradas_cinzas);
            quantidade.setBackgroundResource(R.drawable.caderno_entradas_cinzas);
            pontos.setBackgroundResource(R.drawable.caderno_entradas_cinzas);
            pontosMultiplicados.setBackgroundResource(R.drawable.caderno_entradas_cinzas);
        }

        return linearLayout;
    }
}
