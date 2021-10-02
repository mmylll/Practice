package normal.NetUtils;

import com.pt.ptbase.Utils.PTTools;

public class ProjectNetUtils {
    public static String getBaseUrl(){
        if (PTTools.isDebugTest){
            return "https://www.novelmonkey.app/anybook-web/";
//            return "http://192.168.31.209:8080/";

        }else{
            return "https://www.novelmonkey.app/anybook-web/";
        }
    }
    private static String getUrl(String api){
        return getBaseUrl() + api;
    }

    //获取章节下载链接
    public static String getChapterDownLoadUrl = getUrl("confined/book/buyChapter");



    public static String postUserIconUrl = getUrl("account/updateHeadImage");
}
