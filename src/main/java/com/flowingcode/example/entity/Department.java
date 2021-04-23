package com.flowingcode.example.entity;

import java.util.Objects;

/**
 * This class represents a Department entity.
 *
 */
public class Department {

	private int id;
    private String name;
    private String manager;
    private Department parent;

    public Department(int id, String name, Department parent, String manager) {
        this.id = id;
        this.name = name;
        this.manager = manager;
        this.parent = parent;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public Department getParent() {
        return parent;
    }

    public void setParent(Department parent) {
        this.parent = parent;
    }

    @Override
    public String toString() {
        return name;
    }

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Department))
			return false;
		Department other = (Department) obj;
		return id == other.id;
	}
        
}
