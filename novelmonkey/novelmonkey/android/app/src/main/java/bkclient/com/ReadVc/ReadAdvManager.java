package bkclient.com.ReadVc;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;

import androidx.annotation.NonNull;

import com.google.ads.mediation.admob.AdMobAdapter;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdCallback;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.pt.ptbase.Utils.PTTools;

import bkclient.com.Utils.CheckPackageStyle;

public class ReadAdvManager {
    private static ReadAdvManager instance;
    public static synchronized ReadAdvManager getInstance() {
        if (instance == null) {
            instance = new ReadAdvManager();
        }
        return instance;
    }



    private InterstitialAd mInterstitialAd;
    private RewardedAd rewardedAd;
    public void initAdv(Context context){
        MobileAds.initialize(context, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {

            }
        });
        mInterstitialAd = new InterstitialAd(context);
        mInterstitialAd.setAdUnitId(getInterstitialAdKey());
        loadInterstitialAd();


    }
    @SuppressLint("MissingPermission")
    public void loadInterstitialAd(){
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
    }
    private boolean isLoadedShowInterstitialAd = false;
    private AdvShowListener chayeListener;
    public void showChaYeAdv(AdvShowListener listener){
        chayeListener = listener;
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }else if(mInterstitialAd.isLoading()){
            isLoadedShowInterstitialAd = true;
        }else{
            if (listener != null){
                listener.showFailed();
            }
        }
        mInterstitialAd.setAdListener(new AdListener(){
            @Override
            public void onAdLoaded() {
                if (isLoadedShowInterstitialAd){
                    mInterstitialAd.show();
                    isLoadedShowInterstitialAd = false;
                }
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                if (chayeListener != null){
                    chayeListener.showFailed();
                    chayeListener = null;
                }
                loadInterstitialAd();
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when the ad is displayed.
            }

            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            @Override
            public void onAdClosed() {
                if (listener != null){
                    listener.showSucceed();
                    chayeListener = null;
                }
                loadInterstitialAd();
            }
        });
    }
    

    private String getInterstitialAdKey(){
        if (PTTools.isDebugTest){
            return "ca-app-pub-3940256099942544/1033173712";
        }
        switch (CheckPackageStyle.getInstance().getPlatStyle()){
            case PTPlatEnum_Monkey:{
                return "ca-app-pub-9872048022830865/5165641120";
            }
            case PTPlatEnum_AnyBooks:{
                return "ca-app-pub-9872048022830865/2154720008";
            }
        }
        return "";
    }

    private boolean isVideoLoadedShow = false;//是否视频广告加载完就展示
    private AdvShowListener videoListener;//视频广告展示监听
    private boolean isVideoComplete;//视频是否看完了
    private Activity currentActivity;//当前页面
    public void initVideoAd(Activity activity){
        currentActivity = activity;
        rewardedAd = new RewardedAd(activity,getVideoAdKey());
        loadVideoAd();
    }
    @SuppressLint("MissingPermission")
    public void loadVideoAd(){
        RewardedAdLoadCallback adLoadCallback = new RewardedAdLoadCallback() {
            @Override
            public void onRewardedAdLoaded() {
                if (isVideoLoadedShow == true){
                    showVideoAd(videoListener);
                }
            }

            @Override
            public void onRewardedAdFailedToLoad(LoadAdError adError) {
                if (videoListener != null) {
                    videoListener.showFailed();
                    videoListener = null;
                }
            }
        };
        rewardedAd.loadAd(new AdRequest.Builder().build(), adLoadCallback);
    }
    public void showVideoAd(AdvShowListener listener){
        isVideoComplete = false;
        videoListener = listener;

        if (rewardedAd.isLoaded()){
            rewardedAd.show(currentActivity, videoCallBack);
        }else {
            if (videoListener != null) {
                videoListener.showFailed();
                videoListener = null;
            }
            isVideoLoadedShow = false;
        }
    }
    private RewardedAdCallback videoCallBack = new RewardedAdCallback() {
        @Override
        public void onRewardedAdOpened() {
            // Ad opened.
        }

        @Override
        public void onRewardedAdClosed() {
            if (videoListener != null) {
                if (isVideoComplete) {
                    videoListener.showSucceed();
                } else {
                    videoListener.showFailed();
                }
                videoListener = null;
            }
            isVideoLoadedShow = false;
            initVideoAd(currentActivity);
        }

        @Override
        public void onUserEarnedReward(@NonNull RewardItem reward) {
            isVideoComplete = true;
        }

        @Override
        public void onRewardedAdFailedToShow(AdError adError) {
            if (videoListener != null) {
                videoListener.showFailed();
                videoListener = null;
            }
            isVideoLoadedShow = false;
            loadVideoAd();
        }
    };
    private String getVideoAdKey(){
        if (PTTools.isDebugTest){
            return "ca-app-pub-3940256099942544/5224354917";
        }
        switch (CheckPackageStyle.getInstance().getPlatStyle()){
            case PTPlatEnum_Monkey:{
                return "ca-app-pub-9872048022830865/7914899877";
            }
            case PTPlatEnum_AnyBooks:{
                return "ca-app-pub-9872048022830865/2154720008";
            }
        }
        return "";
    }



    public static interface AdvShowListener{
        public void showSucceed();
        public void showFailed();
    }
}
