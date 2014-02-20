package com.vit.resources;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.faces.bean.ManagedBean;
 
@ManagedBean
public class ConfigurationManager {
    private String configFilePath;
    private Properties properties = new Properties();
    
    public ConfigurationManager(){
    	this.configFilePath = "/com/vit/resources/config.properties";
		InputStream fis = null;
        try {
            
            fis = this.getClass().getResourceAsStream(configFilePath);
            properties.load(fis);
        } catch (FileNotFoundException ex) {
        	System.out.println(ex.getMessage() + " Configuration Manager FileNotFound");
        } catch (IOException e) {
        	System.out.println(e.getMessage() + " Configuration Manager IOException");
		} finally {
            if (fis != null) {
                try {
					fis.close();
				} catch (IOException e) {
					System.out.println(e.getMessage() + " Final Configuration Manager IOEx");
				}
            }
        }
    }
    
    public String getProperty(String key) {
        return properties.getProperty(key);
    }
    
    public String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }
    
    public void setProperty(String key, String value) {
        properties.setProperty(key, value);
    }
}