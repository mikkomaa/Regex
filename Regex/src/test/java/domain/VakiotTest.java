package domain;

import static domain.Vakiot.*;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

public class VakiotTest {
    
    @Before
    public void setUp() {
        Vakiot v = new Vakiot();
    }

    @Test
    public void vakiotOvatOikein() {
        assertEquals('\0', TYHJA);
        assertEquals('\1', KYSYMYS);
    }
}
