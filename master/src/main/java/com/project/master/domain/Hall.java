package com.project.master.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Hall {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "category_id", nullable = false)
	private Category category;

	@Column
	private int totalRows;

	@Column
	private int totalColumns;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "location_id", nullable = false)
	private EventLocation location;

	public Hall() {
		super();
	}

	public Hall(Long id, Category category, int totalRows, int totalColumns, EventLocation location) {
		super();
		this.id = id;
		this.category = category;
		this.totalRows = totalRows;
		this.totalColumns = totalColumns;
		this.location = location;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public int getTotalRows() {
		return totalRows;
	}

	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}

	public int getTotalColumns() {
		return totalColumns;
	}

	public void setTotalColumns(int totalColumns) {
		this.totalColumns = totalColumns;
	}

	public EventLocation getLocation() {
		return location;
	}

	public void setLocation(EventLocation location) {
		this.location = location;
	}

}
