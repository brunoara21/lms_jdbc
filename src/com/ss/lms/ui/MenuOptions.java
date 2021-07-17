/**
 * 
 */
package com.ss.lms.ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import com.diogonunes.jcolor.AnsiFormat;
import com.ss.lms.model.Author;
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

	private AdminService adminSer = new AdminService();

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
			
		}  finally {
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
			crudMenuAuthor();
			break;
		case 3: // Genres
			break;
		case 4: // Publishers
			break;
		default:
			mainMenu();
			return;
		}
	}

	private Integer crudBase(String type) {
		System.out.println(Util.fAdminMessage.format("Operations for " + type + "\n"));
		System.out.print(formatString(Util.fAdminOption, Arrays.asList("Add", "Update", "Delete", "Read")));

		return Integer.parseInt(handleInput("Input: "));
	}

	private void crudMenuAuthor() {
		Integer inp = crudBase("Author");
		List<Author> authors = new ArrayList<>();
		String authorName = null;
		Integer index = null;
		switch (inp) {
		case 1: // Add
			Author author = new Author();
			authorName = handleInput("Add Author\n" + "\ta. Author Name: ");
			author.setAuthorName(authorName);
			System.out.println(adminSer.addAuthor(author));
			break;
		case 2: // Update
			//////////// Display all and choose entry ////////////
			authors = adminSer.readAuthors();
			System.out.println(Util.fAdminMessage.format("Choose the Author to update: \n"));
			System.out.print(formatString(Util.fSysOutput, authors));
			index = Integer.parseInt(handleInput("Input: "));
			Author toUpdate = authors.get(index - 1);
			System.out.println(
					Util.fAdminMessage.format("You have chosen to update Author: " + toUpdate.getAuthorName()));
			//////////// Update Operation////////////
			System.out.println(Util.fAdminMessage.format("Enter 'quit' at any prompt to cancel operation"));
			System.out.println();
			//////////// List all values to update ////////////
			List<Object> updates = formatUpdate("Author", Arrays.asList("name"));
			if (updates != null) {
				toUpdate.setAuthor(updates);
				System.out.println(adminSer.updateAuthor(toUpdate));
			} else {
				System.out.println(Util.fSysAlert.format("Aborted update of Author"));
			}
			///////////////////////////////////////////////////
			break;
		case 3: // Delete
			//////////// Display all and choose entry ////////////
			authors = adminSer.readAuthors();
			System.out.println(Util.fAdminMessage.format("Choose the Author to delete: \n"));
			System.out.print(formatString(Util.fSysOutput, authors));
			index = Integer.parseInt(handleInput("Input: "));
			Author toDelete = authors.get(index - 1);
			System.out.println(
					Util.fAdminMessage.format("You have chosen to delete Author: " + toDelete.getAuthorName()));
			//////////// Confirmation ////////////
			System.out.println(Util.fAdminMessage.format("Please confirm the deletion (Y/N):"));
			char confirmChar = handleInput("Input: ").toCharArray()[0];
			if (confirmChar == 'Y' || confirmChar == 'y') {
				System.out.println(adminSer.deleteAuthor(toDelete));
			} else {
				System.out.println(Util.fSysAlert.format("Aborted delete of Author"));
			}
			///////////////////////////////////////////////////
			break;
		case 4: // Read
			authors = adminSer.readAuthors();
			System.out.println(Util.fAdminMessage.format("Showing all Authors: \n"));
			System.out.println(authors.stream().map((a) -> Util.fSysOutput.format(a.toString()))
					.collect(Collectors.joining(Util.fSysOutput.format("\n-------------------------------\n"))));
			break;
		default:
			adminMenu();
			return;
		}

		System.out.println();
		crudMenuAuthor();

	}

	private List<Object> formatUpdate(String type, List<Object> vals) {
		String inp = null;
		Integer noInpCnt = 0;
		List<Object> inputs = new ArrayList<>();
		for (Object o : vals) {
			System.out.println(
					Util.fAdminMessage.format("Please enter new " + type + " " + o + " or enter N/A for no change:"));
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
		return inputs.size() == noInpCnt ? null : inputs;
	}

	public void printMenu(Menu m) {
		switch (m) {
		case ADMIN:
			System.out.println(Util.fAdminMessage
					.format("Welcome Admin to the SS Library Management System. Please select a command\n"));
			System.out.print(formatString(Util.fAdminOption, Arrays.asList("Add/Update/Delete/Read Book",
					"Add/Update/Delete/Read Author", "Add/Update/Delete/Read Genres", "###TODO###")));
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

	public String formatString(AnsiFormat f, List<? extends Object> options) {
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

	public String handleInput(String message) {
		System.out.print(Util.fSysMessage.format("\n" + message));
		Scanner scn = new Scanner(System.in);
		String s = scn.nextLine();
		System.out.println();
		return s;
	}
}
