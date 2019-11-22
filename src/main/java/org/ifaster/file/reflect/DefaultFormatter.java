package org.ifaster.file.reflect;

/**
 * 默认格式化器
 *
 * @author yangnan
 */
public class DefaultFormatter implements Formatter {
    @Override
    public String format(Object value) {
        return value == null ? "" : value.toString();
    }
}
