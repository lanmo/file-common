package org.ifaster.file.reflect;

import org.ifaster.file.util.StringUtils;

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

    @Override
    public Object parse(String value, Class<?> type) throws Exception {
        if (StringUtils.isBlank(value)) {
            return null;
        }
        if (type.equals(char.class) || type.equals(Character.class)) {
            return value.charAt(0);
        }
        if (type.equals(boolean.class) || type.equals(Boolean.class)) {
            return Boolean.parseBoolean(value);
        }
        return value;
    }
}
