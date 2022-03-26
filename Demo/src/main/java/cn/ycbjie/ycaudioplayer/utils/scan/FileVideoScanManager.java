package cn.ycbjie.ycaudioplayer.utils.scan;


import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import android.support.annotation.NonNull;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.ycbjie.ycaudioplayer.model.bean.VideoBean;

/**
 * <pre>
 *     author: yangchong
 *     blog  : https://github.com/yangchong211
 *     time  : 2018/01/22
 *     desc  : 本地视频扫描工具类
 *     revise:
 * </pre>
 */

public class FileVideoScanManager {

    private static FileVideoScanManager mInstance;
    private static final Object mLock = new Object();

    public static FileVideoScanManager getInstance(){
        if (mInstance == null){
            synchronized (mLock){
                if (mInstance == null){
                    mInstance = new FileVideoScanManager();
                }
            }
        }
        return mInstance;
    }

    /**----------------------------------扫描视频------------------------------------------------**/

    /**
     * 扫描歌曲
     */
    @NonNull
    public List<VideoBean> scanMusic(Context context) {
        List<VideoBean> videos = new ArrayList<>();
        Cursor c = null;
        try {
            c = context.getContentResolver()
                    .query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                            null,
                            null,
                            null,
                            MediaStore.Video.Media.DEFAULT_SORT_ORDER);

            if (c == null) {
                return videos;
            }

            int i = 0;
            while (c.moveToNext()) {
                // 路径
                String path = c.getString(c.getColumnIndexOrThrow(MediaStore.Video.Media.DATA));
                if (!new File(path).exists()) {
                    continue;
                }

                int id = c.getInt(c.getColumnIndexOrThrow(MediaStore.Video.Media._ID));// 视频的id
                //int title = c.getInt( c.getColumnIndexOrThrow(MediaStore.Video.Media.TITLE));// 视频标题
                String name = c.getString(c.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME)); // 视频名称
                String resolution = c.getString(c.getColumnIndexOrThrow(MediaStore.Video.Media.RESOLUTION)); //分辨率
                long size = c.getLong(c.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE));// 大小
                long duration = c.getLong(c.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION));// 时长
                long date = c.getLong(c.getColumnIndexOrThrow(MediaStore.Video.Media.DATE_MODIFIED));//修改时间


                VideoBean video = new VideoBean();
                video.setPath(path);
                video.setType(VideoBean.Type.LOCAL);
                video.setId(id);
                video.setTitle(name);
                video.setResolution(resolution);
                video.setFileSize(size);
                video.setDuration(duration);
                video.setDate(date);
                if (++i <= 20) {
                    // 只加载前20首的缩略图
                    Bitmap videoThumbnail = getVideoThumbnail(context, id);
                    video.setVideoThumbnail(videoThumbnail);
                }

                videos.add(video);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (c != null) {
                c.close();
            }
        }
        return videos;
    }


    /**
     * 获取视频缩略图
     * @return              获取视频缩略图
     */
    private static Bitmap getVideoThumbnail(Context context, int id) {
        Bitmap bitmap ;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inDither = false;
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        bitmap = MediaStore.Video.Thumbnails.getThumbnail(
                context.getContentResolver(), id, MediaStore.Images.Thumbnails.MICRO_KIND, options);
        return bitmap;
    }

}
