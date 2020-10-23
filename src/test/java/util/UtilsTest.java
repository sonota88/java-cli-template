package util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class UtilsTest {

    private static final String BS = "\\";
    private static final String DQ = "\"";

    @Test
    public void escape_basic(){
        String expected = BS + BS
                + " " + BS + DQ
                + " " + BS + "b"
                + " " + BS + "f"
                + " " + BS + "n"
                + " " + BS + "r"
                + " " + BS + "t"
                ;

        assertEquals(
                expected,
                Utils.escape("\\ \" \b \f \n \r \t")
                );
    }

    @Test
    public void escape_multi_occurence(){
        String expected = BS + "n"
                + BS + "n"
                ;

        assertEquals(
                expected,
                Utils.escape("\n\n")
                );
    }

    @Test
    public void escape_bs_normal_char(){
        String expected = BS + BS
                + "n"
                ;

        assertEquals(
                expected,
                Utils.escape(BS + "n")
                );
    }

}
