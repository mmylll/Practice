package bkclient.com.ReadPlugin;

import android.app.Activity;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.UiThread;

import com.google.android.gms.common.util.HttpUtils;
import com.google.gson.reflect.TypeToken;
import com.pt.ptbase.Activity.Base.PTSecondAct;
import com.pt.ptbase.Bean.PTBaseNetBean;
import com.pt.ptbase.Bean.PTBaseNetResponseBean;
import com.pt.ptbase.NetUtils.BasenetUtils.HttpNetUtils;
import com.pt.ptbase.NetUtils.BasenetUtils.RespObjCall;
import com.pt.ptbase.NetUtils.PTHttpMannager;
import com.pt.ptbase.PTBaseConfig;
import com.pt.ptbase.Utils.PTGetImgTool;
import com.pt.ptbase.Utils.PTTools;
import com.pt.ptbase.Utils.SharedLab;
import com.pt.ptwebbase.Fragment.PTBaseWebFragment;
import com.pt.ptwebbase.Fragment.PTJsUtils;
import com.pt.ptwebbase.Services.PTInnerH5Service;
import com.pt.ptwebbase.Services.WebCommenTranstion;
import com.zhy.http.okhttp.callback.Callback;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bkclient.com.PTFragmentEnum;
import bkclient.com.ReadVc.ReadAdvManager;
import bkclient.com.Service.PTAnalysisManager;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.StandardMessageCodec;
import normal.Constant.ProjectConstant;
import normal.LibraryConfig.CommonTrastionConfig;
import normal.NetUtils.ProjectNetUtils;
import okhttp3.Call;

public class ReadPlugin {
//    private static ReadPlugin instance;

    //
//    public static synchronized ReadPlugin getInstance() {
//        if (instance == null) {
//            instance = new ReadPlugin();
//        }
//        return instance;
//    }
    public ReadPlugin() {
        addDefaultMethod();
    }

    MethodChannel methodChannel;
    private static final String channelName = "com.novelmokey.read/native_get";

    private Activity currentActivity;
    private Handler h = new Handler();

    //存储flutter调用原生的方法
    public final Map<String, PluginAppInterface> dealFlutterCallMap = new HashMap<>();

    public void registerWith(Activity activity, @NonNull FlutterEngine flutterEngine) {
        currentActivity = activity;
        methodChannel = new MethodChannel(flutterEngine.getDartExecutor().getBinaryMessenger(), channelName);


        methodChannel.setMethodCallHandler(new MethodChannel.MethodCallHandler() {

            @Override
            public void onMethodCall(@NonNull MethodCall call, @NonNull MethodChannel.Result callBack) {
                h.post(new Runnable() {
                    @Override
                    public void run() {
                        PluginAppInterface flutterCall = dealFlutterCallMap.get(call.method);
                        if (flutterCall != null) {
                            PTTools.loge("PTFlutter", call.method + " >>>> " + PTTools.toJson(call.arguments));
                            flutterCall.flutterCallApp(PTTools.toJson(call.arguments), new PluginResultListener() {
                                @Override
                                public void success(@Nullable Object result) {
                                    String jsonData = null;
                                    if (result != null) {
                                        jsonData = PTTools.toJson(result);
                                    }
                                    callBack.success(jsonData);
                                }

                                @Override
                                public void error(String errorCode, @Nullable String errorMessage, @Nullable Object errorDetails) {
                                    callBack.error(errorCode, errorMessage, errorDetails);
                                }
                            });
                        }
                    }
                });
            }
        });

        addDefaultMethod();
    }

    /**
     * 调用flutter方法
     *
     * @param methodName
     * @param jsonData
     */
    public void callFlutter(String methodName, String jsonData, PluginResultListener listener) {
        methodChannel.invokeMethod(methodName, jsonData, new MethodChannel.Result() {
            @Override
            public void success(@Nullable Object result) {
                if (listener != null) {
                    listener.success(result);
                }
            }

            @Override
            public void error(String errorCode, @Nullable String errorMessage, @Nullable Object errorDetails) {
                if (listener != null) {
                    listener.error(errorCode, errorMessage, errorDetails);
                }
            }

            @Override
            public void notImplemented() {
                if (listener != null) {
                    listener.error("-1", "error", null);
                }
            }
        });
    }

    private void addDefaultMethod() {
        /**
         * 打印日志
         */
        dealFlutterCallMap.put(PTJsUtils.JsCallAppActionName.toast, new PluginAppInterface() {
            @Override
            public void flutterCallApp(String jsonStr, PluginResultListener callBack) {
                PTTools.toast(jsonStr);
            }
        });
        /**
         * 打印日志
         */
        dealFlutterCallMap.put(PTJsUtils.JsCallAppActionName.loge, new PluginAppInterface() {
            @Override
            public void flutterCallApp(String jsonStr, PluginResultListener callBack) {
                jsonStr = PTTools.toJson(jsonStr);
                PTTools.loge(jsonStr);
            }
        });
        dealFlutterCallMap.put(PTJsUtils.JsCallAppActionName.cacheData, new PluginAppInterface() {
            @Override
            public void flutterCallApp(String jsonStr, PluginResultListener callBack) {
                jsonStr = PTTools.toJson(jsonStr);
                PTTools.loge("JsCallAppActionName.cacheData  .>>" + jsonStr);
                PTJsUtils.CacheDataModel bean = PTTools.fromJson(jsonStr, PTJsUtils.CacheDataModel.class);
                try {
                    if (bean.data == null) {
                        SharedLab.deleteObj(bean.key);
                    } else {
                        SharedLab.setObject(bean.key, bean.data);
                    }

                } catch (Exception e) {
                    if (PTTools.isDebugTest) {
                        e.printStackTrace();
                    }
                } finally {
                    callBack.success(null);
                }
            }
        });
        dealFlutterCallMap.put(PTJsUtils.JsCallAppActionName.getCacheData, new PluginAppInterface() {
            @Override
            public void flutterCallApp(String jsonStr, PluginResultListener callBack) {
                try {
                    String data = SharedLab.getString(jsonStr);
                    PTBaseNetResponseBean<String> resp = new PTBaseNetResponseBean<>(0, "成功", data);
                    callBack.success(PTTools.toJson(resp));
                } catch (Exception e) {
                    if (PTTools.isDebugTest) {
                        e.printStackTrace();
                    }
                }
            }
        });

        /**
         * 打印日志
         */
        dealFlutterCallMap.put(PTJsUtils.JsCallAppActionName.postNetMapData, new PluginAppInterface() {
            @Override
            public void flutterCallApp(String jsonStr, PluginResultListener callBack) {
                jsonStr = PTTools.toJson(jsonStr);
                Map<String, Object> map = PTTools.fromJson(jsonStr, new TypeToken<Map<String, Object>>() {
                }.getType());
                Map<String, Object> mapParams = new HashMap<>();
                if (map.get("params") != null) {
                    mapParams = (Map<String, Object>) map.get("params");
                }

//                Map<String, Object> mapParams = new HashMap<>();
//                if (PTTools.isEmptyStr(params) == false) {
//                    params = PTTools.decodeBase64(params);
//                    mapParams = PTTools.fromJson(params, new TypeToken<Map<String, Object>>() {
//                    }.getType());
//                }


                RespObjCall<String> respCallBack = new RespObjCall<String>() {
                    @Override
                    public void onResponse(String s, int i) {
                        PTBaseNetResponseBean<String> result = new PTBaseNetResponseBean<>(0, "succeed", s);

                        String json = PTTools.toJson(result);

                        callBack.success(json);
                    }

                    @Override
                    public void onHttpError(Call call, Exception e, int i) {
                        PTBaseNetResponseBean<String> result = new PTBaseNetResponseBean<>(-1, "error", null);

                        String json = PTTools.toJson(result);

                        callBack.success(json);
                    }
                };
                if (PTBaseConfig.baseInfo.isNeedPostJson) {
                    PTHttpMannager.getInstance().postJsonService((String) map.get("url"), mapParams, respCallBack);
                } else {
                    PTHttpMannager.getInstance().postDataService((String) map.get("url"), mapParams, respCallBack);
                }
            }
        });

        /**
         * 打印日志
         */
        dealFlutterCallMap.put(PTJsUtils.JsCallAppActionName.getNetMapData, new PluginAppInterface() {
            @Override
            public void flutterCallApp(String jsonStr, PluginResultListener callBack) {
                jsonStr = PTTools.toJson(jsonStr);

                Map<String, Object> map = PTTools.fromJson(jsonStr, new TypeToken<Map<String, Object>>() {
                }.getType());
                Map<String, Object> mapParams = new HashMap<>();
                if (map.get("params") != null) {
                    mapParams = (Map<String, Object>) map.get("params");
                }

//                Map<String, Object> mapParams = new HashMap<>();
//                if (PTTools.isEmptyStr(params) == false) {
//                    params = PTTools.decodeBase64(params);
//                    mapParams = PTTools.fromJson(params, new TypeToken<Map<String, Object>>() {
//                    }.getType());
//                }


                RespObjCall<String> respCallBack = new RespObjCall<String>() {
                    @Override
                    public void onResponse(String s, int i) {
                        PTBaseNetResponseBean<String> result = new PTBaseNetResponseBean<>(0, "succeed", s);

                        String json = PTTools.toJson(result);

                        callBack.success(json);
                    }

                    @Override
                    public void onHttpError(Call call, Exception e, int i) {
                        PTBaseNetResponseBean<String> result = new PTBaseNetResponseBean<>(-1, "error", null);

                        String json = PTTools.toJson(result);

                        callBack.success(json);
                    }
                };

                PTHttpMannager.getInstance().getDataService((String) map.get("url"), mapParams, respCallBack);
            }
        });

        dealFlutterCallMap.put("appflyAna", new PluginAppInterface() {
            @Override
            public void flutterCallApp(String jsonStr, PluginResultListener callBack) {

            }
        });
        /**
         * 跳转小说页面
         */
        dealFlutterCallMap.put("goToReadNovel", new PluginAppInterface() {
            @Override
            public void flutterCallApp(String jsonStr, PluginResultListener callBack) {
                WebCommenTranstion.getInstance().checkNetInnerH5Infor(ProjectConstant.localZipLeader, currentActivity, true, new WebCommenTranstion.InnerH5CheckListener() {
                    @Override
                    public void checkH5Ok() {
                        CommonTrastionConfig.getInstance().dealCommonTranstionAction("readerBoook", currentActivity, jsonStr);

//                        String url = PTInnerH5Service.getNetInnerH5Url(ProjectConstant.localZipLeader + "/project/novelmonkey/novel/book/read/read_home.html");
//                        Map<String, Object> params = PTTools.fromJson(jsonStr, Map.class);
//                        String jsonData = PTBaseWebFragment.WebJsonDataBean.getJsonData(url, 1, 0, params);
//
//                        PTSecondAct.goSecondActForResult(currentActivity, jsonData, PTFragmentEnum.ReadPageFragment,1001);
                    }
                });
            }
        });

        /**
         * 选择用户头像
         */
        dealFlutterCallMap.put("selectUserImg", new PluginAppInterface() {
            @Override
            public void flutterCallApp(String jsonStr, PluginResultListener callBack) {

                PTGetImgTool.setGetlistener(new PTGetImgTool.PTGetImgListener() {
                    @Override
                    public void getResultImg(List<String> filePaths) {
                        if (filePaths.size() > 0) {
                            String filePath = filePaths.get(0);
                            List<HttpNetUtils.PTPostFileModel> fileList = new ArrayList<>();
                            fileList.add(new HttpNetUtils.PTPostFileModel("headImage", "headImage.png", new File(filePath)));
                            PTHttpMannager.getInstance().postFileService(ProjectNetUtils.postUserIconUrl, null, fileList, new RespObjCall<String>() {
                                @Override
                                public void onHttpError(Call call, Exception e, int i) {
                                    PTBaseNetResponseBean<String> result = new PTBaseNetResponseBean<>(-1, "net_error", null);
                                    String json = PTTools.toJson(result);
                                    callBack.success(json);
                                }

                                @Override
                                public void onResponse(String o, int i) {
                                    Map<String, Object> map = PTTools.fromJson(o, new TypeToken<Map<String, Object>>() {
                                    }.getType());
                                    if (map.get("code").equals("000")) {
                                        PTBaseNetResponseBean<String> result = new PTBaseNetResponseBean<>(0, "succeed", (String) map.get("content"));
                                        String json = PTTools.toJson(result);
                                        callBack.success(json);
                                    } else {
                                        PTBaseNetResponseBean<String> result = new PTBaseNetResponseBean<>(-1, "error", null);
                                        String json = PTTools.toJson(result);
                                        callBack.success(json);
                                    }
                                }
                            });
                        }
                    }
                });

                PTGetImgTool.getImgFromNativeCameraOrAblum(currentActivity,0);
            }
        });

        dealFlutterCallMap.put("monkey_analysis", new PluginAppInterface() {
            @Override
            public void flutterCallApp(String jsonStr, PluginResultListener callBack) {
                Map<String,Object> map = PTTools.fromJson(jsonStr,new TypeToken<Map<String,Object>>(){}.getType());
                String key = (String) map.get("key");
                Map<String,Object> value = (Map<String, Object>) map.get("value");
                PTAnalysisManager.getInstance().addEvent(currentActivity,key,value);
            }
        });

    }

    /**
     * 处理flutter 调用 原生的 类
     */
    public static interface PluginAppInterface {
        /**
         * 当js调取原生时，调用此方法
         *
         * @param jsonStr
         */
        public void flutterCallApp(String jsonStr, PluginResultListener callBack);
    }

    /**
     * 回调flutter的工具类
     */
    public interface PluginResultListener {
        @UiThread
        void success(@Nullable Object result);

        @UiThread
        void error(String errorCode, @Nullable String errorMessage, @Nullable Object errorDetails);

        public static interface JsCallAppInterface {

            /**
             * 当js调取原生时，调用此方法
             *
             * @param actionCode
             * @param jsonStr
             */
            public void jsCallApp(String actionCode, String jsonStr);
        }
    }
}
