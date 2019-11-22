package org.ifaster.file.reflect;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * @author yangnan
 */
public class DateFormatter implements Formatter {

    private final DateFormat dateFormat;

    public DateFormatter(String format) {
        this.dateFormat = new SimpleDateFormat(format);
    }

    @Override
    public String format(Object value) {
        if (value == null) {
            return "";
        }
        return dateFormat.format(value);
    }
}
