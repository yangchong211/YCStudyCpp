package cn.ycbjie.ycaudioplayer.utils.app;


import android.text.TextUtils;
import android.util.Log;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.FieldNamingStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.Type;

/**
 * <pre>
 *     @author yangchong
 *     blog  :
 *     time  : 2017/6/6
 *     desc  : json工具类
 *     revise:
 * </pre>
 */
public class JsonUtils {


    private static Gson gson;

    /**
     * 第一种方式
     * @return              Gson对象
     */
    public static Gson getJson() {
        if (gson == null) {
            GsonBuilder builder = new GsonBuilder();
            builder.setLenient();
            builder.setFieldNamingStrategy(new AnnotateNaming());
            builder.serializeNulls();
            gson = builder.create();
        }
        return gson;
    }

    private static class AnnotateNaming implements FieldNamingStrategy {
        @Override
        public String translateName(Field field) {
            ParamNames a = field.getAnnotation(ParamNames.class);
            return a != null ? a.value() : FieldNamingPolicy.IDENTITY.translateName(field);
        }
    }

    /**
     * 第二种方式
     * @return              Gson对象
     */
    public static Gson getGson() {
        if (gson == null) {
            gson = new GsonBuilder()            //builder构建者模式
                    .setLenient()               //json宽松
                    .enableComplexMapKeySerialization() //支持Map的key为复杂对象的形式
                    .setPrettyPrinting()        //格式化输出
                    .serializeNulls()           //智能null
                    //.setDateFormat("yyyy-MM-dd HH:mm:ss:SSS")       //格式化时间
                    .disableHtmlEscaping()      //默认是GSON把HTML转义的
                    .registerTypeAdapter(int.class, new JsonDeserializer<Integer>() {
                        //根治服务端int 返回""空字符串
                        @Override
                        public Integer deserialize(JsonElement json, Type typeOfT,
                                                   JsonDeserializationContext context)
                                throws JsonParseException {
                            //try catch不影响效率
                            try {
                                return json.getAsInt();
                            } catch (NumberFormatException e) {
                                return 0;
                            }
                        }
                    })
                    .create();
        }
        return gson;
    }


    /*-----------------------------------格式化输出网络请求---------------------------------------*/

    private static final String LINE_SEPARATOR = System.getProperty("line.separator");
    public static void printJson(String tag, String msg) {
        String message;
        try {
            if (msg.startsWith("{")) {
                JSONObject jsonObject = new JSONObject(msg);
                //最重要的方法，就一行，返回格式化的json字符串，其中的数字4是缩进字符数
                message = jsonObject.toString(4);
            } else if (msg.startsWith("[")) {
                JSONArray jsonArray = new JSONArray(msg);
                message = jsonArray.toString(4);
            } else {
                message = msg;
            }
        } catch (JSONException e) {
            message = msg;
        }
        if(isGoodJson(message)){
            printLine(tag, true);
            String[] lines = message.split(LINE_SEPARATOR);
            for (String line : lines) {
                Log.e(tag, "║ " + line);
            }
            printLine(tag, false);
        }
    }

    private static void printLine(String tag, boolean isTop) {
        if (isTop) {
            Log.e(tag, "Http---------------------");
        } else {
            Log.e(tag, "Http---------------------");
        }
    }

    private static boolean isGoodJson(String json) {
        if (TextUtils.isEmpty(json)) {
            return false;
        }
        try {
            new JsonParser().parse(json);
            return true;
        } catch (JsonSyntaxException e) {
            return false;
        } catch (JsonParseException e) {
            return false;
        }
    }

    /*-----------------------------------格式化输出网络请求---------------------------------------*/


}


@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface ParamNames {
    String value();
}
