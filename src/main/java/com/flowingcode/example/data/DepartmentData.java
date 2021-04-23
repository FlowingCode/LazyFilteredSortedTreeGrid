package com.flowingcode.example.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;

import com.flowingcode.example.entity.Department;

/**
 * Utility class to get department's data. Just for demo purposes.
 * 
 */
public class DepartmentData {
	
	private static final List<String> NAMES = Arrays.asList("Aaron", "Adam", "Addison", "Alexis", "Alyssa", "Andrew", "Anna",
            "Anton", "Arianna", "Audrey", "Ava", "Avery", "Ayden", "Bentley", "Brandon", "Brayden", "Brody", "Brooklyn",
            "Caleb", "Cameron", "Carlos", "Carson", "Charlotte", "Christian", "Cooper", "David", "Dominic", "Easton",
            "Elizabeth", "Ella", "Ellie", "Emma", "Evan", "Faith", "Genesis", "Grace", "Grayson", "Hailey", "Hannah",
            "Harper", "Isaac", "Jack", "Jacob", "Jonathan", "Jose", "Joshua", "Julia", "Katherine", "Kayden", "Kennedy",
            "Kevin", "Kimberly", "Lauren", "Layla", "Leah", "Levi", "Logan", "London", "Lucy", "Lydia", "Madison",
            "Mason", "Mia", "Mickael", "Morgan", "Naomi", "Nathan", "Noah", "Oliver", "Peter", "Riley", "Ryan",
            "Samuel", "Sophie", "Trinity", "Tyler", "Victoria", "Violet");
    
	private static final List<Department> DEPARTMENT_LIST = createDepartmentList();
        		
    private static List<Department> createDepartmentList() {
        List<Department> departmentList = new ArrayList<>();

        Department department = new Department(100, "D 100", null, getName());
        departmentList.add(department);
        addChildren(departmentList, department);
        
        Department department200 = new Department(200, "D 200", null, getName());
        departmentList.add(department200);
        addChildren(departmentList, department200);
        
        for (int i = 0; i < 39; i++) {
        	int code = 300 + i;
            departmentList
                    .add(new Department(code, "D "+ code , null, getName()));
        }
        
        Department department400 = new Department(400, "D 400", null, getName());
        departmentList.add(department400);
        addChildren(departmentList, department400);
           
        Department department500 = new Department(500, "D 500", null, getName());
        departmentList.add(department500);
        addChildren(departmentList, department500);

        return departmentList;
    }

    private static void addChildren(List<Department> departmentList, Department parent) {
        for (int i = 1; i < 60; i++) {
            departmentList
                .add(new Department(parent.getId() + i, parent.getName() + " - " + i, parent, getName()));
        }
    }
    
    private static String getName() {
    	return NAMES.get(new Random().nextInt(NAMES.size()));
    }

    public List<Department> getDepartments() {
        return DEPARTMENT_LIST;
    }
    
    public List<Department> getRootDepartments() {
        return DEPARTMENT_LIST.stream()
                .filter(department -> department.getParent() == null)
                .collect(Collectors.toList());
    }

    public List<Department> getChildDepartments(Department parent) {
        return DEPARTMENT_LIST.stream().filter(
                department -> Objects.equals(department.getParent(), parent))
                .collect(Collectors.toList());
    }
 
}