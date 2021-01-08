package com.railways.ecsoket.base;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import androidx.collection.LongSparseArray;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
import com.railways.R;
import com.railways.ecsoket.EcSocketApplication;
import com.railways.ecsoket.constants.EcSocketConstants;
import com.railways.ecsoket.injection.component.ActivityComponent;
import com.railways.ecsoket.injection.component.ConfigPersistentComponent;
import com.railways.ecsoket.injection.component.DaggerConfigPersistentComponent;
import com.railways.ecsoket.injection.module.ActivityModule;

import java.util.concurrent.atomic.AtomicLong;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {

    private static final String KEY_ACTIVITY_ID = "KEY_ACTIVITY_ID";
    private static final AtomicLong NEXT_ID = new AtomicLong(0);
    private static final LongSparseArray<ConfigPersistentComponent> componentsArray = new LongSparseArray<>();
    private Activity mContext;
    private long activityId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        ButterKnife.bind(this);
        mContext = this;


        ConfigPersistentComponent configPersistentComponent;

        if (componentsArray.get(activityId) == null) {

            configPersistentComponent = DaggerConfigPersistentComponent.builder()
                    .appComponent(EcSocketApplication.get(this).getComponent())
                    .build();
            componentsArray.put(activityId, configPersistentComponent);
        } else {

            configPersistentComponent = componentsArray.get(activityId);
        }
        ActivityComponent activityComponent =
                configPersistentComponent.activityComponent(new ActivityModule(this));
        inject(activityComponent);
        attachView();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    protected abstract int getLayout();

    protected abstract void inject(ActivityComponent activityComponent);

    protected abstract void attachView();

    protected abstract void detachPresenter();

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(KEY_ACTIVITY_ID, activityId);
    }

    @Override
    protected void onDestroy() {
        if (!isChangingConfigurations()) {
            componentsArray.remove(activityId);
        }
        detachPresenter();
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mContext = this;
    }

    public void dropDownText(String msg,String state) {
//        String state =SU_DD;

        final DialogPlus dropDownNotification = DialogPlus.newDialog(this)
                .setContentHolder(new ViewHolder(R.layout.drop_down_text))
                .setCancelable(true)
                .setGravity(Gravity.TOP)
                .setOverlayBackgroundResource(R.color.transparent)
                .setContentBackgroundResource(R.color.transparent)
                .create();

        TextView dropText = (TextView) dropDownNotification.findViewById(R.id.drop_text);

        GradientDrawable gd = getRect();
        switch (state){
            case EcSocketConstants.ER_DD:
                gd.setColor(getResources().getColor(R.color.error_dd));
                break;

            case EcSocketConstants.SU_DD:
                gd.setColor(getResources().getColor(R.color.succ_dd));
                break;

            case EcSocketConstants.IN_DD:
                gd.setColor(getResources().getColor(R.color.info_dd));
                dropText.setTextColor(ContextCompat.getColor(this, R.color.black));
                break;
        }

        dropText.setBackground(gd);

        dropText.setText(msg);
        hideKeyBoard();
        dropDownNotification.show();

        new Handler().postDelayed(() ->{
            dropDownNotification.dismiss();
        }, 3000);
    }

    GradientDrawable getRect(){
        GradientDrawable gd = new GradientDrawable();
        gd.setShape(GradientDrawable.RECTANGLE);
        gd.setCornerRadii(new float[] { 100, 100, 100, 100, 100, 100, 100, 100 });
        return gd;
    }

    public void hideKeyBoard(){
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}

