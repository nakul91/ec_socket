package com.railways.ecsoket.ui.activity.home;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.railways.R;
import com.railways.ecsoket.base.BaseActivity;
import com.railways.ecsoket.constants.EcSocketConstants;
import com.railways.ecsoket.data.PreferenceManager;
import com.railways.ecsoket.injection.component.ActivityComponent;
import com.railways.ecsoket.ui.activity.landing.LandingActivity;
import com.railways.ecsoket.ui.activity.splash.SplashActivity;

import javax.inject.Inject;

import butterknife.BindView;

public class HomeActivity  extends BaseActivity implements HomeActivityView {
    @Inject
    PreferenceManager mPref;
    @Inject
    HomeActivityPresenter presenter;
    @BindView(R.id.login)
    Button Login;
    @BindView(R.id.password)
    EditText passwordText;
    @BindView(R.id.username)
    EditText usernameText;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(usernameText.getText().toString().isEmpty()){
                    dropDown("Enter Username", EcSocketConstants.ER_DD);
                    return;
                }
                if(passwordText.getText().toString().isEmpty()){
                    dropDown("Enter Password", EcSocketConstants.ER_DD);
                    return;
                }
                    progressBar.setVisibility(View.VISIBLE);
                    presenter.Login(usernameText.getText().toString(),passwordText.getText().toString());


                /*Intent welcome = new Intent(HomeActivity.this, LandingActivity.class);
                startActivity(welcome);
                finish();*/
            }
        });

    }



    @Override
    protected int getLayout() {
        return R.layout.activity_home;
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

    @Override
    public void nextActivity() {
        new Handler().postDelayed(() -> {
            Intent welcome = new Intent(HomeActivity.this, LandingActivity.class);
            startActivity(welcome);
            finish();
        }, 1200);

    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);

    }

    @Override
    public void dropDown(String s, String erDd) {
       dropDownText(s,erDd);
    }
}
