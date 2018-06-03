package sample.model;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

public class ModelTest {

    Model sut;
    
    @Before
    public void setup() {
        sut = new Model();
    }

    @Test
    public void add(){
        assertThat(sut.add(1, 2), is(3));
    }

}
