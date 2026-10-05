package com.vikranth.ems.util;
import java.io.*; import java.sql.*; import java.util.Properties;
public final class ConnectionFactory {
 private static final Properties P=new Properties();
 static { try(InputStream in=ConnectionFactory.class.getClassLoader().getResourceAsStream("db.properties")){ if(in==null) throw new IllegalStateException("db.properties not found"); P.load(in); } catch(IOException e){ throw new ExceptionInInitializerError(e); } }
 private ConnectionFactory(){}
 public static Connection getConnection() throws SQLException { return DriverManager.getConnection(P.getProperty("db.url"),P.getProperty("db.username"),P.getProperty("db.password")); }
}