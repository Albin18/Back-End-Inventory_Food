package com.test.inventoryfood.model.service;

import com.test.inventoryfood.model.dao.IProductDao;
import com.test.inventoryfood.model.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class ProductServiceImpl implements IProductService{
    @Autowired
    private IProductDao productDao;
    @Override
    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return (List<Product>) productDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Product findById(Long id) {
        return productDao.findById(id).orElse(null); //boolean, if it finds the id, return the client if not return null
    }

    @Override
    @Transactional
    public Product save(Product product) {
        return productDao.save(product);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        productDao.deleteById(id);
    }
}
