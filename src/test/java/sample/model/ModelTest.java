package sample.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class ModelTest {

    Model sut;

    @BeforeEach
    public void setup() {
        sut = new Model();
    }

    @Nested
    @DisplayName("Model#add の検証")
    class Add{

        @Test
        @DisplayName("Model#add の基本ケース")
        public void add(){
            assertEquals(3, sut.add(1, 2));
        }

    }

}
