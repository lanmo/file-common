package org.ifaster.file.reflect;

import org.ifaster.file.util.StringUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * 数字相关格式化
 *
 * @author yangnan
 */
public class NumberFormatter implements Formatter {

    private final double DEFAULT_VALUE = 0.000000000D;

    private final DecimalFormat format;

    public NumberFormatter(String pattern) {
        this.format = new DecimalFormat(pattern);
    }

    @Override
    public String format(Object value) {
        if (value == null) {
            return format.format(DEFAULT_VALUE);
        }
        return format.format(value);
    }

    @Override
    public Object parse(String value, Class<?> type) throws Exception {
        if (StringUtils.isBlank(value)) {
            return null;
        }
        if (type.equals(float.class) || type.equals(Float.class)) {
            return Float.parseFloat(value);
        }
        if (type.equals(double.class) || type.equals(Double.class)) {
            return Double.parseDouble(value);
        }
        if (type.equals(int.class) || type.equals(Integer.class)) {
            return Integer.parseInt(value);
        }
        if (type.equals(long.class) || type.equals(Long.class)) {
            return Long.parseLong(value);
        }
        if (type.equals(short.class) || type.equals(Short.class)) {
            return Short.parseShort(value);
        }
        if (type.equals(BigDecimal.class)) {
            return new BigDecimal(value);
        }
        if (type.equals(byte.class) || type.equals(Byte.class)) {
            return Byte.parseByte(value);
        }
        return null;
    }
}
