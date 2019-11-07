package com.project.master.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column
	private String name;

	@Column
	private int requiredRows;

	@Column
	private int requiredColumns;

	@Column
	private CategoryType categoryType;

	public Category() {
	}

	public Category(Long id, String name, int requiredRows, int requiredColumns, CategoryType categoryType) {
		super();
		this.id = id;
		this.name = name;
		this.requiredRows = requiredRows;
		this.requiredColumns = requiredColumns;
		this.categoryType = categoryType;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public CategoryType getCategoryType() {
		return categoryType;
	}

	public void setCategoryType(CategoryType categoryType) {
		this.categoryType = categoryType;
	}

}
