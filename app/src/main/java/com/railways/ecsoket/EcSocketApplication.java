package com.railways.ecsoket;

import android.app.Application;
import android.content.Context;

import com.railways.ecsoket.database.EcsocketDatabase;
import com.railways.ecsoket.injection.component.AppComponent;
import com.railways.ecsoket.injection.component.DaggerAppComponent;
import com.railways.ecsoket.injection.module.AppModule;

public class EcSocketApplication extends Application {

    private AppComponent appComponent;
    private  static EcsocketDatabase database;

    public static EcSocketApplication get(Context context) {
        return (EcSocketApplication) context.getApplicationContext();
    }

    public AppComponent getComponent() {
        if (appComponent == null) {

         appComponent = DaggerAppComponent.builder()
                    .appModule(new AppModule(this))
                    .build();
        }
        return appComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        database = EcsocketDatabase.getDatabaseManagerInstance(getApplicationContext());
    }
}
