package com.equiz.db.daos.interfaces;

import java.util.List;

import com.equiz.db.dtos.Role;

/**
 * DAO class for managing {@link Role} entities
 *
 * @author bkalika
 */
public interface IRoleDAO {
	List<Role> find();
	Role find(Long id);
}
