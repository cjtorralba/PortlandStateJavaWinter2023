package edu.pdx.cs410J.torral2;

import java.io.PrintWriter;
import java.io.Writer;
import java.util.Map;

public class OldTextDumper {
  private final Writer writer;

  public OldTextDumper(Writer writer) {
    this.writer = writer;
  }

  public void dump(Map<String, String> dictionary) {
    try (
      PrintWriter pw = new PrintWriter(this.writer)
    ){
      for (Map.Entry<String, String> entry : dictionary.entrySet()) {
        pw.println(entry.getKey() + " : " + entry.getValue());
      }

      pw.flush();
    }
  }
}