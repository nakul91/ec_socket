package com.railways.ecsoket.ui.activity.failure;



import com.railways.ecsoket.base.BasePresenter;
import com.railways.ecsoket.data.PreferenceManager;
import com.railways.ecsoket.services.ApiService;

import javax.inject.Inject;

public class FailurePresenter extends BasePresenter<FailureView> {

@Inject
PreferenceManager mPref;

private final ApiService apiService;

@Inject
FailurePresenter(ApiService apiService) {
            this.apiService = apiService;
            }

}