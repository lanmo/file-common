package org.ifaster.file.util;

import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbookFactory;
import org.ifaster.file.annotation.Column;
import org.ifaster.file.annotation.Importer;
import org.ifaster.file.reflect.Content;
import org.ifaster.file.reflect.FieldInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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
    public static <T> List<T> importFile(String fileFullName, Class<T> tClass) throws Exception {
        Assert.hasLength(fileFullName, "fileFullName must not be null");
        Assert.notNull(tClass, "class must not be null");
        String fileName = StringUtils.convertFileName(fileFullName);
        Importer importer = tClass.getAnnotation(Importer.class);
        if (importer == null) {
            LOGGER.warn("fileFullName:[{}] class:[{}] Importer is not config", fileFullName, tClass);
            return new ArrayList<>();
        }

        List<Field> fields = ClassUtil.getAnnotationField(tClass, Column.class);
        Assert.notEmpty(fields, "fields must have Column Annotation");
        List<FieldInfo> fieldInfoList = fields.stream().map(FieldInfo::new).collect(Collectors.toList());
        //升序排序
        fieldInfoList.sort(Comparator.comparingInt(FieldInfo::getIndex));
        List<T> result = readFile(importer, fileName, fieldInfoList, tClass);
        fieldInfoList.clear();
        fields.clear();
        return result;
    }

    /**
     * 读取文件内容
     *
     * @param doc
     * @param fileFullName
     * @param fields
     * @param tClass
     */
    private static <T> List<T> readFile(Importer doc, String fileFullName, List<FieldInfo> fields, Class<T> tClass) throws Exception {
        String fileName = StringUtils.getFileNameSuffix(fileFullName);
        switch (doc.suffix()) {
            case DEFAULT:
            case TXT:
            case CSV:
                return importDefault(fields, fileName + doc.suffix().getSuffix(), doc, tClass);
            case EXCEL:
                return importExcel(fields, fileName + doc.suffix().getSuffix(), doc, tClass);
            default:
                throw new IllegalArgumentException("不支持的文档类型" + String.valueOf(doc.suffix()));
        }
    }

    /**
     * 解析excel内容
     *
     * @param fields
     * @param fileName
     * @param doc
     * @param tClass
     * @param <T>
     * @return
     */
    private static <T> List<T> importExcel(List<FieldInfo> fields, String fileName, Importer doc, Class<T> tClass) throws Exception {
        POIFSFileSystem fs = null;
        Workbook wb = null;
        List<T> result = new ArrayList<>();
        try {
            // 打开HSSFWorkbook
            wb = XSSFWorkbookFactory.create(new BufferedInputStream(new FileInputStream(fileName)));
            int index = doc.header() ? 1 : 0;
            int sheetCount = wb.getNumberOfSheets();
            for (int i=0; i<sheetCount; i++) {
                Sheet sheet = wb.getSheetAt(i);
                if (sheet == null) {
                    continue;
                }
                //处理每一页的内容
                int rowCount = sheet.getLastRowNum();
                int row = index;
                for (; row <= rowCount; row++) {
                    Row row1 = sheet.getRow(row);
                    T t = tClass.newInstance();
                    for (FieldInfo fieldInfo : fields) {
                        try {
                            fieldInfo.setValue(t, getStringValue(row1, fieldInfo));
                        } catch (Exception e) {
                            Content c = Content.builder().row(row1).build();
                            fieldInfo.getListener().importFail(t, fieldInfo, doc, c, e);
                        }
                    }
                    result.add(t);
                }
            }
        } finally {
            if (wb != null) {
                wb.close();
            }
        }

        return result;
    }

    /**
     * 获取excel 列里面的内容
     *
     * @param row
     * @return
     */
    private static String getStringValue(Row row, FieldInfo fieldInfo) {
        int[] indexes = fieldInfo.getComposeIndex();
        if (indexes == null || indexes.length <= 0) {
            return getStringValue(row.getCell(fieldInfo.getIndex()));
        }
        StringBuilder builder = new StringBuilder();
        for (int index : indexes) {
            builder.append(row.getCell(index)).append(fieldInfo.getComposeConnector());
        }
        return builder.substring(0, builder.length() - fieldInfo.getComposeConnector().length());
    }

    /**
     * 获取excel 列里面的内容
     *
     * @param cell
     * @return
     */
    private static String getStringValue(Cell cell) {
        switch (cell.getCellType()) {
            case BOOLEAN:
                return cell.getBooleanCellValue() ? "true" : "false";
            case STRING:
                return cell.getStringCellValue();
            case FORMULA:
                return cell.getCellFormula();
            case NUMERIC:
                return cell.getNumericCellValue() + "";
            default:
                return cell.getStringCellValue();

        }
    }

    /**
     * 解析文件内容
     *
     * @param fields
     * @param fileName
     * @param doc
     * @param tClass
     * @param <T>
     * @return
     */
    private static <T> List<T> importDefault(List<FieldInfo> fields, String fileName, Importer doc, Class<T> tClass) throws Exception {
        File file = new File(fileName);
        BufferedReader reader = null;
        List<T> result = new ArrayList<>();
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), doc.charSet()));
            String content = null;
            int index = doc.header() ? -1 : 0;
            while ((content = reader.readLine()) != null) {
                if (index < 0) {
                    index++;
                    continue;
                }
                String[] contents = content.split(doc.column());
                T t = tClass.newInstance();
                for (FieldInfo fieldInfo : fields) {
                    try {
                        fieldInfo.setValue(t, getStringValue(contents, fieldInfo));
                    } catch (Exception e) {
                        Content c = Content.builder().content(content).build();
                        fieldInfo.getListener().importFail(t, fieldInfo, doc, c, e);
                    }
                }
                result.add(t);
                index++;
            }
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
        return result;
    }

    /**
     * 获取value值
     *
     * @param contents
     * @param fieldInfo
     * @return
     */
    private static String getStringValue(String[] contents, FieldInfo fieldInfo) {
        int[] indexes = fieldInfo.getComposeIndex();
        if (indexes == null || indexes.length <= 0) {
            return contents[fieldInfo.getIndex()];
        }
        StringBuilder builder = new StringBuilder();
        for (int index : indexes) {
            builder.append(contents[index]).append(fieldInfo.getComposeConnector());
        }
        return builder.substring(0, builder.length() - fieldInfo.getComposeConnector().length());
    }
}
