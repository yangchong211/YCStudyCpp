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


    public static String getH5Url(String url, HashMap<String, String> map) {
        if (TextUtils.isEmpty(url)) {
            return null;
        }
        Uri.Builder builder = Uri.parse(url).buildUpon();
        if (map != null && map.size() > 0) {
            Iterator it = map.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry entry = (Map.Entry) it.next();
                String key = (String) entry.getKey();
                String value = (String) entry.getValue();
                builder.appendQueryParameter(key, value);
            }
        }
        return builder.toString();
    }


}
