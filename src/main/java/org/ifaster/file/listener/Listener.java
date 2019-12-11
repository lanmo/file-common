package org.ifaster.file.listener;

import org.ifaster.file.annotation.Exporter;
import org.ifaster.file.annotation.Importer;
import org.ifaster.file.reflect.Content;
import org.ifaster.file.reflect.FieldInfo;

/**
 * 文件导入监听器
 *
 * @author yangnan
 */
public interface Listener<T> {

    /**
     * 导入失败事件
     *
     * @param e 异常栈
     * @param fieldInfo 对象
     * @param t
     * @param content
     * @param doc
     * @return
     * @throws Exception 异常
     */
    void importFail(T t, FieldInfo fieldInfo, Importer doc, Content content, Exception e) throws Exception;

    /**
     * 导入失败事件
     *
     * @param e 异常栈
     * @param fieldInfo 对象
     * @param t
     * @param content
     * @param doc
     * @return
     * @throws Exception 异常
     */
    void exportFail(T t, FieldInfo fieldInfo, Exporter doc, Content content, Exception e) throws Exception;
}
