/**
 * 
 */
package com.ss.lms.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.ss.lms.model.Genre;
import com.ss.lms.service.AdminService;
import com.ss.lms.service.Util;
import com.ss.lms.ui.MenuOptions.Role;

/**
 * @author Bruno
 *
 */
public class MenuGenre extends MenuBase {

	@Override
	public void displayCRUDMenu() {
		Integer inp = crudBase(new Genre());
		List<Genre> publishers = new ArrayList<>();
		Integer index = null;
		AdminService adminSer = new AdminService();

		switch (inp) {
		case 1: // Add
			Genre toAdd = new Genre();
			menu.printQuitPrompt(Role.ADMINISTRATOR);
			//////////// List all values to update ////////////
			toAdd = (Genre) menu.formatAdd(toAdd);
			break;
		case 2: // Update
			//////////// Display all and choose entry ////////////
			Genre toUpdate = new Genre();
			publishers = new ArrayList<>();
			publishers = adminSer.readGenres();
			System.out.println(Util.fAdminMessage.format("Choose the " + toUpdate.getTableName() + " to update: \n"));
			System.out.print(menu.formatString(Util.fSysOutput, publishers));
			index = Integer.parseInt(menu.handleInput("Input: "));
			toUpdate = publishers.get(index - 1);
			//////////// Update Operation////////////
			menu.printQuitPrompt(Role.ADMINISTRATOR);
			//////////// List all values to update ////////////
			toUpdate = (Genre) menu.formatUpdate(toUpdate, Role.ADMINISTRATOR);
			break;
		case 3: // Delete
			//////////// Display all and choose entry ////////////
			publishers = adminSer.readGenres();
			Genre toDelete = new Genre();
			System.out.println(Util.fAdminMessage.format("Choose the " + toDelete.getTableName() + " to delete: \n"));
			System.out.print(menu.formatString(Util.fSysOutput, publishers));
			index = Integer.parseInt(menu.handleInput("Input: "));
			toDelete = (Genre) menu.deleteBase(publishers.get(index - 1));
			break;
		case 4: // Read
			publishers = adminSer.readGenres();
			System.out.println(Util.fAdminMessage.format("Showing all Genres: \n"));

			System.out.println(publishers.stream().map((a) -> Util.fSysOutput.format(a.toString()))
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
