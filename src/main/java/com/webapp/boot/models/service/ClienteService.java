package com.webapp.boot.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.webapp.boot.models.dao.ClienteDaoInterface;
import com.webapp.boot.models.dao.ProductoDaoInterface;
import com.webapp.boot.models.entity.Cliente;
import com.webapp.boot.models.entity.Producto;

import jakarta.transaction.Transactional;

@Repository
public class ClienteService implements ClienteServiceInterface {

	@Autowired
	private ClienteDaoInterface clienteDao;
	
	@Autowired
	private ProductoDaoInterface productoDao;
	
	@Override
	@Transactional
	public List<Cliente> findAll() {
		return (List<Cliente>) clienteDao.findAll();
	}

	@Override
	@Transactional
	public Cliente findOne(Long id) {
		return clienteDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void save(Cliente cliente) {
		clienteDao.save(cliente);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		clienteDao.deleteById(id);
	}

	@Override
	@Transactional
	public Page<Cliente> findAll(Pageable pageable) {
		return clienteDao.findAll(pageable);
	}
	
	public long total() {
        return clienteDao.count();
    }

	@Override
	@Transactional
	public List<Producto> findByNombre(String term) {
		return productoDao.findByNombre(term);
	}
}
