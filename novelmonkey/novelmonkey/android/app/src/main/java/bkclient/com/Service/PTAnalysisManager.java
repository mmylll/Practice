package bkclient.com.Service;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.appsflyer.AppsFlyerConversionListener;
import com.appsflyer.AppsFlyerLib;
import com.appsflyer.appsflyersdk.AppsFlyerConstants;
import com.appsflyer.attribution.AppsFlyerRequestListener;
import com.facebook.appevents.AppEventsLogger;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.ironsource.environment.ApplicationContext;
import com.pt.ptbase.Utils.PTTools;

import java.util.HashMap;
import java.util.Map;

import bkclient.com.ReadVc.ReadAdvManager;

public class PTAnalysisManager {

    private static PTAnalysisManager instance;
    public static synchronized PTAnalysisManager getInstance() {
        if (instance == null) {
            instance = new PTAnalysisManager();
        }
        return instance;
    }
    public void init(Context mContext){
        this.mContext = mContext;
        fbEventLogger = AppEventsLogger.newLogger(mContext);
        firebaseAnalytics = FirebaseAnalytics.getInstance(mContext);

//        appsFlyerLib.init("DbXzuPwZhF2qsUpAPwQmgd", new AppsFlyerConversionListener() {
//            @Override
//            public void onConversionDataSuccess(Map<String, Object> map) {
//
//            }
//
//            @Override
//            public void onConversionDataFail(String s) {
//
//            }
//
//            @Override
//            public void onAppOpenAttribution(Map<String, String> map) {
//
//            }
//
//            @Override
//            public void onAttributionFailure(String s) {
//
//            }
//        },mContext);
//
//        appsFlyerLib.setDebugLog(PTTools.isDebugTest ? true : false);
    }


    private Context mContext;
    //facebook统计事件
    private AppEventsLogger fbEventLogger;
    //appsflyer 统计
    private AppsFlyerLib appsFlyerLib = AppsFlyerLib.getInstance();

    private FirebaseAnalytics firebaseAnalytics;

    public void addEvent(Context context,String name){
        fbEventLogger.logEvent(name);

        appsFlyerLib.trackEvent(context, name,new HashMap<String,Object>());

        firebaseAnalytics.logEvent(name,null);
    }

    public void addEvent(Context context, String name, Map<String,Object> params){
        context = mContext;// context == null ? mContext : context;
        if (params == null){
            addEvent(context,name);
            return;
        }

        Bundle bundle = new Bundle();
        for (String key : params.keySet()) {
            Object value = params.get(key);
            if (value instanceof Integer){
                bundle.putInt(key, (Integer) value);
            }
            else if (value instanceof Double){
                bundle.putDouble(key, (Double) value);
            }
            else if (value instanceof Boolean){
                bundle.putBoolean(key, (Boolean) value);
            }
            else if (value instanceof String){
                bundle.putString(key, (String) value);
            }
        }
        fbEventLogger.logEvent(name,bundle);

        appsFlyerLib.trackEvent(context, name, params, new AppsFlyerRequestListener() {
            @Override
            public void onSuccess() {
                PTTools.loge("appsflyer 提交成功!");
            }

            @Override
            public void onError(int i, @NonNull String s) {
                PTTools.loge("appsflyer 提交失败!");
            }
        });


        firebaseAnalytics.logEvent(name,bundle);
    }
}
