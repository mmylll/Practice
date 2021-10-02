package normal.DefaultActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.jaeger.library.StatusBarUtil;
import com.pt.ptbase.Activity.Base.PTBaseActivity;
import com.pt.ptbase.Utils.PTPermissionUtils;
import com.pt.ptbase.Utils.PTTools;
import com.pt.ptbase.Utils.PhoneInfo;
import com.pt.ptwebbase.Services.WebCommenTranstion;
import com.pt.ptwebbase.StartSetWebInfoVc;


import bkclient.com.Utils.CheckPackageStyle;
import io.flutter.embedding.android.FlutterActivity;
import normal.Constant.ProjectConstant;
import novelmonkey.fiction.kindle.webnovel.wattpad.novels.ebooks.read.novelmonkey.MainActivity;
import novelmonkey.fiction.kindle.webnovel.wattpad.novels.ebooks.read.novelmonkey.R;

import static bkclient.com.Utils.CheckPackageStyle.PTPlatEnum.PTPlatEnum_Monkey;


public class WelcomeActivity extends PTBaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StatusBarUtil.setTranslucentForImageView(this, 0, null);
        StatusBarUtil.setDarkMode(this);

        setContentView(R.layout.activity_welcome);

//        getHashString();
//        return;
//        if (PTTools.isDebugTest) {
//
////            PTSecondAct.goSecondAct(WelcomeActivity.this,null,PTFragmentEnum.PTFlutterFragment);
//
//
//            StartSetWebInfoVc.startAppWithRootActivity(this, new StartSetWebInfoVc.StartAppEndListener() {
//                @Override
//                public void startAppDealOk() {
//                    WebCommenTranstion.getInstance().checkLocalInnerH5Infor(ProjectConstant.localZipLeader, WelcomeActivity.this, false, new WebCommenTranstion.InnerH5CheckListener() {
//                        @Override
//                        public void checkH5Ok() {
//                            initData();
//                        }
//                    });
//                }
//            });
//            return;
//        }
        WebCommenTranstion.getInstance().checkLocalInnerH5Infor(ProjectConstant.localZipLeader, WelcomeActivity.this, false, new WebCommenTranstion.InnerH5CheckListener() {
            @Override
            public void checkH5Ok() {
                initData();
            }
        });

        PTPermissionUtils.checkAndApplypermission(this, new String[]{PTPermissionUtils.writeStorage, PTPermissionUtils.readStorage}, null);
    }

    private void initData() {
//        if (PTTools.isDebugTest) {
//            h.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                if (PTTools.isDebugTest) {
//                    String url = PTInnerH5Service.getNetInnerH5Url(ProjectConstant.localZipLeader + "/project/novelmonkey/novel/book/read/read_home.html");
//                    String jsonData = PTBaseWebFragment.WebJsonDataBean.getJsonData(url, 1, 0, null);
//
//                    PTSecondAct.goSecondAct(WelcomeActivity.this, jsonData, PTFragmentEnum.ReadPageFragment);
//                    return;
//                }
//
//                    //                Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
//
//
//                }
//            }, 000);
//        }

        Class<? extends FlutterActivity> cls = null;
        CheckPackageStyle.PTPlatEnum style = CheckPackageStyle.getInstance().getPlatStyle();
        switch (style){
            case PTPlatEnum_Monkey:{
                cls = MainActivity.class;
            }
            break;
            case PTPlatEnum_AnyBooks:{
                cls = anybooks.kindle.webnovel.fiction.wattpad.novels.ebooks.read.MainActivity.class;
            }
            break;
        }




//        if(PTTools.isDebugTest){
//            Uri uri = Uri.parse("novelmonkey://reader?bookid=24701");
//            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//            startActivity(intent);
//        }else{
            Intent intent = new FlutterActivity.NewEngineIntentBuilder(cls).initialRoute("main").build(WelcomeActivity.this);
//                Intent intent = new FlutterActivity.CachedEngineIntentBuilder(MainActivity.class,"my_engine_id").build(WelcomeActivity.this);
            startActivity(intent);
//        }


        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}
