package com.vit.view;

public class ClientStatus {

	private String appID;
	private String appName;
	private String targetDate;
	private String revenueMonth;
	private String currentPhase;
	private String remainingDays;
	private String walthamPM;
	private String nepalPM;
	private String accountManager;
	
	public ClientStatus(String appID,String appName,String targetDate,String revenueMonth,String currentPhase,String remainingDays,String walthamPM,String nepalPM,String accountManager){
		this.appID = appID;
		this.appName = appName;
		this.targetDate =  targetDate;
		this.revenueMonth = revenueMonth;
		this.currentPhase = currentPhase;
		this.walthamPM = walthamPM;
		this.nepalPM = nepalPM;
		this.accountManager = accountManager;	
		
		setRemainingDays(remainingDays);
	}
	
	
	public String getAppID() {
		return appID;
	}
	public void setAppID(String appID) {
		this.appID = appID;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public String getTargetDate() {
		return targetDate;
	}
	public void setTargetDate(String targetDate) {
		this.targetDate = targetDate;
	}
	public String getRevenueMonth() {
		return revenueMonth;
	}
	public void setRevenueMonth(String revenueMonth) {
		this.revenueMonth = revenueMonth;
	}
	public String getCurrentPhase() {
		return currentPhase;
	}
	public void setCurrentPhase(String currentPhase) {
		this.currentPhase = currentPhase;
	}
	public String getRemainingDays() {
		return remainingDays;
	}
	public void setRemainingDays(String remainingDays) {
		this.remainingDays = remainingDays;
	}
	public String getWalthamPM() {
		return walthamPM;
	}
	public void setWalthamPM(String walthamPM) {
		this.walthamPM = walthamPM;
	}
	public String getNepalPM() {
		return nepalPM;
	}
	public void setNepalPM(String nepalPM) {
		this.nepalPM = nepalPM;
	}
	public String getAccountManager() {
		return accountManager;
	}
	public void setAccountManager(String accountManager) {
		this.accountManager = accountManager;
	}
	
	
}
