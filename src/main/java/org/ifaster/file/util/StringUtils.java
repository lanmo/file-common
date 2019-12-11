package org.ifaster.file.util;

import static org.ifaster.file.constant.FileConstant.BACKSLASH;
import static org.ifaster.file.constant.FileConstant.BACKSLASH_CH;
import static org.ifaster.file.constant.FileConstant.DOUBLE_SEPARATOR;
import static org.ifaster.file.constant.FileConstant.SINGLE_SEPARATOR;

/**
 * @author yangnan
 * 字符串操作
 */
public class StringUtils {

    /**
     * Check that the given {@code String} is neither {@code null} nor of length 0.
     * <p>Note: this method returns {@code true} for a {@code String} that
     * purely consists of whitespace.
     * @param str the {@code String} to check (may be {@code null})
     * @return {@code true} if the {@code String} is not {@code null} and has length
     * @see #hasLength(String)
     */
    public static boolean hasLength(String str) {
        return (str != null && !str.isEmpty());
    }

    /**
     * 获取文件名
     * @param fileFullName 文件路径全路径名
     * @return
     */
    public static String convertFileName(String fileFullName) {
        String path = fileFullName;
        //将 \ 转换为 /
        if (path.contains(SINGLE_SEPARATOR)) {
            path = path.replace(SINGLE_SEPARATOR, BACKSLASH);
        }
        //将 \\ 转换为 /
        if (path.contains(DOUBLE_SEPARATOR)) {
            path = path.replace(DOUBLE_SEPARATOR, BACKSLASH);
        }
        //最后一位如果是 / 则去掉
        if (path.charAt(path.length() - 1) == BACKSLASH_CH) {
            path = path.substring(0, path.length() - 1);
        }
        return path;
    }

    /**
     * 获取文件名，去除文件路径
     *
     * @param fileFullName
     * @return
     */
    public static String getFileName(String fileFullName) {
        String fileName = fileFullName;
        int index = fileFullName.lastIndexOf("/");
        if (index >= 0) {
            fileName = fileFullName.substring(fileFullName.lastIndexOf("/"));
        }
        return fileName;
    }

    /**
     * 获取文件名, 去除后缀
     *
     * @param fileFullName
     * @return
     */
    public static String getFileNameSuffix(String fileFullName) {
        String fileName = fileFullName;
        int index = fileName.lastIndexOf(".");
        if (index >= 0) {
            fileName = fileName.substring(0, index);
        }
        return fileName;
    }

    /**
     * 判断字符串是否为空
     * @param value
     * @return
     */
    public static boolean isBlank(String value) {
        return value == null ? true : value.isEmpty();
    }
}
