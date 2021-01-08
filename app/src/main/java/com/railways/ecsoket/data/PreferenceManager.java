package com.railways.ecsoket.data;

import android.content.Context;
import android.content.SharedPreferences;

import com.railways.ecsoket.constants.EcSocketConstants;
import com.railways.ecsoket.injection.PreferenceInfo;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PreferenceManager {

    private static PreferenceManager sInstance;
    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;

    private PreferenceManager(Context ctx) {
        mPreferences = ctx.getSharedPreferences(EcSocketConstants.USER_PREF_FILE, Context.MODE_PRIVATE);
        mEditor = mPreferences.edit();
    }

    @Inject
    public PreferenceManager(Context context, @PreferenceInfo String prefFileName) {
        mPreferences = context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE);
        mEditor = mPreferences.edit();
    }

    public static PreferenceManager instance(Context ctx) {
        if (sInstance == null)
            sInstance = new PreferenceManager(ctx);
        return sInstance;
    }

    public void ClearSharedPreferences() {
        mEditor.clear();
        mEditor.commit();
    }


    public String getUserAccess() {
        return mPreferences.getString("userAccess", "");
    }

    public void setUserAccess(String userAccess) {
        mEditor.putString("userAccess", userAccess).commit();
    }


    public String getReasons() {
        return mPreferences.getString("reasons", "");
    }

    public void setReasons(String reasons) {
        mEditor.putString("reasons", reasons).commit();
    }


    public String getFailure() {
        return mPreferences.getString("failure", "");
    }

    public void setFailure(String failure) {
        mEditor.putString("failure", failure).commit();
    }
}
