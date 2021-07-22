/**
 * 
 */
package com.ss.lms.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.ss.lms.model.LibraryBranch;
import com.ss.lms.service.AdminService;
import com.ss.lms.service.Util;

/**
 * @author Bruno
 *
 */
public class MenuLibraryBranch extends MenuBase {

	
	@Override
	public void displayCRUDMenu() {
		Integer inp = crudBase(new LibraryBranch());
		List<LibraryBranch> libBranches = new ArrayList<>();
		Integer index = null;
		AdminService adminSer = new AdminService();
		
		switch (inp) {
		case 1: // Add
			LibraryBranch toAdd = new LibraryBranch();
			menu.printQuitPrompt();
					//////////// List all values to update ////////////
			toAdd = (LibraryBranch) menu.formatAdd(toAdd);
			break;
		case 2: // Update
			//////////// Display all and choose entry ////////////
			LibraryBranch toUpdate = new LibraryBranch();
			libBranches = new ArrayList<>();
			libBranches = adminSer.readLibraryBranches();
			System.out.println(Util.fAdminMessage.format("Choose the " + toUpdate.getTableName() + " to update: \n"));
			System.out.print(menu.formatString(Util.fSysOutput, libBranches));
			index = Integer.parseInt(menu.handleInput("Input: "));
			toUpdate = libBranches.get(index - 1);
			//////////// Update Operation////////////
			menu.printQuitPrompt();
			//////////// List all values to update ////////////
			toUpdate = (LibraryBranch) menu.formatUpdate(toUpdate);
			break;
		case 3: // Delete
			//////////// Display all and choose entry ////////////
			libBranches = adminSer.readLibraryBranches();
			LibraryBranch toDelete = new LibraryBranch();
			System.out.println(Util.fAdminMessage.format("Choose the " + toDelete.getTableName() + " to delete: \n"));
			System.out.print(menu.formatString(Util.fSysOutput, libBranches));
			index = Integer.parseInt(menu.handleInput("Input: "));
			toDelete = (LibraryBranch) menu.deleteBase(libBranches.get(index - 1));
			break;
		case 4: // Read
			libBranches = adminSer.readLibraryBranches();
			System.out.println(Util.fAdminMessage.format("Showing all Library Branches: \n"));

			System.out.println(libBranches.stream().map((a) -> Util.fSysOutput.format(a.toString()))
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
