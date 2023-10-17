package utils.jobject2json;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JObject2JsonService {

    public String jObject2Json(String in) {

        String out = in;
        out = out.replaceAll("[a-zA-Z]+\\(", "{");
        out = out.replaceAll("\\)", "}");


        final Pattern FIELDS_PATTERN = Pattern.compile("([a-zA-Z]+)=");

        Matcher fieldMatcher = FIELDS_PATTERN.matcher(out);
        while (fieldMatcher.find()) {
            String fieldName = fieldMatcher.group(1);
            out = out.replace(fieldName + "=", String.format("'%s':", fieldName));
        }

//        out = out.replaceAll("[a-zA-Z]+\\=", ":")

        return out;

//        String splitRegex = "[ ( { | } ]";
//        String[] terms = out.su(splitRegex);
//        return String.join("", terms);
    }
}
