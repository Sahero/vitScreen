package com.vit.view;

public class Space {

	private String xAxisTitle;
	private double usedSpace;
	private double freeSpace;
	
	public Space(String xAxisTitle, double usedSpace,double freeSpace) {
		setxAxisTitle(xAxisTitle);
		setUsedSpace(usedSpace);
		setFreeSpace(freeSpace);
	}

	public String getxAxisTitle() {
		return xAxisTitle;
	}

	public void setxAxisTitle(String xAxisTitle) {
		this.xAxisTitle = xAxisTitle;
	}

	public double getUsedSpace() {
		return usedSpace;
	}

	public void setUsedSpace(double usedSpace) {
		this.usedSpace = usedSpace;
	}

	public double getFreeSpace() {
		return freeSpace;
	}

	public void setFreeSpace(double freeSpace) {
		this.freeSpace = freeSpace;
	}
}
