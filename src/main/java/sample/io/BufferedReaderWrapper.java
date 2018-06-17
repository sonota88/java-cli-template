package sample.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

public class BufferedReaderWrapper extends BufferedReader {

    char newlineChar;

    public BufferedReaderWrapper(Reader in, char newlineChar) {
        super(in);
        this.newlineChar = newlineChar;
    }

    public String readLineWithNewline() throws IOException {
        StringBuilder sb = new StringBuilder();
        while (true) {
            int n = this.read();
            if (n == -1) {
                break;
            }
            sb.append((char)n);

            if (n == this.newlineChar) {
                return sb.toString();
            }
        }
        if (sb.length() == 0) {
            return null;
        } else {
            return sb.toString();
        }
    }

}
