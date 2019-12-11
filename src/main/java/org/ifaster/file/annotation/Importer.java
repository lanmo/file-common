package org.ifaster.file.annotation;

import org.ifaster.file.enums.SuffixEnum;

import java.lang.annotation.*;

/**
 * 文件导入类标识
 *
 * @author yangnan
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Importer {

    /**
     * 文件后缀
     * @return
     */
    SuffixEnum suffix() default SuffixEnum.TXT;

    /**
     * 列之间的分隔符
     * excel不起作用
     * @return
     */
    String column() default "";

    /**
     * 字符集
     * @return
     */
    String charSet() default "UTF-8";

    /**
     * 首行是否是header信息
     * true  表示 是
     * false 表示 否
     *
     * @return
     */
    boolean header() default true;
}
