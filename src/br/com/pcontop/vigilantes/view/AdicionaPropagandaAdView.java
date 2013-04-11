package br.com.pcontop.vigilantes.view;

import android.app.Activity;
import android.widget.LinearLayout;
import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;

/**
 * Created with IntelliJ IDEA.
 * User: Paulo
 * Date: 23/05/12
 * Time: 22:57
 * Adiciona propagandas da Adview a uma tela (Activity). Não funciona na PaginaDia corretamente, que recebe a propaganda
 * no xml da tela.
 */
public class AdicionaPropagandaAdView {
    public static String MY_AD_UNIT_ID="a14fbcc72821821";
    public static void adicioneAdView(Activity activity, LinearLayout paginaDestino){
        AdView adView = new AdView(activity, AdSize.BANNER, MY_AD_UNIT_ID);
        paginaDestino.addView(adView);
        adView.loadAd(new AdRequest());
    }

}
