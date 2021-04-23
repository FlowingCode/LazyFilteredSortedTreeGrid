package com.flowingcode.example;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.flowingcode.example.entity.Department;
import com.flowingcode.example.filter.DepartmentFilter;
import com.flowingcode.example.service.DepartmentService;
import com.flowingcode.example.sort.DepartmentSort;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.treegrid.TreeGrid;
import com.vaadin.flow.data.provider.SortDirection;
import com.vaadin.flow.data.provider.hierarchy.AbstractBackEndHierarchicalDataProvider;
import com.vaadin.flow.data.provider.hierarchy.HierarchicalConfigurableFilterDataProvider;
import com.vaadin.flow.data.provider.hierarchy.HierarchicalQuery;
import com.vaadin.flow.router.Route;

/**
 * Main view implementing TreeGrid.
 * 
 * 
 * @author Paola De Bartolo / Flowing Code S.A.
 *
 */
@Route
public class MainView extends VerticalLayout {
	
	 public MainView() {
		
		TreeGrid<Department> treeGrid = new TreeGrid<>();
		treeGrid.addHierarchyColumn(Department::getName).setHeader("Department Name").setKey("name").setSortProperty(DepartmentSort.NAME);	  
	    treeGrid.addColumn(Department::getManager).setHeader("Manager").setKey("manager").setSortProperty(DepartmentSort.MANAGER);
	    
	    treeGrid.setMultiSort(true);
	    
	    DepartmentService departmentService = new DepartmentService();	

	    // define hierarchical and filterable data provider
	    HierarchicalConfigurableFilterDataProvider<Department, Void, DepartmentFilter> dataProvider =
            new AbstractBackEndHierarchicalDataProvider<Department, DepartmentFilter>() {

    			// returns the number of immediate child items based on query filter
				@Override
				public int getChildCount(HierarchicalQuery<Department, DepartmentFilter> query) {
					return (int) departmentService.getChildCount(query.getParent(), query.getLimit(), query.getOffset(), query.getFilter().orElse(null));
				}

				// checks if a given item should be expandable
				@Override
				public boolean hasChildren(Department item) {
					return departmentService.hasChildren(item);
				}

				// returns the immediate child items based on offset, limit, filter and sorting
				@Override
				protected Stream<Department> fetchChildrenFromBackEnd(
						HierarchicalQuery<Department, DepartmentFilter> query) {
					List<DepartmentSort> sortOrders = query.getSortOrders().stream()
	            			.map(sortOrder -> new DepartmentSort(sortOrder.getSorted(), sortOrder.getDirection().equals(SortDirection.ASCENDING)))
	            			.collect(Collectors.toList());
					return departmentService.fetchChildren(query.getParent(), query.getLimit(), query.getOffset(), query.getFilter().orElse(null), sortOrders).stream();
				}

        }.withConfigurableFilter();

        // set data provider to tree
        treeGrid.setDataProvider(dataProvider);
        
        // define filter
	    DepartmentFilter treeFilter = new DepartmentFilter();
	    
	    // set filter to data provider
	    dataProvider.setFilter(treeFilter);
	    
	    // create filter row
	    HeaderRow filterRow = treeGrid.prependHeaderRow();
	    
        TextField nameFilterTF = new TextField();
        nameFilterTF.setClearButtonVisible(true);
        nameFilterTF.addValueChangeListener(e -> {
        	treeFilter.setNameFilter(e.getValue());
        	dataProvider.refreshAll();
        });
        filterRow.getCell(treeGrid.getColumnByKey("name")).setComponent(nameFilterTF);
        
        TextField managerFilterTF = new TextField();
        managerFilterTF.setClearButtonVisible(true);
        managerFilterTF.addValueChangeListener(e -> {
        	treeFilter.setManagerFilter(e.getValue());
        	dataProvider.refreshAll();
        });
        filterRow.getCell(treeGrid.getColumnByKey("manager")).setComponent(managerFilterTF);     
        
        add(treeGrid);
    }
	
}
