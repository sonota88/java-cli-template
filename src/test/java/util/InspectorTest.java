package util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import util.inspector.Sample;
import util.inspector.SampleParent;

public class InspectorTest {

    @Test
    public void inspect_null(){
        assertEquals(
            "null",
            Inspector.inspect(null)
        );
    }

    @Test
    public void inspect_int(){
        assertEquals(
            "1",
            Inspector.inspect(1)
        );
    }

    @Test
    public void inspect_long(){
        assertEquals(
            "1",
            Inspector.inspect(1L)
        );
    }

    @Test
    public void inspect_str(){
        assertEquals(
            "\"a\"",
            Inspector.inspect("a")
        );
    }

    @Test
    public void inspect_localDateTime(){
        assertEquals(
            replaceQuote(
                "{"
                + "`__class__` `java.time.LocalDateTime`"
                + " `value` `2022-02-20T12:13:14`"
                + "}"
            ),
            Inspector.inspect(LocalDateTime.parse("2022-02-20T12:13:14"))
        );
    }

    @Test
    public void inspect_localDate(){
        assertEquals(
            replaceQuote(
                "{"
                + "`__class__` `java.time.LocalDate`"
                + " `value` `2022-02-20`"
                + "}"
            ),
            Inspector.inspect(LocalDate.parse("2022-02-20"))
        );
    }

    @Test
    public void inspect_list(){
        List<Integer> xs = new ArrayList<>();
        xs.add(1);
        xs.add(2);
        assertEquals(
            "(1 2)",
            Inspector.inspect(xs)
        );
    }

    @Test
    public void prettyInspect_list(){
        List<Integer> xs = new ArrayList<>();
        xs.add(1111);
        xs.add(2222);
        xs.add(3333);
        xs.add(4444);
        xs.add(5555);
        assertEquals(
            lines(
                    "(",
                    "  1111",
                    "  2222",
                    "  3333",
                    "  4444",
                    "  5555",
                    ")"
            ),
            Inspector.prettyInspect(xs)
        );
    }

    @Test
    public void prettyInspect_list_short(){
        List<Integer> xs = new ArrayList<>();
        xs.add(11);
        xs.add(22);
        xs.add(33);
        assertEquals(
            "(11 22 33)",
            Inspector.prettyInspect(xs)
        );
    }

    @Test
    public void inspect_map(){
        Map<Integer, Integer> map = new HashMap<>();
        map.put(1, 11);
        map.put(2, 22);

        Utils.puts_e(Inspector.inspect(map));

        assertEquals(
            "{1 11 2 22}",
            Inspector.inspect(map)
        );
    }

    @Test
    public void prettyInspect_map(){
        Map<Integer, Integer> map = new HashMap<>();
        map.put(1, 11);
        map.put(2, 22);

        Utils.puts_e(Inspector.inspect(map));

        assertEquals(
            lines(
                "{",
                "  1 11",
                "  2 22",
                "}"
            ),
            Inspector.prettyInspect(map)
        );
    }

    @Test
    public void prettyInspect_map_single_short(){
        Map<String, String> map = new HashMap<>();
        map.put("_12", "_34");

        Utils.puts_e(Inspector.inspect(map));

        assertEquals(
            replaceQuote("{`_12` `_34`}"),
            Inspector.prettyInspect(map)
        );
    }

    @Test
    public void prettyInspect_map_single_long(){
        Map<String, String> map = new HashMap<>();
        map.put("_123456789", "_123456789");

        Utils.puts_e(Inspector.inspect(map));

        assertEquals(
            replaceQuote(
                lines(
                    "{",
                    "  `_123456789` `_123456789`",
                    "}"
                )
            ),
            Inspector.prettyInspect(map)
        );
    }

    @Test
    public void inspect_obj() {
        String actual = Inspector.inspect(new Sample(1, "a"));
        Utils.puts_e(actual);

        assertEquals(
                replaceQuote(
            "{"
                + "`__class__` `util.inspector.Sample`"
                + " `intval` 1"
                + " `strval` `a`"
            + "}"
                ),
            actual
        );
    }

    @Test
    public void inspect_obj_nested() {
        String actual = Inspector.inspect(
                new SampleParent(
                        new Sample(1, "a")
                )
        );
        Utils.puts_e(actual);

        assertEquals(
                replaceQuote(
                        "{"
                        + "`__class__` `util.inspector.SampleParent`"
                                + " `sample` {"
                                + "`__class__` `util.inspector.Sample`"
                                + " `intval` 1"
                                + " `strval` `a`"
                            + "}"
                        + "}"
                ),
            actual
        );
    }

    @Test
    public void prettyInspect_obj_nested() {
        String actual = Inspector.prettyInspect(
                new SampleParent(
                        new Sample(1, "a")
                )
        );
        Utils.puts_e(actual);

        assertEquals(
                replaceQuote(
                        lines(
                        "{",
                        "  `__class__` `util.inspector.SampleParent`",
                        "  `sample`",
                        "    {",
                        "      `__class__` `util.inspector.Sample`",
                        "      `intval` 1",
                        "      `strval` `a`",
                        "    }",
                        "}"
                        )
                ),
            actual
        );
    }

    private String lines(String ... strs) {
        return Arrays.stream(strs).collect(Collectors.joining("\n"));
    }

    private String replaceQuote(String s) {
        return StringUtils.replace(s, "`", "\"");
    }

}
