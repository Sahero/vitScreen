package com.vit.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import com.vit.connection.ConnectDB;


@RequestScoped
@ManagedBean
public class ScreenURLs implements Serializable {
	private static final long serialVersionUID = 332216048756435283L;

	private ArrayList<URL> url ;
	
	public ScreenURLs(){
		url = new ArrayList<URL>();
		setUrl(url);
	}

	public ArrayList<URL> getUrl() {
		return url;
	}

	public void setUrl(ArrayList<URL> url) {
		ConnectDB db = new ConnectDB();
		db.initialize();
		List<List<String>> urlList = db.resultSetToListOfList("SELECT ID,TITLE,URL,VISIBLE,FLAG FROM TBL_OPS_STATUS_SLIDES ORDER BY ID"); 		
		db.endConnection();
		
		if(urlList.size() > 0){
			for(int i=1; i < urlList.size();i++)
			{
				url.add(new URL(urlList.get(i).get(0),urlList.get(i).get(1),urlList.get(i).get(2),urlList.get(i).get(3),urlList.get(i).get(4)));	
			}
		}
		 
		
		/*url.add(new URL("1","Production RAC - Space Utilization Current Status","screen1.jsf","true","ALL"));
		url.add(new URL("2","Production RAC - Space Utilization Trend","screen2.jsf","true","ALL"));
		url.add(new URL("3","DR RAC - Space Utilization Current Status","layoutChange.jsf","true","ALL"));
		/*url.add(new URL("4","CNN News - U.S. Edition","http://www.cnn.com/US/","true","ALL"));
		*/
	}
	
}
