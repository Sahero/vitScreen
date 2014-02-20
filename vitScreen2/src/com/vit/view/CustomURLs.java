package com.vit.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import com.vit.connection.ConnectDB;

@RequestScoped
@ManagedBean
public class CustomURLs implements Serializable {

	private static final long serialVersionUID = -2566293916493360357L;

	private ArrayList<NewURL> url;

	public CustomURLs() {
		url = new ArrayList<NewURL>();
		setUrl(url);
	}

	public ArrayList<NewURL> getUrl() {
		return url;
	}

	public void setUrl(ArrayList<NewURL> url) {
		ConnectDB db = new ConnectDB();
		db.initialize();
		List<List<String>> urlList = db
				.resultSetToListOfList("SELECT rownum, showduration, description, url, 'Y' a FROM TBL_OPS_SITESHOW ORDER BY ID");
		db.endConnection();

		if (urlList.size() > 0) {
			for (int i = 1; i < urlList.size(); i++) {
				url.add(new NewURL(urlList.get(i).get(0), urlList.get(i).get(1),
						urlList.get(i).get(2), urlList.get(i).get(3), urlList
								.get(i).get(4)));
			}
		}

 
	}

}
