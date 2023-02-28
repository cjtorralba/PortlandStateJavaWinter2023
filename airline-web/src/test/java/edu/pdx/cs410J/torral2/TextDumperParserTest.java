package edu.pdx.cs410J.torral2;

import edu.pdx.cs410J.ParserException;
import org.junit.jupiter.api.Test;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.Collections;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class TextDumperParserTest {

  @Test
  void emptyMapCanBeDumpedAndParsed() throws ParserException {
    Map<String, String> map = Collections.emptyMap();
    Map<String, String> read = dumpAndParse(map);
    assertThat(read, equalTo(map));
  }

  private Map<String, String> dumpAndParse(Map<String, String> map) throws ParserException {
    StringWriter sw = new StringWriter();
    OldTextDumper dumper = new OldTextDumper(sw);
    dumper.dump(map);

    String text = sw.toString();

    OldTextParser parser = new OldTextParser(new StringReader(text));
    return parser.parse();
  }

  @Test
  void dumpedTextCanBeParsed() throws ParserException {
    Map<String, String> map = Map.of("one", "1", "two", "2");
    Map<String, String> read = dumpAndParse(map);
    assertThat(read, equalTo(map));
  }
}
