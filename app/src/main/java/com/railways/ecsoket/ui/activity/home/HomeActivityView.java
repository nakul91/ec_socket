package com.railways.ecsoket.ui.activity.home;


import com.railways.ecsoket.base.BaseView;

interface  HomeActivityView extends BaseView {
    void nextActivity();

    void hideProgressBar();

    void dropDown(String s, String erDd);
}
