package org.ifaster.file.annotation;

import static org.ifaster.file.constant.FileConstant.WINDOW_SIZE;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.ifaster.file.enums.SeparatorEnum;
import org.ifaster.file.enums.SuffixEnum;

/**
 * @author yangnan
 * 文档标识
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Exporter {

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
    SeparatorEnum column() default SeparatorEnum.COMMA;

    /**
     * 字符集
     * @return
     */
    String charSet() default "UTF-8";

    /**
     * excel xlsx 格式起作用 内存中保存多少行 大于这个数之后写入临时文件
     * 避免内存溢出
     * @return
     */
    int rowAccessWindowSize() default WINDOW_SIZE;

    /**
     * 是否导出header信息,默认导出
     * 如果导出header信息请设置DataBody中的header字段
     * 如果不导出无需设置,设置了也不用
     *
     * @return
     */
    boolean header() default true;
}
