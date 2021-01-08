package com.railways.ecsoket.injection.component;

import com.railways.ecsoket.ui.activity.ecsocket.EcSocketActivity;
import com.railways.ecsoket.ui.activity.failure.FailureActivity;
import com.railways.ecsoket.ui.activity.failuredetails.DetailActivity;
import com.railways.ecsoket.ui.activity.home.HomeActivity;
import com.railways.ecsoket.injection.PerActivity;
import com.railways.ecsoket.injection.module.ActivityModule;
import com.railways.ecsoket.ui.activity.landing.LandingActivity;
import com.railways.ecsoket.ui.activity.newtest.TestActivity;
import com.railways.ecsoket.ui.activity.splash.SplashActivity;

import dagger.Subcomponent;

@PerActivity
@Subcomponent(modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(HomeActivity homeActivity);

    void inject(SplashActivity splashActivity);

    void inject(LandingActivity landingActivity);

    void inject(TestActivity testActivity);

    void inject(EcSocketActivity ecSocketActivity);

    void inject(FailureActivity failureActivity);

    void inject(DetailActivity detailActivity);


}
