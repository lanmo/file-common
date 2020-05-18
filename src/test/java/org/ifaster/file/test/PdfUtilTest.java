package org.ifaster.file.test;

import org.ifaster.file.util.PdfUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class PdfUtilTest {

    private static String html = "/Users/yangnan/Downloads/order.html";
    private static String pdf = "/Users/yangnan/Downloads/order.pdf";

    public static void main(String[] args) {
        try {
            File file = new File(html);
            BufferedReader reader = new BufferedReader(new FileReader(file));
            StringBuilder content = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
            reader.close();
            PdfUtil.html2PdfFromString(content.toString(), "/u01/font/simsun.ttc", pdf);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
