/**
 * 
 */
package com.ss.lms.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.ss.lms.model.BaseModel;
import com.ss.lms.model.Book;
import com.ss.lms.service.AdminService;
import com.ss.lms.service.Util;

/**
 * @book Bruno
 *
 */
public class MenuBook extends MenuBase {

	@Override
	public void displayCRUDMenu() {
		Integer inp = crudBase(new Book());
		List<Book> books = new ArrayList<>();
		Integer index = null;
		AdminService adminSer = new AdminService();

		switch (inp) {
		case 1: // Add
			Book toAdd = new Book();
			menu.printQuitPrompt();
			//////////// List all values to update ////////////
			toAdd = menu.formatAdd(toAdd);
			break;
		case 2: // Update
			//////////// Display all and choose entry ////////////
			Book toUpdate = new Book();
			books = new ArrayList<>();
			books = adminSer.readBooks();
			System.out.println(Util.fAdminMessage.format("Choose the " + toUpdate.getTableName() + " to update: \n"));
			System.out.print(menu.formatString(Util.fSysOutput, books));
			index = Integer.parseInt(menu.handleInput("Input: "));
			toUpdate = books.get(index - 1);
			//////////// Update Operation////////////
			menu.printQuitPrompt();
			//////////// List all values to update ////////////
			toUpdate = (Book) menu.formatUpdate(toUpdate);
			break;
		case 3: // Delete
			//////////// Display all and choose entry ////////////
			books = adminSer.readBooks();
			Book toDelete = new Book();
			System.out.println(Util.fAdminMessage.format("Choose the " + toDelete.getTableName() + " to delete: \n"));
			System.out.print(menu.formatString(Util.fSysOutput, books));
			index = Integer.parseInt(menu.handleInput("Input: "));
			toDelete = (Book) menu.deleteBase(books.get(index - 1));
			break;
		case 4: // Read
			books = adminSer.readBooks();
			System.out.println(Util.fAdminMessage.format("Showing all Books: \n"));

			System.out.println(books.stream().map((a) -> Util.fSysOutput.format(a.toString()))
					.collect(Collectors.joining(Util.fSysOutput.format("\n-------------------------------\n"))));

			break;
		default:
			menu.adminMenu();
			return;
		}

		System.out.println();
		displayCRUDMenu();

	}
	


}
