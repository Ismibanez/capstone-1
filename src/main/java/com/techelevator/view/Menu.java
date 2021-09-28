package com.techelevator.view;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Menu {

	private PrintWriter out;
	private Scanner in;

	private File vendingMachineList;

	public Menu(InputStream input, OutputStream output) {
		this.out = new PrintWriter(output);
		this.in = new Scanner(input);
		this.vendingMachineList = new File("vendingmachine.csv");
	}

	public List<VendingMachineSlot> getSlots() {
		List<VendingMachineSlot> slotList = new ArrayList<>();
		try (Scanner fileScanner = new Scanner(vendingMachineList)) {
			while (fileScanner.hasNext()) {
				String currentItem = fileScanner.nextLine();
				String[] itemEntry = currentItem.split("\\|");
				ItemClass newItem = new ItemClass(itemEntry[1], Double.parseDouble(itemEntry[2]), itemEntry[itemEntry.length - 1]);
				VendingMachineSlot newSlot = new VendingMachineSlot(newItem, itemEntry[0]);
				slotList.add(newSlot);
//				System.out.println(currentItem);
			}
		} catch (FileNotFoundException e) {
			System.out.println("File not found.");
		} return slotList;
	}

	public Object getChoiceFromOptions(Object[] options) {
		Object choice = null;
		while (choice == null) {
			displayMenuOptions(options);
			choice = getChoiceFromUserInput(options);
		}
		return choice;
	}

	private Object getChoiceFromUserInput(Object[] options) {
		Object choice = null;
		String userInput = in.nextLine();
		try {
			int selectedOption = Integer.valueOf(userInput);
			if (selectedOption > 0 && selectedOption <= options.length) {
				choice = options[selectedOption - 1];
			}
		} catch (NumberFormatException e) {
			// eat the exception, an error message will be displayed below since choice will be null
		}
		if (choice == null) {
			out.println(System.lineSeparator() + "*** " + userInput + " is not a valid option ***" + System.lineSeparator());
		}
		return choice;
	}

	private void displayMenuOptions(Object[] options) {
		out.println();
		for (int i = 0; i < options.length; i++) {
			int optionNum = i + 1;
			out.println(optionNum + ") " + options[i]);
		}
		out.print(System.lineSeparator() + "Please choose an option >>> ");
		out.flush();
	}
}
