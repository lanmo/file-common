package org.ifaster.file.listener;

/**
 * 文件导入监听器
 *
 * @author yangnan
 */
public interface ImporterListener<T> {

    /**
     * 导入失败事件
     *
     * @param e 异常栈，不一定会有
     * @param t 对象
     * @return
     */
    void onFail(T t, Throwable e);

    /**
     * 返回导入成功事件
     *
     * @return
     */
    void onSuccess(T t);
}
