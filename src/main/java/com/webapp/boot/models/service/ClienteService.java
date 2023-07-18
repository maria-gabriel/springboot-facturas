package com.webapp.boot.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.webapp.boot.models.dao.ClienteDaoInterface;
import com.webapp.boot.models.dao.FacturaDaoInterface;
import com.webapp.boot.models.dao.ProductoDaoInterface;
import com.webapp.boot.models.entity.Cliente;
import com.webapp.boot.models.entity.Factura;
import com.webapp.boot.models.entity.Producto;

import jakarta.transaction.Transactional;

@Repository
public class ClienteService implements ClienteServiceInterface {

	@Autowired
	private ClienteDaoInterface clienteDao;
	
	@Autowired
	private ProductoDaoInterface productoDao;
	
	@Autowired
	private FacturaDaoInterface facturaDao;
	
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
		//return productoDao.findByNombreLikeIgnoreCase("%"+term+"%");
		return productoDao.findByNombre(term);
	}

	@Override
	@Transactional
	public void saveFactura(Factura factura) {
		facturaDao.save(factura);
		
	}

	@Override
	@Transactional
	public Producto findProductoById(Long id) {
		return productoDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Factura findFacturaById(Long id) {
		return facturaDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void deleteFactura(Long id) {
		facturaDao.deleteById(id);
		
	}
}
