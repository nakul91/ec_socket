package com.railways.ecsoket.ui.activity.failuredetails;



import com.railways.ecsoket.base.BasePresenter;
import com.railways.ecsoket.data.PreferenceManager;
import com.railways.ecsoket.services.ApiService;

import javax.inject.Inject;

public class DetailPresenter extends BasePresenter<DetailView> {

@Inject
PreferenceManager mPref;

private final ApiService apiService;

@Inject
DetailPresenter(ApiService apiService) {
            this.apiService = apiService;
            }

}