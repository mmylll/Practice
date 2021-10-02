package normal.LibraryConfig;

import android.app.Activity;

import com.pt.ptbase.Activity.Base.PTSecondAct;
import com.pt.ptbase.PTBaseInfo;
import com.pt.ptbase.Utils.PTTools;
import com.pt.ptwebbase.Fragment.PTBaseWebFragment;
import com.pt.ptwebbase.Services.PTInnerH5Service;
import com.pt.ptwebbase.Services.WebCommenTranstion;

import java.util.HashMap;
import java.util.Map;

import bkclient.com.PTFragmentEnum;
import normal.Constant.ProjectConstant;

public class CommonTrastionConfig implements PTBaseInfo.CommonTrastionInterface {
    private static CommonTrastionConfig instance;

    public static synchronized CommonTrastionConfig getInstance() {
        if (instance == null) {
            instance = new CommonTrastionConfig();
        }
        return instance;
    }

    @Override
    public boolean dealCommonTranstionAction(String funcCode, final Activity activity, final String paramsJson) {


        switch (funcCode) {
            //阅读小说
            case "readerBoook": {
                WebCommenTranstion.getInstance().checkNetInnerH5Infor(ProjectConstant.localZipLeader, activity, true, new WebCommenTranstion.InnerH5CheckListener() {
                    @Override
                    public void checkH5Ok() {
                        String url = PTInnerH5Service.getNetInnerH5Url(ProjectConstant.localZipLeader + "/project/novelmonkey/novel/book/read/read_home.html");
                        Map<String,Object> params = null;
                        if (paramsJson != null){
                            params = PTTools.fromJson(paramsJson,Map.class);
                        }
                        if (params == null)params = new HashMap<>();
                        String jsonData = PTBaseWebFragment.WebJsonDataBean.getJsonData(url,0,0,params);

                        PTSecondAct.goSecondActForResult(activity,jsonData, PTFragmentEnum.ReadPageFragment,1001);
                    }
                });
                return true;
            }
        }

        if (WebCommenTranstion.getInstance().goToFunc(funcCode,activity,paramsJson))return true;

        return false;
    }
}
