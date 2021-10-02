package bkclient.com;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Base64;

import com.pt.ptbase.PTBaseConfig;
import com.pt.ptbase.Utils.PTTools;
import com.pt.ptbase.Utils.PhoneInfo;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import bkclient.com.ReadVc.ReadAdvManager;
import bkclient.com.Service.PTAnalysisManager;
import bkclient.com.Service.PTFacebookAppLinkManager;
import bkclient.com.Utils.CheckPackageStyle;
import io.flutter.app.FlutterApplication;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.embedding.engine.FlutterEngineCache;
import io.flutter.embedding.engine.dart.DartExecutor;
import io.flutter.view.FlutterMain;
import normal.LibraryConfig.PTWebLibraryConfig;
import normal.LibraryConfig.PTbaseLibraryConfig;

public class MyApplication extends FlutterApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        //检查深度链接
//        PTFacebookAppLinkManager.checkAppLink(this);

        /**
         * 类库初始化
         */
        CheckPackageStyle.getInstance().initPlatInfor(this);
        PTbaseLibraryConfig.getInstance().init(this);
        PTWebLibraryConfig.getInstance().init(this);

        ReadAdvManager.getInstance().initAdv(this);
        PTAnalysisManager.getInstance().init(this);





//        FlutterEngine flutterEngine = new FlutterEngine(this,null,true);
//        flutterEngine.getDartExecutor().executeDartEntrypoint(
//               new DartExecutor.DartEntrypoint(FlutterMain.findAppBundlePath(), "main")
//        );
//        FlutterEngineCache.getInstance().put("my_engine_id",flutterEngine);

//        FlutterMain.Settings settings=new FlutterMain.Settings();
//        settings.setLogTag("bkclient");
//        FlutterMain.startInitialization(this,settings);
//        String[] args = {"info", "data"};
//        FlutterMain.ensureInitializationComplete(this,args);
    }
}
