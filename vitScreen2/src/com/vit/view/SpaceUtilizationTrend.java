package com.vit.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.primefaces.model.chart.BarChartSeries;
import org.primefaces.model.chart.CartesianChartModel;

import com.vit.connection.ConnectDB;

@ManagedBean
@RequestScoped
public class SpaceUtilizationTrend implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1249234369147461039L;
	
	private CartesianChartModel prodRAC;
	private ArrayList<Space> spaceTrend;
	List<List<String>> spaceList;
	
	public SpaceUtilizationTrend() {
		
	 	ConnectDB db = new ConnectDB();
			db.initializeCloud();
	 
			
			
			
 	 spaceList = db.resultSetToListOfList("select  YYYY,WW,TOTAL,FREE, TOTAL-FREE used , round(FREE/TOTAL*100,2) FREEPCT, round((TOTAL-FREE)/TOTAL*100,2) usedpect from( " + 
		 		"select YYYY,WW, round(\"'total_mb'_SUM_QUALITY\"/1024/1024,2) total, round(\"'usable_file_mb'_SUM_QUALITY\"/1024/1024,2) free from (select " + 
		 		"	to_char(ROLLUP_TIMESTAMP,'YYYY MON') YYYY, " + 
		 		"	to_char(ROLLUP_TIMESTAMP,'W') WW, " + 
		 		"	METRIC_COLUMN, " + 
		 		"	round(AVG(AVERAGE),2) AVERAGE  " + 
		 		"	  " + 
		 		"from " + 
		 		"	MGMT$METRIC_DAILY " + 
		 		"where " + 
		 		"	lower(target_name) like '%nprac%' " + 
		 		"	and     metric_column in ( 'total_mb', 'usable_file_mb') AND metric_name = 'DiskGroup_Usage' " + 
		 		"	and key_value='DATA1' " + 
		 		" 	and rollup_timestamp >= sysdate - 84 " + 
		 		"group by " + 
		 		"	to_char(ROLLUP_TIMESTAMP,'YYYY MON'), " + 
		 		"	to_char(ROLLUP_TIMESTAMP,'W'), " + 
		 		"	METRIC_COLUMN, " + 
		 		"	COLUMN_LABEL " + 
		 		"	order by 1 " + 
		 		"  ) " + 
		 		"pivot (avg(AVERAGE) as sum_quality for (metric_column) in ('total_mb', 'usable_file_mb')) " + 
		 		"order by to_date(yyyy,'YYYY MON') asc,WW asc) " + 
		 		"");  
	 
 			  db.endConnection();
		
		spaceTrend = new ArrayList<Space>();
		setSpaceTrend(spaceTrend);
		
		prodRAC = new CartesianChartModel();
		setProdRAC(prodRAC);
	}
	
	public ArrayList<Space> getSpaceTrend() {
		return spaceTrend;
	}

	public void setSpaceTrend(ArrayList<Space> spaceTrend) {
		
		if(spaceList.size() > 0){
			for(int i=1; i < spaceList.size();i++)
			{
					spaceTrend.add(new Space(spaceList.get(i).get(0) +" W" +spaceList.get(i).get(1), Double.parseDouble(spaceList.get(i).get(6)),Double.parseDouble(spaceList.get(i).get(5))));	 
				//spaceTrend.add(new Space(String.valueOf(i), Double.parseDouble(spaceList.get(i).get(6)),Double.parseDouble(spaceList.get(i).get(5))));	 

			}
		}
	 
	
		
//	
//		spaceTrend.add(new Space("Oct 2013 Week-3", 75,25));
//		spaceTrend.add(new Space("Oct 2013 Week-4", 83,17));
//		spaceTrend.add(new Space("Nov 2013 Week-1", 80,20));
//		spaceTrend.add(new Space("Nov 2013 Week-2", 81,19));
//		spaceTrend.add(new Space("Nov 2013 Week-3", 89,11));
//		spaceTrend.add(new Space("Nov 2013 Week-4", 90,10));
//		spaceTrend.add(new Space("Dec 2013 Week-1", 75,25));
//		spaceTrend.add(new Space("Dec 2013 Week-2", 79,21));
//		spaceTrend.add(new Space("Dec 2013 Week-3", 82,18));
//		spaceTrend.add(new Space("Dec 2013 Week-4", 70,30));
//		spaceTrend.add(new Space("Jan 2014 Week-1", 85.5,14.5));
	}

	public void setProdRAC(CartesianChartModel prodRAC){
		//prodRAC = new CartesianChartModel();

		BarChartSeries used = new BarChartSeries();
		used.setLabel("Used");
		for(int i=0;i<spaceTrend.size();i++){
			used.set(spaceTrend.get(i).getxAxisTitle() ,spaceTrend.get(i).getUsedSpace() );
		}
		/*used.set("Oct 2013 Week-2", 84);
		used.set("Oct 2013 Week-3", 75);
		used.set("Oct 2013 Week-4", 83);
		used.set("Nov 2013 Week-1", 80);
		used.set("Nov 2013 Week-2", 81);
		used.set("Nov 2013 Week-3", 89);
		used.set("Nov 2013 Week-4", 90);
		used.set("Dec 2013 Week-1", 75);
		used.set("Dec 2013 Week-2", 79);
		used.set("Dec 2013 Week-3", 82);
		used.set("Dec 2013 Week-4", 70);
		used.set("Jan 2014 Week-1", 86);
		*/
		BarChartSeries free = new BarChartSeries();
		free.setLabel("Free");
		
		for(int i=0;i<spaceTrend.size();i++){
			free.set(spaceTrend.get(i).getxAxisTitle() ,spaceTrend.get(i).getFreeSpace() );
		}
		/*
		free.set("Oct 2013 Week-2", 16);
		free.set("Oct 2013 Week-3", 25);
		free.set("Oct 2013 Week-4", 17);
		free.set("Nov 2013 Week-1", 20);
		free.set("Nov 2013 Week-2", 19);
		free.set("Nov 2013 Week-3", 11);
		free.set("Nov 2013 Week-4", 10);
		free.set("Dec 2013 Week-1", 25);
		free.set("Dec 2013 Week-2", 21);
		free.set("Dec 2013 Week-3", 18);
		free.set("Dec 2013 Week-4", 30);
		free.set("Jan 2014 Week-1", 14);
		*/
		
		prodRAC.addSeries(used);
		prodRAC.addSeries(free);
				
	}
	public CartesianChartModel getProdRAC() { return prodRAC; }

}
