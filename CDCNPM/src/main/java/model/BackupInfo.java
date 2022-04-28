package model;

import java.sql.Date;

public class BackupInfo {

	private String position;
	private String name;
	private String backupDate;
	private String user;
	
	
	public BackupInfo() {
		super();
	}

	

	@Override
	public String toString() {
		return "BackupInfo [position=" + position + ", name=" + name + ", backupDate=" + backupDate + ", user=" + user
				+ "]";
	}



	public BackupInfo(String position, String name, String backupDate, String user) {
		super();
		this.position = position;
		this.name = name;
		this.backupDate = backupDate;
		this.user = user;
	}


	public String getPosition() {
		return position;
	}


	public void setPosition(String position) {
		this.position = position;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	


	public String getBackupDate() {
		return backupDate;
	}


	public void setBackupDate(String backupDate) {
		this.backupDate = backupDate;
	}


	public String getUser() {
		return user;
	}


	public void setUser(String user) {
		this.user = user;
	}
	
	
	
	
}
