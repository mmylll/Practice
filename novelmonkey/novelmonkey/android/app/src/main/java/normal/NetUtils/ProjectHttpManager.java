package normal.NetUtils;

import com.pt.ptbase.NetUtils.BasenetUtils.RespObjCall;
import com.pt.ptbase.NetUtils.PTHttpMannager;

import java.util.HashMap;
import java.util.Map;

public class ProjectHttpManager {
    public static void getChapterDownLoadUrl(int chapterId, RespObjCall<String> callBack){
        Map<String,Object> params = new HashMap<>();
        params.put("chapterId",chapterId);
        PTHttpMannager.getInstance().postJsonService(ProjectNetUtils.getChapterDownLoadUrl,params,callBack);
    }
}
