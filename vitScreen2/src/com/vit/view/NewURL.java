package com.vit.view;

public class NewURL {

	private String link;
	private String duration;
	private String flag;
	private String title;
	public String id;

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
 
	public NewURL(String sn, String duration, String title, String link, String flag) {
		this.link = link;
		this.duration =  duration;
		this.flag = flag;
		this.title = title;
		this.id = sn;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}
}
