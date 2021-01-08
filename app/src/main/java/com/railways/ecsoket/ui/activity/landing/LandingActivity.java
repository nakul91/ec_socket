package com.railways.ecsoket.ui.activity.landing;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.railways.R;
import com.railways.ecsoket.Utils.EcSocketUtils;
import com.railways.ecsoket.base.BaseActivity;
import com.railways.ecsoket.constants.EcSocketConstants;
import com.railways.ecsoket.data.PreferenceManager;
import com.railways.ecsoket.database.EcsocketDatabase;
import com.railways.ecsoket.injection.component.ActivityComponent;
import com.railways.ecsoket.pojo.ReadSocketResponse;
import com.railways.ecsoket.pojo.ReasonsResponse;
import com.railways.ecsoket.ui.activity.ecsocket.EcSocketActivity;
import com.railways.ecsoket.ui.activity.failure.FailureActivity;
import com.railways.ecsoket.ui.activity.home.HomeActivity;
import com.railways.ecsoket.ui.activity.newtest.TestActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;



public class LandingActivity extends BaseActivity implements LandingView {
    public static String mToken;
    @Inject
    PreferenceManager mPref;
    @Inject
    LandingPresenter presenter;
    @Inject
    EcsocketDatabase ecsocketDatabase;
    @BindView(R.id.splash_icon)
    ImageView splahIcon;
    @BindView(R.id.sync_icon)
    ImageView syncIcon;
    @BindView(R.id.new_twst)
    Button newTest;
    @BindView(R.id.new_socket)
    Button newSocket;

    @BindView(R.id.new_failures)
    Button newFailure;

    double lat = 0;
    double lang = 0;
    private boolean syncFlag=false;
    ObjectAnimator rotate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setup();
        newTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent welcome = new Intent(LandingActivity.this, TestActivity.class);
                    startActivity(welcome);
     }
        });

        newSocket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Intent welcome = new Intent(LandingActivity.this, EcSocketActivity.class);
                    startActivity(welcome);

            }
        });
        newFailure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent failure = new Intent(LandingActivity.this, FailureActivity.class);
                startActivity(failure);

            }
        });
        syncIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //rorateClockwise(syncIcon);
                if( EcSocketUtils.isConnected(LandingActivity.this)) {
                    syncFlag = true;
                    rorateClockwise(syncIcon);
                    presenter.GetEcSocket();
                    presenter.GetReasons();
                    presenter.GetFailures();
                    updateToDb();
                }else{
                    dropDown("Internet connection required",EcSocketConstants.ER_DD);
                }
            }
        });



    }

    private void updateToDb() {
        if(ecsocketDatabase.getStatusCounts()>0){
            Log.e("STATUSCOUNT","COUNT:"+ecsocketDatabase.getStatusCounts());
            Log.e("STATUSCOUNT","COUNT:"+ecsocketDatabase.getSocketStatus("false"));
            /*Gson mGson = new Gson();
            mGson.fromJson(ecsocketDatabase.getSocketStatus("false"), ChaptersResponse.class)*/
            if( EcSocketUtils.isConnected(LandingActivity.this)) {


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
                    presenter.updateEcSocket(userId,sectionInchargeId,sectionId,blockSectionId,ecId,dateOfTesting,testLocationLattitude,testLocationLongitude,boxCondition,socketCondition,autoPairCondition,EMCStatus,dateId,boxCRemarks,socketRemarks,auroPairRemarks,EMCRemarks,anyReamrks,ecType,testPicture,painted,testPictureFileName);

                    Log.e("STATUSCOUNT","COUNT:"+dateId);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }else{
            Log.e("STATUSCOUNT","COUNT:0");
        }


        if(ecsocketDatabase.getNewStatusCounts()>0){
            Log.e("STATUSCOUNT","COUNT:"+ecsocketDatabase.getNewStatusCounts());
            Log.e("STATUSCOUNT","COUNT:"+ecsocketDatabase.getNewSocketStatus("false"));
            /*Gson mGson = new Gson();
            mGson.fromJson(ecsocketDatabase.getSocketStatus("false"), ChaptersResponse.class)*/
            if( EcSocketUtils.isConnected(LandingActivity.this)) {


                try {
                    JSONObject jo= new JSONObject(ecsocketDatabase.getNewSocketStatus("false"));
                    int userId= jo.getInt("userId");
                    String blockId= jo.getString("blockSectionId");
                    String ecName= jo.getString("ecLocation");
                    String lat= jo.getString("ecLattitude");
                    String lang= jo.getString("ecLongitude");
                    String dateId= jo.getString("dataId");
                    String ecType= jo.getString("ecType");
                    presenter.updateNewEcSocket(userId,blockId,ecName,lat,lang,dateId,ecType);
                    Log.e("STATUSCOUNT","COUNT:"+dateId);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }else{
            Log.e("STATUSCOUNT","COUNT:0");
        }

        if(ecsocketDatabase.getFailCounts()>0){
            Log.e("STATUSCOUNT","COUNT:"+ecsocketDatabase.getFailCounts());
            Log.e("STATUSCOUNT","COUNT:"+ecsocketDatabase.getFailStatus("false"));
            /*Gson mGson = new Gson();
            mGson.fromJson(ecsocketDatabase.getSocketStatus("false"), ChaptersResponse.class)*/
            if( EcSocketUtils.isConnected(LandingActivity.this)) {
                try {
                    JSONObject jo= new JSONObject(ecsocketDatabase.getFailStatus("false"));
                    String failureId= jo.getString("failureId");
                    presenter.updateFailure(ecsocketDatabase.getFailStatus("false"),failureId);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }else{
            Log.e("STATUSCOUNT","COUNT:0");
        }



    }

    private void setup() {
        presenter.GetEcSocket();
        presenter.GetReasons();
        presenter.GetFailures();

      /*  ecsocketDatabase.saveSectionData("1", "SSE/T/G/MYS");
        ecsocketDatabase.saveSectionData("2", "SSE/T/HAS");
        ecsocketDatabase.saveSectionData("3", "SSE/T/ASK");
        ecsocketDatabase.saveSectionData("4", "SSE/T/RRB");
        ecsocketDatabase.saveSectionData("5", "SSE/T/CTA");
        ecsocketDatabase.saveSectionData("6", "SSE/T/DVG");


        ecsocketDatabase.saveSectionDetails("1", "MYS-HAS", "1");
        ecsocketDatabase.saveSectionDetails("2", "MYS-CMNR", "1");
        ecsocketDatabase.saveSectionDetails("3", "MYS-Y", "1");
        ecsocketDatabase.saveSectionDetails("4", "ASK-PADL", "2");
        ecsocketDatabase.saveSectionDetails("5", "TK-RRB", "3");
        ecsocketDatabase.saveSectionDetails("6", "DRU-CMGR", "3");
        ecsocketDatabase.saveSectionDetails("7", "RRB-TLGP", "4");
        ecsocketDatabase.saveSectionDetails("8", "JRU-RDG", "5");
        ecsocketDatabase.saveSectionDetails("9", "JRU-KJG", "6");
        ecsocketDatabase.saveSectionDetails("10", "KTY-AVC", "6");


        ecsocketDatabase.saveBlockDetails("2", "MBBC-BLGA", "1");
        ecsocketDatabase.saveBlockDetails("3", "BLGA-STE", "1");
        ecsocketDatabase.saveBlockDetails("4", "STE-KRNR", "1");
        ecsocketDatabase.saveBlockDetails("5", "KRNR-HPA", "1");
        ecsocketDatabase.saveBlockDetails("6", "HPA-AKK", "1");
        ecsocketDatabase.saveBlockDetails("7", "AKK-MGF", "1");
        ecsocketDatabase.saveBlockDetails("8", "MGF-HLN", "1");
        ecsocketDatabase.saveBlockDetails("9", "HLN-MVC", "1");
        ecsocketDatabase.saveBlockDetails("10", "MVC-HAS", "1");
        ecsocketDatabase.saveBlockDetails("11", "MYS-AP", "2");
        ecsocketDatabase.saveBlockDetails("12", "AP-KDO", "2");
        ecsocketDatabase.saveBlockDetails("13", "KDO-NTW", "2");
        ecsocketDatabase.saveBlockDetails("14", "NTW-CMNR", "2");
        ecsocketDatabase.saveBlockDetails("15", "MYA-Y", "3");
        ecsocketDatabase.saveBlockDetails("16", "Y-BDRL", "3");
        ecsocketDatabase.saveBlockDetails("17", "BDRL-PANP", "3");
        ecsocketDatabase.saveBlockDetails("18", "PANP-S", "3");
        ecsocketDatabase.saveBlockDetails("19", "S-NHY", "3");
        ecsocketDatabase.saveBlockDetails("20", "NHY-MNGT", "3");
        ecsocketDatabase.saveBlockDetails("21", "ASK-HHT", "4");
        ecsocketDatabase.saveBlockDetails("22", "HHT-BGPA", "4");
        ecsocketDatabase.saveBlockDetails("23", "BGPA-KRVL", "4");
        ecsocketDatabase.saveBlockDetails("24", "KRVL-HAS", "4");
        ecsocketDatabase.saveBlockDetails("25", "HAS-ALUR", "4");
        ecsocketDatabase.saveBlockDetails("26", "ALUR-BLLT", "4");
        ecsocketDatabase.saveBlockDetails("27", "BLLT-SKLR", "4");
        ecsocketDatabase.saveBlockDetails("28", "SKLR-DOGL", "4");
        ecsocketDatabase.saveBlockDetails("29", "DOGL-KGVL", "4");
        ecsocketDatabase.saveBlockDetails("30", "KGVL-YDK", "4");
        ecsocketDatabase.saveBlockDetails("31", "YDK-SVGL", "4");
        ecsocketDatabase.saveBlockDetails("32", "SVGL-SBHR", "4");
        ecsocketDatabase.saveBlockDetails("33", "SBHR-YDM", "4");
        ecsocketDatabase.saveBlockDetails("34", "YDM-NRJ", "4");
        ecsocketDatabase.saveBlockDetails("35", "NRJ-KBPR", "4");
        ecsocketDatabase.saveBlockDetails("36", "KBPR-NRF", "4");
        ecsocketDatabase.saveBlockDetails("37", "NRF-BNTL", "4");
        ecsocketDatabase.saveBlockDetails("38", "BNTL-PADL", "4");
        ecsocketDatabase.saveBlockDetails("39", "TK-MLSA", "5");
        ecsocketDatabase.saveBlockDetails("40", "MLSA-GBB", "5");
        ecsocketDatabase.saveBlockDetails("41", "GBB-NTR", "5");
        ecsocketDatabase.saveBlockDetails("42", "NTR-SPGR", "5");
        ecsocketDatabase.saveBlockDetails("43", "SPGR-AMSA", "5");
        ecsocketDatabase.saveBlockDetails("44", "AMSA-BSN", "5");
        ecsocketDatabase.saveBlockDetails("45", "BSN-RDI", "5");
        ecsocketDatabase.saveBlockDetails("46", "RDI-TTR", "5");
        ecsocketDatabase.saveBlockDetails("47", "TTR-HVL", "5");
        ecsocketDatabase.saveBlockDetails("48", "HVL-ADHL", "5");
        ecsocketDatabase.saveBlockDetails("49", "ADHL-ASK", "5");
        ecsocketDatabase.saveBlockDetails("50", "ASK-BVR", "5");
        ecsocketDatabase.saveBlockDetails("51", "BVR-VNR", "5");
        ecsocketDatabase.saveBlockDetails("52", "VNR-BLKR", "5");
        ecsocketDatabase.saveBlockDetails("53", "BLKR-DRU", "5");
        ecsocketDatabase.saveBlockDetails("54", "DRU-RRB", "5");
        ecsocketDatabase.saveBlockDetails("55", "DRU-SHYP", "6");
        ecsocketDatabase.saveBlockDetails("56", "SHYP-CMGR", "6");
        ecsocketDatabase.saveBlockDetails("57", "RRB-NVF", "7");
        ecsocketDatabase.saveBlockDetails("58", "NVF-AJP", "7");
        ecsocketDatabase.saveBlockDetails("59", "AJP-SHV", "7");
        ecsocketDatabase.saveBlockDetails("60", "SHV-HSD", "7");
        ecsocketDatabase.saveBlockDetails("61", "HSD-RGI", "7");
        ecsocketDatabase.saveBlockDetails("62", "RGI-HLK", "7");
        ecsocketDatabase.saveBlockDetails("63", "HLK-JRU", "7");
        ecsocketDatabase.saveBlockDetails("64", "RRB-SPV", "7");
        ecsocketDatabase.saveBlockDetails("65", "SPV-TKE", "7");
        ecsocketDatabase.saveBlockDetails("66", "TKE-MSS", "7");
        ecsocketDatabase.saveBlockDetails("67", "MSS-BDVT", "7");
        ecsocketDatabase.saveBlockDetails("68", "BDVT-SMET", "7");
        ecsocketDatabase.saveBlockDetails("69", "SMET-KMSI", "7");
        ecsocketDatabase.saveBlockDetails("70", "KUMSI-ANF", "7");
        ecsocketDatabase.saveBlockDetails("71", "ANF-SRF", "7");
        ecsocketDatabase.saveBlockDetails("72", "SRF-TLGP", "7");
        ecsocketDatabase.saveBlockDetails("73", "JRU -BBH", "8");
        ecsocketDatabase.saveBlockDetails("74", "BBH-AMC", "8");
        ecsocketDatabase.saveBlockDetails("75", "AMC-CTA", "8");
        ecsocketDatabase.saveBlockDetails("76", "CTA-BAHI", "8");
        ecsocketDatabase.saveBlockDetails("77", "BAHI-CHKE", "8");
        ecsocketDatabase.saveBlockDetails("78", "CHKE-THKU", "8");
        ecsocketDatabase.saveBlockDetails("79", "THKU-BOMN", "8");
        ecsocketDatabase.saveBlockDetails("80", "BOMN-MOMU", "8");
        ecsocketDatabase.saveBlockDetails("81", "MOMU-RDG", "8");
        ecsocketDatabase.saveBlockDetails("82", "JRU-SLU", "9");
        ecsocketDatabase.saveBlockDetails("83", "SLU-MYK", "9");
        ecsocketDatabase.saveBlockDetails("84", "MYK-KAG", "9");
        ecsocketDatabase.saveBlockDetails("85", "KAG-THN", "9");
        ecsocketDatabase.saveBlockDetails("86", "THN-DVG", "9");
        ecsocketDatabase.saveBlockDetails("87", "DVG-AVC", "9");
        ecsocketDatabase.saveBlockDetails("88", "AVC-HRR", "9");
        ecsocketDatabase.saveBlockDetails("89", "HRR-KMPS", "9");
        ecsocketDatabase.saveBlockDetails("90", "KMPS-CLI", "9");
        ecsocketDatabase.saveBlockDetails("91", "CLI-RNR", "9");
        ecsocketDatabase.saveBlockDetails("92", "RNR-DAD", "9");
        ecsocketDatabase.saveBlockDetails("93", "DAD-BYD", "9");
        ecsocketDatabase.saveBlockDetails("94", "BYD-HVR", "9");
        ecsocketDatabase.saveBlockDetails("95", "HVR-KJG", "9");
        ecsocketDatabase.saveBlockDetails("96", "KTY-BEHI", "10");
        ecsocketDatabase.saveBlockDetails("97", "BEHI-HPHI", "10");
        ecsocketDatabase.saveBlockDetails("98", "HPHI-TLGI", "10");
        ecsocketDatabase.saveBlockDetails("99", "TLGI-AVC", "10");




        ecsocketDatabase.saveSocketDetails("1", "71/400-500", "1");
        ecsocketDatabase.saveSocketDetails("2", "72/300-400", "1");
        ecsocketDatabase.saveSocketDetails("3", "82/000-000", "2");
        ecsocketDatabase.saveSocketDetails("4", "0/900-000", "3");
        ecsocketDatabase.saveSocketDetails("5", "1/800-900", "3");
        ecsocketDatabase.saveSocketDetails("6", "2/800-900", "3");
        ecsocketDatabase.saveSocketDetails("7", "11/600-700", "4");
        ecsocketDatabase.saveSocketDetails("8", "13/500-600", "5");*/



    }


    @Override
    protected int getLayout() {
        return R.layout.activity_landing;
    }

    @Override
    protected void inject(ActivityComponent activityComponent) {
        activityComponent.inject(this);

    }

    @Override
    protected void attachView() {
        presenter.attachView(this);
    }

    @Override
    protected void detachPresenter() {
        presenter.detachView();

    }






    /* Request updates at startup */

    @Override
    protected void onResume() {
        super.onResume();
        if(ecsocketDatabase.getStatusCounts()>0){
            Log.e("STATUSCOUNT","COUNT:"+ecsocketDatabase.getStatusCounts());
            Log.e("STATUSCOUNT","COUNT:"+ecsocketDatabase.getSocketStatus("false"));
            /*Gson mGson = new Gson();
            mGson.fromJson(ecsocketDatabase.getSocketStatus("false"), ChaptersResponse.class)*/
           if( EcSocketUtils.isConnected(LandingActivity.this)) {


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
                presenter.updateEcSocket(userId,sectionInchargeId,sectionId,blockSectionId,ecId,dateOfTesting,testLocationLattitude,testLocationLongitude,boxCondition,socketCondition,autoPairCondition,EMCStatus,dateId,boxCRemarks,socketRemarks,auroPairRemarks,EMCRemarks,anyReamrks,ecType,testPicture,painted,testPictureFileName);

                Log.e("STATUSCOUNT","COUNT:"+dateId);
            } catch (JSONException e) {
                e.printStackTrace();
            }
           }
        }else{
            Log.e("STATUSCOUNT","COUNT:0");
        }


        if(ecsocketDatabase.getNewStatusCounts()>0){
            Log.e("STATUSCOUNT","COUNT:"+ecsocketDatabase.getNewStatusCounts());
            Log.e("STATUSCOUNT","COUNT:"+ecsocketDatabase.getNewSocketStatus("false"));
            /*Gson mGson = new Gson();
            mGson.fromJson(ecsocketDatabase.getSocketStatus("false"), ChaptersResponse.class)*/
            if( EcSocketUtils.isConnected(LandingActivity.this)) {


                try {
                    JSONObject jo= new JSONObject(ecsocketDatabase.getNewSocketStatus("false"));
                    int userId= jo.getInt("userId");
                    String blockId= jo.getString("blockSectionId");
                    String ecName= jo.getString("ecLocation");
                    String lat= jo.getString("ecLattitude");
                    String lang= jo.getString("ecLongitude");
                    String dateId= jo.getString("dataId");
                    String ecType= jo.getString("ecType");
                    presenter.updateNewEcSocket(userId,blockId,ecName,lat,lang,dateId,ecType);
                    Log.e("STATUSCOUNT","COUNT:"+dateId);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }else{
            Log.e("STATUSCOUNT","COUNT:0");
        }

        if(ecsocketDatabase.getFailCounts()>0){
            Log.e("STATUSCOUNT","COUNT:"+ecsocketDatabase.getFailCounts());
            Log.e("STATUSCOUNT","COUNT:"+ecsocketDatabase.getFailStatus("false"));
            /*Gson mGson = new Gson();
            mGson.fromJson(ecsocketDatabase.getSocketStatus("false"), ChaptersResponse.class)*/
            if( EcSocketUtils.isConnected(LandingActivity.this)) {
                try {
                    JSONObject jo= new JSONObject(ecsocketDatabase.getFailStatus("false"));
                    String failureId= jo.getString("failureId");
                    presenter.updateFailure(ecsocketDatabase.getFailStatus("false"),failureId);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }else{
            Log.e("STATUSCOUNT","COUNT:0");
        }


    }


    private void CheckForRows() {
        if(ecsocketDatabase.getStatusCounts()>0){
            Log.e("STATUSCOUNT","COUNT:"+ecsocketDatabase.getStatusCounts());
            Log.e("STATUSCOUNT","COUNT:"+ecsocketDatabase.getSocketStatus("false"));
            /*Gson mGson = new Gson();
            mGson.fromJson(ecsocketDatabase.getSocketStatus("false"), ChaptersResponse.class)*/
            try {
                JSONObject jo= new JSONObject(ecsocketDatabase.getSocketStatus("false"));
                String dateId= jo.getString("dataId");

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else{
            Log.e("STATUSCOUNT","COUNT:0");
        }

    }



    @Override
    public boolean checkConnection() {
        return EcSocketUtils.isConnected(LandingActivity.this);
    }

    @Override
    public void dropDown(String message,String msg) {
        dropDownText(message,msg);
    }

    @Override
    public void UpdateDataSocket(List<ReadSocketResponse> body) {
        if(body.size()==0){
            dropDown("No data",EcSocketConstants.ER_DD);
            return;
        }
        if(syncFlag) {
            syncFlag=false;
            dropDown("Sync successful", EcSocketConstants.SU_DD);
            rotate.cancel();
        }

        for (int i=0;i<body.size();i++){
            ecsocketDatabase.saveSectionData(body.get(i).getSectionInchargeId(),body.get(i).getSectionInchargeName());
            for(int j=0;j<body.get(i).getSection().size();j++){
                ecsocketDatabase.saveSectionDetails(body.get(i).getSection().get(j).getSectionId(), body.get(i).getSection().get(j).getSectionName(), body.get(i).getSectionInchargeId());
                for(int k=0;k<body.get(i).getSection().get(j).getBlock().size();k++){
                    ecsocketDatabase.saveBlockDetails(body.get(i).getSection().get(j).getBlock().get(k).getBlockSectionId(), body.get(i).getSection().get(j).getBlock().get(k).getBlockSectionName(),body.get(i).getSection().get(j).getSectionId());
                    for(int l=0;l<body.get(i).getSection().get(j).getBlock().get(k).getSockets().size();l++){
                        ecsocketDatabase.saveSocketDetails(body.get(i).getSection().get(j).getBlock().get(k).getSockets().get(l).getEcId(), body.get(i).getSection().get(j).getBlock().get(k).getSockets().get(l).getEcLocation(), body.get(i).getSection().get(j).getBlock().get(k).getBlockSectionId());
                    }
                }
            }

        }

    }

    @Override
    public void UpdateReasons(String body) {
        if(!body.isEmpty()){
            mPref.setReasons(body);
        }
    }

    @Override
    public void UpdateFailure(String body) {
        if(!body.isEmpty()){
            mPref.setFailure(body);
        }
    }

    public void rorateClockwise(View view) {
         rotate = ObjectAnimator.ofFloat(view ,
                 "rotation", 0f, 360f);
         rotate.setRepeatCount(Animation.INFINITE);
         rotate.start();
    }
}
