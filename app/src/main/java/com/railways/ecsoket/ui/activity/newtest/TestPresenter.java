package com.railways.ecsoket.ui.activity.newtest;



import com.railways.ecsoket.base.BasePresenter;
import com.railways.ecsoket.data.PreferenceManager;
import com.railways.ecsoket.services.ApiService;

import javax.inject.Inject;

public class TestPresenter extends BasePresenter<TestView> {

@Inject
PreferenceManager mPref;

private final ApiService apiService;

@Inject
TestPresenter(ApiService apiService) {
            this.apiService = apiService;
            }

}