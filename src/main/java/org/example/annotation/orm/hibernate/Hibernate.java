package org.example.annotation.orm.hibernate;

import org.example.annotation.orm.hibernate.annotations.Column;
import org.example.annotation.orm.hibernate.annotations.PrimaryKey;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Hibernate<T> {
  private Connection connection;

  private AtomicLong id = new AtomicLong(0);

  public static <T> Hibernate<T> getConnection() throws SQLException {
    return new Hibernate<>();
  }

  private Hibernate() throws SQLException {
    this.connection = DriverManager.getConnection("jdbc:h2:C:\\Test\\practice1", "sa", "");
  }

  public void write(T t) throws IllegalAccessException, SQLException {
    Class<? extends Object> clazz = t.getClass();
    Field primarKey = null;
    List<Field> columns = new ArrayList<>();
    Field[] fields = clazz.getDeclaredFields();
    StringJoiner joiner = new StringJoiner(",");
    for (Field field : fields) {
      field.setAccessible(true);
      if (field.isAnnotationPresent(PrimaryKey.class)) {
        primarKey = field;
        System.out.println("Primary key is: " + field.getName() + " and value is: " + field.get(t));
      } else if (field.isAnnotationPresent(Column.class)) {
        joiner.add(field.getName());
        columns.add(field);
        System.out.println("Column name is: " + field.getName() + " and value is: " + field.get(t));
      }
    }
    int number = columns.size() + 1;
    String questionMarks = IntStream.range(0,number).mapToObj(e->"?").collect(Collectors.joining(","));
    String sql = "INSERT INTO " + clazz.getSimpleName() + " (" + primarKey.getName() + "," + joiner.toString() + ") VALUES ("+ questionMarks +")";
    PreparedStatement preparedStatement = connection.prepareStatement(sql);
    if(primarKey.getType().equals(long.class)){
     preparedStatement.setLong(1, id.incrementAndGet());
    }
    int index = 2;
    for (Field field: fields){
      if(field.getType().equals(int.class)){
        preparedStatement.setInt(index++, field.getInt(t));
      }else if(field.getType().equals(String.class)){
        preparedStatement.setString(index++, (String) field.get(t));
      }
    }
    preparedStatement.executeUpdate();
  }

  public T read(Class<T> transactionHistoryClass, long i) throws SQLException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
    Field[] fields = transactionHistoryClass.getDeclaredFields();
    Field pKey = null;
    for(Field field: fields){
      if(field.isAnnotationPresent(PrimaryKey.class)){
        pKey = field;
        break;
      }
    }
    String sql = "SELECT * FROM " + transactionHistoryClass.getSimpleName() + " WHERE " +pKey.getName() + " = ?";
    PreparedStatement preparedStatement = connection.prepareStatement(sql);
    preparedStatement.setLong(1, i);
    ResultSet resultSet = preparedStatement.executeQuery();
    resultSet.next();
    T t = transactionHistoryClass.getConstructor().newInstance();
    long transactionId = resultSet.getInt(pKey.getName());
    pKey.setAccessible(true);
    pKey.set(t, transactionId);
    //All the fields need to be checked for demo purpose only two fields are checked
    for(Field field: fields){
      if(field.isAnnotationPresent(Column.class)){
        field.setAccessible(true);
        if(field.getType().equals(int.class)){
          field.set(t, resultSet.getInt(field.getName()));
        }else if(field.getType().equals(String.class)){
          field.set(t, resultSet.getString(field.getName()));
        }
      }
    }
    return t;
  }
}
