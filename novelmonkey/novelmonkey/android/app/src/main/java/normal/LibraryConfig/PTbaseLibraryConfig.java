package normal.LibraryConfig;

import android.content.Context;
import android.content.pm.PackageInfo;

import androidx.fragment.app.Fragment;

import com.google.gson.reflect.TypeToken;
import com.pt.ptbase.Activity.Base.BaseFragmentEnum;
import com.pt.ptbase.PTBaseConfig;
import com.pt.ptbase.PTBaseInfo;
import com.pt.ptbase.Utils.PTTools;
import com.pt.ptbase.Utils.PhoneInfo;
import com.pt.ptbase.Utils.SharedLab;
import com.pt.ptwebbase.Fragment.PTDefaultH5Frag;

import java.util.HashMap;
import java.util.Map;

import bkclient.com.PTFlutterFragment;
import bkclient.com.PTFragmentEnum;
import bkclient.com.ReadVc.ReadPageFragment;
import io.flutter.embedding.android.FlutterFragment;
import io.flutter.embedding.android.RenderMode;
import io.flutter.embedding.android.TransparencyMode;
import novelmonkey.fiction.kindle.webnovel.wattpad.novels.ebooks.read.novelmonkey.R;

import static bkclient.com.PTFragmentEnum.ReadPageFragment;


public class PTbaseLibraryConfig {
    private static PTbaseLibraryConfig instance;

    public static synchronized PTbaseLibraryConfig getInstance() {
        if(instance == null){
            instance = new PTbaseLibraryConfig();
        }
        return instance;
    }
    public void init(Context context){
        PTBaseConfig.init(context,getBaseInfo(context));
    }

    /**
     * 获取类库的配置信息
     * @return
     */
    private PTBaseInfo getBaseInfo(final Context context){
        PTBaseInfo info = new PTBaseInfo();


        info.baseNvBgColor = R.color.nv_color;

        info.isBackIconBlack = true;

        info.isNeedPostJson = true;


        info.netUtilsListener = new PTBaseInfo.PTBaseNetUtilsInterface() {
            @Override
            public Map<String, String> getMapParams(String url,Map<String, Object> originParams) {
//                String json = "{}";
//                if (originParams != null) {
//                    json = PTTools.toJson(originParams);
//                }
                Map<String,String> resultMap = new HashMap<>();
                if (originParams != null){
                    for (String key : originParams.keySet()) {
                        resultMap.put(key,originParams.get(key) + "");
                    }
                }

//                PackageInfo info = PhoneInfo.getPackageData(context);
//                resultMap.put("appVersion",info.versionName);
//                resultMap.put("packageName",info.packageName);
//                resultMap.put("deviceType","0");
//                resultMap.put("deviceOsVersion",PhoneInfo.getTelSysType() + "");
//                resultMap.put("netType",PhoneInfo.isWifiConnected(context)?"wifi":"4g");


//                resultMap.put("data",json);
                return resultMap;
            }

            @Override
            public String getJsonParams(String url,Map<String, Object> originParams) {
                Map<String,Object> resultMap = new HashMap<>();

                if (originParams != null) {
                    resultMap.putAll(originParams);

                }
//                PackageInfo info = PhoneInfo.getPackageData(context);
//                resultMap.put("appVersion",info.versionName);
//                resultMap.put("packageName",info.packageName);
//                resultMap.put("deviceType","0");
//                resultMap.put("deviceOsVersion",PhoneInfo.getTelSysType() + "");
//                resultMap.put("netType",PhoneInfo.isWifiConnected(context)?"wifi":"4g");
                String json = PTTools.toJson(resultMap);
                return json;
            }

            @Override
            public Map<String, String> getHeaderByParams(String url,Map<String, String> params) {
                Map<String,String> headers = new HashMap<>();
//                String P

                String json = SharedLab.getString("cookie_cache");
                if (json != null){
                    Map<String,String> map = PTTools.fromJson(json,new TypeToken<Map<String,String>>(){}.getType());
                    headers.putAll(map);
                }


                PackageInfo info = PhoneInfo.getPackageData(context);
                headers.put("appVersion",info.versionName);
                headers.put("packageName",info.packageName);
                headers.put("deviceType","0");
                headers.put("deviceOsVersion",PhoneInfo.getTelSysType() + "");
                headers.put("netType",PhoneInfo.isWifiConnected(context)?"wifi":"4g");


                PTTools.loge(PTTools.toJson(headers));
                return headers;
            }
        };

        info.commonTrastionListener = CommonTrastionConfig.getInstance();


        info.fragmentEnum = new PTBaseInfo.FragmentEnumInterface() {
            @Override
            public Fragment getFragmentById(String fragmentId) {
                return getFragmentByFragId(fragmentId);
            }
        };
        return info;
    }
    private Fragment getFragmentByFragId(String fragmentId){
        Fragment result = null;
        switch (fragmentId){
            case BaseFragmentEnum.PTDefaultH5Frag:{
                result = new PTDefaultH5Frag();
            }
            break;
            case ReadPageFragment:{
                result = new ReadPageFragment();
            }
            break;
            case PTFragmentEnum.PTFlutterFragment:{
                PTFlutterFragment fg = new FlutterFragment
                        .NewEngineFragmentBuilder(PTFlutterFragment.class)
                        .renderMode(RenderMode.surface)
                        .transparencyMode(TransparencyMode.transparent)
                        .initialRoute("home")
                        .build();
                result = fg;
            }
            break;
        }
        return result;
    }
}
