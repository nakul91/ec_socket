package com.railways.ecsoket.injection.component;

import android.app.Application;
import android.content.Context;

import com.railways.ecsoket.EcSocketApplication;
import com.railways.ecsoket.data.PreferenceManager;
import com.railways.ecsoket.database.EcsocketDatabase;
import com.railways.ecsoket.injection.module.AppModule;
import com.railways.ecsoket.services.ApiService;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = { AppModule.class })
public interface AppComponent {

    void inject(EcSocketApplication app);

    Context context();

    Application application();

    PreferenceManager getPreferenceManager();

    ApiService apiService();

    EcsocketDatabase ecsocketDatabase();

}
