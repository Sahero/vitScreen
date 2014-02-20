package com.vit.servlet;

import java.io.IOException;
import java.sql.*;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vit.connection.ConnectDB;

  
public class ServletforWget extends HttpServlet  {
 
 
	private static final long serialVersionUID = -8627081318329160882L;
	private static final Logger logger = LoggerFactory.getLogger(ServletforWget.class);
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		logger.info("Process started at >>" + new Date().toString());
		com.vit.connection.ConnectDB db = new ConnectDB();
		
		Connection conn = null;

		Statement stmt = null;

		String spaceData = request.getParameter("SPACEDATA");
		String cpuData = request.getParameter("CPUDATA");
		String iostatData = request.getParameter("IOSTATDATA");
		String topData = request.getParameter ("TOPDATA");
		try {
			conn = db.getConnection("kamalpokhari","1521","orcl","test");
		} catch (Exception e) {
			logger.error(e.getMessage() +" connection issue");
		}
		//Data log for disk utilization
		if (spaceData != null) {

			String lines[] = spaceData.split("[\\r?\\n\\r]+");
			int count = 0;
			String SqlScript = "";
			for (String string : lines) {
				if (count > 0) {
					String fields[] = string.split(",");

					SqlScript = "insert into serverspace (servername, filesystem, totalsize, usedsize, availablesize) values ('"
							+ fields[0]
									+ "','"
									+ fields[1]
											+ "',"
											+ fields[2]
													+ "," + fields[3] + "," + fields[4] + ")";
					// System.out.println(SqlScript);
					try {
						stmt = conn.createStatement();
						stmt.execute(SqlScript);
						//            System.out.println(SqlScript);
					} catch (SQLException e) {
						logger.error(e.getMessage() +" spaceData issue ");
					}
				}
				count++;
			}

		}

		// Data log for CPU utilization


		if (cpuData != null) {

			String lines[] = cpuData.split("[\\r?\\n\\r]+");
			int count = 0;
			String SqlScript = "";
			for (String string : lines) {
				if (count > 0 ) {
					String fields[] = string.split(",");

					SqlScript = "insert into servercpu (  servername,cpu,userusage,nice,sys,iowait,irq,softirq,steal,guest,idle) values ('"
							+ fields[0] + "','"
							+ fields[1] + "',"
							+ fields[2] + "," 
							+ fields[3] + "," 
							+ fields[4] + "," 
							+ fields[5] + "," 
							+ fields[6] + "," 
							+ fields[7] + "," 
							+ fields[8] + "," 
							+ fields[9] + ","                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  
							+ fields[10] + ")";

					try {
						stmt = conn.createStatement();
						stmt.execute(SqlScript);
						// System.out.println(SqlScript);

					} catch (SQLException e) {
						logger.error(e.getMessage() +" cpuData issue ");
					}
				}
				count++;
			}

		} 

		// IOStata                            
		if (iostatData != null) {

			String lines[] = iostatData.split("[\\r?\\n\\r]+");
			int count = 0;
			String SqlScript = "";
			for (String string : lines) {
				if (count > 0 ) {
					String fields[] = string.split(",");

					SqlScript = "insert into serveriostat (servername, device, readrequestqueue, writerequestqueue, readrequest, writerequest, readsector, writesectors, averagesizerequest, averagequeuelength, await, svctm, utilizationpercent) values ('"
							+ fields[ 0] + "','"
							+ fields[ 1] + "',"
							+ fields[ 2] + "," 
							+ fields[ 3] + "," 
							+ fields[ 4] + "," 
							+ fields[ 5] + "," 
							+ fields[ 6] + "," 
							+ fields[ 7] + "," 
							+ fields[ 8] + "," 
							+ fields[ 9] + "," 
							+ fields[10] + "," 
							+ fields[11] + "," 
							+ fields[12] + ")";

					try {

						stmt = conn.createStatement();
						stmt.execute(SqlScript);
						// System.out.println(SqlScript); 

					} catch (SQLException e) {
						logger.error(e.getMessage() +" iostatData issue ");
					}
				}
				count++;
			}

		} 


		if (topData != null) {

			String lines[] = topData.split("[\\r?\\n\\r]+");
			int count = 0;
			String SqlScript = "";
			for (String string : lines) {
				if (count > 0 ) {
					String fields[] = string.split(",");

					SqlScript = "insert into servertop (servername, processid, osuser, priority, nicevalue, virtualimage, resmemory, sharedmemory, processstatus, cpuutilization, memoryutilization, cputime, command) values ('"
							+ fields[ 0] + "','"
							+ fields[ 1] + "','"
							+ fields[ 2] + "','" 
							+ fields[ 3] + "','" 
							+ fields[ 4] + "','" 
							+ fields[ 5] + "','" 
							+ fields[ 6] + "','" 
							+ fields[ 7] + "','" 
							+ fields[ 8] + "'," 
							+ fields[ 9] + "," 
							+ fields[10] + ",'" 
							+ fields[11] + "','" 
							+ fields[12] + "')";

					try {
						// System.out.println(SqlScript); 
						stmt = conn.createStatement();
						stmt.execute(SqlScript);
								

					} catch (SQLException e) {
						logger.error(e.getMessage() +" topData issue ");
					}
				}
				count++;
			}

		} 

		try {
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			logger.error(e.getMessage() +" connection close issue ");
		}
		logger.info("Process end at >>" + new Date().toString());
	}

}
