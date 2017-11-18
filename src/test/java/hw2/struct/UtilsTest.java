package hw2.struct;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UtilsTest {

    @Test
    public void testGtCombinationNumber() {
        Long res = Utils.getCombinationNumber(5, 1);
        assertEquals(new Long(5), res);

        res = Utils.getCombinationNumber(10, 3);
        assertEquals(new Long(120), res);
    }

}