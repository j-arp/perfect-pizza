package net.arpcentral.pizzadoh.models;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class AmountTest {

    @Test
    public void amout_to_int_when_amount_is_string() throws Exception {
        Amount amount = new Amount("4");
        Integer expected = 4;
        assertEquals(expected, amount.toInteger());
    }


    @Test
    public void amout_to_string_when_amount_is_integer() throws Exception {
        Amount amount = new Amount(4);
        String expected = "4";
        assertEquals(expected, amount.toString());
    }

    @Test
    public void amout_to_string_when_amount_is_string() throws Exception {
        Amount amount = new Amount("4");
        String expected = "4";
        assertEquals(expected, amount.toString());
    }

    @Test
    public void amout_to_string_when_amount_is_double() throws Exception {
        Amount amount = new Amount(4.0);
        String expected = "4";
        assertEquals(expected, amount.toString());
    }
}