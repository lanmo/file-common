package org.ifaster.file.test;

import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;
import org.ifaster.file.annotation.Column;
import org.ifaster.file.annotation.Exporter;
import org.ifaster.file.annotation.Exporters;
import org.ifaster.file.annotation.Importer;
import org.ifaster.file.enums.SuffixEnum;

@Data
@Exporters(docs = {
        @Exporter(column = " "),
        @Exporter(column = ",", suffix = SuffixEnum.CSV),
        @Exporter(column = ",", suffix = SuffixEnum.EXCEL, rowAccessWindowSize = 1),
})
@Importer(suffix = SuffixEnum.EXCEL, column = " ")
public class Person {
    @Column(header = "姓名", index = 0)
    private String name;
    @Column(header = "手机号", index = 1)
    private String phone;
    @Column(header = "创建时间(天)", format = "yyyy-MM-dd", index = 2)
    private Date day;
    @Column(header = "创建时间(时)", format = "HH:mm:ss", index = 3)
    private Date time;
    @Column(header = "支付金额", format = "0.00", index = 4)
    private double pay;
    @Column(header = "剩余金额", format = "0.00", index = 5)
    private BigDecimal payAmount;
    @Column(header = "创建时间", format = "yyyy-MM-dd HH:mm:ss", composeIndex = {2, 3}, composeConnector = " ")
    private Date createDate;
}