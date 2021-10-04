package com.techelevator.view;

public class VendingMachineSlot {

    private final int startAmount = 5;
    private int amountRemaining;
    private ItemClass item;
    private String slotNumber;

    public VendingMachineSlot(ItemClass item, String slotNumber) {
        this.item = item;
        this.amountRemaining = this.startAmount;
        this.slotNumber = slotNumber;
    }

    @Override
    public String toString() {
        if(amountRemaining == 0) {
            return "| " + this.slotNumber + " | " + this.item.toString() + " | SOLD OUT |";
        } else {
            return "| " + this.slotNumber + " | " + this.item.toString() + " | " + this.amountRemaining + " |";
        }
    }

    public int getAmountRemaining() {
        return amountRemaining;
    }

    public ItemClass getItem() {
        return item;
    }

    public String getSlotNumber() {
        return slotNumber;
    }

    public void setAmountRemaining(int amountRemaining) {
        this.amountRemaining = amountRemaining;
    }
}
