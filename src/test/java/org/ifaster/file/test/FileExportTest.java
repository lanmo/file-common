package org.ifaster.file.test;

import org.ifaster.file.util.FileExporterUtil;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FileExportTest {

    private static String path = "/Users/yangnan/Downloads";
    private static String name = "test";

    public static void main(String[] args) throws IOException {
        List<Person> personList = new ArrayList<>();
        for (int i=0; i<10; i++) {
            Person person = new Person();
            person.setCreateDate(new Date());
            person.setName("测试" + i);
            person.setPay(1.009);
            person.setPayAmount(new BigDecimal(1.0098));
            person.setPhone("123456789");
            personList.add(person);
        }
        FileExporterUtil.export(personList, path+"\\"+name);
    }
}
