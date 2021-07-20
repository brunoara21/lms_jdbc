/**
 * 
 */
package com.ss.lms.model;

import java.util.List;

/**
 * @author Bruno
 *
 */
public abstract class BaseModel {

	public abstract void setValues(List<Object> inputs);
	public abstract Boolean checkIfRequired(String str);
	public abstract List<String> getValues();
	public abstract String getTableName();
	public abstract String getName();

}
