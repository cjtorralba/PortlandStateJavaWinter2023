package edu.pdx.cs410J.torral2;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 *  This class is used to test units of the Project2 class, such as things like getting information from
 *  certain flights after they have been created.
 *
 * @author Christian Torralba
 * @version 3.0
 * @since 1.0
 */
class Project4Test {


  /**
   * This test is to ensure that the readme is being stored in an external file
   * @throws IOException When readme cannot be found
   */
  @Test
  void readmeCanBeReadAsResource() throws IOException {
    try (
      InputStream readme = Project4.class.getResourceAsStream("README.txt")
    ) {
      assertThat(readme, not(nullValue()));
      BufferedReader reader = new BufferedReader(new InputStreamReader(readme));
      String line = reader.readLine();
      assertThat(line, containsString("The purpose of this program"));
    }
  }


  /**
   * Tests to ensure that program recognizes valid format
   */
  @Test
  void testValidTimeFormat() {
    assertThat(Project4.validTimeFormat("10:15 pm"), equalTo(true));
    assertThat(Project4.validTimeFormat("1:15 AM"), equalTo(true));
    assertThat(Project4.validTimeFormat("1:05 pm"), equalTo(true));
  }
}

