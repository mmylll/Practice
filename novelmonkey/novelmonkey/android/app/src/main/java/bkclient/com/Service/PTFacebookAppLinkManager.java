package bkclient.com.Service;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.facebook.FacebookSdk;
import com.facebook.applinks.AppLinkData;
import com.pt.ptbase.Utils.PTTools;
import com.pt.ptbase.Utils.SharedLab;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bkclient.com.ReadPlugin.ReadPlugin;

public class PTFacebookAppLinkManager {
    public static void checkAppLink(Context context, ReadPlugin plugin) {



        if (context instanceof Activity) {
            Intent intent = ((Activity) context).getIntent();
            String action = intent.getAction();
            PTTools.loge(" AppLink > " + action);
            Uri uri = intent.getData();
            dealUri(uri, plugin);
        }


        if (PTTools.isDebugTest) {
            FacebookSdk.setIsDebugEnabled(true);
        }

        FacebookSdk.setAutoInitEnabled(true);
        FacebookSdk.fullyInitialize();
        AppLinkData.fetchDeferredAppLinkData(context,
                new AppLinkData.CompletionHandler() {
                    @Override
                    public void onDeferredAppLinkDataFetched(AppLinkData appLinkData) {
                        PTTools.loge("appLinkData 收到");
                        if (appLinkData == null) return;

                        Uri uri = appLinkData.getTargetUri();
                        PTTools.loge("appLinkData uri" + uri);
                        if (uri == null) return;
                        dealUri(uri, plugin);
                    }
                }
        );
    }

    private static void dealUri(Uri uri, ReadPlugin plugin) {
        if (uri == null) return;
        PTTools.loge(" AppLink getHost> " + uri.getHost());


        if (!uri.getHost().equals("bookdetail") && !uri.getHost().equals("reader")) return;


        String url = uri.toString();
        PTTools.loge("appLinkData url" + url);
        Map<String, List<String>> map = getQueryParams(url);
        PTTools.loge("appLinkData map" + map);

        if (map == null || map.size() == 0) return;

        List<String> bookIdList = map.get("bookid");
        PTTools.loge("appLinkData bookIdList" + bookIdList);

        if (bookIdList == null || bookIdList.size() == 0) return;


        int bookid = Integer.parseInt(bookIdList.get(0));
        PTTools.loge("appLinkData bookid" + bookid);

        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("bookid", bookid);
        jsonMap.put("action", uri.getHost());
        SharedLab.setObject("goToReadBook", PTTools.toJson(jsonMap));
        plugin.callFlutter("goToReadBook", null, null);

    }

    public static Map<String, List<String>> getQueryParams(String url) {
        try {
            Map<String, List<String>> params = new HashMap<String, List<String>>();
            String[] urlParts = url.split("\\?");//这里分割uri成请求路径和请求参数两部分，注意这里的?不能直接作为分隔符，需要转义
            if (urlParts.length > 1) {
                String query = urlParts[1];//获取到参数字符串
                for (String param : query.split("&")) {
                    String[] pair = param.split("=");
                    String key = URLDecoder.decode(pair[0], "UTF-8");
                    String value = "";
                    if (pair.length > 1) {
                        value = URLDecoder.decode(pair[1], "UTF-8");
                    }

                    List<String> values = params.get(key);
                    if (values == null) {
                        values = new ArrayList<String>();
                        params.put(key, values);
                    }
                    values.add(value);
                }
            }

            return params;
        } catch (UnsupportedEncodingException ex) {
            throw new AssertionError(ex);
        }

    }
}
