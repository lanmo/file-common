package org.ifaster.file.reflect;

import lombok.Builder;
import lombok.Data;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

/**
 * @author yangnan
 */
@Data
@Builder
public class Content {
    private String content;
    private Row row;
    private Cell cell;
}
