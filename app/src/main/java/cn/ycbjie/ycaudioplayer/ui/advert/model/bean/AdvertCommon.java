package cn.ycbjie.ycaudioplayer.ui.advert.model.bean;

import java.io.Serializable;

/**
 * Created by yc on 2018/2/8.
 */

public class AdvertCommon {

    /**
     * 返回码，0成功
     */
    public String message;
    /**
     * 返回状态 200代表成功
     */
    public int status;
    /**
     * 具体内容
     */
    public Splash splash;

    public class Splash implements Serializable {

        /**这里需要写死序列化Id*/
        private static final long serialVersionUID = 7382351359868556980L;
        public int id;
        /**大图 url*/
        public String burl;
        /**小图url*/
        public String surl;
        /**点击跳转 URl*/
        public String clickUrl;
        /**图片的存储地址*/
        public String savePath;
        /**图片的存储地址*/
        public String title;

        public Splash(String burl, String surl, String clickUrl, String savePath) {
            this.burl = burl;
            this.surl = surl;
            this.clickUrl = clickUrl;
            this.savePath = savePath;
        }

    }


}
