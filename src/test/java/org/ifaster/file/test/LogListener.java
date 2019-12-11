package org.ifaster.file.test;

import org.ifaster.file.annotation.Exporter;
import org.ifaster.file.annotation.Importer;
import org.ifaster.file.listener.Listener;
import org.ifaster.file.reflect.Content;
import org.ifaster.file.reflect.FieldInfo;

public class LogListener implements Listener<Object> {
    @Override
    public void importFail(Object o, FieldInfo fieldInfo, Importer doc, Content content, Exception e) throws Exception {
        System.out.println("importFail=" +o + ",fieldInfo=" + fieldInfo);
    }

    @Override
    public void exportFail(Object o, FieldInfo fieldInfo, Exporter doc, Content content, Exception e) throws Exception {
        System.out.println("exportFail="+o + ",fieldInfo=" + fieldInfo);
    }
}
