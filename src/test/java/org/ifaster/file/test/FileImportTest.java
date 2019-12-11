package org.ifaster.file.test;

import org.ifaster.file.util.FileImporterUtil;

import java.util.List;

public class FileImportTest {
    private static String path = "/Users/yangnan/Downloads";
    private static String name = "test";

    public static void main(String[] args) throws Exception {
        List<Person> personList = FileImporterUtil.importFile(path+"\\"+name, Person.class);
        System.out.println(personList.size());
        System.out.println(personList);
    }
}
