package br.com.pcontop.vigilantes.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.*;
import android.widget.*;
import br.com.pcontop.vigilantes.R;
import br.com.pcontop.vigilantes.control.*;
import br.com.pcontop.vigilantes.control.comportamento.ComportamentoPaginaDia;
import br.com.pcontop.vigilantes.control.validacao.ValidacaoCampo;
import br.com.pcontop.vigilantes.control.validacao.ValidacaoCampoDoubleNaoVazio;
import br.com.pcontop.vigilantes.control.validacao.ValidacaoCampoTextoNaoVazio;
import br.com.pcontop.vigilantes.model.bean.EntradaPontos;
import br.com.pcontop.vigilantes.model.bean.LimitePontos;
import com.google.inject.Inject;
import roboguice.inject.InjectView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Tela que apresenta uma lista com as EntradaPontos do dia, assim como os totais. Mostra também os limites de pontos
 * do dia (total e livres), apresentando os totais de ponto em um código de cor (verde - dentro dos pontos do dia,
 * amarelo - acima dos pontos do dia, dentro dos pontos livres da semana, vermelho - acima dos pontos do dia e dos pontos livres).
 * Possui um botão que leva a tela de calendário, mas isto irá mudar na apresentação com os tablets.
 *
 * É possível navegar para os dias anteriores e seguintes com as setas direcionais no título, assim como é possível
 * navegar utilizando gestos. Outra forma é utilizar a PaginaMes para escolher outra data.
 */
public class PaginaDia extends PaginaSistema implements Toaster
{
    private Date dataCaderno;
    private EntradaPontos entradaPontos;
    private List<EntradaPontos> entradasPontos;
    private boolean modoEditar = false;
    @InjectView(R.id.linear_calendario)
    LinearLayout botaoCalendario;
    @InjectView(R.id.caderno_lista_dia)
    ListView listaEntradas;
    @InjectView(R.id.caderno_entrada)
    AutoCompleteTextView entrada;
    @InjectView(R.id.caderno_quantidade)
    EditText quantidade;
    @InjectView(R.id.caderno_pontos)
    EditText pontos;
    @InjectView(R.id.caderno_total)
    TextView vTotal;
    @InjectView(R.id.caderno_data)
    TextView textoData;
    @InjectView(R.id.caderno_adicionar)
    ImageView imagemAdicionar;
    @InjectView(R.id.linear_salvar)
    LinearLayout botaoSalvar;
    @InjectView(R.id.img_calendario)
    ImageView imagemCalendario;
    @InjectView(R.id.caderno_limite_dia)
    TextView textoLimiteDia;
    @InjectView(R.id.pagina_dia_anterior)
    ImageView botaoDiaAnterior;
    @InjectView(R.id.pagina_dia_posterior)
    ImageView botaoDiaPosterior;
    @InjectView(R.id.pagina_layout_dia)
    LinearLayout paginaDia;

    @Inject
    ComportamentoPaginaDia comportamentoPaginaDia;

    @Inject
    ControleCaderno controleCaderno;


    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //ContextoGlobal.setContext(this);
        setContentView(R.layout.pagina_dia);
        inicializePagina();
        chequeSeDiaSemanaReuniaoEstaDefinido();
    }

    private void inicializePagina() {
        getDataCadernoFromIntent();
        apresenteData();
        inicializeComportamentosPagina();
    }

    private void chequeSeDiaSemanaReuniaoEstaDefinido() {
        controleCaderno.chequeSeDiaSemanaReuniaoEstaDefinido(this);
    }

    private void inicializeComportamentoPaginaDia() {
        comportamentoPaginaDia.inicializePaginaDia(controleCaderno, this, dataCaderno);
    }

    private void getDataCadernoFromIntent() {
        dataCaderno = (Date) getIntent().getSerializableExtra("dia");
        if (dataCaderno==null){
            dataCaderno = DataAtual.getDataAtual();
        }
    }

    private void vaParaDiaAnterior(){
        comportamentoPaginaDia.vaParaDiaAnterior();
    }

    private void vaParaDiaSeguinte(){
        comportamentoPaginaDia.vaParaDiaSeguinte();
    }


    private void toast(String mensagem){
        controleCaderno.toast(mensagem);
    }

    private void inicializeComportamentosPagina() {
        inicializeComportamentoPaginaDia();
        adicioneComportamentoAdicionar();
        adicioneComportamentoBotoes();
        adicioneComportamentoEntradas();
        adicioneComportamentoTouch();
    }

    private void adicioneComportamentoTouch() {
        ArrayList<View> listaViews = new ArrayList<View>();
        listaViews.add(paginaDia);
        listaViews.add(listaEntradas);
        //listaViews.add(linearDia);
        comportamentoPaginaDia.adicioneComportamentoTouch(listaViews);
    }

    private void adicioneComportamentoEntradas() {

        View.OnKeyListener enterListenerEntrada = new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                int keyCode = keyEvent.getKeyCode();
                int keyAction = keyEvent.getAction();
                if (keyCode == KeyEvent.KEYCODE_ENTER){
                    if (keyAction==KeyEvent.ACTION_DOWN){
                        quantidade.requestFocus();
                    }
                    return true;
                }
                return false;
            }
        };

        View.OnKeyListener enterListenerQuantidade = new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                int keyCode = keyEvent.getKeyCode();
                int keyAction = keyEvent.getAction();
                if (keyCode == KeyEvent.KEYCODE_ENTER){
                    if (keyAction==KeyEvent.ACTION_DOWN){
                        if (modoEditar){
                            comportamentoAdicioneOuEditeEntrada();
                        } else {
                            pontos.requestFocus();
                        }
                    }
                    return true;
                }
                return false;
            }
        };

        View.OnKeyListener enterListenerPontos = new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                int keyCode = keyEvent.getKeyCode();
                int keyAction = keyEvent.getAction();
                if (keyCode == KeyEvent.KEYCODE_ENTER){
                    if (keyAction==KeyEvent.ACTION_DOWN){
                        comportamentoAdicioneOuEditeEntrada();
                    }
                    return true;
                }
                return false;  //To change body of implemented methods use File | Settings | File Templates.
            }
        };

        entrada.setFocusable(true);
        quantidade.setFocusable(true);
        pontos.setFocusable(true);
        entrada.setOnKeyListener(enterListenerEntrada);
        adicioneAutoCompleteEntrada();
        quantidade.setOnKeyListener(enterListenerQuantidade);
        pontos.setOnKeyListener(enterListenerPontos);
    }

    private void adicioneAutoCompleteEntrada() {
        String[] entradas = controleCaderno.pesquiseNomesEntradasPontos();
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.lista_autocomplete_item, entradas);
        entrada.setAdapter(adapter);
        entrada.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String nomeEntrada = adapter.getItem(i);
                EntradaPontos entradaPontos1 = controleCaderno.pesquiseEntradaPontosPorNome(nomeEntrada);
                // Resolvendo bug de valores decimais.
                String language = Locale.getDefault().getDisplayLanguage();
                if (language.equals("português")){
                    pontos.setText((entradaPontos1.getPontos() + "").replace(".",","));
                }   else {
                    pontos.setText(entradaPontos1.getPontos() + "");
                }
                quantidade.requestFocus();
            }
        });
    }

    private void vaParaPaginaMes(){
        comportamentoPaginaDia.vaParaMes();
    }

    private void adicioneComportamentoBotoes() {
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vaParaPaginaMes();
            }
        };

        botaoCalendario.setOnClickListener(onClickListener);
        imagemCalendario.setOnClickListener(onClickListener);

        botaoDiaAnterior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vaParaDiaAnterior();
            }
        });

        botaoDiaPosterior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vaParaDiaSeguinte();
            }
        });

    }

    public void onStart(){
        super.onStart();
        apresenteListaEntradasPontos();
    }

    private void apresenteData(){

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        String texto = getString(R.string.dataInsercao);

        texto +=  " " + format.format(dataCaderno);
        textoData.setText(texto);

    }

    private void apresenteListaEntradasPontos() {
        busqueListaEntradasPontos();

        final ListaCadernoDiaAdapter adapter = new ListaCadernoDiaAdapter(this, entradasPontos);
        listaEntradas.setAdapter(adapter);
        Utility.setListViewHeightBasedOnChildren(listaEntradas);
        listaEntradas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //noinspection unchecked
                entradaPontos = (EntradaPontos) adapter.getItem(i);
                editeEntradaSelecionada();
            }
        });

        listaEntradas.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                entradaPontos = (EntradaPontos) adapter.getItem(i);
                registerForContextMenu(listaEntradas);
                return false;
            }
        }) ;

        apresenteTotalizacoes();
    }

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.equals(listaEntradas)) {
            infleMenuEntrada(menu, v, menuInfo);
        }
    }

    public boolean onContextItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case R.id.menuEntradaEdita:
                editeEntradaSelecionada();
                break;
            case R.id.menuEntradaRemove:
                removaEntradaSelecionada();
                break;
        }
        return false;
    }

    private void entreModoEditar(){
        modoEditar = true;
        imagemAdicionar.setImageResource(R.drawable.folder);
    }

    private void editeEntradaSelecionada() {
        entreModoEditar();
        entrada.setText(entradaPontos.getNome()+"");
        quantidade.setText(entradaPontos.getQuantidade()+"");
        pontos.setText(entradaPontos.getPontos()+"");
    }


    private void infleMenuEntrada(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_entrada,menu);
    }

    private void removaEntradaSelecionada(){
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle(getText(R.string.deletar))
                .setMessage(getText(R.string.confirma_delecao))
                .setPositiveButton(getText(R.string.quero),
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (modoEditar) {
                                    saiaModoEditar();
                                    limpeCampos();
                                }
                                apagueEntradaSelecionada();
                            }
                        }).setNegativeButton(getText(R.string.nao), null).show();
    }
    
    private void apagueEntradaSelecionada(){
        controleCaderno.deletarEntrada(entradaPontos);
        apresenteListaEntradasPontos();
    }

    private void busqueListaEntradasPontos() {
        entradasPontos = controleCaderno.getEntradasPontosData(dataCaderno);
    }

    private void apresenteTotalizacoes(){
        apresenteTotalPontosEntradas();
        apresenteLimitesPontos();
    }

    //TODO - Migrar lógica para controle.
    private void apresenteLimitesPontos(){
        try {
            LimitePontos limitePontos;
            limitePontos = controleCaderno.getLimitePontos(dataCaderno);
            if (limitePontos!=null){
                long pontosDia = limitePontos.getPontosDia();
                long pontosExtras= controleCaderno.getPontosExtras(dataCaderno);
                String textoLimitePontos=getString(R.string.limite_pontos_dia);
                textoLimitePontos+= " " + pontosDia + "(+" + pontosExtras + ")";
                textoLimiteDia.setText(textoLimitePontos);
                definaCorTotalPontosEntradasAutomatico();
            } else {
                if (controleCaderno.isDataEmPeriodoLimiteAlteravel(dataCaderno)){
                    textoLimiteDia.setText(getString(R.string.defina_metas));
                    textoLimiteDia.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            busqueLimitePontos();
                        }
                    });
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void definaCorTotalPontosEntradasAutomatico(){
        int cor = controleCaderno.getCorData(dataCaderno);
        if (cor== Color.YELLOW){
            cor=Color.parseColor("#FFD700");
        }
        if (cor==Color.GREEN){
            cor=Color.parseColor("#32CD32");
        }
        vTotal.setTextColor(cor);
    }

    private void apresenteTotalPontosEntradas() {
        double totalPontos = controleCaderno.getPontosDia(dataCaderno);
        String textoTotalPontos = ConversaoLocale.converta(totalPontos);
        String strTotal = (String) getText(R.string.total);
        vTotal.setText(strTotal + " " + textoTotalPontos);
    }

    private void adicioneComportamentoAdicionar() {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                comportamentoAdicioneOuEditeEntrada();
            }
        };
        botaoSalvar.setOnClickListener(listener);
        imagemAdicionar.setOnClickListener(listener);
    }

    private void comportamentoAdicioneOuEditeEntrada(){
        boolean resultadoOk;
        if (modoEditar) {
            resultadoOk = editeEntradaPontosNaBase();
        } else {
            resultadoOk = adicioneEntradaPontosNaBase();
        }
        if (resultadoOk){
            limpeCampos();
            apresenteListaEntradasPontos();
        }
    }

    private boolean editeEntradaPontosNaBase() {
        if (atualizeEntradaPontos(entradaPontos)){
            saiaModoEditar();
            return true;
        }
        return false;
    }

    private void saiaModoEditar() {
        modoEditar= false;
        imagemAdicionar.setImageResource(R.drawable.plus);
    }

    private void limpeCampos(){
        entrada.setText("");
        quantidade.setText("");
        pontos.setText("");
        entrada.requestFocus();
    }

    private boolean adicioneEntradaPontosNaBase(){
        return atualizeEntradaPontos(new EntradaPontos());
    }

    /**
     * Faz um parse de um campo com valor numérico double, no formato americano ou brasileiro, e retorna
     * o valor.
     * @param campo  Campo descritivo de um double, formato inglês ou português.
     * @return  O valor double do campo String
     */
    private Double parseCampoDouble(String campo){
        Double dblValor;
        try {
            dblValor = Double.parseDouble(campo);
        } catch (NumberFormatException e){
            dblValor = Double.parseDouble(campo.replace(",","."));
        }
        return dblValor;
    }

    private boolean atualizeEntradaPontos(EntradaPontos entradaPontos) {
        if (!valideCampos()){
            return false;
        }
        String nome = entrada.getText().toString();
        Double numeroQuantidade = parseCampoDouble(quantidade.getText().toString());
        Double numeroPontos = parseCampoDouble(pontos.getText().toString());

        entradaPontos.setNome(nome);
        entradaPontos.setQuantidade(numeroQuantidade);
        entradaPontos.setPontos(numeroPontos);
        entradaPontos.definaDataInsercaoInicial(dataCaderno);

        controleCaderno.insiraOuAtualizeEntradaPontos(entradaPontos);
        return true;
    }

    private boolean valideCampos(){
        ArrayList<ValidacaoCampo> validacoes = new ArrayList<ValidacaoCampo>();

        validacoes.add(new ValidacaoCampoTextoNaoVazio(entrada, R.string.alarme_nome, this));
        validacoes.add(new ValidacaoCampoDoubleNaoVazio(quantidade, R.string.alarme_quantidade, this));
        validacoes.add(new ValidacaoCampoDoubleNaoVazio(pontos, R.string.alarme_pontos, this));

        for (ValidacaoCampo validacaoCampo:validacoes){
            if (!validacaoCampo.valide()){
                return false;
            }
        }

        return true;

    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuInflater = getMenuInflater();
        if (ContextoGlobal.debugMode){
            menuInflater.inflate(R.menu.pagina_dia_debug, menu);
        } else {
            menuInflater.inflate(R.menu.pagina_dia, menu);
        }
        return true;
        
    }

    public boolean onOptionsItemSelected(MenuItem menuItem){
        if (menuItem.getItemId()==R.id.definir_meta){
            busqueLimitePontos();
        }
        if (menuItem.getItemId()==R.id.selecione_dia){
            setDiaSemanaReuniao();
        }
        if (menuItem.getItemId()==R.id.sobre){
            mostreSobre();
        }
        if (menuItem.getItemId()==R.id.defina_data_atual){
            forceDataAtual();
        }
        if (menuItem.getItemId()==R.id.exportar_excel){
            exporteExcel();
        }

        return false;
    }

    private void exporteExcel() {
        controleCaderno.exporteExcelEntradasPontos();
    }

    private void busqueLimitePontos() {
        controleCaderno.busqueLimitePontos(this, dataCaderno);
    }

    private void forceDataAtual() {
        controleCaderno.busqueDataAtual(this);

    }

    private void mostreSobre(){
        controleCaderno.mostreSobre(this);
    }

    /*
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
                return false;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
    */

    @Override
    public void onBackPressed(){
        if (modoEditar){
            saiaModoEditar();
            limpeCampos();
        } else {
            finish();

        }
    }

    private void setDiaSemanaReuniao(){
        controleCaderno.busqueDiaSemanaReuniao(this);
    }

    public void activate(){
        toast(R.string.item_salvo);
        apresenteTotalizacoes();
        //apresenteLimitesPontos();
    }

    @Override
    public void toast(int messageId) {
        toast(getString(messageId));
    }

    public ControleCaderno getControleCaderno(){
        return controleCaderno;
    }
}
