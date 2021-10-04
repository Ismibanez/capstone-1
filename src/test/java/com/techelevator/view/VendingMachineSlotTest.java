package com.techelevator.view;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class VendingMachineSlotTest {
    @Test
    public void toStringTest_SoldOut() {
        VendingMachineSlot testSlot = new VendingMachineSlot(new ItemClass("TestCandy", 1.50, "Candy"), "A5");

        testSlot.setAmountRemaining(0);

        String testSlotAsString = testSlot.toString();
        assertEquals("| " + testSlot.getSlotNumber() + " | " + testSlot.getItem() + " | SOLD OUT |", testSlotAsString);
    }
}
