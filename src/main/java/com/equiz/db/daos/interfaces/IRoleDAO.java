package com.equiz.db.daos.interfaces;

import java.util.List;

import com.equiz.db.dtos.Role;

public interface IRoleDAO {
	List<Role> find();
	Role find(Long id);
}
