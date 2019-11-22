package org.ifaster.file.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author yangnan
 * 文件导入处理
 */
public class FileImporterUtil {

    private static Logger LOGGER = LoggerFactory.getLogger(FileImporterUtil.class);

    /**
     * 解析文件中的数据
     *
     * @param fileFullName 文件全路径名
     * @param tClass       类型
     * @return 集合列表
     */
    public static <T> List<T> importFile(String fileFullName, Class<T> tClass) {
        Assert.hasLength(fileFullName, "fileFullName must not be null");
        String fileName = StringUtils.convertFileName(fileFullName);
        String suffix = StringUtils.getFileName(fileName);



        return null;
    }
}
