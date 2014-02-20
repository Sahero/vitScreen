package com.vit.view;

import java.io.Serializable;  
import java.util.List;

import com.vit.connection.ConnectDB;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.primefaces.model.chart.BarChartSeries;
import org.primefaces.model.chart.CartesianChartModel;

@ManagedBean  
@RequestScoped
public class SpaceUtilizationCurrentStatus implements Serializable {  
  
    /**
	 * 
	 */
	private static final long serialVersionUID = 8400002856168005920L;
	private CartesianChartModel prodRAC;
	private CartesianChartModel drRAC;
	private CartesianChartModel procServer;
	private CartesianChartModel scrubServer;
	List<List<String>> spaceList;
	List<List<String>> spaceList2;
	List<List<String>> spaceList3;
	List<List<String>> spaceList4;
	
    public SpaceUtilizationCurrentStatus() {  
    	
    	ConnectDB db = new ConnectDB();
		db.initializeCloud();
		
		spaceList  = db.resultSetToListOfList("SELECT group_name, " + 
				"       Round(used / total_num, 2) AS used, " + 
				"       Round(free / total_num, 2) AS free, " + 
				"       Round(( totalsize - freesize ) / 1024 / 1024, 2) ||' TB' UsedSize, " + 
				"       Round(freesize / 1024 / 1024, 2) || ' TB' FreeSize " + 
				"FROM   (SELECT CASE " + 
				"                 WHEN host_name LIKE '%SCRUB%' " + 
				"                       OR host_name IN( 'NPHIGHMARKP5', 'NPHIGHMARKP1' ) THEN  'SCRUB' " + 
				"                 WHEN host_name LIKE '%PROC%' " + 
				"                       OR host_name IN( 'NPHIGHMARKP2', 'NPHIGHMARKP6','NPHIGHMARKP7', 'NPHIGHMARKP8', 'NPHIGHMARKP12', 'NPHIGHMARKP12.D2HAWKEYE.NET', 'NPHIGHMARKP7.D2HAWKEYE.NET' ) THEN 'PROC' " + 
				"                 ELSE 'OTHERS' " + 
				"               END                       AS GROUP_NAME, " + 
				"               SUM(asm_space_used)       AS used, " + 
				"               SUM(100 - asm_space_used) AS free, " + 
				"               Count(*)                  AS total_num, " + 
				"               SUM(totalsize)            totalSize, " + 
				"               SUM(freesize)             freeSize " + 
				"        FROM   (SELECT host_name                         AS Host_Name, " + 
				"                       Max(Decode (metric, 1, pct_used)) AS Root_Space_Used, " + 
				"                       Max(Decode (metric, 2, pct_used)) AS Raw_Space_Used, " + 
				"                       Max(Decode (metric, 4, pct_used)) AS ASM_Space_Used, " + 
				"                       Max(Decode (metric, 3, pct_used)) AS CPU_Utilization, " + 
				"                       Max(Decode (metric, 5, pct_used)) AS Memory_Utilization, " + 
				"                       Max(Decode (metric, 4, sizeb))    AS totalSize, " + 
				"                       Max(Decode (metric, 4, freeb))    AS FreeSize " + 
				"                FROM   (SELECT host_name, name, pct_used, sizeb, freeb, " + 
				"                               Row_number () over (PARTITION BY host_name ORDER BY name) AS metric " + 
				"                        FROM   (SELECT Upper(target_name) host_name, name, " + 
				"                                       Round(( ( sizeb - freeb ) / sizeb ) * 100 , 2) AS pct_used, " + 
				"                                       sizeb, 	 freeb " + 
				"                                FROM   sysman.mgmt$storage_report_data " + 
				"                                WHERE  entity_type = 'Mountpoint' " + 
				"                                       AND name NOT LIKE '%boot%' " + 
				"                                       AND name NOT LIKE '%6%' " + 
				"                                       AND name NOT LIKE '%data1%' " + 
				"                                       AND target_name NOT IN ( " + 
				"                                           '172.16.3.70', '172.16.3.66', 'npprocracp1', 'npprocracp2', 'npprocracp3', 'npracp1', 'npracp2', " + 
				"                                           'npracp3','nvcaWEBst1.veriskhealth.net', 'nvcldcntrlp1.d2hawkeye.net' , 'nvdmxsuitdbu1.d2hawkeye.net', " + 
				"                                               'nvupuatdbd1', 'npbenchmarkp1.d2hawkeye.net', 'nphighmarkp11' ) " + 
				"                                UNION ALL " + 
				"                                SELECT " + 
				"					  	Upper(Substr(target_name, Instr(  target_name, '_'   ) + 1)) AS host_name, diskgroup, " + 
				"                                       Round(( Max (Decode (seq, 4, value)) ), 2 ) pct_used , " + 
				"                                       Round(( Max (Decode (seq, 5, value)) ), 2 ) sizeb, " + 
				"                                       Round(( Max (Decode (seq, 3, value)) ), 2 ) freeb " + 
				"                                FROM   (SELECT target_name,key_value diskgroup, value, metric_column, " + 
				"                                               Row_number ()over (PARTITION BY target_name, key_value ORDER BY metric_column) AS seq " + 
				"                                        FROM   mgmt$metric_current " + 
				"                                        WHERE  target_type = 'osm_instance' " + 
				"                                               AND metric_column IN ( 'free_mb', 'usable_file_mb', 'computedImbalance', 'usable_total_mb', 'percent_used', 'diskCnt' ) " + 
				"                                                OR ( metric_column = 'total_mb' AND metric_name = 'DiskGroup_Usage' )) " + 
				"                                WHERE  diskgroup LIKE '%DATA%' " + 
				"                                       AND Substr(target_name, Instr(target_name, '_') + 1) NOT " + 
				"                                           IN  (  'npracpscan', 'npprocracpscan','nvupuatdbd1', 'nvdmxsuitdbu1.d2hawkeye.net' ) " + 
				"                                GROUP  BY Substr(target_name,  Instr(target_name, '_') +  1), diskgroup " + 
				"                                UNION ALL " + 
				"                                SELECT Upper(target_name) host_name, " + 
				"					  	'MEMORY_UTIL', " + 
				"                                       Round(value, 2)    MEM_UTIL, " + 
				"                                       0                  SIZEB, " + 
				"                                       0                  FREEB " + 
				"                                FROM   mgmt$metric_current " + 
				"                                WHERE  metric_name = 'Load' " + 
				"                                       AND metric_column = 'memUsedPct' " + 
				"                                       AND target_name <> 'nphighmarkp11' " + 
				"                                       AND sysman.Isnumeric(CASE WHEN Instr(target_name, '.') <> 0 THEN Substr(target_name, 1,Instr(target_name, '.') - 1) " + 
				"                                           ELSE target_name END) <> 1 " + 
				"                                UNION ALL " + 
				"                                SELECT Upper(target_name) host_name, 'CPU_UTIL', " + 
				"                                       Round(value, 2)    CPU_UTIL, " + 
				"                                       0                  SIZEB, " + 
				"                                       0                  FREEB " + 
				"                                FROM   mgmt$metric_current " + 
				"                                WHERE  metric_name = 'Load' " + 
				"                                       AND metric_column = 'cpuUtil' " + 
				"                                       AND target_name <> 'nphighmarkp11' " + 
				"                                       AND sysman.Isnumeric(CASE WHEN Instr(target_name, '.') <> 0 THEN Substr(target_name, 1, Instr(target_name, '.') - 1 ) " + 
				"                                           ELSE target_name END) <> 1) " + 
				"                        WHERE  host_name NOT IN ( " + 
				"                               'NVUPUATDBD1', 'NPBENCHMARKP1.D2HAWKEYE.NET', 'NVCLDCNTRLP1.D2HAWKEYE.NET','NVDMXSUITDBU1.D2HAWKEYE.NET','NPPROCRACP2', 'NPPROCRACP1', 'NPPROCRACP3', 'NPRACP1', " + 
				"                               'NPRACP3', 'NPRACP2', 'NVCAWEBST1.VERISKHEALTH.NET', 'NPNORMP1.D2HAWKEYE.NET','NPTAPESTGP2', 'NPWAREHOUSEP2', 'NVPROCP1', 'NVHMEDISP1','NVNORMIMPP1', 'NVCDFMREP1' )) " + 
				"                GROUP  BY host_name " + 
				"                ORDER  BY 2 DESC, 5 DESC, 4 DESC, 6 DESC) " + 
				"        GROUP  BY CASE " + 
				"                    WHEN host_name LIKE '%SCRUB%' OR host_name IN( 'NPHIGHMARKP5', 'NPHIGHMARKP1' ) THEN 'SCRUB' " + 
				"                    WHEN host_name LIKE '%PROC%' " + 
				"                          OR host_name IN( 'NPHIGHMARKP2', 'NPHIGHMARKP6', 'NPHIGHMARKP7', 'NPHIGHMARKP8', 'NPHIGHMARKP12','NPHIGHMARKP12.D2HAWKEYE.NET','NPHIGHMARKP7.D2HAWKEYE.NET' ) " + 
				"       				THEN 'PROC' ELSE 'OTHERS' END) " + 
				"ORDER  BY 1");
		

		/*
		spaceList = db.resultSetToListOfList("SELECT group_name,Round(used/total_num,2) AS used,Round(free/total_num,2) AS free FROM ( " + 
				"SELECT CASE WHEN host_name LIKE '%SCRUB%' " + 
				"OR  host_name IN('NPHIGHMARKP5','NPHIGHMARKP1') THEN 'SCRUB' " + 
				"WHEN host_name LIKE '%PROC%' " + 
				"OR host_name IN('NPHIGHMARKP2','NPHIGHMARKP6','NPHIGHMARKP7','NPHIGHMARKP8','NPHIGHMARKP12','NPHIGHMARKP12.D2HAWKEYE.NET'  , 'NPHIGHMARKP7.D2HAWKEYE.NET') " + 
				"THEN 'PROC' " + 
				"ELSE 'OTHERS' " + 
				"END AS GROUP_NAME, " + 
				" Sum(ASM_SPACE_USED) AS used, sum(100-ASM_SPACE_USED) AS free,Count(*) AS total_num FROM " + 
				"( " + 
				"SELECT " + 
				"host_name AS Host_Name, " + 
				"max(DECODE (metric, 1, PCT_USED)) AS Root_Space_Used, " + 
				"max(DECODE (metric, 2, PCT_USED)) AS Raw_Space_Used, " + 
				"max(DECODE (metric, 4, PCT_USED)) AS ASM_Space_Used, " + 
				"max(DECODE (metric, 3, PCT_USED)) AS CPU_Utilization, " + 
				"max(DECODE (metric, 5, PCT_USED)) AS Memory_Utilization " + 
				"FROM " + 
				"( " + 
				"SELECT " + 
				"HOST_NAME,name,PCT_used , " + 
				"ROW_NUMBER () " + 
				"                 OVER (PARTITION BY host_name " + 
				"                       ORDER BY name) " + 
				"                    AS metric FROM " + 
				"( " + 
				"select " + 
				"Upper(target_name) host_name, " + 
				"name, " + 
				"round(((sizeb-freeb)/sizeb)*100,2) AS pct_used " + 
				"from sysman.mgmt$storage_report_data " + 
				"where entity_type='Mountpoint' " + 
				"AND name  NOT LIKE '%boot%' and name  NOT LIKE '%6%' and name NOT LIKE '%data1%' " + 
				"AND TARGET_NAME NOT IN " + 
				"('172.16.3.70','172.16.3.66','npprocracp1','npprocracp2','npprocracp3','npracp1','npracp2','npracp3','nvcaWEBst1.veriskhealth.net','nvcldcntrlp1.d2hawkeye.net', " + 
				"'nvdmxsuitdbu1.d2hawkeye.net','nvupuatdbd1','npbenchmarkp1.d2hawkeye.net','nphighmarkp11') " + 
				"UNION ALL " + 
				"SELECT " + 
				"Upper(SubStr(target_name, InStr(target_name,'_')+1)) AS host_name , " + 
				"         diskgroup, " + 
				"        Round((MAX (DECODE (seq, 4, VALUE))),2) pct_used " + 
				"    FROM (SELECT target_name, " + 
				"                 key_value diskgroup, " + 
				"                 VALUE, " + 
				"                 metric_column, " + 
				"                 ROW_NUMBER () " + 
				"                 OVER (PARTITION BY target_name, key_value " + 
				"                       ORDER BY metric_column) " + 
				"                    AS seq " + 
				"            FROM MGMT$METRIC_CURRENT " + 
				"          WHERE        target_type = 'osm_instance' " + 
				"                   AND metric_column IN " + 
				"                          ( " + 
				"                           'free_mb', " + 
				"                           'usable_file_mb', " + 
				"                           'computedImbalance', " + 
				"                           'usable_total_mb', " + 
				"                           'percent_used','diskCnt') " + 
				"                OR (    metric_column = 'total_mb' " + 
				"                    AND metric_name = 'DiskGroup_Usage')) " + 
				"                    WHERE diskgroup LIKE '%DATA%' " + 
				" " + 
				"                    AND SubStr(target_name, InStr(target_name,'_')+1) " + 
				"                    NOT IN ('npracpscan','npprocracpscan','nvupuatdbd1','nvdmxsuitdbu1.d2hawkeye.net') " + 
				"                    GROUP BY SubStr(target_name, InStr(target_name,'_')+1),diskgroup " + 
				"UNION ALL " + 
				"SELECT Upper(target_name) host_name,'MEMORY_UTIL', Round(Value,2) MEM_UTIL " + 
				"FROM   mgmt$metric_current " + 
				"WHERE  metric_name = 'Load' " + 
				"  AND  metric_column = 'memUsedPct' " + 
				"  AND target_name<>'nphighmarkp11' " + 
				" AND  sysman.isnumeric(CASE WHEN InStr(target_name,'.')<>0 then " + 
				"    SubStr(target_name,1,InStr(target_name,'.')-1) ELSE target_name END)<>1 " + 
				"UNION ALL " + 
				"SELECT Upper(target_name) host_name,'CPU_UTIL', Round(Value,2) CPU_UTIL " + 
				"FROM   mgmt$metric_current " + 
				"WHERE  metric_name = 'Load' " + 
				"  AND  metric_column = 'cpuUtil' " + 
				"  AND target_name<>'nphighmarkp11' " + 
				" AND  sysman.isnumeric(CASE WHEN InStr(target_name,'.')<>0 then " + 
				"    SubStr(target_name,1,InStr(target_name,'.')-1) ELSE target_name END)<>1 " + 
				") " + 
				" " + 
				"WHERE Upper(host_name) NOT IN ('NVUPUATDBD1','NPBENCHMARKP1.D2HAWKEYE.NET','NVCLDCNTRLP1.D2HAWKEYE.NET','NVDMXSUITDBU1.D2HAWKEYE.NET' " + 
				",'NPPROCRACP2','NPPROCRACP1','NPPROCRACP3','NPRACP1','NPRACP3','NPRACP2','NVCAWEBST1.VERISKHEALTH.NET', " + 
				"'NPNORMP1.D2HAWKEYE.NET','NPTAPESTGP2','NPWAREHOUSEP2','NVPROCP1','NVHMEDISP1','NVNORMIMPP1','NVCDFMREP1') " + 
				") " + 
				"GROUP BY host_name " + 
				"ORDER BY 2 desc,5 desc,4 desc,6 DESC " + 
				") " + 
				
				"GROUP BY CASE WHEN host_name LIKE '%SCRUB%' " + 
				"OR  host_name IN('NPHIGHMARKP5','NPHIGHMARKP1') THEN 'SCRUB' " + 
				"WHEN host_name LIKE '%PROC%' " + 
				"OR host_name IN('NPHIGHMARKP2','NPHIGHMARKP6','NPHIGHMARKP7','NPHIGHMARKP8','NPHIGHMARKP12','NPHIGHMARKP12.D2HAWKEYE.NET'  , 'NPHIGHMARKP7.D2HAWKEYE.NET') " + 
				"THEN 'PROC' " + 
				"ELSE 'OTHERS' " + 
				"END " + 
				") " + 
				"ORDER BY 1"); 		
*/
	  spaceList2 = db.resultSetToListOfList("SELECT " + 
			  		"METRIC_COLUMN as Met, " + 
			  		"COLUMN_LABEL, " + 
			  		"Value,metric_name "  + 
			  		"FROM " + 
			  		"MGMT$METRIC_CURRENT " + 
			  		"WHERE        " + 
			  		"lower(target_name) like '%nprac%' " + 
			  		"and     metric_column in ( 'total_mb', 'usable_file_mb') AND metric_name = 'DiskGroup_Usage' " + 
			  		"and key_value='DATA1' " + 
			  		"order by " + 
			  		"METRIC_NAME, metric_column ") ;	
		
spaceList3 = db.resultSetToListOfList("SELECT Ceil(( Max(total_mb) - Max(usable_file_mb) ) / Max(total_mb) * 100.0) pused,  " + 
		"       Floor(Max(usable_file_mb) / Max(total_mb) * 100.0)  pfree,  " + 
		"       round(Max(total_mb)/1024/1024 ,2) || ' TB' total,  " + 
		"       round(Max(usable_file_mb)/1024/1024 ,2) || ' TB' free ,  " + 
		"       round((Max(total_mb) - Max(usable_file_mb))/1024/1024,2) || ' TB' used  " + 
		"FROM   (SELECT CASE  " + 
		"                 WHEN met = 'total_mb' THEN value  " + 
		"               END total_mb,  " + 
		"               CASE  " + 
		"                 WHEN met = 'usable_file_mb' THEN value  " + 
		"               END usable_file_mb  " + 
		"        FROM   (SELECT metric_column AS Met,  " + 
		"                       column_label,  " + 
		"                       value,  " + 
		"                       metric_name  " + 
		"                FROM   mgmt$metric_current  " + 
		"                WHERE  Lower(target_name) LIKE '%nprac%'  " + 
		"                       AND metric_column IN ( 'total_mb', 'usable_file_mb' )  " + 
		"                       AND metric_name = 'DiskGroup_Usage'  " + 
		"                       AND key_value = 'DATA1'  " + 
		"                ORDER  BY metric_name,  " + 
		"                          metric_column))");	 

spaceList4 = db.resultSetToListOfList("SELECT 100 - ROUND(SUM(FREE_MB)/SUM(TOTAL_MB)*100) USED,ROUND(SUM(FREE_MB)/SUM(TOTAL_MB)*100) PFREE, ROUND(SUM(TOTAL_MB/1024/1024),2)TOTAL, ROUND(SUM(FREE_MB/1024/1024),2) || ' TB' FREE, ROUND(SUM(TOTAL_MB/1024/1024),2)- ROUND(SUM(FREE_MB/1024/1024),2) || ' TB' USED FROM V$ASM_DISKGROUP@DRRAC WHERE NAME IN ('ORCL_DATA1','ORCL_DATA','ORCL_MASTER')");
	  db.endConnection();
    	
      prodRAC = new CartesianChartModel();
      setProdRAC(prodRAC);
      
      drRAC = new CartesianChartModel();
      setDrRAC(drRAC);
      
      procServer = new CartesianChartModel();
      setProcServer(procServer);
      
      scrubServer = new CartesianChartModel();
      setScrubServer(scrubServer);
      
    }  
  
  
	public CartesianChartModel getDrRAC() {
		return drRAC;
	}

	public void setDrRAC(CartesianChartModel drRAC) {
	
		BarChartSeries used = new BarChartSeries();
    	BarChartSeries free = new BarChartSeries(); 
    	used.setLabel("Used " + spaceList4.get(1).get(4));
    	free.setLabel("Free " + spaceList4.get(1).get(3));
    	used.set("", Integer.parseInt( spaceList4.get(1).get(0)));  
    	free.set("", Integer.parseInt( spaceList4.get(1).get(1)));
    	
    	drRAC.addSeries(used);
    	drRAC.addSeries(free);
	}

	public CartesianChartModel getProcServer() {
		return procServer;
	}

	public void setProcServer(CartesianChartModel procServer) {
		BarChartSeries used = new BarChartSeries();
    	BarChartSeries free = new BarChartSeries(); 
  
    	      
		if(spaceList.size() > 0){
			for(int i=1; i < spaceList.size();i++)
			{
				if(spaceList.get(i).get(0).equalsIgnoreCase("PROC"))
				{
				  	used.setLabel("Used "+ spaceList.get(i).get(3));
			    	free.setLabel("Free "+spaceList.get(i).get(4));
					used.set("",Double.parseDouble(spaceList.get(i).get(1)));
					free.set("",Double.parseDouble(spaceList.get(i).get(2)));
				}
			}
		}
    	
    	//used.set("", 85);  
    	//free.set("", 15);
    	
    	procServer.addSeries(used);
    	procServer.addSeries(free);
	}

	public CartesianChartModel getScrubServer() {
		return scrubServer;
	}

	public void setScrubServer(CartesianChartModel scrubServer) {
		BarChartSeries used = new BarChartSeries();
    	BarChartSeries free = new BarChartSeries(); 
    	used.setLabel("Used");
    	free.setLabel("Free");
    	
    	
    	if(spaceList.size() > 0){
			for(int i=1; i < spaceList.size();i++)
			{
				if(spaceList.get(i).get(0).equalsIgnoreCase("SCRUB"))
				{
				  	used.setLabel("Used "+ spaceList.get(i).get(3));
			    	free.setLabel("Free "+spaceList.get(i).get(4));
					used.set("",Double.parseDouble(spaceList.get(i).get(1)));
					free.set("",Double.parseDouble(spaceList.get(i).get(2)));
				}
			}
		}
    	//used.set("", 85);  
    	//free.set("", 15);
    	
    	scrubServer.addSeries(used);
    	scrubServer.addSeries(free);
	}

	public void setProdRAC(CartesianChartModel prodRAC) {
		
		
		
		
		BarChartSeries used = new BarChartSeries();
    	BarChartSeries free = new BarChartSeries(); 
     	used.setLabel("Used " + spaceList3.get(1).get(4));
    	free.setLabel("Free " + spaceList3.get(1).get(3));
       	used.set("", Integer.parseInt( spaceList3.get(1).get(0)));  
    	free.set("", Integer.parseInt( spaceList3.get(1).get(1)));
     
    	
    	prodRAC.addSeries(used);
		prodRAC.addSeries(free);
	}  
	
    public CartesianChartModel getProdRAC() {  
        return prodRAC;  
    }  
}   