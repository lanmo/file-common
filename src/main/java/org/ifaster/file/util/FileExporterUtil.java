package org.ifaster.file.util;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.ifaster.file.annotation.Column;
import org.ifaster.file.annotation.Exporter;
import org.ifaster.file.annotation.Exporters;
import org.ifaster.file.reflect.Content;
import org.ifaster.file.reflect.FieldInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

import static org.ifaster.file.constant.FileConstant.WINDOW_SIZE;

/**
 * @author yangnan
 * 文件导出处理
 */
public class FileExporterUtil {

    private static Logger LOGGER = LoggerFactory.getLogger(FileExporterUtil.class);

    /**
     * 导出文件
     * @param values 实体类 必须要注解
     * {@link Exporter}
     * {@link Exporters}
     *
     * @param fileFullName 文件全路径名,不要带后缀,后缀会自动添加根据Document配置的
     * @param values
     */
    public static <T> void export(List<T> values, String fileFullName) throws Exception {
        Assert.notEmpty(values, "Collection must have elements");
        Assert.hasLength(fileFullName, "fileFullName must be not null");

        fileFullName = StringUtils.convertFileName(fileFullName);
        Class<?> tClass = values.get(0).getClass();
        Exporters exporters = tClass.getAnnotation(Exporters.class);
        Exporter doc = tClass.getAnnotation(Exporter.class);
        if (exporters == null && doc == null) {
            LOGGER.warn("Exporters or Exporter Annotation is null");
            return;
        }
        List<Field> fields = ClassUtil.getAnnotationField(tClass, Column.class);
        Assert.notEmpty(fields, "fields must have Column Annotation");
        List<FieldInfo> fieldInfoList = fields.stream().map(FieldInfo::new).collect(Collectors.toList());
        //升序排序
        fieldInfoList.sort(Comparator.comparingInt(FieldInfo::getIndex));
        if (doc != null) {
            export(values, fieldInfoList, fileFullName, doc);
        }
        if (exporters != null) {
            export(values, fieldInfoList, fileFullName, exporters);
        }
        fields.clear();
        fieldInfoList.clear();
        fields = null;
        fieldInfoList = null;
    }

    /**
     * 导出
     * @param values 数据集合
     * @param fileFullName 输出流
     * @param doc
     * @param <T>
     */
    private static <T> void export(List<T> values, List<FieldInfo> fields, String fileFullName, Exporter doc) throws Exception {
        switch (doc.suffix()) {
            case DEFAULT:
            case TXT:
            case CSV:
                exportDefault(values, fields, fileFullName + doc.suffix().getSuffix(), doc);
                break;
            case EXCEL:
                exportExcel(values, fields, fileFullName + doc.suffix().getSuffix(), doc);
                break;
            default:
                throw new IllegalArgumentException("不支持类型" + String.valueOf(doc.suffix()));
        }
    }

    /**
     * excel 写入
     * @param <T>
     * @param ts
     * @param fields
     * @param fileFullName
     * @param doc
     */
    private static <T> void exportExcel(List<T> ts, List<FieldInfo> fields, String fileFullName, Exporter doc)
            throws Exception {
        int size = doc.rowAccessWindowSize();
        if (size <= 0) {
            size = WINDOW_SIZE;
        }
        SXSSFWorkbook wb = new SXSSFWorkbook(size);
        BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(fileFullName));
        try {
            int rowNumber = 0;
            //创建表头
            Sheet sheet = wb.createSheet("sheet1");
            //创建行
            Row row = sheet.createRow(rowNumber++);
            List<String> headers = getHeader(fields);
            for (int i=0; i<headers.size(); i++) {
                Cell cell = row.createCell(i);
                cell.setCellValue(headers.get(i));
            }

            for (T t : ts) {
                Row r = sheet.createRow(rowNumber++);
                List<String> contents = getContent(t, doc, fields);
                for (int i=0; i<contents.size(); i++) {
                    Cell cell = r.createCell(i);
                    cell.setCellValue(contents.get(i));
                }
            }
            wb.write(outputStream);
            outputStream.flush();
        } finally {
            if (outputStream != null) {
                outputStream.flush();
                outputStream.close();
            }
            wb.close();
            wb.dispose();
        }
    }

    /**
     * 导出默认的文件类型
     * @param ts
     * @param fields
     * @param fileFullName
     * @param doc
     * @param <T>
     */
    private static <T> void exportDefault(List<T> ts, List<FieldInfo> fields, String fileFullName, Exporter doc)
            throws Exception {
        PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileFullName, false), doc.charSet())));
        try {
            if (doc.header()) {
                List<String> headers = getHeader(fields);
                String header = Joiner.join(headers, doc.column());
                pw.println(header);
            }
            for (T t : ts) {
                List<String> contents = getContent(t, doc, fields);
                String content = Joiner.join(contents, doc.column());
                pw.println(content);
            }
        } finally {
            pw.flush();
            pw.close();
        }
    }

    /**
     * 获取文件里面的内容
     * @param t
     * @param fields
     * @param <T>
     * @param doc
     * @returnø
     */
    private static <T> List<String> getContent(T t, Exporter doc, List<FieldInfo> fields) throws Exception {
        List<String> contents = new ArrayList<String>(fields.size());
        for (FieldInfo f : fields) {
            try {
                contents.add(f.getValue(t));
            } catch (Exception e) {
                f.getListener().exportFail(t, f, doc, null, e);
            }
        }
        return contents;
    }

    /**
     * 获取header信息
     * @param fields
     * @return
     */
    private static List<String> getHeader(List<FieldInfo> fields) {
        List<String> headers = new ArrayList<String>(fields.size());
        for (FieldInfo f : fields) {
            headers.add(f.getHeader());
        }
        return headers;
    }

    /**
     * 导出
     * @param values 数据集合
     * @param fileFullName 输出流
     * @param docs
     * @param <T>
     */
    private static <T> void export(List<T> values, List<FieldInfo> fields, String fileFullName, Exporters docs) throws Exception {
        Exporter[] exporters = docs.value();
        for (Exporter doc : exporters) {
            export(values, fields, fileFullName, doc);
        }
    }
}
