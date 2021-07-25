/**
 * 
 */
package com.ss.lms.ui;

import java.util.Arrays;

import com.ss.lms.model.BaseModel;
import com.ss.lms.service.Util;

/**
 * @author Bruno
 *
 */
public abstract class MenuBase {

	protected MenuOptions menu = MenuOptions.getMenu();
	public abstract void displayCRUDMenu();
	
	protected Integer crudBase(BaseModel type) {
		System.out.println(Util.fAdminMessage.format("Operations for " + type.getTableName() + "\n"));
		System.out.print(menu.formatString(Util.fAdminOption, Arrays.asList("Add", "Update", "Delete", "Read", "Back")));

		return Integer.parseInt(menu.handleInput("Input: "));
	}
}
