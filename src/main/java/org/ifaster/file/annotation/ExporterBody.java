package org.ifaster.file.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author yangnan
 * 数据实体
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ExporterBody {

    /**
     * header 表头
     * @return
     */
    String header() default "";

    /**
     * 格式化
     * 时间格式使用的{@link java.text.SimpleDateFormat}
     * 数字 {@link java.text.DecimalFormat#format}
     * @return
     */
    String format() default "";

    /**
     * 排序 越小越靠前
     * 不设置默认是字段书写顺序
     * @return
     */
    int order() default 0;
}
