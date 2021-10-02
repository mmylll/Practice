package bkclient.com;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.util.Log;

import com.ironsource.mediationsdk.IronSource;
import com.ironsource.mediationsdk.IronSourceObject;
import com.ironsource.mediationsdk.integration.IntegrationHelper;
import com.ironsource.mediationsdk.logger.IronSourceError;
import com.ironsource.mediationsdk.logger.IronSourceLogger;
import com.ironsource.mediationsdk.logger.LogListener;
import com.ironsource.mediationsdk.model.Placement;
import com.ironsource.mediationsdk.sdk.InterstitialListener;
import com.ironsource.mediationsdk.sdk.RewardedVideoListener;

public class IronSourceManager {
    private static final String TAG = "PTT>";

    private static IronPlayOkListener listener;

    public static IronPlayOkListener getListener() {
        return listener;
    }

    public static void setListener(IronPlayOkListener list) {
        listener = list;
    }

    public static void initIronSource(Activity activity) {
        //您可以通过ironSource网络更改状态功能确定和监视用户设备上的Internet连接。
        // 这使SDK可以根据网络修改来更改其可用性，即在没有网络连接的情况下，可用性将变为FALSE。
        //该函数的默认值为false；如果您想听听它的连通性变化，请使用以下字符串在SDK初始化中将其激活：
        IronSource.shouldTrackNetworkState(activity, true);
        //用户是否同意协议
        IronSource.setConsent(true);

        //精确广告信息配置
        // Create segment object
//       var mIronSegment = IronSourceSegment()
//        // Set user age
//        mIronSegment.setAge(age)
//        // Set user gender
//        mIronSegment.setGender(gender)
//        // Set user's level
//        mIronSegment.setLevel(level)
//        // Set user creation date
//        mIronSegment.setUserCreationDate(date)
//        // Set user's total in-app purchases
//        mIronSegment.setIAPTotal(iapt)
//        // Set user's paying status
//        mIronSegment.setIsPaying(isPaying)
//        IronSource.setSegment(mIronSegment)

        //视频广告
        IronSource.setRewardedVideoListener(new RewardedVideoListener() {

            @Override
            public void onRewardedVideoAdOpened() {
                Log.e(TAG,"onRewardedVideoAdOpened>");
            }

            @Override
            public void onRewardedVideoAdClosed() {
                Log.e(TAG,"onRewardedVideoAdClosed>");
                if (listener != null){
                    listener.resultOk();
                    listener = null;
                }
            }

            @Override
            public void onRewardedVideoAvailabilityChanged(boolean b) {
                Log.e(TAG,"onRewardedVideoAvailabilityChanged>" + b);
                if (b){
                    showVideo();
                }
            }

            @Override
            public void onRewardedVideoAdStarted() {
                Log.e(TAG,"onRewardedVideoAdStarted>");
                if (listener != null){
                    listener.resultOk();
                    listener = null;
                }
            }

            @Override
            public void onRewardedVideoAdEnded() {
                Log.e(TAG,"onRewardedVideoAdEnded>");
                if (listener != null){
                    listener.resultOk();
                    listener = null;
                }
            }

            @Override
            public void onRewardedVideoAdRewarded(Placement placement) {
                Log.e(TAG,"onRewardedVideoAdRewarded>" + placement.getPlacementName());
                if (listener != null){
                    listener.resultOk();
                    listener = null;
                }
            }

            @Override
            public void onRewardedVideoAdShowFailed(IronSourceError ironSourceError) {
                Log.e(TAG,"onRewardedVideoAdShowFailed>" + ironSourceError.getErrorMessage());
                if (listener != null){
                    listener.resultFailed();
                    listener = null;
                }
            }

            @Override
            public void onRewardedVideoAdClicked(Placement placement) {
                Log.e(TAG,"onRewardedVideoAdClicked>" + placement.getPlacementName());
                if (listener != null){
                    listener.resultOk();
                    listener = null;
                }
            }
        });

        //插页广告
        IronSource.setInterstitialListener(new InterstitialListener() {
            /**
              这个方法回调，说明插页式广告预加载完成
             */
            @Override
            public void onInterstitialAdReady() {
                Log.e(TAG,"onInterstitialAdReady >>  ");
                showInterstitial();
            }
            /**
             invoked when there is no Interstitial Ad available after calling load function.
             */
            @Override
            public void onInterstitialAdLoadFailed(IronSourceError error) {
                Log.e(TAG,"onInterstitialAdLoadFailed >>  " + error.toString());
                if (listener != null){
                    listener.resultFailed();
                    listener = null;
                }
            }
            /**
             Invoked when the Interstitial Ad Unit is opened
             表明广告格式已接管应用程序屏幕并已显示，但并不表示广告已成功提供给最终用户。
             */
            @Override
            public void onInterstitialAdOpened() {
                Log.e(TAG,"onInterstitialAdOpened >>  ");
            }
            /*
             * Invoked when the ad is closed and the user is about to return to the application.
             */
            @Override
            public void onInterstitialAdClosed() {
                Log.e(TAG,"onInterstitialAdClosed >>  ");
                if (listener != null){
                    listener.resultOk();
                    listener = null;
                }
            }
            /*
             * Invoked when the ad was opened and shown successfully.
             * 事件表明广告格式已接管应用程序屏幕并已显示，但并不表示广告已成功提供给最终用户。
             */
            @Override
            public void onInterstitialAdShowSucceeded() {
                Log.e(TAG,"onInterstitialAdShowSucceeded >>  ");

            }
            /**
             * Invoked when Interstitial ad failed to show.
             // @param error - An object which represents the reason of showInterstitial failure.
             */
            @Override
            public void onInterstitialAdShowFailed(IronSourceError error) {
                Log.e(TAG, "onInterstitialAdClicked >>  " + error.getErrorMessage());
                if (listener != null){
                    listener.resultFailed();
                    listener = null;
                }
            }
            /*
             * Invoked when the end user clicked on the interstitial ad.
             */
            @Override
            public void onInterstitialAdClicked() {
                Log.e(TAG,"onInterstitialAdClicked >>  ");
            }
        });

        String ironAppKey = "e42a8309"; //这是anybooks的key
        if (activity.getApplicationInfo().packageName.equals("novelmonkey.fiction.kindle.webnovel.wattpad.novels.ebooks.read.novelmonkey")) {
            ironAppKey = "e47558f1";//这是novelmokey的可以
        }
        Log.e(TAG,"ironAppKey>" + ironAppKey);
        //插页及视频激励广告初始化
        IronSource.init(activity, ironAppKey, IronSource.AD_UNIT.REWARDED_VIDEO,IronSource.AD_UNIT.INTERSTITIAL);

        IntegrationHelper.validateIntegration(activity);




    }

    /**
     * 复制到粘贴板中
     *
     * @param ctx
     * @param text
     */
    public static void clipBoardText(Context ctx, String text) {
        if (ctx == null || text == null) return;
        ClipboardManager myClipboard;
        myClipboard = (ClipboardManager) ctx.getSystemService(ctx.CLIPBOARD_SERVICE);
        ClipData myClip = ClipData.newPlainText(null, text);
        myClipboard.setPrimaryClip(myClip);
    }

    private static final String videoPlaceName = null;//"DefaultRewardedVideo";
    //视频广告是否可用
    public static boolean isAvailableVideo(){
        //视频广告是否可以用
        boolean available = IronSource.isRewardedVideoAvailable();
        Log.e(TAG,"isRewardedVideoAvailable>" + available);
//        if(available) {
//            //为确保在展示位置上限时，不显示流量驱动程序（“奖励视频”按钮）提示用户观看广告，您必须调用以下方法来验证特定的展示位置是否已达到其广告限制
//            available = IronSource.isRewardedVideoPlacementCapped(videoPlaceName);
//            Log.e(TAG,"isRewardedVideoPlacementCapped>" + available);
//        }
        return available;
    }

    //展示视频广告
    public static void showVideo(){
        if (isAvailableVideo()) {
            IronSource.showRewardedVideo(videoPlaceName);
        }else{
            if (listener != null){
                listener.resultOk();
                listener = null;
            }
        }

//        要获取与每个广告展示位置相关的特定奖励的详细信息，您可以调用以下命令：
//        Placement placement = IronSource.getRewardedVideoPlacementInfo("placementName");
//// Null can be returned instead of a placement if the placementName is not valid.
//if (placement != null) {
//    String rewardName = placement.getRewardName();
//    int rewardAmount = placement.getRewardAmount();
//}
    }


    static final String interstitialName = null;//
    /**
     * 插页广告预加载
     * 先请求插页式广告，因为加载过程可能会花费一些时间。
     * 注意：  如果您想在应用程序中投放多个插页式广告，则必须在显示并关闭上一个插页式广告后重复此步骤。一旦onInterstitialAdClosed 功能被解雇，你就可以加载新的插页式广告。
     */
    public static void loadInterstitial() {
        IronSource.loadInterstitial();
    }

    /**
     * 插页式广告是否可用
     * @return
     */
    public static boolean isInterstitialReady(){
        return IronSource.isInterstitialReady();
    }

    public static void showInterstitial() {
        Log.e(TAG, "isInterstitialReady >>  " + isInterstitialReady());
        if (isInterstitialReady()) {
            IronSource.showInterstitial(interstitialName);
        }else{
            if (listener != null){
                listener.resultFailed();
                listener = null;
            }
        }
    }

    public static interface IronPlayOkListener{
        public void resultOk();
        public void resultFailed();
    }
}
