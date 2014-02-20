package com.vit.admin;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name="loginbean")
@SessionScoped
public class Login implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3390282285970474975L;
	private String username;
	private String password;
	private boolean usernameValid;
	private boolean passwordValid;
	private boolean validationComplete = false;
	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * @param username
	 *            the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password 
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the isUsernameValid
	 */
	public boolean getUsernameValid() {
		return usernameValid;
	}

	public void setUsernameValid(boolean isUsernameValid) {
		this.usernameValid = isUsernameValid;
	}
	/**
	 * @return the isPasswordValid
	 */
	public boolean getPasswordValid() {
		return passwordValid;
	}
	/**
	 * @paramisPasswordValid the isPasswordValid to set
	 */
	public void setPasswordValid(boolean isPasswordValid) {
		this.passwordValid = isPasswordValid;
	}
	/**
	 * @return the validationComplete
	 */
	public boolean getValidationComplete() {
		return validationComplete;
	}
	/**
	 * @paramvalidationComplete the validationComplete to set
	 */
	public void setValidationComplete(boolean validationComplete) {
		this.validationComplete = validationComplete;
	}

	public String checkValidity() {
		if (this.username == null || this.username.equals("") ){
			usernameValid = false;
		}
		else if(this.password == null  || this.password.equals("")) {
			usernameValid = true;
			passwordValid = false;
		} 
		else {
			usernameValid = true;
			passwordValid = true;

			    if ((username.equals("sagar") && password.equals("sagar")) ||
			    ( username.equals("admin") && password.equals("admin"))){		
			    	//System.out.println("1");
			        validationComplete = true;
					return "Config.jsf?faces-redirect=true";
			    }
			    else{
			    	//System.out.println("f"+username + password);
			    	validationComplete = false;	
			    	return "admin/";
			    }
		
			
		}
		return "admin/";
		 
	}

	public String logout() {
		//System.out.println("2");
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "index.jsf?faces-redirect=true";	      
	   }
}
