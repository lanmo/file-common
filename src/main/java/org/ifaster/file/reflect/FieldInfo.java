package org.ifaster.file.reflect;

import lombok.Getter;
import org.ifaster.file.annotation.Column;
import org.ifaster.file.util.StringUtils;

import java.lang.reflect.Field;
import java.util.Date;

/**
 * @author yangnan
 */
public class FieldInfo {
    @Getter
    private final Field field;
    @Getter
    private Formatter formatter;
    @Getter
    private final String header;
    @Getter
    private final int index;
    @Getter
    private int[] composeIndex;
    private final Column column;
    @Getter
    private String composeConnector;

    public FieldInfo(Field field) {
        this.field = field;
        this.field.setAccessible(true);
        this.column = field.getAnnotation(Column.class);
        this.header = column.header();
        this.index = column.index();
        this.composeIndex = column.composeIndex();
        this.composeConnector = column.composeConnector();
        initFormatter();
    }

    private void initFormatter() {
        Class<?> fieldType = field.getType();
        if (fieldType == String.class) {
            formatter = new DefaultFormatter();
        } else if (!StringUtils.hasLength(column.format())) {
            formatter = new DefaultFormatter();
        } else if (fieldType == Date.class) {
            formatter = new DateFormatter(column.format());
        } else if (fieldType == double.class) {
            formatter = new NumberFormatter(column.format());
        } else if (fieldType == float.class) {
            formatter = new NumberFormatter(column.format());
        } else if (Number.class.isAssignableFrom(fieldType)) {
            formatter = new NumberFormatter(column.format());
        } else {
            formatter = new DefaultFormatter();
        }
    }

    /**
     * 获取值
     *
     * @param obj
     * @return
     * @throws IllegalAccessException
     */
    public String getValue(Object obj) throws IllegalAccessException {
        Object value = field.get(obj);
        return formatter.format(value);
    }

    /**
     * 设置值
     *
     * @param obj
     */
    public void setValue(Object obj, String value) throws Exception {
        Object v = formatter.parse(value, field.getType());
        field.set(obj, v);
    }
}
