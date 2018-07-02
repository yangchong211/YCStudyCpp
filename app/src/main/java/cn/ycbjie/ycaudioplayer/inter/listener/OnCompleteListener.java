package cn.ycbjie.ycaudioplayer.inter.listener;


import cn.ycbjie.ycaudioplayer.db.dl.TasksManagerModel;

/**
 * <pre>
 *     @author yangchong
 *     blog  : www.pedaily.cn
 *     time  : 2018/03/22
 *     desc  : 下载完成监听器
 *     revise:
 * </pre>
 */

public interface OnCompleteListener {

    /**
     * 下载完成
     * @param model                 model
     * @param position              索引位置
     */
    void downloadCompleted(TasksManagerModel model, int position);
}
