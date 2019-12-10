package org.ifaster.file.test;

import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;
import org.ifaster.file.annotation.Entity;
import org.ifaster.file.annotation.Exporter;
import org.ifaster.file.annotation.Exporters;
import org.ifaster.file.enums.SuffixEnum;

@Data
@Exporters(docs = {
        @Exporter(column = "\\t"),
        @Exporter(column = ",", suffix = SuffixEnum.CSV),
        @Exporter(column = ",", suffix = SuffixEnum.EXCEL, rowAccessWindowSize = 1),
})
public class Person {
    @Entity(header = "姓名")
    private String name;
    @Entity(header = "手机号")
    private String phone;
    @Entity(header = "创建时间", format = "yyyy-MM-dd HH:mm:ss")
    private Date createDate;
    @Entity(header = "支付金额", format = "0.00")
    private double pay;
    @Entity(header = "剩余金额", format = "0.00", index = -1)
    private BigDecimal payAmount;
}