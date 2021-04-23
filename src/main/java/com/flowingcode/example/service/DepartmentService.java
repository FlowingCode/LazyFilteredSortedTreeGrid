package com.flowingcode.example.service;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.flowingcode.example.data.DepartmentData;
import com.flowingcode.example.entity.Department;
import com.flowingcode.example.filter.DepartmentFilter;
import com.flowingcode.example.sort.DepartmentSort;

/**
 * This class represents the external service that would be in charge to provide the data requested using the information provided.
 * 
 * This is just defined for demo purposes, it could be a REST endpoint, or a Database-backed service.
 * 
 * 
 * @author Paola De Bartolo / Flowing Code S.A.
 *
 */
public class DepartmentService {
	
	private DepartmentData departmentData = new DepartmentData();
	
    private List<Department> departmentList = departmentData.getDepartments();

    public int getChildCount(Department parent, int limit, int offset, DepartmentFilter filter) {	
    	Stream<Department> stream = getChildren(parent).stream();
    	List<Department> results = getFilteredStream(stream, parent, filter)
				.skip(offset)
				.limit(limit)
	      		.collect(Collectors.toList());
    	
        return results.size();
	}
    
    public Boolean hasChildren(Department parent) {
        return departmentList.stream()
                .anyMatch(department -> Objects.equals(parent, department.getParent()));
    }
    
    public Collection<Department> fetchChildren(Department parent, int limit, int offset,
			DepartmentFilter filter, List<DepartmentSort> sortOrders) {		
		
    	// get filtered list
    	Stream<Department> stream = getChildren(parent).stream();
    	Stream<Department> childStream = getFilteredStream(stream, parent, filter);
    	
		// apply sorting
		Comparator<Department> comparator = (o1, o2) -> 0;
		for (DepartmentSort departmentSort : sortOrders) {
			switch (departmentSort.getPropertyName()) {
				case DepartmentSort.NAME:
					comparator = comparator.thenComparing(Department::getName);
					break;
				case DepartmentSort.MANAGER:
					comparator = comparator.thenComparing(Department::getManager);
					break;
			}
			if (!departmentSort.isDescending()) comparator = comparator.reversed();
		}
		
		childStream = childStream.sorted(comparator);
				
		// return results
    	return childStream.skip(offset).limit(limit).collect(Collectors.toList());
	}
    
    private Stream<Department> getFilteredStream(Stream<Department> stream, Department parent, DepartmentFilter filter){    	
    	String departmentNameFilter = filter != null ? filter.getNameFilter() : null;
		String managerNameFilter = filter != null ? filter.getManagerFilter() : null;
		
		Predicate<Department> p1 = department -> department.getName().contains(departmentNameFilter == null ? "": departmentNameFilter);
		Predicate<Department> p2 = department -> department.getManager().contains(managerNameFilter == null ? "" : managerNameFilter);
		Predicate<Department> filterPredicate = p1.and(p2);
		
    	return Optional.of(filterPredicate).map(f -> stream.filter(element -> flattenElement(element).anyMatch(f)))
                .orElse(stream);
    }
    
    private Stream<Department> flattenElement(Department element) {
        return Stream.concat(Stream.of(element),
            getChildren(element).stream().flatMap(this::flattenElement));
    }
    
    private List<Department> getChildren(Department parent){
        return departmentList.stream()
                .filter(department -> Objects.equals(parent, department.getParent())).collect(Collectors.toList());
    }
    
}
