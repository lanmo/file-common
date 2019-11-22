package org.ifaster.file.annotation;

import java.lang.annotation.*;

/**
 * @author yangnan
 * 数据实体
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ImporterBody {

    /**
     * header 表头 无实际作用只是用来做个标识
     * @return
     */
    String header() default "";

    /**
     * 解析器
     * 时间格式使用的{@link java.text.SimpleDateFormat}
     * 数字 {@link java.text.DecimalFormat#format}
     * @return
     */
    String format() default "";

    /**
     * 索引值 不能小于0
     *
     * @return
     */
    int index() default 0;

    /**
     * 组合索引值，将某两个字段的索引值合并成一个
     * 如果配置了这个字段, #index将不使用
     *
     * @return
     */
    int[] composeIndex() default {};
}
