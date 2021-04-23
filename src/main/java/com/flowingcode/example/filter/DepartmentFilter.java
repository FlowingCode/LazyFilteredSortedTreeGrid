package com.flowingcode.example.filter;

/**
 * This class represents the filters that can be applied to a collection of Departments.
 * 
 * 
 * @author Paola De Bartolo / Flowing Code S.A.
 *
 */
public class DepartmentFilter {

	private String nameFilter = null;
	
	private String managerFilter = null;
	
	public DepartmentFilter() {	}

	public DepartmentFilter(String nameFilter, String managerFilter) {
		super();
		this.nameFilter = nameFilter;
		this.managerFilter = managerFilter;
	}

	public String getNameFilter() {
		return nameFilter;
	}

	public void setNameFilter(String nameFilter) {
		this.nameFilter = nameFilter;
	}

	public String getManagerFilter() {
		return managerFilter;
	}

	public void setManagerFilter(String managerFilter) {
		this.managerFilter = managerFilter;
	}
	
}
