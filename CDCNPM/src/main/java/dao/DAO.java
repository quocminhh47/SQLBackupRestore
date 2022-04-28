package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import model.BackupInfo;

public class DAO{
   // static ResourceBundle resourceBundle = ResourceBundle.getBundle("db");

    public Connection getConnection(String dbName, String user,  String password) {
        try {
            Class.forName(("com.microsoft.sqlserver.jdbc.SQLServerDriver"));
            String url = "jdbc:sqlserver://localhost:1433;databasename="+dbName;
            System.out.println("Connected to " + dbName);
            return DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    
	@SuppressWarnings("finally")
	public List<String> getListDB(String dbName, String user,  String password) {
        Connection connection = getConnection("master", user, password);
        if (null == connection) {
            return null;
        }
        List<String> listDB = new ArrayList<String>();
        String sql = "SELECT        name \n"
        		+ "	FROM      sys.databases \n"
        		+ "	WHERE    (database_id >= 5) AND (NOT (name LIKE N'ReportServer%')) \n"
        		+ "	ORDER BY NAME";
        //System.out.println(sql);
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while(rs.next()) {
            	String s = rs.getString("name");
            	listDB.add(s);
            }
        } catch (SQLException e) {
        	
            e.printStackTrace();

        } finally {
        	  connection = getConnection("master", user, password);
        	  return listDB;
        }
    }

    //drop dev
	@SuppressWarnings("finally")
	public Integer dopDev(String dbName, String user,  String password) {
        Connection connection = getConnection(dbName, user, password);
        String sql = "EXEC sp_dropdevice  'DEV_"+dbName+"'";
        System.out.println(sql);
        Integer count = 0;
        if (null == connection) {
            count= 0;
        }
        
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            count = statement.executeUpdate();
            count = 1;
        } catch (SQLException e) {
        	count = 0;
            e.printStackTrace();

        } finally {
        	connection = getConnection("master", user, password);
        	return count;
        }
    }
	
	@SuppressWarnings("finally")
	public Integer dopDevWithDel(String dbName, String user,  String password) {
        Connection connection = getConnection(dbName, user, password);
        String sql = "EXEC sp_dropdevice  'DEV_"+dbName+"'  , 'delfile'";
        System.out.println(sql);
        Integer count = 0;
        if (null == connection) {
            count = 0;
        }
        
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            count = statement.executeUpdate();
            count = 1;
        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
        	connection = getConnection("master", user, password);
        	return count;
        }
    }
	
	
	//create new device
	@SuppressWarnings("finally")
	public Integer createDev(String dbName, String user,  String password) {
        Connection connection = getConnection(dbName, user, password);
        String sql = "USE "+dbName+" \n"
        		+ "	EXEC sp_addumpdevice 'disk', 'DEV_"+dbName+"', 'E:\\SQL_BACKUP\\"+dbName+"_BK.bak'";
        System.out.println(sql);
        Integer count = 0;
        if (null == connection) {
            count = 0;
        }
      
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            count = statement.executeUpdate();
            count = 1;

        } catch (SQLException e) {
            e.printStackTrace();
            count = 0;

        } finally {
        	connection = getConnection("master", user, password);
        	return count;
        }
    }
	
	
	//Create db backup
	@SuppressWarnings("finally")
	public Integer createBackup(String dbName, String user,  String password) {
		 int count = 0;
        Connection connection = getConnection(dbName, user, password);
        String sql = "USE master \n"
        		+ "BACKUP DATABASE "+dbName+" TO DEV_"+dbName;
        System.out.println(sql);
        if (null == connection) {
             count = 0;
        }
       
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
             statement.executeUpdate();
             count = 1;

        } catch (SQLException e) {
            e.printStackTrace();
            count = 0;

        } finally {
        	connection = getConnection("master", user, password);
        	return count;
    }
	}
	
	@SuppressWarnings("finally")
	public Integer createBackup2(String dbName, String user,  String password) {
		 int count = 0;
        Connection connection = getConnection(dbName, user, password);
        String sql = "USE master \n"
        		+ "BACKUP DATABASE "+dbName+" TO DEV_"+dbName +"\t WITH INIT";
        System.out.println(sql);
        if (null == connection) {
             count = 0;
        }
       
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
             statement.executeUpdate();
             count = 1;

        } catch (SQLException e) {
            e.printStackTrace();
            count = 0;

        } finally {
        	connection = getConnection("master", user, password);
        	return count;
    }
	}
	
	//delete db backup from a date
	@SuppressWarnings("finally")
	public Integer delDbLog(String user, String pass, String date) {
        Connection connection = getConnection("master", user, pass);
        String sql = "EXECUTE master.dbo.xp_delete_file 0, 'E:\\SQL_BACKUP\', 'trn', '"+date+"', 1" ;
        System.out.println(sql);
        Integer count = 0;
        if (null == connection) {
            count =  0;
        }
     
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            count = statement.executeUpdate();
            count = 1;
        } catch (SQLException e) {
            e.printStackTrace();
            count=0;

        } finally {
        	connection = getConnection("master", user, pass);
        	return count;
        	
        }	
    }
	
	//restore db from file backup co san
	@SuppressWarnings("finally")
	public Integer restoreDbFromFile(String dbName, String user,  String password, String position) {
        Connection connection = getConnection(dbName, user, password);
        String sql = "USE [master] \n"
        		+ "	ALTER DATABASE "+dbName+" SET RECOVERY FULL ;  \n"
        		+ "	ALTER DATABASE "+dbName+" SET SINGLE_USER WITH ROLLBACK IMMEDIATE  \r\n"
        		+ "	USE tempdb \n"
        		+ "	RESTORE DATABASE "+dbName+" FROM  DEV_"+dbName+"  WITH FILE= "+position+", REPLACE \n"
        		+ "	ALTER DATABASE "+dbName+"  SET MULTI_USER" ;
      System.out.println(sql);
        
        int count = 0;
        if (null == connection) {
            count =  0;
        }
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            count = statement.executeUpdate();
            count = 1;
        } catch (SQLException e) {
            e.printStackTrace();
            count = 0 ;

        } finally {
       
        	connection = getConnection("master", user, password);
        	return count;
        }
        	
        }
    
	
	//restore db from time
	@SuppressWarnings({ "finally", "resource" })
	public Integer restoreDbFromTime(String dbName, String user,  String password, String time) throws SQLException {
        Connection connection = getConnection(dbName, user, password);
        String sql = "USE [master] ;  \n"
        		+ "	ALTER DATABASE "+dbName+" SET RECOVERY FULL ;  \n"
        		+ "	ALTER DATABASE "+dbName+" SET SINGLE_USER WITH ROLLBACK IMMEDIATE  \n"
        		+"  BACKUP LOG "+dbName+" TO DISK = 'E:\\SQL_BACKUP\\"+dbName+"_LOG.trn'  WITH  NORECOVERY, NO_TRUNCATE, INIT \n"
        		+ "	RESTORE DATABASE "+dbName+" FROM DISK = 'E:\\SQL_BACKUP\\"+dbName+"_BK.bak' WITH  NORECOVERY\r\n"
        		+ "	RESTORE DATABASE "+dbName+" FROM DISK = 'E:\\SQL_BACKUP\\"+dbName+"_LOG.trn' WITH STOPAT='"+time+"' \n"
        		+ "	ALTER DATABASE "+dbName+" SET MULTI_USER " ;
        String position = new DAO().getMaxPosition(user, password, dbName);
        String sql2 = "	RESTORE DATABASE "+dbName+" FROM  DEV_"+dbName+"  WITH FILE= "+position+" , RECOVERY , REPLACE";
        
        int count = 0;
        if (null == connection) {
            count =  0;
        }
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            count = statement.executeUpdate();
            count = 1;

        } catch (SQLException e) {
        	statement = connection.prepareStatement(sql2);
            count = statement.executeUpdate();
            count = 0;
            e.printStackTrace();
        

        } finally {
        	connection = getConnection("master", user, password);
        	return count;
        }
    }
	
	@SuppressWarnings("finally")
	public List<BackupInfo> getBackupList(String dbName, String user,  String password) {
		List<BackupInfo> listBackup = new ArrayList<BackupInfo>();
        Connection connection = getConnection(dbName, user, password);
        String sql = "SELECT    position, name, backup_start_date , user_name \n"
        		+ "	FROM  msdb.dbo.Backupset\r\n"
        		+ "   WHERE     database_name = '"+dbName+"' AND type='D' AND \n"
        		+ "     backup_set_id >= \r\n"
        		+ "        (  SELECT  MAX(backup_set_id) \r\n"
        		+ "        FROM msdb.dbo.backupset  \n"
        		+ "         WHERE database_name = '"+dbName+"' AND type='D'\n"
        		+ "                  AND position=1  \n"
        		+ "        )\r\n"
        		+ "    ORDER BY position DESC" ;
        //System.out.println(sql);
        if (null == connection) {
            return null;
        }
        Integer count = null;
        PreparedStatement statement = null;
        try {
        	 statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery();
             while(rs.next()) {
            	 BackupInfo bk = new BackupInfo();
            	 bk.setPosition(rs.getString("position"));
            	 bk.setName(rs.getString("name"));
            	 bk.setBackupDate(rs.getString("backup_start_date"));
            	 bk.setUser(rs.getString("user_name"));
            	 listBackup.add(bk);
             }
        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
        	connection = getConnection("master", user, password);
        	return listBackup;
        }
    }
	
	@SuppressWarnings("finally")
	public List<String> listDev( String user,  String password){
		List<String> listDevice = new ArrayList<String>();
        Connection connection = getConnection("master", user, password);
        String sql = "SELECT name  FROM sys.backup_devices" ;
        if (null == connection) {
            return null;
        }
        PreparedStatement statement = null;
        try {
        	 statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery();
             while(rs.next()) {
            	 String s = rs.getString("name");
            	 listDevice.add(s);
             }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;

        } finally {
        	connection = getConnection("master", user, password);
        	return listDevice;
        }
		 
	}
	
	@SuppressWarnings("finally")
	public Integer delDbBackupLog(String user, String pass, String dbName) {
        Connection connection = getConnection("master", user, pass);
        String sql = "EXECUTE master.dbo.xp_delete_file 0, 'E:\\SQL_BACKUP\\"+dbName+"_BK.bak'" ; // EXEC master.dbo.xp_delete_file 0, 'C:\Some Path\Backup file.bak' 
        System.out.println(sql);
        Integer count = 0;
        if (null == connection) {
            count = 0;
        }
        
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            count = statement.executeUpdate();
            count = 1;
        } catch (SQLException e) {
            e.printStackTrace();
            count=0;

        } finally {
        	connection = getConnection("master", user, pass);
        	return count;
        }
    }
	
	@SuppressWarnings("finally")
	public String getMaxPosition(String user, String pass, String dbName) {
		String p = "1"; 
        Connection connection = getConnection("master", user, pass);
        String sql = "SELECT  MAX( position) as 'position' \n"
        		+ "	FROM  msdb.dbo.Backupset \n"
        		+ "   WHERE     database_name = '"+dbName+"' AND type='D' AND "
        		+ "     backup_set_id >= \n"
        		+ "        (  SELECT  MAX(backup_set_id)  \n"
        		+ "        FROM msdb.dbo.backupset  \n"
        		+ "         WHERE database_name = '"+dbName+"' AND \n"
        		+ "		 type='D' \n"
        		+ "                  AND position=1  \n"
        		+ "        )" ; // EXEC master.dbo.xp_delete_file 0, 'C:\Some Path\Backup file.bak' 
        System.out.println(sql);
        Integer count = 0;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while(rs.next()) {
            	p = rs.getString("position");
            }
            return p;
        } catch (SQLException e) {
            e.printStackTrace();
            return "1";

        }
        finally {
        	connection = getConnection("master", user, pass);
		}
    }
	
	

    public static void main(String[] args) {
//        Test.update("ALTER DATABASE DemoVolley SET SINGLE_USER WITH ROLLBACK IMMEDIATE\n" +
//                "USE TEMPDB\n" +
//                "SELECT GETDATE()\n" +
//                "\n" +
//                "BACKUP LOG DemoVolley TO DISK = 'C:\\backup\\DemoVolley.trn'");
//    	Test.update("USE TEST  \n"
//    			//+ "	GO \n"
//    			+ "	EXEC sp_addumpdevice 'disk', 'DEV_TEST', 'E:\\SQL_BACKUP\\TEST_BK.bak'");
    	//System.out.println(new DAO().createDev("TEST"));
    	//new DAO().createDev("TEST");
    	//new DAO().createBackup("TEST");
    	//System.out.println(new DAO().restoreDbFromFile("TEST", "1"));
    	//new DAO().getBackupList("QLBH");
    	//;
    	System.out.println(new DAO().getMaxPosition("sa", "123", "QLBH"));
    }


}

