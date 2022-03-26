package cn.ycbjie.ycaudioplayer.utils.scan;


/**
 * <pre>
 *     author: yangchong
 *     blog  : https://github.com/yangchong211
 *     time  : 2018/01/22
 *     desc  : 本地图片扫描工具类
 *     revise:
 * </pre>
 */

public class FileImageScanManager {

    private static FileImageScanManager mInstance;
    private static final Object mLock = new Object();

    public static FileImageScanManager getInstance(){
        if (mInstance == null){
            synchronized (mLock){
                if (mInstance == null){
                    mInstance = new FileImageScanManager();
                }
            }
        }
        return mInstance;
    }

    /**----------------------------------扫描图片------------------------------------------------**/




}
