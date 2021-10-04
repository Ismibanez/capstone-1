package com.techelevator;

import com.techelevator.view.ItemClass;
import com.techelevator.view.Menu;
import com.techelevator.view.VendingMachineLog;
import com.techelevator.view.VendingMachineSlot;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class VendingMachineCLI {

	private static final String MAIN_MENU_OPTION_DISPLAY_ITEMS = "Display Vending Machine Items";
	private static final String MAIN_MENU_OPTION_PURCHASE = "Purchase";
	private static final String MAIN_MENU_OPTION_EXIT = "Exit";
	private static final String FEED_MONEY = "Feed Money";
	private static final String SELECT_PRODUCT = "Select Product";
	private static final String FINISH = "Finish Transaction";
	private static final String ONE_DOLLAR = "$1";
	private static final String TWO_DOLLAR = "$2";
	private static final String FIVE_DOLLAR = "$5";
	private static final String TEN_DOLLAR = "$10";
	private static final String EXIT_PAYMENT_MENU = "Return to Purchase Menu";
	private static final String[] PAYMENT_MENU_OPTIONS = {ONE_DOLLAR,TWO_DOLLAR,FIVE_DOLLAR,TEN_DOLLAR,EXIT_PAYMENT_MENU};
	private static final String[] PURCHASE_MENU_OPTIONS = {FEED_MONEY, SELECT_PRODUCT, FINISH};
	private static final String[] MAIN_MENU_OPTIONS = { MAIN_MENU_OPTION_DISPLAY_ITEMS, MAIN_MENU_OPTION_PURCHASE, MAIN_MENU_OPTION_EXIT };
	private Scanner userInput = new Scanner(System.in);

	private Menu menu;

	public double credit = 0;

	public VendingMachineCLI() {

	}

	public double getCredit() {
		return credit;
	}

	public void setCredit(double credit) {
		this.credit = credit;
	}

	public Menu getMenu() {
		return menu;
	}

	//	List <ItemClass> cart = new ArrayList<>();

	List <VendingMachineSlot> slots = new ArrayList<>();


	public VendingMachineCLI(Menu menu) {

		this.menu = menu;
		this.slots = menu.getSlots();
	}

	public void run() {
		while (true) {
			String choice = (String) menu.getChoiceFromOptions(MAIN_MENU_OPTIONS);

			if (choice.equals(MAIN_MENU_OPTION_DISPLAY_ITEMS)) {
				// display vending machine items
				for(VendingMachineSlot slot : this.slots) {
					System.out.println(slot.toString());
				}
			} else if (choice.equals(MAIN_MENU_OPTION_PURCHASE)) {
				doPurchaseMenu();
			} else if (choice.equals(MAIN_MENU_OPTION_EXIT)) {
				//exit
				break;
			}
		}
	}

	public static void main(String[] args) {
		Menu menu = new Menu(System.in, System.out);
		VendingMachineCLI cli = new VendingMachineCLI(menu);
		cli.run();
	}

	public int[] makeChange() {
		//remaining credit
		int change = (int) (credit * 100);
		int quarters = (int) (change / 25);
		int remainder = change % 25;
		int dimes = remainder / 10;
		remainder %= 10;
		int nickles = remainder / 05;

		int[] changeArray = new int[] {quarters, dimes, nickles};

		System.out.println("Your change: " + quarters + " quarters, " + dimes + " dimes, " + nickles + " nickles.");
		return changeArray;
	}

	private void doPurchaseMenu() {
		// do purchase
		while (true) {
			System.out.println(String.format("Your credits: $%.2f", credit));

			String purchaseChoice = (String) menu.getChoiceFromOptions(PURCHASE_MENU_OPTIONS);

			if (purchaseChoice.equals(FEED_MONEY)) {
				//Increasing credit
				feedMoney();

			} else if (purchaseChoice.equals(SELECT_PRODUCT)) {
				//Purchasing
				selectProduct();

			} else if (purchaseChoice.equals(FINISH)) {
				double logCredit = credit;
				System.out.println();
				System.out.println(String.format("Your credits: $%.2f", credit));
				System.out.println();
				makeChange();
				credit = 0;
				VendingMachineLog.log("| GIVE CHANGE | $" + logCredit + "| $" + credit);
				break;
			}
		}
	}

	private void feedMoney() {
		double logCredit = credit;
		while (true) {
			System.out.println();
			System.out.println(String.format("Your credits: $%.2f", credit));
			System.out.println();
			String paymentChoice = (String) menu.getChoiceFromOptions(PAYMENT_MENU_OPTIONS);
			if (paymentChoice.equals(ONE_DOLLAR)) {
				credit += 1;
			} else if (paymentChoice.equals(TWO_DOLLAR)) {
				credit += 2;
			} else if (paymentChoice.equals(FIVE_DOLLAR)) {
				credit += 5;
			} else if (paymentChoice.equals(TEN_DOLLAR)) {
				credit += 10;
			} else if (paymentChoice.equals(EXIT_PAYMENT_MENU)) {
				VendingMachineLog.log("| FEED MONEY | $" + logCredit + "| $" + credit);
				break;
			}
		}
	}

	private void selectProduct() {
		double logCredit = credit;
		while (true) {
			for (VendingMachineSlot slot : this.slots) {
				System.out.println(slot.toString());
			}
			System.out.println();
			System.out.println(String.format("Your credits: $%.2f", credit));
			System.out.println();
			System.out.println("Which product would you like to purchase?");
			String slotInput = userInput.nextLine();

			//checking all slots for selected slot
			VendingMachineSlot selectedSlot = null;
			for (VendingMachineSlot slot : this.slots) {
				if (slot.getSlotNumber().equals(slotInput)) {
					selectedSlot = slot;
				}
			}
			if (selectedSlot == null) {
				System.out.println("Slot not found. Enter new slot.");
				System.out.println();
				continue;
			}
			//checking for enough money
			if (this.credit >= selectedSlot.getItem().getPrice()) {
//				credit -= selectedSlot.getItem().getPrice();

				if (selectedSlot.getAmountRemaining() > 0) {
					selectedSlot.setAmountRemaining(selectedSlot.getAmountRemaining() - 1);
					String selectedItemType = selectedSlot.getItem().getType();

					if (selectedItemType.equals("Chip")) {
						System.out.println("Crunch Crunch, Yum!");
					} else if (selectedItemType.equals("Candy")) {
						System.out.println("Munch Munch, Yum!");
					} else if (selectedItemType.equals("Drink")) {
						System.out.println("Gulp Gulp, Yum!");
					} else if (selectedItemType.equals("Gum")) {
						System.out.println("Chew Chew, Yum!");
					}
					credit -= selectedSlot.getItem().getPrice();
					VendingMachineLog.log(" | " + selectedSlot.getItem().getName() + " " + selectedSlot.getSlotNumber() + " | $" + logCredit + " | $" + credit + " |");
					break;


				} else {
					System.out.println("SOLD OUT");
					break;
				}
			} else {
				System.out.println("Not enough credit.");
				break;
			}
		}
	}
}
