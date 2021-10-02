package normal.Constant;

import android.content.Context;

import com.pt.ptbase.Utils.PTConstant;
import com.pt.ptbase.Utils.PTTools;

import java.io.File;

public class ProjectConstant {
    //本地存储的zip包 leader
    public static final String localZipLeader = "novelmonkeyLocal";

    //服务器存储的zip包 leader
    public static final String netZipLeader = "novelmonkey";

    /**
     * 获取书籍内容存储路径
     * @return
     */
    public static String getBooksFilePath(Context context){
        String bookPath = PTConstant.sandboxRootFilePath(context) + "/book";
        final File path = new File(bookPath);
        if (!path.exists()) {
            boolean mkdirs = path.mkdirs();
            if (!mkdirs) {
                PTTools.loge("文件夹创建失败");
            } else {
                PTTools.loge("文件夹创建成功");
            }
        }
        return bookPath;
    }

}
