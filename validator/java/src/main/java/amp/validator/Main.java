package amp.validator;

import dev.amp.validator.ExitCondition;
import dev.amp.validator.ValidatorProtos;
import dev.amp.validator.parser.AMPHtmlParser;
import dev.amp.validator.parser.ParserException;

public class Main {
  public static void main(String[] args) {
    final AMPHtmlParser ampHtmlParser;
    try {
      ampHtmlParser = new AMPHtmlParser();
      final ValidatorProtos.ValidationResult validationResult = ampHtmlParser.parse("<!--\n" +
        "  CSS_SYNTAX_DISALLOWED_PROPERTY_VALUE\n" +
        "-->\n" +
        "<!doctype html>\n" +
        "<html amp4email>\n" +
        "<head>\n" +
        "    <meta charset=\"utf-8\">\n" +
        "    <style amp4email-boilerplate>body{visibility:hidden}</style>\n" +
        "    <script async src=\"https://cdn.ampproject.org/v0.js\"></script>\n" +
        "</head>\n" +
        "<body>\n" +
        "<h1 style=\"position:blue;text-align:center;\">This is a heading</h1>\n" +
        "<p style=\"color:red;\">This is a paragraph.</p>\n" +
        "\n" +
        "Test body.\n" +
        "</body>\n" +
        "</html>", ValidatorProtos.HtmlFormat.Code.AMP4EMAIL, ExitCondition.EXIT_ON_FIRST_ERROR, 50);
      System.out.println(validationResult.getErrorsCount());
    } catch (ParserException e) {
      e.printStackTrace();
    }
  }
}
