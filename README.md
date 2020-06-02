# 文件相关处理

- excel文件导出

- csv文件导出

- txt文件导出

- 默认格式导出

- pdf导出,支持url以及html文件

    ```
    <dependency>  
        <groupId>org.ifaster.file</groupId>  
        <artifactId>file-common</artifactId>  
        <version>${file-common.version}</version>  
    </dependency>
    ```
    
    demo
    ```
    
    public class FileExportTest {
    
    private static String path = "/Users/xxxxx/Downloads";
    private static String name = "test";
    
    public static void main(String[] args) throws Exception {
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
    
    @Data
    @Exporter(suffix = SuffixEnum.TXT)
    @Exporter(suffix = SuffixEnum.CSV)
    @Exporter(suffix = SuffixEnum.EXCEL, rowAccessWindowSize = 1000)
    public static class Person {
        @Column(header = "姓名")
        private String name;
        @Column(header = "手机号")
        private String phone;
        @Column(header = "创建时间", format = "yyyy-MM-dd HH:mm:ss")
        private Date createDate;
        @Column(header = "支付金额", format = "0.00")
        private double pay;
        @Column(header = "剩余金额", format = "0.00", index = -1)
        private BigDecimal payAmount;
    }
    }
    ```
    pdf生成
    ```
        public class PdfUtilTest {
         
            private static String html = "/Users/xxxxx/Downloads/order.html";
            private static String pdf = "/Users/xxxxx/Downloads/order.pdf";
         
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
    ```
    
