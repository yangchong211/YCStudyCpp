package cn.ycbjie.ycaudioplayer.model.bean;

import java.io.Serializable;

/**
 * <pre>
 *     @author yangchong
 *     blog  : www.pedaily.cn
 *     time  : 2018/03/22
 *     desc  : 视频信息
 *     revise:
 * </pre>
 */

public class OfficeBean implements Serializable {

    /** 文件的路径*/
    public String path;
    /**文件图片资源的id，drawable或mipmap文件中已经存放doc、xml、xls等文件的图片*/
    public int iconId;

    public OfficeBean(String path, int iconId) {
        this.path = path;
        this.iconId = iconId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }

    /**
     * 对比本地文件是否相同
     */
    @Override
    public boolean equals(Object o) {
        return o instanceof OfficeBean && this.getIconId() == ((OfficeBean) o).getIconId();
    }
}
