package org.ifaster.file.reflect;

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
}
