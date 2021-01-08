
package com.railways.ecsoket.ui.activity.ecsocket;



import com.railways.ecsoket.base.BasePresenter;
import com.railways.ecsoket.data.PreferenceManager;
import com.railways.ecsoket.services.ApiService;

import javax.inject.Inject;

public class EcSocketPresenter extends BasePresenter<EcSocketView> {

@Inject
PreferenceManager mPref;

private final ApiService apiService;

@Inject
EcSocketPresenter(ApiService apiService) {
            this.apiService = apiService;
            }

}