package cn.ycbjie.ycaudioplayer.utils.url;

import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import java.util.HashMap;
import java.util.List;
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
        Uri uri = Uri.parse(url);
        // 完整的url信息
        String urlStr = uri.toString();
        Log.e( "UrlUtils","url: " + urlStr);
        // scheme部分
        String scheme = uri.getScheme();
        Log.e( "UrlUtils","scheme: " + scheme);
        // host部分
        String host = uri.getHost();
        Log.e( "UrlUtils","host: " + host);
        //port部分
        int port = uri.getPort();
        Log.e( "UrlUtils","port: " + port);
        // 访问路劲
        String path = uri.getPath();
        Log.e( "UrlUtils","path: " + path);
        List<String> pathSegments = uri.getPathSegments();
        Log.e( "UrlUtils","pathSegments: " + pathSegments.toString());
        // Query部分
        String query = uri.getQuery();
        Log.e( "UrlUtils","query: " + query);
        //获取此URI的解码权限部分。对于服务器地址，权限的结构如下：Examples: "google.com", "bob@google.com:80"
        String authority = uri.getAuthority();
        Log.e( "UrlUtils","authority: " + authority);
        //从权限获取已解码的用户信息。例如，如果权限为“任何人@google.com”，此方法将返回“任何人”。
        String userInfo = uri.getUserInfo();
        Log.e( "UrlUtils","userInfo: " + userInfo);


        //UrlUtils: url: https://m.dev.haowumc.com/app/financialManagement
        //UrlUtils: scheme: https
        //UrlUtils: host: m.dev.haowumc.com
        //UrlUtils: port: -1
        //UrlUtils: path: /app/financialManagement
        //UrlUtils: pathSegments: [app, financialManagement]
        //UrlUtils: query: null
        //UrlUtils: authority: m.dev.haowumc.com
        //UrlUtils: userInfo: null

        Uri.Builder builder = uri.buildUpon();
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
