package cn.ycbjie.ycaudioplayer.utils.scan;


import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.ycbjie.ycaudioplayer.R;
import cn.ycbjie.ycaudioplayer.model.bean.OfficeBean;

/**
 * <pre>
 *     author: yangchong
 *     blog  : https://github.com/yangchong211
 *     time  : 2018/01/22
 *     desc  : 本地office扫描工具类
 *     revise:
 * </pre>
 */

public class FileOfficeScanManager {

    /**文档类型*/
    public static final int TYPE_DOC = 0;
    /**apk类型*/
    public static final int TYPE_APK = 1;
    /**压缩包类型*/
    public static final int TYPE_ZIP = 2;

    private static FileOfficeScanManager mInstance;
    private static final Object mLock = new Object();

    public static FileOfficeScanManager getInstance(){
        if (mInstance == null){
            synchronized (mLock){
                if (mInstance == null){
                    mInstance = new FileOfficeScanManager();
                }
            }
        }
        return mInstance;
    }

    /**----------------------------------扫描图片------------------------------------------------**/

    /**
     * 通过文件类型得到相应文件的集合
     **/
    public List<OfficeBean> getFilesByType(Context context , int fileType) {
        List<OfficeBean> files = new ArrayList<>();
        // 扫描files文件库
        Cursor c = null;
        try {
            c = context.getContentResolver()
                    .query(MediaStore.Files.getContentUri("external"),
                            new String[]{"_id", "_data", "_size"},
                            null,
                            null,
                            null);

            if (c == null) {
                return files;
            }


            int dataIndex = c.getColumnIndex(MediaStore.Files.FileColumns.DATA);
            int sizeIndex = c.getColumnIndex(MediaStore.Files.FileColumns.SIZE);

            while (c.moveToNext()) {
                String path = c.getString(dataIndex);

                if (getFileType(path) == fileType) {
                    if (!new File(path).exists()) {
                        continue;
                    }
                    long size = c.getLong(sizeIndex);
                    OfficeBean fileBean = new OfficeBean(path, getFileIconByPath(path));
                    files.add(fileBean);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (c != null) {
                c.close();
            }
        }
        return files;
    }


    private int getFileType(String path) {
        path = path.toLowerCase();
        if (path.endsWith(".doc") || path.endsWith(".docx")
                || path.endsWith(".xls") || path.endsWith(".xlsx")
                || path.endsWith(".ppt") || path.endsWith(".pptx")
                || path.endsWith(".pdf") || path.endsWith(".txt")) {
            return TYPE_DOC;
        }else if (path.endsWith(".apk")) {
            return TYPE_APK;
        }else if (path.endsWith(".zip") || path.endsWith(".rar")
                || path.endsWith(".tar") || path.endsWith(".gz")) {
            return TYPE_ZIP;
        }else{
            return -1;
        }
    }


    /**通过文件名获取文件图标*/
    private int getFileIconByPath(String path){
        path = path.toLowerCase();
        int iconId = R.drawable.rc_file_icon_file;
        if (path.endsWith(".txt")){
            iconId = R.drawable.rc_file_icon_file;
        }else if(path.endsWith(".doc") || path.endsWith(".docx")){
            iconId = R.drawable.rc_file_icon_word;
        }else if(path.endsWith(".xls") || path.endsWith(".xlsx")){
            iconId = R.drawable.rc_file_icon_excel;
        }else if(path.endsWith(".ppt") || path.endsWith(".pptx")){
            iconId = R.drawable.rc_file_icon_file;
        }else if(path.endsWith(".xml")){
            iconId = R.drawable.rc_file_icon_file;
        }else if(path.endsWith(".htm") || path.endsWith(".html")){
            iconId = R.drawable.rc_file_icon_file;
        }
        return iconId;
    }

}
