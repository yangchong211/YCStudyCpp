package cn.ycbjie.ycaudioplayer.ui.web;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 不可用的方法版本号
 * 过滤掉，实际开发中临时解决前版本的不兼容问题
 */
@Target({ElementType.TYPE,
        ElementType.METHOD,
        ElementType.FIELD,
        ElementType.CONSTRUCTOR})
@Retention(RetentionPolicy.RUNTIME)
public @interface MethodNoVersionCode {
    String[] value() default "1.0.0";
}
