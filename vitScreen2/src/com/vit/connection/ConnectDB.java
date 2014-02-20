package com.vit.connection;

import java.sql.*;
import java.util.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ConnectDB{

	protected Connection conn;
	protected ResultSet rst;
	protected Statement stmt;

	private static final Logger logger = LoggerFactory.getLogger(ConnectDB.class);
	com.vit.resources.ConfigurationManager CM = new com.vit.resources.ConfigurationManager();
	protected String execModule;

	/** Creates a new instance of ConnectDB */
	public ConnectDB(){

	}

	
	 public Connection getConnection(){
		Context initContext;
		Connection conn = null;
		try {
			initContext = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			DataSource ds = (DataSource)envContext.lookup("jdbc/datadashboard");
			conn = ds.getConnection();
		} catch (NamingException e) {
			logger.error(e.getMessage() +" Connection Failed Naming Exception");
		} catch (SQLException e) {
			logger.error(e.getMessage() +" Connection Pool Failed SQL Exception");
		}
		return conn;
	}
	
	 public Connection getConnectionCloud(){
			Context initContext;
			Connection conn = null;
			try {
				initContext = new InitialContext();
				Context envContext  = (Context)initContext.lookup("java:/comp/env");
				DataSource ds = (DataSource)envContext.lookup("jdbc/cloud12");
				conn = ds.getConnection();
			} catch (NamingException e) {
				logger.error(e.getMessage() +" Connection Failed Naming Exception Cloud");
			} catch (SQLException e) {
				logger.error(e.getMessage() +" Connection Pool Cloud Failed SQL Exception");
			}
			return conn;
		}
	 
	public Connection getConnectionWithoutPool(){
		String url      = "jdbc:oracle:thin:@"+CM.getProperty("default_ServerIP")+":"+CM.getProperty("default_TCP_Port")+":"+CM.getProperty("default_SID");
		Connection c = null;
		try {
			Class.forName( CM.getProperty("Oracle_Driver"));
			c = DriverManager.getConnection(url,CM.getProperty("default_Schema"),CM.getProperty("default_Pwd"));
		} catch (ClassNotFoundException e) {
			logger.error(e.getMessage() +" Driver Class not found Without Pool");
		} catch (SQLException e) {
			logger.error(e.getMessage() +" Connection Failed Without Pool");
		}
		return c;
	}

	public Connection getConnection(String server, String tcp,String sid, String schema){
		String url      = "jdbc:oracle:thin:@"+server+":"+tcp+":"+sid;
		Connection c = null;
		try {
			Class.forName( CM.getProperty("Oracle_Driver"));
			c = DriverManager.getConnection(url,schema,CM.getProperty("default_Pwd"));
		} catch (ClassNotFoundException e) {
			logger.error(e.getMessage() +" Driver Class not found Without Pool");
		} catch (SQLException e) {
			logger.error(e.getMessage() +" Connection Failed");
		}
		return c;
	}
	
	public void initializeWithoutPool(){
		try{
			conn = getConnectionWithoutPool();
			stmt = conn.createStatement();
		}catch(Exception e){
			logger.error(e.getMessage() +" Initialize dashboard Connection Without Pool");
		}
	}
	
	public void initialize(){
		try{
			conn = getConnection();
			stmt = conn.createStatement();
		}catch(Exception e){
			logger.error(e.getMessage() +" Initialize dashboard Connection");
		}
	}
	
	public void initializeCloud(){
		try{
			conn = getConnectionCloud();
			stmt = conn.createStatement();
		}catch(Exception e){
			logger.error(e.getMessage() +" Initialize dashboard Cloud Connection");
		}
	}

	public void initialize(String server, String tcp,String sid, String schema){
		try{
			conn = getConnection(server,tcp,sid,schema);
			stmt = conn.createStatement();
		}catch(Exception e){
			logger.error(e.getMessage() +" Initialize dashboard Connection ");
		}
	}
	
	public List<List<String>> resultSetToListOfList(String sql) {
		try {
			Statement stm = this.conn.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			ResultSetMetaData metaData = rs.getMetaData();
			int numberOfColumns = metaData.getColumnCount();
			List<String> columnNames = new ArrayList<String>(numberOfColumns);
			// Get the column names
			for (int column = 0; column < numberOfColumns; column++) {
				columnNames.add(metaData.getColumnLabel(column + 1));
			}
			// Get all rows, but first make the first row the column names
			List<List<String>> rows = new ArrayList<List<String>>();
			rows.add(columnNames);
			while (rs.next()) {
				List<String> newRow = new ArrayList<String>(numberOfColumns);
				for (int i = 1; i <= numberOfColumns; i++) {
					if(rs.getObject(i)!=null && rs.getObject(i)!=""){
						newRow.add(rs.getObject(i).toString());
					}
					else{
						newRow.add("");
					}
				}
				rows.add(newRow);
			}
			if(rs!=null || !rs.isClosed()) {rs.close();}
			stm.close();
			return rows;

		} catch (Exception e) {
			logger.error(e.getMessage() +" Result Set To List OF List "+sql);
			return null;
		}
	}

	public void endConnection(){
		try{
			if (rst != null ) { rst.close();}
			if (conn!= null ){conn.close();}
			if (stmt!= null ){stmt.close();}
		} catch(Exception e){
			logger.error(e.getMessage()+" EndConnection");
		}
	}

	public String executeDML(String Query){
		if(Query!=null && Query!=""){
			try{
				PreparedStatement pstatement = null;
				pstatement = conn.prepareStatement(Query);
				pstatement.executeUpdate();
				pstatement.close();
				return "1";
			}catch(Exception e){
				logger.error(e.getMessage()+" "+Query+" "+" Update Query");
				return e.getMessage();
			}       
		}  
		else{
			return "Empty Query";
		}
	}	
}
