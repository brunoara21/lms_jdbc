/**
 * 
 */
package com.ss.lms.ui;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import com.diogonunes.jcolor.AnsiFormat;
import com.ss.lms.model.Author;
import com.ss.lms.model.BaseModel;
import com.ss.lms.model.Book;
import com.ss.lms.model.BookCopies;
import com.ss.lms.model.BookLoans;
import com.ss.lms.model.Borrower;
import com.ss.lms.model.Genre;
import com.ss.lms.model.LibraryBranch;
import com.ss.lms.model.Publisher;
import com.ss.lms.service.AdminService;
import com.ss.lms.service.BorrowerService;
import com.ss.lms.service.LibrarianService;
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
	private static LibrarianService librarianSer = null;
	private static BorrowerService borrowerSer = null;

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
						librarianSer = new LibrarianService();
						borrowerSer = new BorrowerService();
						
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
				librarianMenu();
				break;
			case 2:
				adminMenu();
				break;
			case 3:
				borrowerMenu();
				break;
			case 4:
				System.out
						.println(Util.fSysAlert.format("Thank you for using the Library Management System. Goodbye!"));
				return;
			default:
				mainMenu();
				return;
			}
			mainMenu();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void librarianMenu() {
		printMenu(Menu.LIB1);

		Integer inp = Integer.parseInt(handleInput("Input: "));
		switch (inp) {
		case 1: // Library Branches
			//////////// Display all and choose entry ////////////
			List<LibraryBranch> branches = librarianSer.readLibraryBranches();
			List<Object> branchL = new ArrayList<>();
			for (LibraryBranch lb : branches) {
				branchL.add(lb);
			}
			branchL.add("Back");
			System.out.print(formatString(Util.fSysOutput, branchL));

			printMenu(Menu.LIB2); // LIB2
			inp = Integer.parseInt(handleInput("Input: "));
			if (inp == branches.size() + 1) {
				librarianMenu();
				return;
			}
			librarianThreeMenu(branches.get(inp - 1));

			break;
		case 2: // Back
			return;
		default:
			return;
		}
		librarianMenu();
		return;
	}

	public void librarianThreeMenu(BaseModel selection) {
		printMenu(Menu.LIB3, selection);

		String inp = handleInput("Input: ");
		Integer inpI = Integer.parseInt(inp);
		switch (inpI) {
		case 1: // Update Details of Library
			//////////// Update Operation////////////
			printQuitPrompt(Role.LIBRARIAN);
			//////////// List all values to update ////////////
			selection = formatUpdate(selection, Role.LIBRARIAN);
			break;
		case 2: // Add copies of Book to the Branch
			List<Book> books = librarianSer.readBooksFromBranch((LibraryBranch) selection);
			List<Object> bookL = new ArrayList<>();
			for (Book b : books) {
				bookL.add(b);
			}
			bookL.add("Back");
			System.out.print(formatString(Util.fSysOutput, bookL));
			printQuitPrompt(Role.LIBRARIAN);
			System.out.println(
					Util.fLibrarianMessage.format("Choose the Book you want to add copies of, to your Branch"));
			inp = handleInput("Input: ");
			if (inp.equals("QUIT")) {
				break;
			} else {
				inpI = Integer.parseInt(inp);
			}
			if (inpI == books.size() + 1) {
				librarianThreeMenu(selection);
				break;
			}

			BookCopies bc = librarianSer.readBookCopiesFromBookBranch(books.get(inpI - 1), (LibraryBranch) selection);
			System.out.println(Util.fLibrarianMessage
					.format("Existing number of copies: " + (bc.getNoOfCopies() != null ? bc.getNoOfCopies() : 0)));
			System.out.println(Util.fLibrarianMessage.format("Enter new number of copies: "));
			inp = handleInput("Input: ");
			if (inp.equals("QUIT")) {
				break;
			} else {
				inpI = Integer.parseInt(inp);
			}

			if (inpI < 0) {
				System.out.println(Util.fSysAlert.format("Aborted Copies update due to negative number"));
				break;
			}

			bc.setNoOfCopies(inpI);
			System.out.println(librarianSer.update(bc));

			break;
		case 3:
			return;
		default:
			return;
		}
		librarianThreeMenu(selection);
		return;
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
			overrideDueDate();
			break;
		case 8: // Back
			return;
		default:
			return;
		}
		adminMenu();
		return;
	}

	private void overrideDueDate() {
		Borrower borrower = null;
		while (borrower == null) {
			System.out.println(Util.fAdminMessage.format("Please enter the Card Number to override a Book Due Date"));
			String inp = handleInput("Input: ");
			Integer inpI = 0;
			if(inp.equals("QUIT")) {
				return;
			}else {
				inpI = Integer.parseInt(inp);
			}
			try {
				borrower = adminSer.readBorrower(inpI);
			} catch (Exception e) {
				
			} finally {
				if(borrower == null) {
					System.out.println(Util.fSysAlert.format("Card Number was invalid or not found. Try again or enter 'QUIT' to go back to Main Menu"));
				}
			}
		}
		
		////////////Display all and choose entry ////////////
		List<BookLoans> bookloans = adminSer.readBookLoansFromBorrower(borrower);
		List<Object> loansL = new ArrayList<>();
		for (BookLoans lb : bookloans) {
			loansL.add(lb);
		}
		loansL.add("Back");
		System.out.print(formatString(Util.fSysOutput, loansL));	
		printQuitPrompt(Role.ADMINISTRATOR);
		System.out.println(Util.fAdminMessage.format("Pick the Book you want to return:"));			
		
		String inp = handleInput("Input: ");
		if(inp.equals("QUIT")) {
			return;
		}
		Integer inpI = Integer.parseInt(inp);
		if (inpI == bookloans.size() + 1) {
			return;
		}
		
		// Return book from BookLoan
		BookLoans bookLoan = bookloans.get(inpI -1);
		
		System.out.println(Util.fAdminMessage.format("Current Due Date is '" + bookLoan.getDueDate().toLocalDateTime().toLocalDate() + "' "));			
		LocalDate ld = null;
		while(ld == null) {
			System.out.println(Util.fAdminMessage.format("Enter new year: "));
			String year = handleInput("Input: ");
			if(inp.equals("QUIT")) {
				return;
			}
			
			System.out.println(Util.fAdminMessage.format("Enter new month: "));
			String month = handleInput("Input: ");
			if(inp.equals("QUIT")) {
				return;
			}
			
			System.out.println(Util.fAdminMessage.format("Enter new day: "));
			String day = handleInput("Input: ");
			if(inp.equals("QUIT")) {
				return;
			}

			DateTimeFormatter dateFormatter = DateTimeFormatter.BASIC_ISO_DATE;
			try {
				ld = LocalDate.parse(year+month+day,dateFormatter);
				
				if(ld.compareTo(LocalDate.now()) < 0) {
					System.out.println(Util.fSysAlert.format("New date cannot be before today's date."));
					ld = null;
				} else {
					bookLoan.setDueDate(Timestamp.valueOf(ld.atTime(12, 0)));
					System.out.println(adminSer.updateDueDate(bookLoan));
				}
			} catch (DateTimeParseException e){
				System.out.println(Util.fSysAlert.format("New date is not a correct date."));
				ld = null;
			} 
		}
	}
	
	private void borrowerMenu() {
		Borrower borrower = null;
		while (borrower == null) {
			printMenu(Menu.BORR);
			String inp = handleInput("Input: ");
			Integer inpI = 0;
			if(inp.equals("QUIT")) {
				return;
			}else {
				inpI = Integer.parseInt(inp);
			}
			try {
				borrower = borrowerSer.readBorrower(inpI);
			} catch (Exception e) {
				
			} finally {
				if(borrower == null) {
					System.out.println(Util.fSysAlert.format("Card Number was invalid or not found. Try again or enter 'QUIT' to go back to Main Menu"));
				}
			}
		}
		
		borrowerOneMenu(borrower);
	}

	private void borrowerOneMenu(Borrower borrower) {
		printMenu(Menu.BORR1, borrower);
		String inp = handleInput("Input: ");
		Integer inpI = Integer.parseInt(inp);
		List<LibraryBranch> branches = new ArrayList<>();
		List<Object> branchL = new ArrayList<>();
		switch (inpI) {
		case 1: // Check out Book
			//////////// Display all and choose entry ////////////
			branches = borrowerSer.readLibraryBranches();
			branchL = new ArrayList<>();
			for (LibraryBranch lb : branches) {
				branchL.add(lb);
			}
			branchL.add("Back");
			System.out.print(formatString(Util.fSysOutput, branchL));	
			printQuitPrompt(Role.BORROWER);
			System.out.println(Util.fBorrowerMessage.format("Pick the Branch you want to check out from:"));			
			
			inp = handleInput("Input: ");
			if(inp.equals("QUIT")) {
				break;
			}
			inpI = Integer.parseInt(inp);
			if (inpI == branches.size() + 1) {
				break;
			}
			
			LibraryBranch branch = branches.get(inpI -1);
			List<Book> books = borrowerSer.readBooksFromBranchEnoughCopies(branch);
			List<Object> bookL = new ArrayList<>();
			for (Book b : books) {
				bookL.add(b);
			}
			bookL.add("Back");
			System.out.print(formatString(Util.fSysOutput, bookL));	
			printQuitPrompt(Role.BORROWER);
			System.out.println(Util.fBorrowerMessage.format("Pick the Book you want to check out: "));			
			
			inp = handleInput("Input: ");
			if(inp.equals("QUIT")) {
				break;
			}
			inpI = Integer.parseInt(inp);
			if (inpI == books.size() + 1) {
				break;
			}

			// Add Entry to Book Loans
			BookLoans bl = new BookLoans();
			Book book = books.get(inpI -1);
			bl.setValues(Arrays.asList(book, branch, borrower, null, null, null));
			System.out.println(borrowerSer.addBookLoan(bl));			
			break;
		case 2: // Return Book
			
			////////////Display all and choose entry ////////////
			List<BookLoans> bookloans = borrowerSer.readBookLoansFromBorrower(borrower);
			List<Object> loansL = new ArrayList<>();
			for (BookLoans lb : bookloans) {
				loansL.add(lb);
			}
			loansL.add("Back");
			System.out.print(formatString(Util.fSysOutput, loansL));	
			printQuitPrompt(Role.BORROWER);
			System.out.println(Util.fBorrowerMessage.format("Pick the Book you want to return:"));			
			
			inp = handleInput("Input: ");
			if(inp.equals("QUIT")) {
				break;
			}
			inpI = Integer.parseInt(inp);
			if (inpI == bookloans.size() + 1) {
				break;
			}
			
			// Return book from BookLoan
			BookLoans bookLoan = bookloans.get(inpI -1);
			System.out.println(borrowerSer.updateReturnBookLoan(bookLoan));
			break;
		case 3: // Back
			return;
		default:
			return;
		}
		borrowerOneMenu(borrower);
		return;
	}

	protected void printMenu(Menu m) {
		printMenu(m, null);
	}

	private void printMenu(Menu m, BaseModel model) {
		switch (m) {
		case ADMIN:
			System.out.println(Util.fAdminMessage
					.format("Welcome Admin to the SS Library Management System. Please select a command\n"));
			System.out.print(formatString(Util.fAdminOption,
					Arrays.asList("Add/Update/Delete/Read Book", "Add/Update/Delete/Read Author",
							"Add/Update/Delete/Read Genres", "Add/Update/Delete/Read Publishers",
							"Add/Update/Delete/Read Library Branches", "Add/Update/Delete/Read Borrowers",
							"Over-ride Due Date for a Book Loan", "Back")));
			break;
		case BORR:
			System.out.println(Util.fBorrowerMessage.format(
					"Welcome Borrower to the SS Library Management System. Please enter your Card Number to proceed:\n"));
			break;
		case BORR1:
			System.out.println(Util.fBorrowerMessage.format(
					"Welcome '" + model.getName() + "' to the SS Library Management System. Please select a command:\n"));
			System.out.print(formatString(Util.fBorrowerOption, Arrays.asList("Check out a book", "Return a Book", "Back")));
			break;
		case LIB1:
			System.out.println(Util.fLibrarianMessage
					.format("Welcome Librarian to the SS Library Management System. Please select a command\n"));
			System.out.print(formatString(Util.fLibrarianOption, Arrays.asList("Enter Branch you manage", "Back")));
			break;
		case LIB2:
			System.out.println(Util.fLibrarianMessage.format("Choose the Library Branch number to choose Branch: \n"));
			break;
		case LIB3:
			System.out.println(Util.fLibrarianMessage.format("You are at Branch '" + model.getName() + "': \n"));
			System.out.print(formatString(Util.fLibrarianOption,
					Arrays.asList("Update the details of the Library", "Add copies of Book to the Branch", "Back")));
			break;
		case MAIN:
			System.out.println(Util.fSysMessage
					.format("Welcome to the SS Library Management System. Which category of a user are you\n"));
			System.out.print(
					formatString(Util.fSysOption, Arrays.asList("Librarian", "Administrator", "Borrower", "Quit")));
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

	protected void printQuitPrompt(Role role) {
		switch (role) {
		case ADMINISTRATOR:
			System.out.println(Util.fAdminMessage.format("Enter 'QUIT' at any prompt to cancel operation"));
			System.out.println();
			break;
		case BORROWER:
			break;
		case LIBRARIAN:
			System.out.println(Util.fLibrarianMessage.format("Enter 'QUIT' at any prompt to cancel operation"));
			System.out.println();
			break;
		case SYSTEM:
			break;
		default:
			break;

		}

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
			if (inp.equals("QUIT")) { // Aborts Add
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
		List<LibraryBranch> libBranches = new ArrayList<>();
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
						authors.add(adminSer.readAuthor((dAuthors.get(Integer.parseInt(inp) - 1).getAuthorId())));
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
				} while (!inp.equals("QUIT") && !inp.equals("N/A"));

				// Input Branching
				if (inp.equals("QUIT")) { // Aborts Add
					inputs = null;
					break;
				} else if (inp.equals("N/A")) { // No input
					if (authors.size() == 0) { // N/A on first Author input - abort
						inputs = null;
						break;
					} else {
						noInpCnt++;
						System.out.println("NUM OF AUTHORS: " + authors.size());
						inputs.add(authors);
						for (Author a : authors) {
							if (a != null) {
								System.out.println(a);

							} else {
								System.out.println("NULL");
							}
						}
					}
				}

			} else if (value == "Genres") {
				List<Genre> genres = new ArrayList<>();
				List<Genre> dGenres = adminSer.readGenres();
				System.out.print(formatString(Util.fSysOutput, dGenres));
				do {
					if (inp != null) {
						genres.add(adminSer.readGenre((dGenres.get(Integer.parseInt(inp) - 1).getGenreId())));
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
				} while (!inp.equals("QUIT") && !inp.equals("N/A"));

				// Input Branching
				if (inp.equals("QUIT")) { // Aborts Add
					inputs = null;
					break;
				} else if (inp.equals("N/A")) { // No input
					if (genres.size() == 0) { // N/A on first Genre input - abort
						inputs = null;
						break;
					} else {
						noInpCnt++;
						inputs.add(genres);
					}
				} else { // Regular Input
					System.out.println("ADDED TO INPUTS");
					inputs.add(genres);
				}
			} else if (value == "Library Branches") {
				List<LibraryBranch> dLibBranches = adminSer.readLibraryBranches();
				System.out.print(formatString(Util.fSysOutput, dLibBranches));
				do {
					if (inp != null) {
						libBranches.add(adminSer
								.readLibraryBranch((dLibBranches.get(Integer.parseInt(inp) - 1).getBranchId())));
					}

					outp = Util.fAdminMessage.format("\t" + (char) ('a' + i)
							+ ". Please enter a Branch ID you would like to add this Book to or enter N/A for no input:");

					System.out.println(outp);
					inp = handleInput("Input : ");
				} while (!inp.equals("QUIT") && !inp.equals("N/A"));

				// Input Branching
				if (inp.equals("QUIT")) { // Aborts Add
					inputs = null;
					break;
				} else if (inp.equals("N/A")) { // No input
					if (libBranches.size() == 0) { // N/A on first LibraryBranch input - abort
						inputs = null;
						break;
					} else {
						noInpCnt++;
					}

					System.out.println("ADDED TO INPUTS");
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
				if (inp.equals("QUIT")) { // Aborts Add
					inputs = null;
					break;
				} else if (inp.equals("N/A") && !toAdd.checkIfRequired(value)) { // No input
					noInpCnt++;
					inputs.add(null);
				} else { // Regular Input
					System.out.println("ADDED TO INPUTS");
					if (value == "Publisher ID") {
						inputs.add(adminSer.readPublisher(Integer.parseInt(inp)));
					} else {
						inputs.add(inp);
					}
				}
			}
		}

		if (inputs == null || inputs.size() == noInpCnt) { // Decides Whether to Abort Add or send off to Service to Add
			System.out.println(Util.fSysAlert.format("Aborted update of " + toAdd.getTableName()));
		} else {
			toAdd.setValues(inputs);
			for (Author a : toAdd.getBookAuthors()) {
				if (a != null) {
					System.out.println(a);

				} else {
					System.out.println("NULL");
				}
			}

			System.out.println(adminSer.add(toAdd));

		}

		for (LibraryBranch libB : libBranches) {
			if (libB != null) {
				outp = Util.fAdminMessage.format("\t" + ". Please enter a Number of Copies for the Branch "
						+ libB.getBranchId() + "-" + libB.getBranchName() + ":");

				System.out.println(outp);
				inp = handleInput("Input : ");
				if (inp != null) {
					if (inp.equals("QUIT")) {
						inputs = null;
						break;
					} else {
						BookCopies bc = new BookCopies();
						bc.setValues(Arrays.asList(toAdd, libB, Integer.parseInt(inp)));
						System.out.println(adminSer.addBookCopies(bc));
					}
				}
			}
		}
		System.out.println("FINISHED ADD");

		return toAdd;
	}

	protected BaseModel formatUpdate(BaseModel toUpdate, Role role) {
		AnsiFormat Aformat = null;
		if (role == Role.ADMINISTRATOR) {
			Aformat = Util.fAdminMessage;
		} else if (role == Role.LIBRARIAN) {
			Aformat = Util.fLibrarianMessage;
		}
		String inp = null;
		Integer noInpCnt = 0;
		List<Object> inputs = new ArrayList<>();
		System.out.println(
				Aformat.format("You have chosen to update " + toUpdate.getTableName() + ": " + toUpdate.getName()));
		for (Object o : toUpdate.getValues()) {
			System.out.println(Aformat
					.format("Please enter new " + toUpdate.getTableName() + " " + o + " or enter N/A for no change:"));
			inp = handleInput("Input: ");
			if (inp.equals("QUIT")) {
				inputs = null;
				break;
			} else if (inp.equals("N/A")) {
				noInpCnt++;
				inputs.add(null);
			} else {
				inputs.add(inp);
			}
		}

		if (inputs == null || inputs.size() == noInpCnt) {
			System.out.println(Util.fSysAlert.format("Aborted update of " + toUpdate.getTableName()));
		} else {
			System.out.println(Util.fSysAlert.format("Aborted update of " + toUpdate.getTableName()));
			toUpdate.setValues(inputs);
			if (role == Role.ADMINISTRATOR) {
				System.out.println(adminSer.update(toUpdate));
			} else if (role == Role.ADMINISTRATOR) {
				System.out.println(librarianSer.update(toUpdate));
			}
		}

		return toUpdate;
	}

	protected Book formatUpdate(Book toUpdate) {

		String inp = null, value, outp = null;
		Boolean updated = false;
		List<Object> inputs = new ArrayList<>();
		List<LibraryBranch> libBranches = new ArrayList<>();
		List<Publisher> dPublishers = new ArrayList<>();
		System.out.println(Util.fAdminMessage
				.format("You have chosen to update " + toUpdate.getTableName() + ": " + toUpdate.getName()));
		for (int i = 0; i < toUpdate.getValues().size(); i++) {
			value = toUpdate.getValues().get(i);
			inp = null;
			if (value == "Authors") { // Author Input Handling for Book
				List<Author> dAuthors = adminSer.readAuthors();
				System.out.println(Util.fAdminMessage.format("All Authors: "));
				System.out.print(formatString(Util.fSysOutput, dAuthors));
				do {
					outp = Util.fAdminMessage.format("\t" + (char) ('a' + i)
							+ ". Please enter Author Number you want to update for this Book, you can also add a new Author or enter N/A for no input:\n");

					List<Object> authorL = new ArrayList<>();
					for (Author a : toUpdate.getBookAuthors()) {
						authorL.add(a);
					}
					authorL.add("Add Author");
					outp = outp + formatString(Util.fSysOutput, authorL);
					System.out.println(outp);
					inp = handleInput("Input : ");

					Author fromUpdate = null;
					if (inp.equals("QUIT")) { // Aborts Update
						break;
					} else if (inp.equals("N/A")) { // No input
						break;
					} else if (Integer.parseInt(inp) == toUpdate.getBookAuthors().size() + 1) { // Add Author

						outp = Util.fAdminMessage.format("\t" + (char) ('a' + i)
								+ ". Please enter Author Number you want to add to this Book or enter 'BACK' to change your mind:\n");

						System.out.println(outp);
						inp = handleInput("Input : ");

						Author toAdd = null;

						if (inp.equals("QUIT")) { // Aborts Update
							break;
						} else if (inp.equals("N/A")) { // No input
							continue;
						} else if (inp.equals("BACK")) { // No input
							continue;
						} else {
							toUpdate.getBookAuthors().add(dAuthors.get(Integer.parseInt(inp) - 1));
							System.out.println(
									adminSer.addAuthorToBook(toUpdate, dAuthors.get(Integer.parseInt(inp) - 1)));
							updated = true;
						}
						continue;
					} else {
						fromUpdate = toUpdate.getBookAuthors().get(Integer.parseInt(inp) - 1);
					}

					outp = Util.fAdminMessage.format("\t" + (char) ('a' + i)
							+ ". Please enter Author Number you want to update " + fromUpdate.getAuthorName()
							+ " to, enter 'BACK' to change your mind or enter 'DELETE' to remove Author:");

					System.out.println(outp);
					inp = handleInput("Input : ");
					if (inp.equals("QUIT")) { // Aborts Update
						break;
					} else if (inp.equals("N/A")) { // No input
						continue;
					} else if (inp.equals("BACK")) { // No input
						continue;
					} else if (inp.equals("DELETE")) { // No input
						toUpdate.getBookAuthors().remove(fromUpdate);
						System.out.println(adminSer.deleteAuthorFromBook(toUpdate, fromUpdate));
						updated = true;
					} else {
						toUpdate.getBookAuthors().remove(fromUpdate);
						toUpdate.getBookAuthors().add(dAuthors.get(Integer.parseInt(inp) - 1));
						System.out.println(adminSer.updateAuthorFromBook(toUpdate, fromUpdate,
								dAuthors.get(Integer.parseInt(inp) - 1)));
						updated = true;
					}
				} while (!inp.equals("QUIT") && !inp.equals("N/A"));

				// Input Branching
				if (inp.equals("QUIT")) { // Aborts Add
					inputs = null;
					break;
				} else if (inp.equals("N/A")) { // No input
					continue;
				}
			} else if (value == "Genres") {
				List<Genre> dGenres = adminSer.readGenres();
				System.out.println(Util.fAdminMessage.format("All Genres: "));
				System.out.print(formatString(Util.fSysOutput, dGenres));
				do {
					outp = Util.fAdminMessage.format("\t" + (char) ('a' + i)
							+ ". Please enter Genre Number you want to update for this Book, you can also add a new Genre or enter N/A for no input:\n");

					List<Object> genreL = new ArrayList<>();
					for (Genre a : toUpdate.getBookGenres()) {
						genreL.add(a);
					}
					genreL.add("Add Genre");
					outp = outp + formatString(Util.fSysOutput, genreL);
					System.out.println(outp);
					inp = handleInput("Input : ");

					Genre fromUpdate = null;
					if (inp.equals("QUIT")) { // Aborts Update
						break;
					} else if (inp.equals("N/A")) { // No input
						System.out.println("GETTING OUT OF GENRE");
						break;
					} else if (Integer.parseInt(inp) == toUpdate.getBookGenres().size() + 1) { // Add Genre

						outp = Util.fAdminMessage.format("\t" + (char) ('a' + i)
								+ ". Please enter Genre Number you want to add to this Book or enter 'BACK' to change your mind:\n");

						System.out.println(outp);
						inp = handleInput("Input : ");

						Genre toAdd = null;

						if (inp.equals("QUIT")) { // Aborts Update
							break;
						} else if (inp.equals("N/A")) { // No input
							continue;
						} else if (inp.equals("BACK")) { // No input
							continue;
						} else {
							toUpdate.getBookGenres().add(dGenres.get(Integer.parseInt(inp) - 1));
							System.out
									.println(adminSer.addGenreToBook(toUpdate, dGenres.get(Integer.parseInt(inp) - 1)));
							updated = true;
						}
						continue;
					} else {
						fromUpdate = toUpdate.getBookGenres().get(Integer.parseInt(inp) - 1);
					}

					outp = Util.fAdminMessage.format("\t" + (char) ('a' + i)
							+ ". Please enter Genre Number you want to update " + fromUpdate.getGenreName()
							+ " to, enter 'BACK' to change your mind or enter 'DELETE' to remove Genre:");

					System.out.println(outp);
					inp = handleInput("Input : ");
					if (inp.equals("QUIT")) { // Aborts Update
						break;
					} else if (inp.equals("N/A")) { // No input
						continue;
					} else if (inp.equals("BACK")) { // No input
						continue;
					} else if (inp.equals("DELETE")) { // No input
						toUpdate.getBookGenres().remove(fromUpdate);
						System.out.println(adminSer.deleteGenreFromBook(toUpdate, fromUpdate));
						updated = true;
					} else {
						toUpdate.getBookGenres().remove(fromUpdate);
						toUpdate.getBookGenres().add(dGenres.get(Integer.parseInt(inp) - 1));
						System.out.println(adminSer.updateGenreFromBook(toUpdate, fromUpdate,
								dGenres.get(Integer.parseInt(inp) - 1)));
						updated = true;
					}

				} while (!inp.equals("QUIT") && !inp.equals("N/A"));

				// Input Branching
				if (inp.equals("QUIT")) { // Aborts Add
					inputs = null;
					break;
				} else if (inp.equals("N/A")) { // No input
					continue;
				}
			} else { // Regular Input Handling
				if (value == "Library Branches") {
					break;
				}
				if (value == "Publisher ID") {
					dPublishers = adminSer.readPublishers();
					System.out.println(Util.fAdminMessage.format("All Publishers: "));
					System.out.println(formatString(Util.fSysOutput, dPublishers));
					outp = Util.fAdminMessage
							.format("\nCurrent Publisher: " + toUpdate.getPublisher().getName() + "\n");
					outp = outp + Util.fAdminMessage.format("\t" + (char) ('a' + i)
							+ ". Please enter Publisher ID if you want to update for this Book or enter N/A for no input:");

				} else if (value == "Title") {
					outp = Util.fAdminMessage.format("\t" + (char) ('a' + i) + ". Please enter "
							+ toUpdate.getTableName() + " " + value + " or enter N/A for no input:");
				}
				System.out.println(outp);
				inp = handleInput("Input: ");

				// Input Branching
				if (inp.equals("QUIT")) { // Aborts Add
					inputs = null;
					break;
				} else if (inp.equals("N/A")) { // No input
					continue;
				} else { // Regular Input
					if (value == "Publisher ID") {
						System.out.println(
								adminSer.updatePublisherFromBook(toUpdate, dPublishers.get(Integer.parseInt(inp) - 1)));
						updated = true;
						continue;
					} else if (value == "Title") {
						System.out.println(adminSer.updateTitleFromBook(toUpdate, inp));
						updated = true;
						continue;
					}
				}
			}
		}

		if (!updated) { // If no updates were committed
			System.out.println(Util.fSysAlert.format("Aborted update of " + toUpdate.getTableName()));
		} else {
			System.out.println("Finished Updating Book");
		}
		/*
		 * for (LibraryBranch libB : libBranches) { if (libB != null) { outp =
		 * Util.fAdminMessage.format("\t" +
		 * ". Please enter a Number of Copies for the Branch " + libB.getBranchId() +
		 * "-" + libB.getBranchName() + ":");
		 * 
		 * System.out.println(outp); inp = handleInput("Input : "); if (inp != null) {
		 * if (inp.equals("QUIT")) { inputs = null; break; } else { BookCopies bc = new
		 * BookCopies(); bc.setValues(Arrays.asList(toUpdate, libB,
		 * Integer.parseInt(inp))); System.out.println(adminSer.addBookCopies(bc)); } }
		 * } }
		 */

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
			} else if (s instanceof Book) {
				Book o = (Book) s;
				sb.append(i + ") '" + o.getTitle() + "' by ");
				sb.append(o.getBookAuthors().stream().map(a -> a.getAuthorName()).collect(Collectors.joining(", ")));
				sb.append("\n");
			} else if (s instanceof BookLoans) {
				BookLoans o = (BookLoans) s;
				sb.append(i + ") '" + o.getBook().getName() + "' at branch '" + o.getBranch().getName() + "'\n");
			} else {
				if (s.equals("Back") || s.equals("Quit")) {
					sb.append(Util.fSysAlert.format(i + ") " + s.toString() + "\n"));
				} else if (s.equals("Add Author") || s.equals("Add Genre")) {
					sb.append(Util.fSysAdd.format(i + ") " + s + "\n"));
				} else {
					sb.append(i + ") " + s + "\n");
				}
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
