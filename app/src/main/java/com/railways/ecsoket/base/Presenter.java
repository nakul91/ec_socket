package com.railways.ecsoket.base;

public interface Presenter<V extends BaseView> {

    void attachView(V mvpView);

    void detachView();
}
