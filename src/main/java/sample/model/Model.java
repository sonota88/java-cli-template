package sample.model;

import static util.Utils.puts;
import static util.Utils.debug;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.List;

public class Model {

    public void main(List<String> args) {
        debug("-->> Model.main");
        puts(args);
        puts(add( Integer.valueOf(args.get(0)), Integer.valueOf(args.get(1)) ));
    }

    int add(int a, int b) {
        return a + b;
    }

    public void cmdA(Boolean hasOptF, List<String> args) {

        puts("cmd_1");
        puts(hasOptF);
        puts(args);
    }

    public void cmdB(String optValB, List<String> args) {

        puts("cmd_2");
        puts(optValB);
        puts(args);
    }

    public void cat() {
        try (
                Reader r = new InputStreamReader(System.in, "UTF-8");
                Writer w = new OutputStreamWriter(System.out, "UTF-8");
                )
        {
            int c;

            while (true) {
                c = r.read();
                if (c < 0) {
                    break;
                }
                w.write(c);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
