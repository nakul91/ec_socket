package com.railways.ecsoket.ui.activity.home;

import android.os.Build;
import android.util.Log;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.railways.ecsoket.base.BasePresenter;
import com.railways.ecsoket.constants.EcSocketConstants;
import com.railways.ecsoket.data.PreferenceManager;
import com.railways.ecsoket.injection.ConfigPersistent;
import com.railways.ecsoket.pojo.LoginResponse;
import com.railways.ecsoket.services.ApiService;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by nakul on 30/03/18.
 */

@ConfigPersistent
public class HomeActivityPresenter extends BasePresenter<HomeActivityView> {

    @Inject PreferenceManager mPref;

    private final ApiService apiService;

    @Inject
    HomeActivityPresenter(ApiService apiService) {
        this.apiService = apiService;
    }

    void Login(String name,String password) {
        JsonObject obj= new JsonObject();
        try {
            obj.addProperty("userName",name);
            obj.addProperty("userPassword",password);

        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.e("ACTIVATION ===> %s", new GsonBuilder().create().toJson(obj));
        Call<LoginResponse> call = apiService.Login(obj);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                Log.e("ACTIVATION ===> %s", new GsonBuilder().create().toJson(response));
                getView().hideProgressBar();
                if(response.body().getMessage()!=null){
                    if(response.body().getMessage().equalsIgnoreCase("Login Successful")) {
                        mPref.setUserAccess(response.body().getToken());
                        getView().dropDown(response.body().getMessage(), EcSocketConstants.SU_DD);
                        getView().nextActivity();
                    }else{
                        getView().dropDown(response.body().getMessage(), EcSocketConstants.ER_DD);
                    }
                }



            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                t.printStackTrace();
                getView().hideProgressBar();
                getView().dropDown("Something went worng, Please try again", EcSocketConstants.ER_DD);

            }
        });
    }


}
