package org.example.annotation.spring;

import java.util.ArrayList;
import java.util.List;

public class Application {
  public static void main(String[] args) {
    ApplicationContext context = new ApplicationContext(AppConfig.class);
    ProductService productService =context.getBean(ProductService.class);
    List<Product> items= new ArrayList<>();
    items.add(new Product("book", 10));
    items.add(new Product("pencil", 20));
    items.add(new Product("sharper", 20));
    List<Product> finalPrice = productService.getFinalPrice(items);
    for(Product product: finalPrice){
      System.out.println(product.getName() + " : " + product.getDiscount());
    }
  }
}
