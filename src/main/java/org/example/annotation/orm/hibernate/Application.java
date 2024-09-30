package org.example.annotation.orm.hibernate;

public class Application {

  public static void main(String[] args) throws Exception {
    TransactionHistory t1 = new TransactionHistory(123456, "John Doe", "Deposit", 1000);
    TransactionHistory t2 = new TransactionHistory(123457, "Alice Den", "Credit", 2000);
    TransactionHistory t3 = new TransactionHistory(123458, "Tom Doe", "Deposit", 3000);
    TransactionHistory t4 = new TransactionHistory(123459, "Jerry Den", "Credit", 5000);

    Hibernate<TransactionHistory> hibernate = Hibernate.getConnection();
    hibernate.write(t1);
    TransactionHistory read = hibernate.read(TransactionHistory.class, 1);
    System.out.println(read);
  }
}
