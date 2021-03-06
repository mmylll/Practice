package bkclient.com.ReadVc;

import android.content.pm.ActivityInfo;
import android.view.WindowManager;

import com.google.gson.reflect.TypeToken;
import com.jaeger.library.StatusBarUtil;
import com.pt.ptbase.Activity.Base.PTBaseActivity;
import com.pt.ptbase.Bean.PTBaseNetResponseBean;
import com.pt.ptbase.NetUtils.BasenetUtils.RespObjCall;
import com.pt.ptbase.NetUtils.PTHttpMannager;
import com.pt.ptbase.Utils.PTDialogProfig;
import com.pt.ptbase.Utils.PTFileUtils;
import com.pt.ptbase.Utils.PTTools;
import com.pt.ptwebbase.Fragment.PTBaseWebFragment;
import com.pt.ptwebbase.Fragment.PTJsUtils;
import com.zhy.http.okhttp.callback.FileCallBack;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import bkclient.com.Service.PTAnalysisManager;
import normal.Constant.ProjectConstant;
import normal.NetUtils.ProjectHttpManager;
import okhttp3.Call;

public class ReadPageFragment extends PTBaseWebFragment {
    ReadAdvManager advManager = new ReadAdvManager();

    @Override
    public void onResume() {
        super.onResume();
//        StatusBarUtil.setDarkMode(getMyActivity());

        if (getMyActivity() instanceof PTBaseActivity){
            ((PTBaseActivity)getMyActivity()).setFullWindow(true);
        }
        ReadAdvManager.getInstance().loadInterstitialAd();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (getMyActivity() instanceof PTBaseActivity){
            ((PTBaseActivity)getMyActivity()).setFullWindow(false);
        }
    }

    @Override
    public String getSelfID() {
        return null;
    }

    @Override
    protected void initData() {
        super.initData();
        statusBar.setBackgroundColor(0xff000000);



        getMyActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getMyActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//                    getMyActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
//        showStatusBar(false);

        addJsApi();

        advManager.initAdv(getMyActivity());
        advManager.loadInterstitialAd();
    }

    private void addJsApi(){
        //???????????????
        jsUtils.addJsCallAppAction("goOutRead", new PTJsUtils.JsCallAppInterface() {
            @Override
            public void jsCallApp(String actionCode, String jsonStr) {
                pop(null);
            }
        });

        //????????????
        jsUtils.addJsCallAppAction("showChaYeAdv", new PTJsUtils.JsCallAppInterface() {
            @Override
            public void jsCallApp(String actionCode, String jsonStr) {
                advManager.showChaYeAdv(new ReadAdvManager.AdvShowListener() {
                    @Override
                    public void showSucceed() {
                        PTBaseNetResponseBean bean = new PTBaseNetResponseBean(0,"succeed",null);
                        jsUtils.returnToJs(actionCode,PTTools.toJson(bean));
                    }

                    @Override
                    public void showFailed() {
                        PTBaseNetResponseBean bean = new PTBaseNetResponseBean(-1,"net_error",null);
                        jsUtils.returnToJs(actionCode,PTTools.toJson(bean));
                    }
                });
            }
        });

        jsUtils.addJsCallAppAction("goToBuyCoinPage", new PTJsUtils.JsCallAppInterface() {
            @Override
            public void jsCallApp(String actionCode, String jsonStr) {
                pop("goToBuyCoinPage");
            }
        });

        //??????????????????
        jsUtils.addJsCallAppAction("getChapterText", new PTJsUtils.JsCallAppInterface() {
            @Override
            public void jsCallApp(final String actionCode, String jsonStr) {
                final GetChapterJsonModel bean = PTTools.fromJson(jsonStr,GetChapterJsonModel.class);

                //????????????????????????
                final String bookPath = ProjectConstant.getBooksFilePath(getMyActivity()) + "/" + bean.bookId;
                final String chapterName = bean.chapterId + ".txt";
                final String filePath = bookPath + "/" + chapterName;

                String text = PTFileUtils.readTxtFile(filePath);
                if (text != null){
                    if (bean.isOnlyPrepare == 0) {
                        //?????????????????????????????????
                        PTBaseNetResponseBean<String> response = new PTBaseNetResponseBean<>(0, "succeed", text);
                        String json = PTTools.toJson(response);
                        PTTools.loge(json);
                        jsUtils.returnToJs(actionCode, json);
                    }
                    return;
                }
                //???????????????????????????????????????
                File bookFile = new File(bookPath);
                if(bookFile.exists() == false){
                    bookFile.mkdirs();
                }
                if (bean.isShowDialog == 1 && bean.isOnlyPrepare == 0) {
                    PTDialogProfig.showProgressDialog(getMyActivity());
                }
                if (PTTools.isEmptyStr(bean.downloadUrl) == false){
                    //???????????????????????????????????????
                    PTHttpMannager.getInstance().downLoadFile(bean.downloadUrl,null,new FileCallBack(bookPath,chapterName){

                        @Override
                        public void onError(Call call, Exception e, int i) {
                            PTDialogProfig.dissMissAllDialog();
                            if (bean.isOnlyPrepare == 0) {
                                PTBaseNetResponseBean<String> response = new PTBaseNetResponseBean<>(-1, "net_error", null);
                                jsUtils.returnToJs(actionCode, PTTools.toJson(response));
                            }
                        }

                        @Override
                        public void onResponse(File file, int i) {
                            PTDialogProfig.dissMissAllDialog();
                            if (bean.isOnlyPrepare == 0) {
                                String result = PTFileUtils.readTxtFile(filePath);
                                if (result != null) {
                                    PTBaseNetResponseBean<String> response = new PTBaseNetResponseBean<>(0, "succeed", result);
                                    jsUtils.returnToJs(actionCode, PTTools.toJson(response));
                                }
                            }
                        }
                    });
                }else{
                    //????????????????????????
                    ProjectHttpManager.getChapterDownLoadUrl(bean.chapterId, new RespObjCall<String>() {
                        @Override
                        public void onHttpError(Call call, Exception e, int i) {
                            PTDialogProfig.dissMissAllDialog();
                            if (bean.isOnlyPrepare == 0) {
                                PTBaseNetResponseBean<String> response = new PTBaseNetResponseBean<>(-1, "net_error", null);
                                jsUtils.returnToJs(actionCode, PTTools.toJson(response));
                            }
                        }

                        @Override
                        public void onResponse(String s, int i) {
                            Map<String,Object> map = PTTools.fromJson(s,new TypeToken<Map<String,Object>>() {}.getType());
                            if (map.get("code").equals("000")){
                                Map<String,Object> params = new HashMap<>();
                                params.put("book_id",bean.bookId);
                                PTAnalysisManager.getInstance().addEvent(getMyActivity(),"reader_unlockchap",params);

                                Map<String,String> content = (Map<String, String>) map.get("content");
                                //???????????????????????????????????????
                                PTHttpMannager.getInstance().downLoadFile(content.get("chapterDownloadUrl"),null,new FileCallBack(bookPath,chapterName){

                                    @Override
                                    public void onError(Call call, Exception e, int i) {
                                        PTDialogProfig.dissMissAllDialog();
                                        if (bean.isOnlyPrepare == 0) {
                                            PTBaseNetResponseBean<String> response = new PTBaseNetResponseBean<>(-1, "net_error", null);
                                            jsUtils.returnToJs(actionCode, PTTools.toJson(response));
                                        }
                                    }

                                    @Override
                                    public void onResponse(File file, int i) {
                                        PTDialogProfig.dissMissAllDialog();
                                        if (bean.isOnlyPrepare == 0) {
                                            String result = PTFileUtils.readTxtFile(filePath);
                                            if (result != null) {
                                                PTBaseNetResponseBean<String> response = new PTBaseNetResponseBean<>(0, "succeed", result);
                                                jsUtils.returnToJs(actionCode, PTTools.toJson(response));
                                            }
                                        }
                                    }
                                });
                            }else{
                                PTDialogProfig.dissMissAllDialog();
                                if (bean.isOnlyPrepare == 0) {
                                    PTBaseNetResponseBean<String> response = null;
                                    if (map.get("code").equals("1001")){
                                        response = new PTBaseNetResponseBean<>(-10, "net_error", null);
                                    }else{
                                        response = new PTBaseNetResponseBean<>(-1, "net_error", null);
                                    }
                                    jsUtils.returnToJs(actionCode, PTTools.toJson(response));
                                }
                            }

                        }
                    });
                }
            }
        });
    }



    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }

    //????????????????????????json??????
    private class GetChapterJsonModel{
        int bookId;//??????id
        int chapterId;//????????????id
        int isShowDialog;//??????????????????
        int isOnlyPrepare;//????????????????????????????????????????????????
        String downloadUrl;//??????????????????
    }
}
