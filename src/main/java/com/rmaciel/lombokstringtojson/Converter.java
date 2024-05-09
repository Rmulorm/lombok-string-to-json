package com.rmaciel.lombokstringtojson;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Converter {

  private static String CLASS_NAME_REGEX = "([A-Z][a-z0-9]+)+\\(";

  private static String NAME_VALUE_REGEX = "([\\s\\{]*)(\\w+)=([^,\\[\\{\\}]*)([,\\[\\]\\{\\}]*)";

  //  ([\s\{]*)(\w+)=([^,\[\}\{]*)([,\[\]\{\}]*)

  private Converter() {}

  public static String convert(String input) {
    String inputWithoutClassNames = input.replaceAll(CLASS_NAME_REGEX, "{").replace(")", "}");
    Pattern pattern = Pattern.compile(NAME_VALUE_REGEX);
    Matcher matcher = pattern.matcher(inputWithoutClassNames);

    StringBuilder convertedStringBuilder = new StringBuilder();
    while (matcher.find()) {
      boolean isValueArray = matcher.group(3).isEmpty();
      convertedStringBuilder
          .append(matcher.group(1).contains("(") ? "{" : matcher.group(1))
          .append("\"")
          .append(matcher.group(2))
          .append("\":")
          .append(isValueArray ? "" : "\"")
          .append(matcher.group(3).replace("\"", "\\\""))
          .append(isValueArray ? "" : "\"")
          .append(matcher.group(4));
    }

    return convertedStringBuilder.toString();
  }
}
