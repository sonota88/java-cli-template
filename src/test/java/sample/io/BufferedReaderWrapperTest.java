package sample.io;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.io.Reader;
import java.io.StringReader;

import org.junit.After;
import org.junit.Test;

public class BufferedReaderWrapperTest {

    BufferedReaderWrapper sut;

    @After
    public void teardown() throws Exception{
        sut.close();
    }

    @Test
    public void test_empty() throws Exception {
        String str = "";

        Reader r = new StringReader(str);
        sut = new BufferedReaderWrapper(r, '\n');

        String line = sut.readLineWithNewline();
        assertThat(line, is(nullValue()));
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
            assertThat(line, is("aa\n"));
        }
        {
            String line = sut.readLineWithNewline();
            assertThat(line, is("\n"));
        }
        {
            String line = sut.readLineWithNewline();
            assertThat(line, is("ああ\n"));
        }
        {
            String line = sut.readLineWithNewline();
            assertThat(line, is(nullValue()));
        }
    }

    @Test
    public void test_lf_no_last_newline() throws Exception {
        String str = "aa\n"
                + "ああ";

        Reader r = new StringReader(str);
        sut = new BufferedReaderWrapper(r, '\n');

        {
            String line = sut.readLineWithNewline();
            assertThat(line, is("aa\n"));
        }
        {
            String line = sut.readLineWithNewline();
            assertThat(line, is("ああ"));
        }
        {
            String line = sut.readLineWithNewline();
            assertThat(line, is(nullValue()));
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
            assertThat(line, is("aa\r\n"));
        }
        {
            String line = sut.readLineWithNewline();
            assertThat(line, is("\r\n"));
        }
        {
            String line = sut.readLineWithNewline();
            assertThat(line, is("ああ\r\n"));
        }
        {
            String line = sut.readLineWithNewline();
            assertThat(line, is(nullValue()));
        }
    }

}
