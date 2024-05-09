package com.test.inventoryfood.model.service;

import com.test.inventoryfood.model.entity.Product;

import java.util.List;
public interface IProductService {

    public List<Product> findAll();

    public Product findById(Long id);

    public Product save(Product product);

    public void delete (Long id);

}
