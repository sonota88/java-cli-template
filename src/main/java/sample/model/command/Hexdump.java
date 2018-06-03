package sample.model.command;

import static util.Utils.fmt;
import static util.Utils.print;
import static util.Utils.puts;

import java.io.File;
import java.io.FileInputStream;

public class Hexdump {

    public void exec(String path) {
        File inFile = new File(path);

        try (
                FileInputStream fis = new FileInputStream(inFile);
                )
        {
            int ri = -1;
            byte[] buf = new byte[16];
            int len;
            int lastPos = 0;
            while (true) {
                ri++;
                len = fis.read(buf);
                if (len == -1) {
                    print(fmt("%08x\n", lastPos));
                    break;
                }

                puts(formatLine(ri, buf, len));

                lastPos = ri * 16 + len;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String formatLine(int ri, byte[] buf, int len) {
        String line = "";
        line += fmt("%08x ", ri * 16);

        for (int ci = 0; ci < len; ci++) {
            if ((ci + 8) % 16 == 0) {
                line += " ";
            }
            line += fmt(" %02x", buf[ci]);
        }

        return line;
    }

}
