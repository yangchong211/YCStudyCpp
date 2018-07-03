package cn.ycbjie.ycaudioplayer.utils.file;


import android.text.TextUtils;

import com.blankj.utilcode.util.SDCardUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *     @author yangchong
 *     blog  :
 *     time  : 2017/6/9.
 *     desc  : 保存文件路径
 *     revise:
 * </pre>
 */
public class FileSaveUtils {


    /**
     * app保存路径
     */
    private final static String APP_ROOT_SAVE_PATH = "ycPlayer";
    private final static String property = File.separator;


    /**
     * 系统保存文件目录
     * @param name                  自己命名
     * @return                      路径
     */
    public static String getLocalRootSavePathDir(String name){
        if(TextUtils.isEmpty(name)){
            return "";
        }
        //获得SDCard 的路径,storage/sdcard
        String sdPath = SDUtils.getSDCardPath();

        //判断 SD 卡是否可用
        if (!SDCardUtils.isSDCardEnable() || TextUtils.isEmpty(sdPath)) {
            //获取 SD 卡路径
            List<String> sdPathList = SDCardUtils.getSDCardPaths();
            if (sdPathList != null && sdPathList.size() > 0 && !TextUtils.isEmpty(sdPathList.get(0))) {
                sdPath = sdPathList.get(0);
            }
        }
        if (TextUtils.isEmpty(sdPath)) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(sdPath);
        sb.append(property);
        sb.append(APP_ROOT_SAVE_PATH);
        sb.append(property);
        sb.append(name);
        sb.append(property);
        return sb.toString();
    }



}
