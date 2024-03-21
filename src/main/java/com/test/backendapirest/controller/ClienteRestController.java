package com.test.backendapirest.controller;

import com.test.backendapirest.model.entity.Cliente;
import com.test.backendapirest.model.service.IClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public Cliente show(@PathVariable Long id){
    return clienteService.findById(id);
    }

    @PostMapping("/clientes")
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente create(@RequestBody Cliente cliente){
    return clienteService.save(cliente);
    }

    @PutMapping("/clientes/{id}")
public Cliente update(@RequestBody Cliente cliente, @PathVariable long id){
    Cliente clienteActual = clienteService.findById(id);

    clienteActual.setApellido(cliente.getApellido());
    clienteActual.setNombre(cliente.getNombre());
    clienteActual.setEmail(cliente.getEmail());

    return clienteService.save(clienteActual);
    }

    @DeleteMapping("/clientes/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
    clienteService.delete(id);
    }

}
