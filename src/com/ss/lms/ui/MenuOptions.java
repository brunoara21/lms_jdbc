/**
 * 
 */
package com.ss.lms.ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import com.diogonunes.jcolor.AnsiFormat;
import com.ss.lms.model.Author;
import com.ss.lms.model.BaseModel;
import com.ss.lms.service.AdminService;
import com.ss.lms.service.Util;

/**
 * @author Bruno
 *
 */
public class MenuOptions {

	public enum Role {
		SYSTEM, LIBRARIAN, ADMINISTRATOR, BORROWER
	}

	public enum Menu {
		MAIN, LIB1, LIB2, LIB3, BORR, BORR1, ADMIN
	}

	public enum Entry {
		BOOK, AUTHOR, GENRE, PUBLISHER, LIB_BRANCH, BORROWER
	}

	private static volatile MenuOptions menuInstance = null;
	private static final Object obj = new Object();

	private static AdminService adminSer = null;
	private static MenuPublisher mPublisher = null;
	private static MenuAuthor mAuthor = null;
	private static MenuGenre mGenre = null;
	private static MenuBorrower mBorrower = null;

	private MenuOptions() {
	}

	public static MenuOptions getMenu() {
		MenuOptions localMenu = menuInstance;
		try {
			if (localMenu == null) {
				synchronized (obj) {
					if (localMenu == null) {
						menuInstance = localMenu = new MenuOptions();
						adminSer = new AdminService();
						mPublisher = new MenuPublisher();
						mAuthor = new MenuAuthor();
						mGenre = new MenuGenre();
						mBorrower = new MenuBorrower();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return localMenu;
	}

	public void mainMenu() {
		printMenu(Menu.MAIN);

		Integer inp = null;
		String strIn = handleInput("Input: ");
		try {
			inp = Integer.parseInt(strIn);
			switch (inp) {
			case 1:
				break;
			case 2:
				adminMenu();
				break;
			case 3:
				break;
			default:
				mainMenu();
			}
		} catch (NumberFormatException e) {

		} finally {
			mainMenu();
		}

	}

	public void adminMenu() {
		printMenu(Menu.ADMIN);

		Integer inp = Integer.parseInt(handleInput("Input: "));
		switch (inp) {
		case 1: // Book
			break;
		case 2: // Author
			mAuthor.displayCRUDMenu();
			break;
		case 3: // Genres
			mGenre.displayCRUDMenu();
			break;
		case 4: // Publishers
			mPublisher.displayCRUDMenu();
			break;
		case 5: // Library Branches
			// crudMenulibraryBranch();
			break;
		case 6: // Borrower
			mBorrower.displayCRUDMenu();
			break;
		case 7: // OverrideDueDate
			// crudMenuOverrideDueDate();
			break;
		default:
			mainMenu();
			return;
		}
	}

	protected void printMenu(Menu m) {
		switch (m) {
		case ADMIN:
			System.out.println(Util.fAdminMessage
					.format("Welcome Admin to the SS Library Management System. Please select a command\n"));
			System.out.print(formatString(Util.fAdminOption,
					Arrays.asList("Add/Update/Delete/Read Book", "Add/Update/Delete/Read Author",
							"Add/Update/Delete/Read Genres", "Add/Update/Delete/Read Publishers", "###TODO###Add/Update/Delete/Read Library Branches", "Add/Update/Delete/Read Borrowers")));
			break;
		case BORR:
			break;
		case BORR1:
			break;
		case LIB1:
			break;
		case LIB2:
			break;
		case LIB3:
			break;
		case MAIN:
			System.out.println(Util.fSysMessage
					.format("Welcome to the SS Library Management System. Which category of a user are you\n"));
			System.out.print(formatString(Util.fSysOption, Arrays.asList("Librarian", "Administrator", "Borrower")));
			break;
		default:
			break;

		}
	}

	protected BaseModel deleteBase(BaseModel toDelete) {
		//////////// Confirmation ////////////
		System.out.println(Util.fAdminMessage
				.format("You have chosen to delete " + toDelete.getTableName() + ": " + toDelete.getName()));
		System.out.println(Util.fAdminMessage.format("Please confirm the deletion (Y/N):"));
		char confirmChar = handleInput("Input: ").toCharArray()[0];
		if (confirmChar == 'Y' || confirmChar == 'y') {
			System.out.println(adminSer.delete(toDelete));
		} else {
			System.out.println(Util.fSysAlert.format("Aborted delete of " + toDelete.getTableName()));
		}

		return toDelete;
	}

	protected void printQuitPrompt() {
		System.out.println(Util.fAdminMessage.format("Enter 'quit' at any prompt to cancel operation"));
		System.out.println();
	}

	protected BaseModel formatAdd(BaseModel toAdd) {
		String inp, value = null;
		Integer noInpCnt = 0;
		List<Object> inputs = new ArrayList<>();
		System.out.println("Add " + toAdd.getTableName());
		for (int i = 0; i < toAdd.getValues().size(); i++) {
			value = toAdd.getValues().get(i);

			System.out.println(toAdd.checkIfRequired(value)
					? Util.fAdminMessage.format(
							"\t" + (char) ('a' + i) + ". Please enter " + toAdd.getTableName() + " " + value + ":")
					: Util.fAdminMessage.format("\t" + (char) ('a' + i) + ". Please enter " + toAdd.getTableName() + " "
							+ value + " or enter N/A for no input:"));
			inp = handleInput("Input: ");

			// Input Branching
			if (inp.equals("quit")) { // Aborts Add
				inputs = null;
			} else if (inp.equals("N/A") && !toAdd.checkIfRequired(value)) { // No input
				noInpCnt++;
				inputs.add(null);
			} else { // Regular Input
				inputs.add(inp);
			}
		}
		inputs = inputs.size() == noInpCnt ? null : inputs;
		if (inputs != null) {
			toAdd.setValues(inputs);
			System.out.println(adminSer.add(toAdd));
		} else {
			System.out.println(Util.fSysAlert.format("Aborted update of " + toAdd.getTableName()));
		}

		return toAdd;
	}

	protected BaseModel formatUpdate(BaseModel toUpdate) {
		String inp = null;
		Integer noInpCnt = 0;
		List<Object> inputs = new ArrayList<>();
		System.out.println(Util.fAdminMessage
				.format("You have chosen to update " + toUpdate.getTableName() + ": " + toUpdate.getName()));
		for (Object o : toUpdate.getValues()) {
			System.out.println(Util.fAdminMessage
					.format("Please enter new " + toUpdate.getTableName() + " " + o + " or enter N/A for no change:"));
			inp = handleInput("Input: ");
			if (inp.equals("quit")) {
				return null;
			} else if (inp.equals("N/A")) {
				noInpCnt++;
				inputs.add(null);
			} else {
				inputs.add(inp);
			}
		}

		inputs = inputs.size() == noInpCnt ? null : inputs;

		if (inputs != null) {
			toUpdate.setValues(inputs);
			System.out.println(adminSer.update(toUpdate));
		} else {
			System.out.println(Util.fSysAlert.format("Aborted update of " + toUpdate.getTableName()));
		}

		return toUpdate;
	}

	protected String formatString(AnsiFormat f, List<? extends Object> options) {
		StringBuilder sb = new StringBuilder();
		Integer i = 1;
		for (Object s : options) {
			if (s instanceof Author) {
				Author a = (Author) s;
				sb.append(i + ") " + a.getAuthorName() + "\n");
			} else {
				sb.append(i + ") " + s + "\n");
			}
			i++;
		}
		return f.format(sb.toString());
	}

	protected String handleInput(String message) {
		System.out.print(Util.fSysMessage.format("\n" + message));
		Scanner scn = new Scanner(System.in);
		String s = scn.nextLine();
		System.out.println();
		return s;
	}
}
