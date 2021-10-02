package normal.LibraryConfig;

import android.content.Context;

import com.google.gson.reflect.TypeToken;
import com.pt.ptbase.Utils.PTTools;
import com.pt.ptwebbase.Fragment.PTJsUtils;
import com.pt.ptwebbase.PTBaseWebConfig;
import com.pt.ptwebbase.PTBaseWebInfo;

import java.util.HashMap;
import java.util.Map;

import bkclient.com.Service.PTAnalysisManager;

public class PTWebLibraryConfig {
    private static PTWebLibraryConfig instance;

    public static synchronized PTWebLibraryConfig getInstance() {
        if(instance == null){
            instance = new PTWebLibraryConfig();
        }
        return instance;
    }
    public void init(Context context){
        PTBaseWebConfig.init(context,getBaseInfo(context));
    }

    /**
     * 获取类库的配置信息
     * @return
     */
    private PTBaseWebInfo getBaseInfo(Context context){
        PTBaseWebInfo info = new PTBaseWebInfo();
        info.applicationContext = context;

        info.jsApiListener = new PTBaseWebInfo.PTBaseJsApiInterface(){

            @Override
            public Map<String, PTJsUtils.JsCallAppInterface> returnJsApiList(PTJsUtils jsUtils) {
                Map<String, PTJsUtils.JsCallAppInterface> resultMap = new HashMap<>();


                resultMap.put("monkey_analysis", new PTJsUtils.JsCallAppInterface() {
                    @Override
                    public void jsCallApp(String actionCode, String jsonStr) {
                        Map<String,Object> map = PTTools.fromJson(jsonStr,new TypeToken<Map<String,Object>>(){}.getType());
                        String key = (String) map.get("key");
                        Map<String,Object> value = (Map<String, Object>) map.get("value");
                        PTAnalysisManager.getInstance().addEvent(jsUtils.getActivity(),key,value);
                    }
                });

                return resultMap;
            }
        };

        return info;
    }
}
