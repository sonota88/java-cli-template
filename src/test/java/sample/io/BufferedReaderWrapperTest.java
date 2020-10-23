package sample.io;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.Reader;
import java.io.StringReader;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class BufferedReaderWrapperTest {

    BufferedReaderWrapper sut;

    @AfterEach
    public void teardown() throws Exception{
        sut.close();
    }

    @Test
    public void test_empty() throws Exception {
        String str = "";

        Reader r = new StringReader(str);
        sut = new BufferedReaderWrapper(r, '\n');

        String line = sut.readLineWithNewline();
        assertEquals(null, line);
    }

    @Test
    public void test_lf() throws Exception {
        String str = "aa\n"
                + "\n"
                + "ああ\n";

        Reader r = new StringReader(str);
        sut = new BufferedReaderWrapper(r, '\n');

        {
            String line = sut.readLineWithNewline();
            assertEquals("aa\n", line);
        }
        {
            String line = sut.readLineWithNewline();
            assertEquals("\n", line);
        }
        {
            String line = sut.readLineWithNewline();
            assertEquals("ああ\n", line);
        }
        {
            String line = sut.readLineWithNewline();
            assertEquals(null, line);
        }
    }

    @Test
    @DisplayName("末尾に改行がないパターン")
    public void test_lf_no_last_newline() throws Exception {
        String str = "aa\n"
                + "ああ";

        Reader r = new StringReader(str);
        sut = new BufferedReaderWrapper(r, '\n');

        {
            String line = sut.readLineWithNewline();
            assertEquals("aa\n", line);
        }
        {
            String line = sut.readLineWithNewline();
            assertEquals("ああ", line);
        }
        {
            String line = sut.readLineWithNewline();
            assertEquals(null, line);
        }
    }

    @Test
    public void test_crlf() throws Exception {
        String str = "aa\r\n"
                + "\r\n"
                + "ああ\r\n";

        Reader r = new StringReader(str);
        sut = new BufferedReaderWrapper(r, '\n');

        {
            String line = sut.readLineWithNewline();
            assertEquals("aa\r\n", line);
        }
        {
            String line = sut.readLineWithNewline();
            assertEquals("\r\n", line);
        }
        {
            String line = sut.readLineWithNewline();
            assertEquals("ああ\r\n", line);
        }
        {
            String line = sut.readLineWithNewline();
            assertEquals(null, line);
        }
    }

}
