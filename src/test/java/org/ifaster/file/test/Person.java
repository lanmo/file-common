package org.ifaster.file.test;

import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;
import org.ifaster.file.annotation.ExporterBody;
import org.ifaster.file.annotation.Exporter;
import org.ifaster.file.annotation.Exporters;
import org.ifaster.file.enums.SeparatorEnum;
import org.ifaster.file.enums.SuffixEnum;

@Data
@Exporters(docs = {
        @Exporter(column = SeparatorEnum.TAB),
        @Exporter(column = SeparatorEnum.COMMA, suffix = SuffixEnum.CSV),
        @Exporter(column = SeparatorEnum.COMMA, suffix = SuffixEnum.EXCEL, rowAccessWindowSize = 1),
})
public class Person {
    @ExporterBody(header = "姓名")
    private String name;
    @ExporterBody(header = "手机号")
    private String phone;
    @ExporterBody(header = "创建时间", format = "yyyy-MM-dd HH:mm:ss")
    private Date createDate;
    @ExporterBody(header = "支付金额", format = "0.00")
    private double pay;
    @ExporterBody(header = "剩余金额", format = "0.00", order = -1)
    private BigDecimal payAmount;
}