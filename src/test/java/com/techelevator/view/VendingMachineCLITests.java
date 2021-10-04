package com.techelevator.view;

import com.techelevator.VendingMachineCLI;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class VendingMachineCLITests {

    @Test
    public void makeChangeTest() {
        VendingMachineCLI testMachine = new VendingMachineCLI();

        double credit = 0.40;

        testMachine.makeChange().toString();

//        int [] testArray = new int[] {quarters, dimes, nickles};

        StringBuffer testBuffer = new StringBuffer(3);

        for (int i = 0; i < 3; i++){


            testBuffer.append(i);
        }

        String bufferAsString = testBuffer.toString();

        String testArrayAsString = testMachine.makeChange().toString();

        assertEquals("0,0,0",  testMachine.makeChange().toString());
    }
}
