package sample.model;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class ModelTest {

    @Test
    public void add(){
        assertThat(Model.add(1, 2), is(3));
    }

}
