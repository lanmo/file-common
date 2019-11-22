package org.ifaster.file.enums;

import lombok.Getter;

/**
 * @author yangnan
 * 文件后缀
 */
public enum SuffixEnum {
    DEFAULT(""),
    TXT(".txt"),
    CSV(".csv"),
    EXCEL(".xlsx"),
    SQL(".sql")
    ;

    SuffixEnum(String suffix) {
        this.suffix = suffix;
    }
    @Getter
    private String suffix;

    /**
     * 获取后缀
     *
     * @param suffix
     * @return
     */
    public static SuffixEnum getSuffix(String suffix) {
        for (SuffixEnum suffixEnum : values()) {
            if (suffixEnum.getSuffix().equals(suffix)) {
                return suffixEnum;
            }
        }
        return DEFAULT;
    }
}
