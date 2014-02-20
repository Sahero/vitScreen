package com.vit.admin;

import java.io.IOException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;


@ManagedBean  
@RequestScoped
public class SlideShowBean {

	public void screen1() {
		try {
			FacesContext
					.getCurrentInstance()
					.getExternalContext()
					.redirect(
							FacesContext.getCurrentInstance()
									.getExternalContext()
									.getRequestContextPath()
									+ "/screen1.jsf");
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
									+ "/screen2.jsf");
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
									+ "/upcomingclients.jsf");
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
									+ "/layoutChange.jsf");
		} catch (IOException e) {
			e.printStackTrace();}}
	
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
//									+ "/markmoddy.jsf");
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

 
