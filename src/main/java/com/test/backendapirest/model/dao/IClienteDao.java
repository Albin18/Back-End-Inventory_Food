package com.test.backendapirest.model.dao;

import com.test.backendapirest.model.entity.Cliente;
import org.springframework.data.repository.CrudRepository;

public interface IClienteDao extends CrudRepository <Cliente, Long> {


}
