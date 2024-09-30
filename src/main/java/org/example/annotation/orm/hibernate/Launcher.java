package org.example.annotation.orm.hibernate;

import org.h2.tools.Server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Launcher {
  public static void main(String[] args) throws SQLException, ClassNotFoundException {
    Class.forName("org.h2.Driver");

    Connection conn = DriverManager.getConnection("jdbc:h2:C:\\Test\\practice1", "sa", "");

    System.out.println("Database created!");
    Server.main();
  }
}