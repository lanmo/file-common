package org.ifaster.file.listener;

import org.ifaster.file.annotation.Exporter;
import org.ifaster.file.annotation.Importer;
import org.ifaster.file.reflect.Content;
import org.ifaster.file.reflect.FieldInfo;

/**
 * 直接抛出异常
 *
 * @author yangnan
 */
public class NoOpListener implements Listener<Object> {

    @Override
    public void importFail(Object o, FieldInfo fieldInfo, Importer doc, Content content, Exception e) throws Exception {
        throw e;
    }

    @Override
    public void exportFail(Object o, FieldInfo fieldInfo, Exporter doc, Content content, Exception e) throws Exception {
        throw e;
    }
}
