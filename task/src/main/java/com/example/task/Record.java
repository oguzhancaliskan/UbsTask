package com.example.task;

public class Record {
	private String primaryKey;
	private String name;
	private String description;
	private String updatedTimestamp;
	
	public String getPrimaryKey() {
		return primaryKey;
	}
	
	public String getName() {
		return name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public String getUpdatedTimestamp() {
		return updatedTimestamp;
	}
	
	public void setPrimaryKey(String primaryKey) {
		this.primaryKey = primaryKey;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public void setUpdatedTimestamp(String updatedTimestamp) {
		this.updatedTimestamp = updatedTimestamp;
	}
}
