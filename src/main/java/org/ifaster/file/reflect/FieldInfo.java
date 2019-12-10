package org.ifaster.file.reflect;

import lombok.Getter;
import org.ifaster.file.annotation.Entity;
import org.ifaster.file.util.StringUtils;

import java.lang.reflect.Field;
import java.util.Date;

/**
 * @author yangnan
 */
public class FieldInfo {
    @Getter
    private final Field field;
    private Formatter formatter;
    @Getter
    private final String header;
    @Getter
    private final int index;

    public FieldInfo(Field field) {
        this.field = field;
        this.field.setAccessible(true);
        Entity entity = field.getAnnotation(Entity.class);
        this.header = entity.header();
        this.index = entity.index();
        initFormatter(entity);
    }

    private void initFormatter(Entity entity) {
        Class<?> fieldType = field.getType();
        if (fieldType == String.class) {
            formatter = new DefaultFormatter();
        } else if (!StringUtils.hasLength(entity.format())) {
            formatter = new DefaultFormatter();
        } else if (fieldType == Date.class) {
            formatter = new DateFormatter(entity.format());
        } else if (fieldType == double.class) {
            formatter = new NumberFormatter(entity.format());
        } else if (fieldType == float.class) {
            formatter = new NumberFormatter(entity.format());
        } else if (Number.class.isAssignableFrom(fieldType)) {
            formatter = new NumberFormatter(entity.format());
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
}
