package com.railways.ecsoket.ui.activity.splash;



import com.railways.ecsoket.base.BasePresenter;
import com.railways.ecsoket.data.PreferenceManager;
import com.railways.ecsoket.services.ApiService;

import javax.inject.Inject;

public class SplashPresenter extends BasePresenter<SplashView> {

@Inject
PreferenceManager mPref;

private final ApiService apiService;

@Inject
SplashPresenter(ApiService apiService) {
            this.apiService = apiService;
            }

}