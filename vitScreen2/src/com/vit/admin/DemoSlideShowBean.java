package com.vit.admin;

import java.io.IOException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;


@ManagedBean  
@RequestScoped

public class DemoSlideShowBean {

	public void screen1() {
		try {
			FacesContext
					.getCurrentInstance()
					.getExternalContext()
					.redirect(
							FacesContext.getCurrentInstance()
									.getExternalContext()
									.getRequestContextPath()
									+ "/demoscreen1.jsf");
		} catch (IOException e) {
			e.printStackTrace();}}
	
	
	
	public void screen2() {
		try {
			FacesContext
					.getCurrentInstance()
					.getExternalContext()
					.redirect(
							FacesContext.getCurrentInstance()
									.getExternalContext()
									.getRequestContextPath()
									+ "/demoscreen2.jsf");
		} catch (IOException e) {
			e.printStackTrace();}}
	
	
	public void screen3() {
		try {
			FacesContext
					.getCurrentInstance()
					.getExternalContext()
					.redirect(
							FacesContext.getCurrentInstance()
									.getExternalContext()
									.getRequestContextPath()
									+ "/demoupcomingclients.jsf");
		} catch (IOException e) {
			e.printStackTrace();}}
	
	public void screen4() {
 
		try {
			FacesContext
					.getCurrentInstance()
					.getExternalContext()
					.redirect(
							FacesContext.getCurrentInstance()
									.getExternalContext()
									.getRequestContextPath()
									+ "/demolayoutChange.jsf");
		} catch (IOException e) {
			e.printStackTrace();}}
//	
//	public void screen5() {
// 
//		try {
//			FacesContext
//					.getCurrentInstance()
//					.getExternalContext()
//					.redirect(
//							FacesContext.getCurrentInstance()
//									.getExternalContext()
//									.getRequestContextPath()
//									+ "/upcomingclientsdemo.jsf");
//		} catch (IOException e) {
//			e.printStackTrace();}}
//	public void screen6() {
//		 
//		try {
//			FacesContext
//					.getCurrentInstance()
//					.getExternalContext()
//					.redirect(
//							FacesContext.getCurrentInstance()
//									.getExternalContext()
//									.getRequestContextPath()
//									+ "/megha.jsf");
//		} catch (IOException e) {
//			e.printStackTrace();}}
}

 
