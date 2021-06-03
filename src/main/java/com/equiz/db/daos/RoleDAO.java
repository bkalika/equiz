package com.equiz.db.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.equiz.db.Query;
import com.equiz.db.daos.interfaces.IRoleDAO;
import com.equiz.db.dtos.Role;
import com.equiz.db.dtos.Subject;
import com.equiz.db.dtos.User;

public class RoleDAO extends Role implements IRoleDAO {
	private static final long serialVersionUID = -1848507614736929041L;
	public static final Logger LOG = Logger.getLogger(RoleDAO.class);

	public Role find(Long id) {
		LOG.trace("Star tracing RoleDAO#find");
		Role role = null;
		try (Connection connection = ConnectionPool.getConnection()) {
			if (connection != null) {
				try (PreparedStatement statement = connection.prepareStatement(Query.SELECT_ROLE_BY_ID)) {
					connection.setAutoCommit(false);
					statement.setLong(1, id);
					statement.execute();
					ResultSet rs = statement.getResultSet();
					if (rs.next()) {
						role = new Role();
						role.setId(rs.getLong("id"));
						role.setName(rs.getString("name"));
					}
					rs.close();
					connection.commit();
				} catch (SQLException e) {
					LOG.error(e.getLocalizedMessage());
					connection.rollback();
				}
			}
		} catch (SQLException e) {
			LOG.error(e.getLocalizedMessage());
		}
		return role;
	}

	@Override
	public List<Role> find() {
		LOG.trace("Starting tracing RoleDAO#find");
		List<Role> roles = new ArrayList<>();
		try (Connection connection = ConnectionPool.getConnection()) {
			if (connection != null) {
				try (Statement statement = connection.createStatement()) {
					try (ResultSet rs = statement.executeQuery(Query.SELECT_ROLES)) {
						while (rs.next()) {
							roles.add(new Role(rs.getLong(1), rs.getString(2)));
						}
					} catch (SQLException e) {
						e.getSQLState();
					}
				} catch (SQLException e) {
					LOG.info(e.getSQLState());
				}
			}
		} catch (SQLException e) {
			LOG.info(e.getLocalizedMessage());
		}
		return roles;
	}

}
