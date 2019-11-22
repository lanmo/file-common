package org.ifaster.file.util;

import java.util.List;
import java.util.ListIterator;

/**
 * @author yangnan
 * 接合器
 */
public class Joiner {

    /**
     * 将集合中的数据拼接成字符串
     *
     * @param list
     * @param separator
     * @return
     */
    public static String join(List<String> list, String separator) {
        ListIterator<String> it = list.listIterator();
        StringBuilder builder = new StringBuilder();
        if (it.hasNext()) {
            builder.append(it.next());
            while (it.hasNext()) {
                builder.append(separator);
                builder.append(it.next());
            }
        }
        return builder.toString();
    }
}
