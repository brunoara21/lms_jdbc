/**
 * 
 */
package com.ss.lms.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.ss.lms.model.Borrower;
import com.ss.lms.service.AdminService;
import com.ss.lms.service.Util;
import com.ss.lms.ui.MenuOptions.Role;

/**
 * @author Bruno
 *
 */
public class MenuBorrower extends MenuBase {

	
	@Override
	public void displayCRUDMenu() {
		Integer inp = crudBase(new Borrower());
		List<Borrower> borrowers = new ArrayList<>();
		Integer index = null;
		AdminService adminSer = new AdminService();
		
		switch (inp) {
		case 1: // Add
			Borrower toAdd = new Borrower();
			menu.printQuitPrompt(Role.ADMINISTRATOR);
					//////////// List all values to update ////////////
			toAdd = (Borrower) menu.formatAdd(toAdd);
			break;
		case 2: // Update
			//////////// Display all and choose entry ////////////
			Borrower toUpdate = new Borrower();
			borrowers = new ArrayList<>();
			borrowers = adminSer .readBorrowers();
			System.out.println(Util.fAdminMessage.format("Choose the " + toUpdate.getTableName() + " to update: \n"));
			System.out.print(menu.formatString(Util.fSysOutput, borrowers));
			index = Integer.parseInt(menu.handleInput("Input: "));
			toUpdate = borrowers.get(index - 1);
			//////////// Update Operation////////////
			menu.printQuitPrompt(Role.ADMINISTRATOR);
			//////////// List all values to update ////////////
			toUpdate = (Borrower) menu.formatUpdate(toUpdate, Role.ADMINISTRATOR);
			break;
		case 3: // Delete
			//////////// Display all and choose entry ////////////
			borrowers = adminSer.readBorrowers();
			Borrower toDelete = new Borrower();
			System.out.println(Util.fAdminMessage.format("Choose the " + toDelete.getTableName() + " to delete: \n"));
			System.out.print(menu.formatString(Util.fSysOutput, borrowers));
			index = Integer.parseInt(menu.handleInput("Input: "));
			toDelete = (Borrower) menu.deleteBase(borrowers.get(index - 1));
			break;
		case 4: // Read
			borrowers = adminSer.readBorrowers();
			System.out.println(Util.fAdminMessage.format("Showing all Borrowers: \n"));

			System.out.println(borrowers.stream().map((a) -> Util.fSysOutput.format(a.toString()))
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
