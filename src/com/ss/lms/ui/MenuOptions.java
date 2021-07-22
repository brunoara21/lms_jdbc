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
import com.ss.lms.model.Book;
import com.ss.lms.model.BookCopies;
import com.ss.lms.model.Genre;
import com.ss.lms.model.LibraryBranch;
import com.ss.lms.model.Publisher;
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
	private static MenuLibraryBranch mLibraryBranch = null;
	private static MenuBook mBook = null;

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
						mLibraryBranch = new MenuLibraryBranch();
						mBook = new MenuBook();
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
			mBook.displayCRUDMenu();
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
			mLibraryBranch.displayCRUDMenu();
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
							"Add/Update/Delete/Read Genres", "Add/Update/Delete/Read Publishers",
							"Add/Update/Delete/Read Library Branches", "Add/Update/Delete/Read Borrowers",
							"###TODO### Over-ride Due Date for a Book Loan")));
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
				break;
			} else if (inp.equals("N/A") && !toAdd.checkIfRequired(value)) { // No input
				noInpCnt++;
				inputs.add(null);
			} else { // Regular Input
				inputs.add(inp);
			}
		}
		// inputs = inputs.size() == noInpCnt ? null : inputs;
		if (inputs == null || inputs.size() == noInpCnt) {
			System.out.println(Util.fSysAlert.format("Aborted update of " + toAdd.getTableName()));
		} else {
			toAdd.setValues(inputs);
			System.out.println(adminSer.add(toAdd));
		}

		return toAdd;
	}

	protected Book formatAdd(Book toAdd) {
		String inp = null, value, outp = null;
		Integer noInpCnt = 0;
		List<Object> inputs = new ArrayList<>();
		System.out.println("Add " + toAdd.getTableName());
		for (int i = 0; i < toAdd.getValues().size(); i++) {
			value = toAdd.getValues().get(i);
			inp = null;
			if (value == "Authors") { // Author Input Handling for Book
				List<Author> authors = new ArrayList<>();
				List<Author> dAuthors = adminSer.readAuthors();
				System.out.print(formatString(Util.fSysOutput, dAuthors));
				do {
					if (inp != null) {
						authors.add(adminSer.readAuthor(Integer.parseInt(inp)));
					}
					if (authors.size() == 0) { // No Author has been chosen
						outp = Util.fAdminMessage
								.format("\t" + (char) ('a' + i) + ". Please enter Author ID for this Book:");
					} else { // At least one Author has been chosen
						outp = Util.fAdminMessage.format("\t" + (char) ('a' + i)
								+ ". Please enter another Author ID for this Book or enter N/A for no input:");
					}

					System.out.println(outp);
					inp = handleInput("Input : ");
				} while (!inp.equals("quit") && !inp.equals("N/A"));

				// Input Branching
				if (inp.equals("quit")) { // Aborts Add
					inputs = null;
					break;
				} else if (inp.equals("N/A")) { // No input
					if (authors.size() == 0) { // N/A on first Author input - abort
						inputs = null;
						break;
					} else {
						noInpCnt++;
						inputs.add(null);
					}
				} else { // Regular Input
					inputs.add(authors);
				}

			} else if (value == "Genres") {
				List<Genre> genres = new ArrayList<>();
				List<Genre> dGenres = adminSer.readGenres();
				System.out.print(formatString(Util.fSysOutput, dGenres));
				do {
					if (inp != null) {
						genres.add(adminSer.readGenre(Integer.parseInt(inp)));
					}
					if (genres.size() == 0) { // No Genre has been chosen
						outp = Util.fAdminMessage
								.format("\t" + (char) ('a' + i) + ". Please enter Genre ID for this Book:");
					} else { // At least one Genre has been chosen
						outp = Util.fAdminMessage.format("\t" + (char) ('a' + i)
								+ ". Please enter another Genre ID for this Book or enter N/A for no input:");
					}

					System.out.println(outp);
					inp = handleInput("Input : ");
				} while (!inp.equals("quit") && !inp.equals("N/A"));

				// Input Branching
				if (inp.equals("quit")) { // Aborts Add
					inputs = null;
					break;
				} else if (inp.equals("N/A")) { // No input
					if (genres.size() == 0) { // N/A on first Genre input - abort
						inputs = null;
						break;
					} else {
						noInpCnt++;
						inputs.add(null);
					}
				} else { // Regular Input
					inputs.add(genres);
				}
			} else if (value == "Library Branches") {
				List<LibraryBranch> libBranches = new ArrayList<>();
				List<LibraryBranch> dLibBranches = adminSer.readLibraryBranches();
				System.out.print(formatString(Util.fSysOutput, dLibBranches));
				do {
					if (inp != null) {
						libBranches.add(adminSer.readLibraryBranch(Integer.parseInt(inp)));
					}

					outp = Util.fAdminMessage.format("\t" + (char) ('a' + i)
							+ ". Please enter a Branch ID you would like to add this Book to or enter N/A for no input:");

					System.out.println(outp);
					inp = handleInput("Input : ");
				} while (!inp.equals("quit") && !inp.equals("N/A"));

				// Input Branching
				if (inp.equals("quit")) { // Aborts Add
					inputs = null;
					break;
				} else if (inp.equals("N/A")) { // No input
					if (libBranches.size() == 0) { // N/A on first LibraryBranch input - abort
						inputs = null;
						break;
					} else {
						noInpCnt++;
						inputs.add(null);
					}
				} else { // Regular Input

					for (LibraryBranch libB : libBranches) {
						outp = Util.fAdminMessage
								.format("\t" + (char) ('a' + i) + ". Please enter a Number of Copies for the Branch "
										+ libB.getBranchId() + ") " + libB.getBranchName() + ":");

						System.out.println(outp);
						inp = handleInput("Input : ");
						if (inp != null) {
							if (inp.equals("quit")) {
								inputs = null;
								break;
							} else {
								BookCopies bc = new BookCopies();
								bc.setValues(
										Arrays.asList(toAdd.getBookId(), libB.getBranchId(), Integer.parseInt(inp)));
								adminSer.addBookCopies(bc);
							}
						}

					}

					inputs.add(libBranches);
				}
			} else { // Regular Input Handling
				if (value == "Publisher ID") {
					List<Publisher> dPublishers = adminSer.readPublishers();
					System.out.print(formatString(Util.fSysOutput, dPublishers));
					outp = Util.fAdminMessage.format(
							"\t" + (char) ('a' + i) + ". Please enter " + value + " or enter N/A for no input:");
				} else {
					outp = toAdd.checkIfRequired(value)
							? Util.fAdminMessage.format("\t" + (char) ('a' + i) + ". Please enter "
									+ toAdd.getTableName() + " " + value + ":")
							: Util.fAdminMessage.format("\t" + (char) ('a' + i) + ". Please enter "
									+ toAdd.getTableName() + " " + value + " or enter N/A for no input:");
				}
				System.out.println(outp);
				inp = handleInput("Input: ");

				// Input Branching
				if (inp.equals("quit")) { // Aborts Add
					inputs = null;
					break;
				} else if (inp.equals("N/A") && !toAdd.checkIfRequired(value)) { // No input
					noInpCnt++;
					inputs.add(null);
				} else { // Regular Input
					inputs.add(inp);
				}
			}
		}

		if (inputs == null || inputs.size() == noInpCnt) { // Decides Whether to Abort Add or send off to Service to Add
			System.out.println(Util.fSysAlert.format("Aborted update of " + toAdd.getTableName()));
		} else {
			toAdd.setValues(inputs);
			System.out.println(adminSer.addBook(toAdd));
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
				Author o = (Author) s;
				sb.append(i + ") " + o.getAuthorName() + "\n");
			} else if (s instanceof Genre) {
				Genre o = (Genre) s;
				sb.append(i + ") " + o.getGenreName() + "\n");
			} else if (s instanceof Publisher) {
				Publisher o = (Publisher) s;
				sb.append(i + ") " + o.getPublisherName() + "\n");
			} else if (s instanceof LibraryBranch) {
				LibraryBranch o = (LibraryBranch) s;
				sb.append(i + ") " + o.getBranchName() + "\n");
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
