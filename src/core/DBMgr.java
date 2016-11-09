package core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DBMgr {
    public static String PSTMT_TYPE_STRING = "String";
    public static String PSTMT_TYPE_INT = "Int";
    
	public static void main(String[] a) throws Exception {
	    List names = new ArrayList();
	    List types = new ArrayList();
	    List contents = new ArrayList();
	    names.add( "title" );
	    names.add( "content" );
	    names.add( "id" );
	    types.add( PSTMT_TYPE_STRING );
	    types.add( PSTMT_TYPE_STRING );
	    types.add( PSTMT_TYPE_INT );
	    contents.add( "title22222" );
	    contents.add( "content22222" );
	    contents.add( "33" );
	    
	    //executeInsert("main_article", names, types, contents);
	    executeUpdate("main_article", names, types, contents);
	    
//	    Connection conn = DBMgr.getConnection();
//	    String sql = "update main_article set title=?, content=? where id=?";
//	    PreparedStatement pstmt = conn.prepareStatement( sql );
//	    pstmt.setString( 1, "new" );
//	    pstmt.setString( 2, "再给我" );
//	    pstmt.setInt( 3, 33 );
//	    pstmt.execute();
//	    conn.close();
	    
		//DBMgr.execute("insert into sys_role values(5,'555','55555')");
//		List list = (DBMgr.executeQuery("select * from main_article"));
//		TableObject tb = (TableObject)list.get(0);
//		String s1 = tb.get("title");
//		System.out.println(s1);
//		
//		String s2 = new String(s1.getBytes("ISO-8859-1"),"GB2312"); 
//		System.out.println(s2);	
//		
//	    String s3 = new String(s1.getBytes("ISO-8859-1"),"UTF-8"); 
//	    System.out.println(s3); 
	}	
	
	private DBMgr() {}
	private static String DRIVER_CLASS_NAME;
	private static String URL_NAME;
	static {
		PropMgr propMgr = new PropMgr(PathMgr.getClassPath() + "jdbc.properties");
		String driverClass = propMgr.getValue("jdbc.driverClassName");
		String url = propMgr.getValue("jdbc.url");
		String username = propMgr.getValue("jdbc.username");
		String password = propMgr.getValue("jdbc.password");
		DRIVER_CLASS_NAME = driverClass;
		URL_NAME = url + "&user=" + username + "&password=" + password;
	}

	public static void execute(String sql){
	    System.out.println( sql );
	    
		try{
			Connection conn = DBMgr.getConnection();
			Statement stmt = conn.createStatement();
			stmt.execute(sql);
			stmt.close();
			conn.close();
		}catch(SQLException e){
			e.printStackTrace();
		}		
	}
	
	/**
	 * in the meantime it supports only string and int insert
	 * @param table
	 * @param columnNames
	 * @param columnTypes
	 * @param columnContents
	 */
	public static void executeInsert(String table, List columnNames, List columnTypes, List columnContents)
	{
	    String columnNameStr = "";
	    String columnContentStr = "";
	    
	    for ( int i=0; i<columnNames.size(); i++ )
	    {
	        columnNameStr += (String) columnNames.get(i);
	        columnContentStr += "?";
	        
	        if ( i < columnNames.size()-1 )
	        {
	            columnNameStr += ",";
	            columnContentStr += ",";
	        }	            
	    }
	    
	    //insert into table (column1,column2) values (?,?)
	    String sql = "insert into " + table + " (" + columnNameStr + ") values (" + columnContentStr + ")";
	    System.out.println( sql );
	    
	    Connection conn = DBMgr.getConnection();
	    try
        {
            PreparedStatement pstmt = conn.prepareStatement( sql );
            
            for ( int i=0; i<columnTypes.size(); i++ )
            {
                String columnType = (String)columnTypes.get(i);
                if( columnType.equalsIgnoreCase( "String" ) )
                    pstmt.setString( i+1, (String) columnContents.get(i) );
                else if( columnType.equalsIgnoreCase( "Int" ) )
                    pstmt.setInt( i+1, Integer.parseInt( (String) columnContents.get(i) ) );
                else // by default
                    pstmt.setString( i+1, (String) columnContents.get(i) );
            }            
            
            pstmt.execute();
            pstmt.close();
            conn.close();
        }
        catch ( SQLException e )
        {
            e.printStackTrace();
        }
	}
	
	
	/**
	 * The last parameter preserved for where clause - key=value
	 * See executeInsert method
	 * @param table
	 * @param columnNames
	 * @param columnTypes
	 * @param columnContents
	 */
    public static void executeUpdate(String table, List columnNames, List columnTypes, List columnContents)
    {
        String columnNameStr = "";
        
        for ( int i=0; i<columnNames.size()-1; i++ )
        {
            columnNameStr += (String) columnNames.get(i) + "=?";
            
            if ( i < columnNames.size()-2 )
            {
                columnNameStr += ",";
            }
        }
        
        //update table set column1=?, column2=? where columnLast=?
        String sql = "update " + table + " set " + columnNameStr + " where " + 
                       ( (String) columnNames.get( columnNames.size()-1 ) ) + "=?";
        System.out.println( sql );
        
        Connection conn = DBMgr.getConnection();
        try
        {
            PreparedStatement pstmt = conn.prepareStatement( sql );
            
            for ( int i=0; i<columnTypes.size(); i++ )
            {
                String columnType = (String)columnTypes.get(i);
                if( columnType.equalsIgnoreCase( "String" ) )
                    pstmt.setString( i+1, (String) columnContents.get(i) );
                else if( columnType.equalsIgnoreCase( "Int" ) )
                    pstmt.setInt( i+1, Integer.parseInt( (String) columnContents.get(i) ) );
                else // by default
                    pstmt.setString( i+1, (String) columnContents.get(i) );
            }            
            
            pstmt.execute();
            pstmt.close();
            conn.close();
        }
        catch ( SQLException e )
        {
            e.printStackTrace();
        }
    }
	
	
	public static List executeQuery(String sql){
	    System.out.println( sql );
	    
		List tbList = new ArrayList();
		
		try{
			Connection conn = DBMgr.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);		
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();		
			
			List keys = new ArrayList();
			for(int i=1;i<=columnCount;i++){
				keys.add(rsmd.getColumnName(i));
			}		
			while(rs.next()){
				List values = new ArrayList();
				for(int i=1;i<=columnCount;i++){
					values.add(rs.getString(i));
				}
				TableObject tb = new TableObject();
				tb.setKeys(keys);
				tb.setValues(values);
				tbList.add(tb);
			}
			
			stmt.close();
			conn.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
		
		return tbList;
	}
	
	public static class TableObject{
		private List keys = new ArrayList();
		private List values = new ArrayList();
		public String get(String key){
			return (String)values.get(keys.indexOf(key));
		}
		public void set(String key, String value){
			values.set(keys.indexOf(key), value);
		}
		public void setKeys(List keys) {
			this.keys = keys;
		}
		public void setValues(List values) {
			this.values = values;
		}
		public String toString(){
			return "key="+keys+"; values="+values;
		}
	}

	public static Connection getConnection() {
		Connection conn = null;
		try {
			Class.forName(DRIVER_CLASS_NAME).newInstance();
			conn = DriverManager.getConnection(URL_NAME);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
}