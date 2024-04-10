package com.test.backendapirest.controller;

import com.test.backendapirest.model.entity.Cliente;
import com.test.backendapirest.model.service.IClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;


import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin(origins= {"http://localhost:4200"})
@RestController
@RequestMapping("/api")
public class ClienteRestController {
@Autowired
private IClienteService clienteService;

@GetMapping("/clientes")
    public List<Cliente> index(){
        return clienteService.findAll();
    }

    @GetMapping("/clientes/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> show(@PathVariable Long id){

    Cliente cliente = null;
        Map<String, Object> response = new HashMap<>();
    try{
        cliente = clienteService.findById(id);
    } catch(DataAccessException e){
        response.put("mensaje", "Error al realizar la consulta en la base de datos");
        response.put("Error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    if(cliente == null){
        response.put("mensaje", "El cliente ID: ".concat(id.toString().concat(" No existe en la base de datos!")));
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<Cliente>(cliente, HttpStatus.OK);
    }


    @PostMapping("/clientes")
    //@ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> create(@Valid @RequestBody Cliente cliente, BindingResult result){
        Cliente clienteNew = null;
        Map<String,Object> response = new HashMap<>();
        if (cliente.getNombre() == null || cliente.getNombre().isEmpty() || cliente.getApellido() == null ||
                cliente.getApellido().isEmpty() || cliente.getEmail() == null || cliente.getEmail().isEmpty()) {
            response.put("mensaje", "Error: Campos Vacios");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    try {
        clienteNew = clienteService.save(cliente);
    } catch (DataAccessException e){
        if(cliente.getEmail() != null){
            response.put("mensaje", "Error: Este email ya esta registrado");
            return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje", "Error al realizar la creacion a la base de datos!");
      //  response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    response.put("mensaje", "El cliente ha sido creado con exito!");
    response.put("cliente", clienteNew);
    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @PutMapping("/clientes/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody Cliente cliente, BindingResult result, @PathVariable long id){

    Cliente clienteActual = clienteService.findById(id);

        Cliente clienteUpdate=null;

        Map<String,Object> response = new HashMap<>();
        if (cliente.getNombre() == null || cliente.getNombre().isEmpty() || cliente.getApellido() == null ||
                cliente.getApellido().isEmpty() || cliente.getEmail() == null || cliente.getEmail().isEmpty()) {
            response.put("mensaje", "Error: Campos Vacios");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        if(clienteActual == null){
            response.put("mensaje", "Error: no se pudo editar el cliente ID: ".concat(Long.toString(id).concat("No existe en la base de datos!")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
try{
    clienteActual.setApellido(cliente.getApellido());
    clienteActual.setNombre(cliente.getNombre());
    clienteActual.setEmail(cliente.getEmail());
    clienteActual.setCreateAt(cliente.getCreateAt());

    clienteUpdate = clienteService.save(clienteActual);
} catch(DataAccessException e){
    response.put("mensaje", "Error al actualizar el cliente en la base de datos");
    response.put("Error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
}
    response.put("mensaje","El cliente ha sido cambiado con exito!" );
    response.put("cliente", clienteUpdate);
    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/clientes/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();

        Cliente cliente = clienteService.findById(id);

        try {
            clienteService.delete(id);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al eliminar el cliente de la base de datos!");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if(cliente == null){
            response.put("mensaje", "El cliente ID: ".concat(id.toString().concat(" No existe en la base de datos!")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        response.put("mensaje", "El cliente se eliminó con éxito!");
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }

}
