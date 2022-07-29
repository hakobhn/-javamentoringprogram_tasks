package com.epam.mentoring.messenger;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.CoreMatchers.containsString;

public class Exception3Test {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testDivisionWithException() {

        thrown.expect(ArithmeticException.class);
        thrown.expectMessage(containsString("/ by zero"));

        int i = 1 / 0;
    }

    @Test
    public void throwsExceptionWithSpecificType() {
        thrown.expect(NullPointerException.class);
        throw new NullPointerException();
    }

}
