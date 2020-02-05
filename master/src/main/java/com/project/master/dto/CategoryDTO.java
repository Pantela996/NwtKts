package com.project.master.dto;

import com.project.master.domain.CategoryType;

public class CategoryDTO {
	
	private String name;

	private int requiredRows;

	private int requiredColumns;

	private Long id;
	
	private CategoryType categoryType;
	
	public CategoryDTO() {
		super();
	}
	
	public CategoryDTO(Long id, String name, int requiredRows, int requiredColumns, CategoryType categoryType) {
		super();
		this.name = name;	
		this.requiredRows = requiredRows;
		this.requiredColumns = requiredColumns;
		this.id = id;
		this.categoryType = categoryType;
	}
	


	public CategoryDTO(String name, int requiredRows, int requiredColumns) {
		super();
		this.name = name;
		this.requiredRows = requiredRows;
		this.requiredColumns = requiredColumns;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getRequiredRows() {
		return requiredRows;
	}

	public void setRequiredRows(int requiredRows) {
		this.requiredRows = requiredRows;
	}

	public int getRequiredColumns() {
		return requiredColumns;
	}

	public void setRequiredColumns(int requiredColumns) {
		this.requiredColumns = requiredColumns;
	}
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public CategoryType getCategoryType() {
		return categoryType;
	}

	public void setCategoryType(CategoryType categoryType) {
		this.categoryType = categoryType;
	}


	
	

}
