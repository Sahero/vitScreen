package com.vit.view;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
 

import com.vit.connection.ConnectDB;

@ManagedBean
@RequestScoped
public class DemoUpcomingClientDeliverable {

	private ArrayList<UpcomingClients> clientDeliverablesList;
	
	public DemoUpcomingClientDeliverable(){
		clientDeliverablesList = new ArrayList<UpcomingClients>();
		setClientDeliverablesList(clientDeliverablesList);
	}

	public ArrayList<UpcomingClients> getClientDeliverablesList() {
		return clientDeliverablesList;
	}

	public void setClientDeliverablesList(ArrayList<UpcomingClients> clientDeliverablesList) {
		String query = "select appid, applicationname, phasedesc, to_char(targetDate, 'MM/DD/YYYY') targetDate, to_char(revenuemonth,'Mon-YYYY') revenueMonth,diff,wpm,npm,am,'MI' product " + 
				"FROM   (SELECT c.clientid, " + 
				"               c.appid, " + 
				"               app.applicationname, " + 
				"               m.phasecode, " + 
				"               ph.phasedesc, " + 
				"               targetdate, " + 
				"               CASE " + 
				"                 WHEN targetdate IS NOT NULL THEN " + 
				"                   CASE " + 
				"                     WHEN m.phasecode <> '00069' " + 
				"                          AND m.phasecode <> '00047' THEN " + 
				"                       CASE " + 
				"                         WHEN targetdate >= SYSDATE THEN " + 
				"                         Day_diff@OAMDB(SYSDATE, targetdate) - " + 
				"                         Fn_weekenddays@OAMDB (SYSDATE, targetdate) " + 
				"                         ELSE Day_diff@OAMDB(SYSDATE, targetdate) " + 
				"                              + Fn_weekenddays@OAMDB (targetdate, SYSDATE) " + 
				"                       END " + 
				"                     WHEN m.phasecode = '00069' THEN " + 
				"                       CASE " + 
				"                         WHEN targetdate >= c.maxposted THEN ( " + 
				"                         Day_diff@OAMDB(c.maxposted, targetdate) - " + 
				"                         Fn_weekenddays@OAMDB (c.maxposted, targetdate) ) " + 
				"                         ELSE ( Day_diff@OAMDB(c.maxposted, targetdate) " + 
				"                                + Fn_weekenddays@OAMDB (targetdate, c.maxposted) ) " + 
				"                       END " + 
				"                     ELSE NULL " + 
				"                   END " + 
				"                 ELSE NULL " + 
				"               END diff  , " + 
				"		   wpm.username	wpm, " + 
				"		   npm.username npm, " + 
				"		   acmgr.username am, " + 
				"		   m.revenueMonth " + 
				"        FROM   oam_productionmanager@OAMDB m " + 
				"               inner join " + 
				"               (SELECT DISTINCT clientid, " + 
				"                                appid " + 
				"                FROM   oam_productionmanagermessages@OAMDB " + 
				"                WHERE  ( Upper(comments) LIKE Upper( " + 
				"                         '%Phase change: from Waiting on Data From Client to%' " + 
				"                             ) " + 
				"                          OR Upper(comments) LIKE Upper( " + 
				"                             'Phase change: from Posted to%: New Data%') ))p " + 
				"                       ON m.productioncode = p.clientid " + 
				"                          AND m.appid = p.appid " + 
				"				  inner join (select  	 USERID, USERNAME   from usr_users@oamdb) wpm " + 
				"				  on wpm.userid = m.accid " + 
				"				 	  inner join (select  	 USERID, USERNAME   from usr_users@oamdb) npm " + 
				"				  on npm.userid = m.npmid " + 
				"				   	  inner join (select  	 USERID, USERNAME   from usr_users@oamdb) acmgr " + 
				"				  on acmgr.userid = m.amid " + 
				" " + 
				" " + 
				"               inner join (SELECT clientid, " + 
				"                                  appid, " + 
				"                                  postedon maxposted " + 
				"                           FROM   oam_productionmanagermessages@OAMDB " + 
				"                           WHERE  isrecent = 'Y' " + 
				"                           ORDER  BY clientid, " + 
				"                                     appid) c " + 
				"                       ON m.productioncode = c.clientid " + 
				"                          AND m.appid = c.appid " + 
				"               join oam_clientapps@OAMDB app " + 
				"                 ON app.applicationid = c.appid " + 
				"               join oam_pm_phases@OAMDB ph " + 
				"                 ON m.phasecode = ph.phasecode)data " + 
				"WHERE  1 = 1 " + 
				"       AND phasedesc NOT IN ( 'Posted', 'Front Review/QD and SignOff', " + 
				"                              'Transfer to Production', 'Pending', " + 
				"                              'On Hold', 'Waiting on Data From Client', " + 
				"                                  'Data Transer/Inventory', " + 
				"                              'Aggregation Processing Completed' ) " + 
				"	and appid not in ('159-001','159-002','159-003','159-004','159-005','159-006','159-403','376-001','622-001','522-001','743-001') " + 
				"       AND diff BETWEEN 0 AND 3 " + 
				"union all " + 
				"SELECT   to_char(d.clientID)clientid, clientname,phasename,  to_char(targetDate,'MM/DD/YYYY') targetDate, processingMonth revenuemnt,diff, wpm,npm,am,'EI' product " + 
				"FROM   (SELECT clientid, " + 
				"               cubeid, " + 
				"               a.phaseid, " + 
				"               targetdate, " + 
				"               phasename, " + 
				"               CASE " + 
				"                 WHEN targetdate IS NOT NULL THEN " + 
				"                   CASE " + 
				"                     WHEN targetdate >= SYSDATE THEN " + 
				"                     Day_diff@OAMDB(SYSDATE, targetdate) - " + 
				"                     Fn_weekenddays@OAMDB (SYSDATE, targetdate) " + 
				"                     ELSE Day_diff@OAMDB(SYSDATE, targetdate) " + 
				"                          + Fn_weekenddays@OAMDB (targetdate, SYSDATE) " + 
				"                   END " + 
				"                 ELSE NULL " + 
				"               END diff	     , " + 
				"		     wpm.username  wpm 	, " + 
				"		     npm.username npm, " + 
				"		     am.username am, " + 
				"		     processingmonth " + 
				"        FROM   vh_productionmanager@OAMDB a " + 
				"	  	   left join usr_users@oamdb wpm " + 
				"	  	 on wpm.userid = a.wpm " + 
				"		 	   left join usr_users@oamdb npm " + 
				"	  	 on npm.userid = a.npm " + 
				"	   left join usr_users@oamdb am " + 
				"	  	 on am.userid = a.am " + 
				"               join vh_phases@OAMDB ph " + 
				"                 ON a.phaseid = ph.phaseid)d " + 
				"       join vh_clients@OAMDB e " + 
				"         ON d.clientid = e.clientid " + 
				"WHERE  1 = 1 " + 
				"      AND diff BETWEEN 0 AND 3 " + 
				" 	 and d.phasename not in ('Data Acquisition','Waiting for Data','Waiting for Data From Client','Production Cube Signoff') " + 
				"    order by diff asc	  ";
		ConnectDB db = new ConnectDB();
		db.initialize();
		List<List<String>> clientStatusList = db.resultSetToListOfList(query); 		
		db.endConnection();
		
		if(clientStatusList.size() > 0){
			for(int i=1; i < clientStatusList.size();i++)
			{
				clientDeliverablesList.add(new UpcomingClients(clientStatusList.get(i).get(0),clientStatusList.get(i).get(1),clientStatusList.get(i).get(2),clientStatusList.get(i).get(3),clientStatusList.get(i).get(4),clientStatusList.get(i).get(5),clientStatusList.get(i).get(6),clientStatusList.get(i).get(7),clientStatusList.get(i).get(8),clientStatusList.get(i).get(9)));	
			}
		}
	}
	
	
}
