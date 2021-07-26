/**
 * 
 */
package com.ss.lms.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.ss.lms.model.Author;
import com.ss.lms.service.AdminService;
import com.ss.lms.service.Util;
import com.ss.lms.ui.MenuOptions.Role;

/**
 * @author Bruno
 *
 */
public class MenuAuthor extends MenuBase {

	@Override
	public void displayCRUDMenu() {
		Boolean quit = false;
		while (!quit) {
			try {
				System.out.println();

				Integer inp = crudBase(new Author());
				List<Author> publishers = new ArrayList<>();
				Integer index = null;
				AdminService adminSer = new AdminService();

				switch (inp) {
				case 1: // Add
					Author toAdd = new Author();
					menu.printQuitPrompt(Role.ADMINISTRATOR);
					//////////// List all values to update ////////////
					toAdd = (Author) menu.formatAdd(toAdd);
					break;
				case 2: // Update
					//////////// Display all and choose entry ////////////
					Author toUpdate = new Author();
					publishers = new ArrayList<>();
					publishers = adminSer.readAuthors();
					System.out.println(
							Util.fAdminMessage.format("Choose the " + toUpdate.getTableName() + " to update: \n"));
					System.out.print(menu.formatString(Util.fSysOutput, publishers));
					index = Integer.parseInt(menu.handleInput("Input: "));
					toUpdate = publishers.get(index - 1);
					//////////// Update Operation////////////
					menu.printQuitPrompt(Role.ADMINISTRATOR);
					//////////// List all values to update ////////////
					toUpdate = (Author) menu.formatUpdate(toUpdate, Role.ADMINISTRATOR);
					break;
				case 3: // Delete
					//////////// Display all and choose entry ////////////
					publishers = adminSer.readAuthors();
					Author toDelete = new Author();
					System.out.println(
							Util.fAdminMessage.format("Choose the " + toDelete.getTableName() + " to delete: \n"));
					System.out.print(menu.formatString(Util.fSysOutput, publishers));
					index = Integer.parseInt(menu.handleInput("Input: "));
					toDelete = (Author) menu.deleteBase(publishers.get(index - 1));
					break;
				case 4: // Read
					publishers = adminSer.readAuthors();
					System.out.println(Util.fAdminMessage.format("Showing all Authors: \n"));

					System.out.println(publishers.stream().map((a) -> Util.fSysOutput.format(a.toString())).collect(
							Collectors.joining(Util.fSysOutput.format("\n-------------------------------\n"))));

					break;
				case 5:
					quit = true;
					break;
				default:
					break;
				}
			} catch (Exception e) {

			}
		}
		return;

	}

}
