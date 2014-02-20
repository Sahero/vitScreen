package com.vit.view;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import com.vit.connection.ConnectDB;

@ManagedBean
@RequestScoped
public class UpcomingClientDeliverables {

	private ArrayList<ClientStatus> clientDeliverablesList;
	
	public UpcomingClientDeliverables(){
		clientDeliverablesList = new ArrayList<ClientStatus>();
		setClientDeliverablesList(clientDeliverablesList);
	}

	public ArrayList<ClientStatus> getClientDeliverablesList() {
		return clientDeliverablesList;
	}

	public void setClientDeliverablesList(ArrayList<ClientStatus> clientDeliverablesList) {
		String query = "SELECT * FROM ( " + 
				"SELECT * FROM ( " + 
				" " + 
				"SELECT          a.APPLICATIONID,a.APPLICATIONNAME, To_Char(b.TARGETDATE,'MM/DD/YYYY'),To_Char(b.REVENUEMONTH,'MON-YYYY'), " + 
				"                g.PHASEDESC, " + 
				"                CASE WHEN b.TARGETDATE > SYSDATE THEN b.TARGETDATE - Trunc(SYSDATE) - 2 * (to_char(TARGETDATE, 'WW') - to_char(SYSDATE, 'WW')) " + 
				"                ELSE - (Trunc(SYSDATE) - b.TARGETDATE - 2 * (to_char(SYSDATE, 'WW')-to_char(TARGETDATE, 'WW'))) " + 
				"                END AS REMAININGDAYS, " + 
				"                e.USERNAME AS WMP , d.USERNAME AS NPM, f.USERNAME AS ACCOUNTMANAGER " + 
				"FROM " + 
				"                OAM_CLIENTAPPS@OAMDB a " + 
				"LEFT JOIN " + 
				"                OAM_PRODUCTIONMANAGER@OAMDB b " + 
				"ON " + 
				"                a.APPLICATIONID = b.AppID " + 
				"LEFT JOIN " + 
				"                ( " + 
				"                SELECT * FROM HEUSER.USR_USERS@OAMDB " + 
				"                WHERE NOT (NVL(UPPER(LOGINNAME),'TEST') LIKE '%TEST%' OR NVL(UPPER(LOGINNAME),'TEST') LIKE '%INTERN%') " + 
				"                ) c " + 
				"ON " + 
				"                b.BUSINESSINTID = c.USERID " + 
				"LEFT JOIN " + 
				"                HEUSER.USR_USERS@OAMDB d " + 
				"ON " + 
				"                b.NPMID = d.USERID " + 
				"LEFT JOIN " + 
				"                HEUSER.USR_USERS@OAMDB e " + 
				"ON " + 
				"                b.ACCID = e.USERID " + 
				"LEFT JOIN " + 
				"                HEUSER.USR_USERS@OAMDB f " + 
				"ON " + 
				"                b.AMID = f.USERID " + 
				"LEFT JOIN " + 
				"                OAM_PM_PHASES@OAMDB g " + 
				"ON " + 
				"                b.PHASECODE= g.phasecode " + 
				"WHERE " + 
				"                a.INPRODUCTION = 'Y' " + 
				"AND " + 
				"                b.TARGETDATE IS NOT NULL " + 
				"AND " + 
				"                b.REVENUEMONTH IS NOT NULL " + 
				"AND " + 
				"                Upper(g.PHASEDESC) NOT IN ('POSTED','ON HOLD', 'WAITING ON DATA FROM CLIENT') " + 
				"AND " + 
				"                a.APPLICATIONID NOT IN ('159-001','159-002','159-003','159-004','159-005','159-006','159-403','376-001','622-001','522-001','743-001') " + 
				"AND " + 
				"                e.USERNAME IS NOT NULL " + 
				"AND " + 
				"                d.USERNAME IS NOT NULL " + 
				"AND " + 
				"                f.USERNAME IS NOT NULL " + 
				"GROUP BY " + 
				"                a.APPLICATIONID,a.APPLICATIONNAME, To_Char(b.TARGETDATE,'MM/DD/YYYY'),To_Char(b.REVENUEMONTH,'MON-YYYY'), " + 
				"                g.PHASEDESC, " + 
				"                CASE WHEN b.TARGETDATE > SYSDATE THEN b.TARGETDATE - Trunc(SYSDATE) - 2 * (to_char(TARGETDATE, 'WW') - to_char(SYSDATE, 'WW')) " + 
				"                ELSE - (Trunc(SYSDATE) - b.TARGETDATE - 2 * (to_char(SYSDATE, 'WW')-to_char(TARGETDATE, 'WW'))) " + 
				"                END, " + 
				"                e.USERNAME, d.USERNAME, f.USERNAME " + 
				"ORDER BY REMAININGDAYS " + 
				") " + 
				"WHERE REMAININGDAYS BETWEEN -6 AND 6 " + 
				") " + 
				"WHERE ROWNUM < 31 " + 
				"";
		ConnectDB db = new ConnectDB();
		db.initialize();
		List<List<String>> clientStatusList = db.resultSetToListOfList(query); 		
		db.endConnection();
		
		if(clientStatusList.size() > 0){
			for(int i=1; i < clientStatusList.size();i++)
			{
				clientDeliverablesList.add(new ClientStatus(clientStatusList.get(i).get(0),clientStatusList.get(i).get(1),clientStatusList.get(i).get(2),clientStatusList.get(i).get(3),clientStatusList.get(i).get(4),clientStatusList.get(i).get(5),clientStatusList.get(i).get(6),clientStatusList.get(i).get(7),clientStatusList.get(i).get(8)));	
			}
		}
	}
	
	
}
