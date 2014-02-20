package com.vit.view;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import com.vit.connection.ConnectDB;

public class URL {

        private String link;
        private boolean visible;
        private String flag;
        private String title;
        public int id;
       
        public int getId() {
			return id;
		}


		public void setId(int id) {
			this.id = id;
		}


		public String getFlag() {
			return flag;
		}


		public void setFlag(String flag) {
			this.flag = flag;
		}


		public URL(String sn,String title, String link, String visible,String flag) {
                this.link = link;
                this.visible = Boolean.parseBoolean(visible);
                this.flag = flag;
                this.title = title;
                this.id = Integer.parseInt(sn);
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


		public boolean isVisible() {
			return visible;
		}


		public void setVisible(boolean visible) {
			this.visible = visible;
		}

		
		public void updateScreen(){
			
			ConnectDB db = new ConnectDB();
			db.initialize();
			String result = db.executeDML("UPDATE TBL_OPS_STATUS_SLIDES SET VISIBLE='"+isVisible()+"' WHERE ID='"+getId()+"'");
			db.endConnection();
			String summary = "";
			if(result.equalsIgnoreCase("1")){
				summary = isVisible() ? getTitle()+" will be displayed." : getTitle()+" will not be displayed.";
			}			
			else{
				summary = "Error Updating "+result;
			}  			  
	        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(summary));  
		}

}
