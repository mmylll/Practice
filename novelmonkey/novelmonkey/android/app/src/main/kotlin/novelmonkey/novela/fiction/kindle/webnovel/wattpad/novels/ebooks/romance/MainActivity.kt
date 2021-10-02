package novelmonkey.novela.fiction.kindle.webnovel.wattpad.novels.ebooks.romance

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Handler
import android.widget.ImageView
import bkclient.com.ReadPlugin.ReadPlugin
import bkclient.com.ReadVc.ReadAdvManager
import bkclient.com.Service.PTFacebookAppLinkManager
import com.freshchat.consumer.sdk.Freshchat
import com.freshchat.consumer.sdk.FreshchatImageLoader
import com.freshchat.consumer.sdk.FreshchatImageLoaderRequest
import com.jaeger.library.StatusBarUtil
import com.pt.ptbase.Bean.PTBaseNetResponseBean
import com.pt.ptbase.Utils.PTGetImgTool
import com.pt.ptbase.Utils.PTImgLoadUtils
import com.pt.ptbase.Utils.PTTools
import io.flutter.embedding.android.FlutterActivity

class MainActivity: FlutterActivity() {
    var plugin : ReadPlugin = ReadPlugin();
    var h = Handler();
    override fun onResume() {
        super.onResume()
//        IronSource.onResume(this);
        ReadAdvManager.getInstance().initVideoAd(this);

        //通知flutter，页面展示了
        plugin.callFlutter("viewWillApper",null, null)
    }

    override fun onPause() {
        super.onPause()
//        IronSource.onPause(this);
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //设置状态栏
        StatusBarUtil.setTranslucentForImageView(this, 0, null)
        //状态栏背景色为透明
//        window.statusBarColor = 0



//        IronSourceManager.initIronSource(this);
//        IronSourceManager.loadInterstitial();

        //注册flutter交互监听事件
//        GeneratedPluginRegistrant.registerWith(flutterEngine!!)

        plugin.registerWith(this,flutterEngine!!);
        plugin.dealFlutterCallMap.put("showVideoAdv", ReadPlugin.PluginAppInterface { jsonStr, callBack ->
            ReadAdvManager.getInstance().showVideoAd(object : ReadAdvManager.AdvShowListener {
                override fun showSucceed() {
                    val bean = PTBaseNetResponseBean(0, "succeed", "")
                    var json = PTTools.toJson(bean)
                    callBack.success(json)
                }

                override fun showFailed() {
                    val bean = PTBaseNetResponseBean(-1, "failed", "")
                    var json = PTTools.toJson(bean)
                    callBack.success(json)
                }
            })
        })

//        //检查深度链接
        PTFacebookAppLinkManager.checkAppLink(this,plugin);



        //聊天图片加载不出来的问题
        Freshchat.setImageLoader(object : FreshchatImageLoader {
            override fun get(p0: FreshchatImageLoaderRequest): Bitmap? {
                return null;
            }

            override fun load(p0: FreshchatImageLoaderRequest, p1: ImageView) {
                PTImgLoadUtils.displayImg(context,p0.uri,p1,-1);

            }

            override fun fetch(p0: FreshchatImageLoaderRequest) {

            }

        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 1001){
            //小说阅读器回调
            if(data != null){
                var jsonData = data!!.getStringExtra("data");
                if (jsonData != null){
                    when(jsonData){
                        "goToBuyCoinPage" ->{
                            plugin.callFlutter("goToBuyCoinPage",null,null)
                        }
                    }
                }
            }
            return;
        }
        if(PTGetImgTool.onActivityResult(requestCode,resultCode,data))return;

        super.onActivityResult(requestCode, resultCode, data)
    }
}
