package org.ifaster.file.annotation;

import org.ifaster.file.listener.Listener;
import org.ifaster.file.listener.NoOpListener;

import java.lang.annotation.*;

/**
 * @author yangnan
 * 数据实体
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Column {

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
     * 索引值 越小越靠前
     * 不设置默认是字段书写顺序
     * 导入的时候 必须>=0
     * @return
     */
    int index() default 0;

    /**
     * 组合索引 数组里面写索引下标
     *
     * @return
     */
    int[] composeIndex() default {};

    /**
     * 配合 #composeIndex使用，组合索引的几个值的连接符
     *
     * @return
     */
    String composeConnector() default " ";

    /**
     * 导入
     *
     * @return
     */
    Class<? extends Listener> listener() default NoOpListener.class;

}
