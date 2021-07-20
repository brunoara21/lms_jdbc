/**
 * 
 */
package com.ss.lms.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.ss.lms.model.Publisher;
import com.ss.lms.service.AdminService;
import com.ss.lms.service.Util;

/**
 * @author Bruno
 *
 */
public class MenuPublisher extends MenuBase {

	
	@Override
	public void displayCRUDMenu() {
		Integer inp = crudBase(new Publisher());
		List<Publisher> publishers = new ArrayList<>();
		Integer index = null;
		AdminService adminSer = new AdminService();
		
		switch (inp) {
		case 1: // Add
			Publisher toAdd = new Publisher();
			menu.printQuitPrompt();
					//////////// List all values to update ////////////
			toAdd = (Publisher) menu.formatAdd(toAdd);
			break;
		case 2: // Update
			//////////// Display all and choose entry ////////////
			Publisher toUpdate = new Publisher();
			publishers = new ArrayList<>();
			publishers = adminSer .readPublishers();
			System.out.println(Util.fAdminMessage.format("Choose the " + toUpdate.getTableName() + " to update: \n"));
			System.out.print(menu.formatString(Util.fSysOutput, publishers));
			index = Integer.parseInt(menu.handleInput("Input: "));
			toUpdate = publishers.get(index - 1);
			//////////// Update Operation////////////
			menu.printQuitPrompt();
			//////////// List all values to update ////////////
			toUpdate = (Publisher) menu.formatUpdate(toUpdate);
			break;
		case 3: // Delete
			//////////// Display all and choose entry ////////////
			publishers = adminSer.readPublishers();
			Publisher toDelete = new Publisher();
			System.out.println(Util.fAdminMessage.format("Choose the " + toDelete.getTableName() + " to delete: \n"));
			System.out.print(menu.formatString(Util.fSysOutput, publishers));
			index = Integer.parseInt(menu.handleInput("Input: "));
			toDelete = (Publisher) menu.deleteBase(publishers.get(index - 1));
			break;
		case 4: // Read
			publishers = adminSer.readPublishers();
			System.out.println(Util.fAdminMessage.format("Showing all Publishers: \n"));

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
