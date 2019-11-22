package org.ifaster.file.listener;

import java.util.List;

/**
 * 文件导入监听器
 *
 * @author yangnan
 */
public interface ImporterListener<T> {
    /**
     * 返回导入失败的集合
     *
     * @return
     */
    List<T> onFail();

    /**
     * 返回导入成功的集合
     *
     * @return
     */
    List<T> onSuccess();
}
