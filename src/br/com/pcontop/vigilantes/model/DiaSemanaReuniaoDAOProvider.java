package br.com.pcontop.vigilantes.model;

import android.content.Context;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Provides;

/**
 * Created with IntelliJ IDEA.
 * User: Paulo
 * Date: 25/09/12
 * Time: 11:52
 * To change this template use File | Settings | File Templates.
 */
public class DiaSemanaReuniaoDAOProvider implements Provider<DiaSemanaReuniaoDAO1> {
    Context context;

    @Inject
    public DiaSemanaReuniaoDAOProvider (Provider<Context> providerContext){
        this.context = providerContext.get();
    }

    @Provides
    public DiaSemanaReuniaoDAO1 get() {
        return new DiaSemanaReuniaoDAO1(context);
    }
}
