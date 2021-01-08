package com.railways.ecsoket.ui.activity.landing;


import com.railways.ecsoket.base.BaseView;
import com.railways.ecsoket.pojo.ReadSocketResponse;
import com.railways.ecsoket.pojo.ReasonsResponse;

import java.util.List;

public interface LandingView extends BaseView {
    boolean checkConnection();

    void dropDown(String message,String msg);

    void UpdateDataSocket(List<ReadSocketResponse> body);

    void UpdateReasons(String body);

    void UpdateFailure(String body);
}
