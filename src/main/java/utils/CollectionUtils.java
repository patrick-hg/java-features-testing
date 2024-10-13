package utils;

import java.util.Collection;

public class CollectionUtils {

    public static String makePrintableString(Collection<?> collection) {
        if (collection == null || collection.isEmpty()) {
            return "[]";
        }
        StringBuilder sb = new StringBuilder();
        collection.forEach(o -> sb.append(",").append(o.toString()));
        sb.replace(0, 1, "[");
        sb.append("]");
        return sb.toString();
    }
}
