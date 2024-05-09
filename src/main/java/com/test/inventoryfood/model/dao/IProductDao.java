package com.test.inventoryfood.model.dao;

import com.test.inventoryfood.model.entity.Product;
import org.springframework.data.repository.CrudRepository;

public interface IProductDao extends CrudRepository <Product, Long> {


}
