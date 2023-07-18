package com.webapp.boot.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.webapp.boot.models.entity.Factura;

public interface FacturaDaoInterface extends CrudRepository<Factura, Long> {

}
