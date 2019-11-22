package org.ifaster.file.reflect;

import org.ifaster.file.annotation.ImporterBody;

import java.lang.reflect.Field;

/**
 * @author yangnan
 */
public class ImporterField {
    private Field field;
    /**
     * 索引值
     * */
    private int index;
    private String header;

    public ImporterField(Field field) {
        this.field = field;
        this.field.setAccessible(true);
        ImporterBody body = field.getAnnotation(ImporterBody.class);
    }
}
