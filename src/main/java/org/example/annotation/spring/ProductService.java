package org.example.annotation.spring;

import org.example.annotation.spring.annotations.Autowired;
import org.example.annotation.spring.annotations.Component;

import java.util.List;

@Component
public class ProductService {

  @Autowired
  private ProductRepository repo;

  public List<Product> getFinalPrice(List<Product> items){
    List<Product> list = repo.getPrice(items);

    for (Product product : list) {
      product.setPrice(product.getPrice() * (100 - product.getDiscount())/100);

      System.out.println("Price of "+ product.getName()+" after " + product.getDiscount()+ "% discount is "+product.getPrice()+ "$.");
    }

    return list;

  }

}