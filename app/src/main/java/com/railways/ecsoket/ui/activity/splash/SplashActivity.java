package com.railways.ecsoket.ui.activity.splash;

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

import androidx.annotation.NonNull;


import com.railways.R;
import com.railways.ecsoket.base.BaseActivity;
import com.railways.ecsoket.data.PreferenceManager;
import com.railways.ecsoket.injection.component.ActivityComponent;
import com.railways.ecsoket.ui.activity.home.HomeActivity;
import com.railways.ecsoket.ui.activity.landing.LandingActivity;

import javax.inject.Inject;

import butterknife.BindView;

public class SplashActivity extends BaseActivity implements SplashView {
    public static String mToken;
    @Inject
    PreferenceManager mPref;
    @Inject SplashPresenter presenter;
    @BindView(R.id.splash_icon)
    ImageView splahIcon;
     AnimatorSet mAnimationSet=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new Handler().postDelayed(() -> {
            mAnimationSet.end();
            if(mPref.getUserAccess().isEmpty()) {
                Intent welcome = new Intent(SplashActivity.this, LandingActivity.class);
                startActivity(welcome);
                finish();
            }else{
                Intent landing = new Intent(SplashActivity.this, LandingActivity.class);
                startActivity(landing);
                finish();
            }
        }, 3000);
        setAlphaAnimation(splahIcon);
    }


    private void loadAnimation() {
        TranslateAnimation animate = new TranslateAnimation(0, 0, 100, 0);
        animate.setFillAfter(true);
        animate.setDuration(400);
        animate.setStartOffset(500);
        splahIcon.startAnimation(animate);
        splahIcon.setVisibility(View.VISIBLE);
    }


    public  void setAlphaAnimation(ImageView v) {
        ObjectAnimator fadeOut = ObjectAnimator.ofFloat(v, "alpha",  1f, .3f);
        fadeOut.setDuration(1000);
        ObjectAnimator fadeIn = ObjectAnimator.ofFloat(v, "alpha", .3f, 1f);
        fadeIn.setDuration(1000);

        mAnimationSet = new AnimatorSet();

        mAnimationSet.play(fadeIn).after(fadeOut);

        mAnimationSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mAnimationSet.start();
            }
        });
        mAnimationSet.start();
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_splash;
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
