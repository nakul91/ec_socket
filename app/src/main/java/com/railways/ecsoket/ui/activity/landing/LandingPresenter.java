package com.railways.ecsoket.ui.activity.landing;



import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.railways.ecsoket.Utils.EcSocketUtils;
import com.railways.ecsoket.base.BasePresenter;
import com.railways.ecsoket.constants.EcSocketConstants;
import com.railways.ecsoket.data.PreferenceManager;
import com.railways.ecsoket.database.EcsocketDatabase;
import com.railways.ecsoket.pojo.FailureResponse;
import com.railways.ecsoket.pojo.LoginResponse;
import com.railways.ecsoket.pojo.ReadSocketResponse;
import com.railways.ecsoket.pojo.ReasonsResponse;
import com.railways.ecsoket.services.ApiService;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LandingPresenter extends BasePresenter<LandingView> {

@Inject
PreferenceManager mPref;

    @Inject
    EcsocketDatabase ecsocketDatabase;

private final ApiService apiService;

@Inject
LandingPresenter(ApiService apiService) {
            this.apiService = apiService;
            }

    public void updateEcSocket(int userId,String sectionInchargeId,String sectionId,String blockId,String socketId,String datetime,String lat
            ,String lang,String boxCondition,String socketCondition,String autoPairCondition,String emcStatus,String Id,String boxRemarks,String socketRemarks,String AutoRemarks,String EmcRemarks,String anyReamrks,String ecType,String testPicture,String painted,String testPictureFileName) {
        JsonObject obj= new JsonObject();
        try {
            obj.addProperty("userId",userId);
            //obj.addProperty("token",mPref.getUserAccess());
            obj.addProperty("sectionInchargeId",sectionInchargeId);
            obj.addProperty("sectionId",sectionId);
            obj.addProperty("blockSectionId", blockId);
            obj.addProperty("ecId",socketId);
            obj.addProperty("dateOfTesting",datetime);
            obj.addProperty("testLocationLattitude",lat);
            obj.addProperty("testLocationLongitude",lang);
            obj.addProperty("boxCondition",boxCondition);
            obj.addProperty("boxCRemarks",boxRemarks);
            obj.addProperty("socketCondition",socketCondition);
            obj.addProperty("socketRemarks",socketRemarks);
            obj.addProperty("autoPairCondition",autoPairCondition);
            obj.addProperty("auroPairRemarks",AutoRemarks);
            obj.addProperty("EMCStatus",emcStatus);
            obj.addProperty("EMCRemarks",EmcRemarks);
            obj.addProperty("anyReamrks",anyReamrks);
            obj.addProperty("ecType",ecType);
            obj.addProperty("testPicture",testPicture);
            obj.addProperty("painted",painted);
            obj.addProperty("testPictureFileName",testPictureFileName);

        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.e("ACTIVATION ===> %s", new GsonBuilder().create().toJson(obj));
        Call<JsonObject> call = apiService.CreateTest(obj);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.e("ACTIVATION ===> %s", new GsonBuilder().create().toJson(response));
                if(response.body().getAsJsonObject().has("message")){
                    if(response.code()==200) {
                        CheckForUpdates(Id);
                        getView().dropDown(response.body().getAsJsonObject().get("message").getAsString(),EcSocketConstants.SU_DD);
                    }else{
                        CheckForUpdates(Id);
                        getView().dropDown(response.body().getAsJsonObject().get("message").getAsString(),EcSocketConstants.ER_DD);
                    }
                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                t.printStackTrace();

            }
        });
    }

    private void CheckForUpdates(String id) {
        ecsocketDatabase.deleteMessageIdTable(id);
        if(ecsocketDatabase.getStatusCounts()>0){
            Log.e("STATUSCOUNT","COUNT:"+ecsocketDatabase.getStatusCounts());
            Log.e("STATUSCOUNT","COUNT:"+ecsocketDatabase.getSocketStatus("false"));
            /*Gson mGson = new Gson();
            mGson.fromJson(ecsocketDatabase.getSocketStatus("false"), ChaptersResponse.class)*/
            if(getView().checkConnection()) {

                try {
                    JSONObject jo= new JSONObject(ecsocketDatabase.getSocketStatus("false"));
                    int userId= jo.getInt("userId");
                    String sectionInchargeId= jo.getString("sectionInchargeId");
                    String sectionId= jo.getString("sectionId");
                    String blockSectionId= jo.getString("blockSectionId");
                    String ecId= jo.getString("ecId");
                    String dateOfTesting= jo.getString("dateOfTesting");
                    String testLocationLattitude= jo.getString("testLocationLattitude");
                    String testLocationLongitude= jo.getString("testLocationLongitude");
                    String boxCondition= jo.getString("boxCondition");
                    String socketCondition= jo.getString("socketCondition");
                    String autoPairCondition= jo.getString("autoPairCondition");
                    String EMCStatus= jo.getString("EMCStatus");
                    String dateId= jo.getString("dataId");
                    String boxCRemarks= jo.getString("boxCRemarks");
                    String socketRemarks= jo.getString("socketRemarks");
                    String auroPairRemarks= jo.getString("auroPairRemarks");
                    String EMCRemarks= jo.getString("EMCRemarks");
                    String anyReamrks= jo.getString("anyReamrks");
                    String ecType= jo.getString("ecType");
                    String testPicture= jo.getString("testPicture");
                    String painted= jo.getString("painted");
                    String testPictureFileName= jo.getString("testPictureFileName");

                    updateEcSocket(userId,sectionInchargeId,sectionId,blockSectionId,ecId,dateOfTesting,testLocationLattitude,testLocationLongitude,boxCondition,socketCondition,autoPairCondition,EMCStatus,dateId,boxCRemarks,socketRemarks,auroPairRemarks,EMCRemarks,anyReamrks,ecType,testPicture,painted,testPictureFileName);
                    Log.e("STATUSCOUNT","COUNT:"+dateId);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }else{
            Log.e("STATUSCOUNT","COUNT:0");
        }

    }



    public void GetEcSocket() {

        Call<List<ReadSocketResponse>> call = apiService.GetSockets();
        call.enqueue(new Callback<List<ReadSocketResponse>>() {
            @Override
            public void onResponse(Call<List<ReadSocketResponse>> call, Response<List<ReadSocketResponse>> response) {
                Log.e("Response ===> %s", new GsonBuilder().create().toJson(response));
                Log.e("Response ===> %s", response.body().size()+"");
                getView().UpdateDataSocket(response.body());

            }

            @Override
            public void onFailure(Call<List<ReadSocketResponse>> call, Throwable t) {
                t.printStackTrace();

            }
        });
    }


    public void GetReasons() {

        Call<ReasonsResponse> call = apiService.GetReasons();
        call.enqueue(new Callback<ReasonsResponse>() {
            @Override
            public void onResponse(Call<ReasonsResponse> call, Response<ReasonsResponse> response) {
                Log.e("Response ===> %s", new GsonBuilder().create().toJson(response));
                getView().UpdateReasons(new GsonBuilder().create().toJson(response.body()));

            }

            @Override
            public void onFailure(Call<ReasonsResponse> call, Throwable t) {
                t.printStackTrace();

            }
        });
    }


    public void GetFailures() {

        JsonObject obj= new JsonObject();
        try {
            obj.addProperty("userId", EcSocketConstants.USER_ID);
        }catch (Exception e){
            e.printStackTrace();
        }

        Call<FailureResponse> call = apiService.GetFailure(obj);
        call.enqueue(new Callback<FailureResponse>() {
            @Override
            public void onResponse(Call<FailureResponse> call, Response<FailureResponse> response) {
                Log.e("Response ===> %s", new GsonBuilder().create().toJson(response));
                getView().UpdateFailure(new GsonBuilder().create().toJson(response.body()));

            }

            @Override
            public void onFailure(Call<FailureResponse> call, Throwable t) {
                t.printStackTrace();

            }
        });
    }



    public void updateNewEcSocket(int userId,String blockId,String ecName,String lat,String lang,String Id,String ecType) {
        JsonObject obj= new JsonObject();
        try {
            obj.addProperty("userId",userId);
            obj.addProperty("blockSectionId",blockId);
            obj.addProperty("ecLocation",ecName);
            obj.addProperty("ecLattitude", lat);
            obj.addProperty("ecLongitude",lang);
            obj.addProperty("ecType",ecType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.e("ACTIVATION ===> %s", new GsonBuilder().create().toJson(obj));
        Call<JsonObject> call = apiService.CreateSocket(obj);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.e("ACTIVATION ===> %s", new GsonBuilder().create().toJson(response));
                if(response.body().getAsJsonObject().has("message")){
                    if(response.code()==200)  {
                        CheckForNewUpdates(Id);
                        getView().dropDown(response.body().getAsJsonObject().get("message").getAsString(),EcSocketConstants.SU_DD);
                    }else{
                        CheckForNewUpdates(Id);
                        getView().dropDown(response.body().getAsJsonObject().get("message").getAsString(),EcSocketConstants.ER_DD);
                    }
                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                t.printStackTrace();

            }
        });
    }






    private void CheckForNewUpdates(String id) {
        ecsocketDatabase.deleteNewMessageIdTable(id);
        if(ecsocketDatabase.getNewStatusCounts()>0){
            Log.e("STATUSCOUNT","COUNT:"+ecsocketDatabase.getNewStatusCounts());
            Log.e("STATUSCOUNT","COUNT:"+ecsocketDatabase.getNewSocketStatus("false"));
            /*Gson mGson = new Gson();
            mGson.fromJson(ecsocketDatabase.getSocketStatus("false"), ChaptersResponse.class)*/
            if(getView().checkConnection()) {

                try {
                    JSONObject jo= new JSONObject(ecsocketDatabase.getNewSocketStatus("false"));
                    int userId= jo.getInt("userId");
                    String blockId= jo.getString("blockSectionId");
                    String ecName= jo.getString("ecLocation");
                    String lat= jo.getString("ecLattitude");
                    String lang= jo.getString("ecLongitude");
                    String dateId= jo.getString("dataId");
                    String ecType=jo.getString("ecType");
                    updateNewEcSocket(userId,blockId,ecName,lat,lang,dateId,ecType);
                    Log.e("STATUSCOUNT","COUNT:"+dateId);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }else{
            Log.e("STATUSCOUNT","COUNT:0");
        }

    }



    public void updateFailure(String object,String Id) {
        JsonObject obj= new Gson().fromJson(object, JsonObject.class);

        Log.e("ACTIVATION ===> %s", new GsonBuilder().create().toJson(obj));
        Call<JsonObject> call = apiService.CreateFailure(obj);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.e("ACTIVATION ===> %s", new GsonBuilder().create().toJson(response));
                if(response.body().getAsJsonObject().has("message")){
                    if(response.code()==200)  {
                        CheckForFailUpdates(Id);
                        getView().dropDown(response.body().getAsJsonObject().get("message").getAsString(),EcSocketConstants.SU_DD);
                    }else{
                        CheckForFailUpdates(Id);
                        getView().dropDown(response.body().getAsJsonObject().get("message").getAsString(),EcSocketConstants.ER_DD);
                    }
                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                t.printStackTrace();

            }
        });
    }

    private void CheckForFailUpdates(String id) {
        ecsocketDatabase.deleteFailIdTable(id);
        if(ecsocketDatabase.getFailCounts()>0){
            Log.e("STATUSCOUNT","COUNT:"+ecsocketDatabase.getFailCounts());
            Log.e("STATUSCOUNT","COUNT:"+ecsocketDatabase.getFailStatus("false"));
            /*Gson mGson = new Gson();
            mGson.fromJson(ecsocketDatabase.getSocketStatus("false"), ChaptersResponse.class)*/
            if(getView().checkConnection()) {

                try {
                    JSONObject jo= new JSONObject(ecsocketDatabase.getNewSocketStatus("false"));
                    String failureId= jo.getString("failureId");
                    updateFailure(ecsocketDatabase.getNewSocketStatus("false"),failureId);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }else{
            Log.e("STATUSCOUNT","COUNT:0");
        }
    }


}