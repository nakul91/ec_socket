package com.railways.ecsoket.ui.activity.failure;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.railways.R;
import com.railways.ecsoket.base.BaseActivity;
import com.railways.ecsoket.data.PreferenceManager;
import com.railways.ecsoket.injection.component.ActivityComponent;
import com.railways.ecsoket.pojo.FailureResponse;
import com.railways.ecsoket.pojo.ReasonsResponse;
import com.railways.ecsoket.ui.activity.landing.LandingActivity;

import javax.inject.Inject;

import butterknife.BindView;

public class FailureActivity extends BaseActivity implements FailureView {
    public static String mToken;
    @Inject
    PreferenceManager mPref;
    @Inject
    FailurePresenter presenter;
    @BindView(R.id.back_arrow)
    ImageView backArrow;
    @BindView(R.id.failure_recycler)
    RecyclerView failureRecycler;
     AnimatorSet mAnimationSet=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(mPref.getFailure().isEmpty()||mPref.getFailure().equalsIgnoreCase("{}")){
            Toast.makeText(getApplicationContext(),"There are no failures",Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        Gson mGson= new Gson();
        FailureResponse failures=mGson.fromJson(mPref.getFailure(),FailureResponse.class);
        LinearLayoutManager chapterLayoutManager = new LinearLayoutManager(FailureActivity.this, LinearLayoutManager.VERTICAL, false);
        failureRecycler.setLayoutManager(chapterLayoutManager);
        if(failures.getRecords().size()==0){
            Toast.makeText(getApplicationContext(),"There are no failures",Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        FailureAdapter fAdapter = new FailureAdapter(FailureActivity.this,failures.getRecords());
        failureRecycler.setAdapter(fAdapter);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }



    @Override
    protected int getLayout() {
        return R.layout.failure_layout;
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
}
