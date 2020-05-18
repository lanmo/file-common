package org.ifaster.file.util;

import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.BaseFont;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.*;

import static org.ifaster.file.constant.FileConstant.*;

/**
 * pdf生成工具类
 *
 * @author yangnan
 */
public class PdfUtil {

    /**
     * html转成pdf文件
     *
     * @param htmlContent  html文件路径绝对路径
     * @param fontPath  字体路径 支持绝对路径以及classpath:路径
     * @param destFile  目标文件 绝对路径
     */
    public static void html2PdfFromString(String htmlContent, String fontPath, String destFile) throws IOException, DocumentException {
        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(htmlContent);
        html2Pdf(renderer, fontPath, destFile);
    }

    /**
     * html uri转成pdf文件
     *
     * @param uri       uri
     * @param fontPath  字体路径 支持绝对路径以及classpath:路径
     * @param destFile  目标文件 绝对路径
     */
    public static void html2PdfFromUri(String uri, String fontPath, String destFile) throws IOException, DocumentException {
        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocument(uri);
        html2Pdf(renderer, fontPath, destFile);
    }

    /**
     * html uri转成pdf文件
     *
     * @param htmlFile  htmlFile文件路径
     * @param fontPath  字体路径 支持绝对路径以及classpath:路径
     * @param destFile  目标文件 绝对路径
     */
    public static void html2PdfFromFile(File htmlFile, String fontPath, String destFile) throws IOException, DocumentException {
        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocument(htmlFile);
        html2Pdf(renderer, fontPath, destFile);
    }


    /**
     * html转成pdf文件
     *
     * @param renderer  html文件路径绝对路径
     * @param fontPath  字体路径 支持绝对路径以及classpath:路径
     * @param destFile  目标文件 绝对路径
     */
    public static void html2Pdf(ITextRenderer renderer, String fontPath, String destFile) throws IOException, DocumentException {
        ITextFontResolver fontResolver = renderer.getFontResolver();
        // 解决中文支持问题
        fontResolver.addFont(getFontPath(StringUtils.convertFileName(fontPath)), BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
        html2Pdf(renderer, destFile);
    }

    /**
     * html转成pdf文件
     *
     * @param renderer  html文件路径绝对路径
     * @param destFile  目标文件 绝对路径
     */
    public static void html2Pdf(ITextRenderer renderer, String destFile) throws IOException, DocumentException {
        OutputStream os = null;
        try {
            os = new BufferedOutputStream(new FileOutputStream(destFile));
            html2Pdf(renderer, os);
        } finally {
            if (os != null) {
                os.close();
            }
        }
    }


    /**
     * html转成pdf文件
     *
     * @param htmlContent  html文件路径绝对路径
     * @param fontPath  字体路径 支持绝对路径以及classpath:路径
     * @param outputStream  输出流 流关闭由调用者关闭
     */
    public static void html2PdfFromString(String htmlContent, String fontPath, OutputStream outputStream) throws IOException, DocumentException {
        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(htmlContent);
        html2Pdf(renderer, fontPath, outputStream);
    }

    /**
     * html uri转成pdf文件
     *
     * @param uri       uri
     * @param fontPath  字体路径 支持绝对路径以及classpath:路径
     * @param outputStream  输出流 流关闭由调用者关闭
     */
    public static void html2PdfFromUri(String uri, String fontPath, OutputStream outputStream) throws IOException, DocumentException {
        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocument(uri);
        html2Pdf(renderer, fontPath, outputStream);
    }

    /**
     * html uri转成pdf文件
     *
     * @param htmlFile  htmlFile文件路径
     * @param fontPath  字体路径 支持绝对路径以及classpath:路径
     * @param outputStream  输出流 流关闭由调用者关闭
     */
    public static void html2PdfFromFile(File htmlFile, String fontPath,OutputStream outputStream) throws IOException, DocumentException {
        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocument(htmlFile);
        html2Pdf(renderer, fontPath, outputStream);
    }


    /**
     * html转成pdf文件
     *
     * @param renderer  html文件路径绝对路径
     * @param fontPath  字体路径 支持绝对路径以及classpath:路径
     * @param outputStream  输出流 流关闭由调用者关闭
     */
    public static void html2Pdf(ITextRenderer renderer, String fontPath, OutputStream outputStream) throws IOException, DocumentException {
        ITextFontResolver fontResolver = renderer.getFontResolver();
        // 解决中文支持问题
        fontResolver.addFont(getFontPath(StringUtils.convertFileName(fontPath)), BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
        html2Pdf(renderer, outputStream);
    }

    /**
     * html转成pdf文件
     *
     * @param renderer  html文件路径绝对路径
     * @param outputStream  输出流 流关闭由调用者关闭
     */
    public static void html2Pdf(ITextRenderer renderer, OutputStream outputStream) throws IOException, DocumentException {
        renderer.layout();
        renderer.createPDF(outputStream);
        outputStream.flush();
    }

    /**
     * 判断文件是否存在
     *
     * @param fontPath
     */
    private static String getFontPath(String fontPath) throws IOException {
        //如果不是classpath 直接退出
        if (!fontPath.startsWith(CLASSPATH_PATH)) {
            return fontPath;
        }
        String path = fontPath.substring(CLASSPATH_PATH.length());
        File fontFile = new File(DEFAULT_FONT_PATH, path);
        if (fontFile.exists()) {
            return fontFile.getPath();
        }
        if (!fontFile.getParentFile().exists()) {
            fontFile.getParentFile().mkdirs();
        }
        InputStream inputStream = PdfUtil.class.getClassLoader().getResourceAsStream(path.startsWith(BACKSLASH) ? path.substring(BACKSLASH.length()) : path);
        if (inputStream == null) {
            throw new IOException(fontPath + " can't found");
        }
        BufferedOutputStream writer = null;
        BufferedInputStream reader = null;
        try {
            writer = new BufferedOutputStream(new FileOutputStream(fontFile));
            reader = new BufferedInputStream(inputStream);
            byte[] bytes = new byte[1024 * 8];
            int count;
            while ((count = reader.read(bytes)) != -1) {
                writer.write(bytes, 0, count);
            }
            writer.flush();
        } finally {
            if (writer != null) {
                writer.close();
            }
            if (reader != null) {
                reader.close();
            }
        }
        return fontFile.getPath();
    }
}
