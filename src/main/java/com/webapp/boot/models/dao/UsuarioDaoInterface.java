package com.webapp.boot.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.webapp.boot.models.entity.Usuario;

public interface UsuarioDaoInterface extends CrudRepository<Usuario, Long> {

	public Usuario findByUsername(String username);
}
