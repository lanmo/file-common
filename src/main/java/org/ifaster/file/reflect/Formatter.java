package org.ifaster.file.reflect;

/**
 * @author yangnan
 *
 * 格式化容器
 *
 */
public interface Formatter {

    /**
     * 格式化
     *
     * @param value 对象值
     * @return 返回格式化后的字符串
     */
    String format(Object value);
}
