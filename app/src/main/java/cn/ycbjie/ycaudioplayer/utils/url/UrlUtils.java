package cn.ycbjie.ycaudioplayer.utils.url;

import android.net.Uri;
import android.text.TextUtils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * <pre>
 *     @author yangchong
 *     blog  :
 *     time  : 2017/6/6
 *     desc  : url工具类
 *     revise:
 * </pre>
 */
public class UrlUtils {


    /**
     * 拼接字符串
     * @param url                       url
     * @param map                       map集合
     * @return
     */
    public static String getUrl(String url, HashMap<String, String> map){
        if(TextUtils.isEmpty(url)){
            return null;
        }
        //解析一个url
        Uri parse = Uri.parse(url);
        Uri.Builder builder = parse.buildUpon();
        if (map != null && map.size() > 0) {
            //使用迭代器进行遍历
            for (Object o : map.entrySet()) {
                Map.Entry entry = (Map.Entry) o;
                String key = (String) entry.getKey();
                String value = (String) entry.getValue();
                //对键和值进行编码，然后将参数追加到查询字符串中。
                builder.appendQueryParameter(key, value);
            }
        }
        return builder.toString();
    }


}
