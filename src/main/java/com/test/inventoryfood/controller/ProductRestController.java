package com.test.inventoryfood.controller;

import com.test.inventoryfood.model.entity.Product;
import com.test.inventoryfood.model.service.IProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import java.util.*;

@CrossOrigin(origins= {"http://localhost:4200"})
@RestController
@RequestMapping("/api")
public class ProductRestController {
@Autowired
private IProductService productService;

@GetMapping("/products")
    public List<Product> index(){
        return productService.findAll();
    }

    @GetMapping("/products/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> show(@PathVariable Long id){

    Product product = null;
        Map<String, Object> response = new HashMap<>();
    try{
        product = productService.findById(id);
    } catch(DataAccessException e){
        response.put("mensaje", "Error al realizar la consulta en la base de datos");
        response.put("Error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    if(product == null){
        response.put("mensaje", "El cliente ID: ".concat(id.toString().concat(" No existe en la base de datos!")));
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<Product>(product, HttpStatus.OK);
    }


    @PostMapping("/products")
    //@ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> create(@Valid @RequestBody Product product, BindingResult result){
        Product productNew = null;
        Map<String,Object> response = new HashMap<>();
        if (product.getDescription() == null || product.getDescription().isEmpty() || product.getBrand() == null ||
                product.getBrand().isEmpty() || product.getQuantity() == 0 || product.getPrice() == 0 ||
                product.getCategory() == null  || product.getCategory().isEmpty() ) {
            response.put("mensaje", "Error: Campos Vacios");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    try {
        productNew = productService.save(product);
    } catch (DataAccessException e){
        response.put("mensaje", "Error al realizar la creacion a la base de datos!");
      //  response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    response.put("mensaje", "El producto ha sido agregado con exito!");
    response.put("producto", productNew);
    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody Product product, BindingResult result, @PathVariable long id){

    Product productActual = productService.findById(id);

        Product productUpdate=null;

        Map<String,Object> response = new HashMap<>();
        if (product.getDescription() == null || product.getDescription().isEmpty() || product.getBrand() == null ||
                product.getBrand().isEmpty() || product.getQuantity() == 0 || product.getPrice() == 0 ||
                product.getCategory() == null  || product.getCategory().isEmpty() ) {
            response.put("mensaje", "Error: Campos Vacios");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        if(productActual == null){
            response.put("mensaje", "Error: no se pudo editar el producto ID: ".concat(Long.toString(id).concat("No existe en la base de datos!")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
try{
    productActual.setDescription(product.getDescription());
    productActual.setBrand(product.getBrand());
    productActual.setQuantity(product.getQuantity());
    productActual.setPrice(product.getPrice());
    productActual.setCategory(product.getCategory());

    productUpdate = productService.save(productActual);
} catch(DataAccessException e){
    response.put("mensaje", "Error al actualizar el producto en la base de datos");
    response.put("Error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
}
    response.put("mensaje","El cliente ha sido cambiado con exito!" );
    response.put("product", productUpdate);
    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/products/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();

        Product product = productService.findById(id);

        try {
            productService.delete(id);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al eliminar el producto de la base de datos!");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if(product == null){
            response.put("mensaje", "El producto ID: ".concat(id.toString().concat(" No existe en la base de datos!")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        response.put("mensaje", "El producto se eliminó con éxito!");
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }

}
