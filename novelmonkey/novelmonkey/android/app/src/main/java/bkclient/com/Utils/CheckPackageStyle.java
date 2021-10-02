package bkclient.com.Utils;

import android.content.Context;
import android.content.pm.PackageInfo;

import com.pt.ptbase.PTBaseConfig;
import com.pt.ptbase.Utils.PhoneInfo;

public class CheckPackageStyle {
    private static CheckPackageStyle instance;

    public static synchronized CheckPackageStyle getInstance() {
        if (instance == null) {
            instance = new CheckPackageStyle();
        }
        return instance;
    }


    private PTPlatEnum platStyle;

    public PTPlatEnum getPlatStyle() {
        if (platStyle == null) {
            initPlatInfor(PTBaseConfig.baseInfo.appContext);
        }
        return platStyle;
    }

    /*
     * 初始化
     */
    public void initPlatInfor(Context context) {
        PackageInfo info = PhoneInfo.getPackageData(context);
        switch (info.packageName) {
            case "anybooks.kindle.webnovel.fiction.wattpad.novels.ebooks.read": {
                platStyle = PTPlatEnum.PTPlatEnum_AnyBooks;
            }
            break;
            case "novelmonkey.fiction.kindle.webnovel.wattpad.novels.ebooks.read.novelmonkey": {
                platStyle = PTPlatEnum.PTPlatEnum_Monkey;
            }
            break;
            default: {
                platStyle = PTPlatEnum.PTPlatEnum_Monkey;
            }
            break;
        }
    }

    public static enum PTPlatEnum {
        PTPlatEnum_AnyBooks,
        PTPlatEnum_Monkey
    }
}
