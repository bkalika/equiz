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
import com.equiz.db.daos.interfaces.IUserDAO;
import com.equiz.db.dtos.Role;
import com.equiz.db.dtos.User;

public class UserDAO extends User implements IUserDAO {
	private static final long serialVersionUID = -4441056017849375653L;
	public static final Logger LOG = Logger.getLogger(UserDAO.class);

	@Override
	public User findUserByLogin(String login) {
		LOG.trace("Start tracing UserDAO#findUserByLogin");
		User user = null;
		try (Connection connection = ConnectionPool.getConnection()) {
			if (connection != null) {
				try (PreparedStatement statement = connection.prepareStatement(Query.SELECT_USER_BY_NAME)) {
					connection.setAutoCommit(false);
					statement.setString(1, login);
					statement.execute();
					ResultSet resultSet = statement.getResultSet();
					if (resultSet.next()) {
						user = new User();
						user.setId(resultSet.getLong("id"));
						user.setName(resultSet.getString("name"));
						user.setPassword(resultSet.getString("password"));
						Role role = DAOFactory.getRoleDAO().find(resultSet.getLong("role_id"));
						user.setRole(role);
					}
					resultSet.close();
					connection.commit();
				} catch (SQLException e) {
					LOG.error(e.getLocalizedMessage());
					connection.rollback();
				}
			}
		} catch (SQLException ex) {
			LOG.error(ex.getLocalizedMessage());
		}
		return user;
	}
	
	@Override
	public User find(Long userId) {
		LOG.trace("Start tracing UserDAO#find");
		User user = new User();
		try (Connection connection = ConnectionPool.getConnection()) {
			if (connection != null) {
				try (PreparedStatement statement = connection.prepareStatement(Query.SELECT_USER_BY_ID)) {
					connection.setAutoCommit(false);
					statement.setLong(1, userId);
					statement.execute();
					ResultSet resultSet = statement.getResultSet();
					if (resultSet.next()) {
						user.setId(resultSet.getLong("id"));
						user.setName(resultSet.getString("name"));
						user.setPassword(resultSet.getString("password"));
						Role role = DAOFactory.getRoleDAO().find(resultSet.getLong("role_id"));
						user.setRole(role);
					}
					resultSet.close();
					connection.commit();
				} catch (SQLException e) {
					LOG.error(e.getLocalizedMessage());
					connection.rollback();
				}
			}
		} catch (SQLException ex) {
			LOG.error(ex.getLocalizedMessage());
		}
		return user;
	}

	@Override
	public List<User> find() {
		LOG.trace("Starting tracing UserDAO#find");
		List<User> users = new ArrayList<>();
		try (Connection connection = ConnectionPool.getConnection()) {
			if (connection != null) {
				try (Statement statement = connection.createStatement()) {
					try (ResultSet rs = statement.executeQuery(Query.SELECT_USERS)) {
						while (rs.next()) {
							users.add(mapUser(rs));
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
		return users;
	}
	
	@Override
	public List<User> findUsersForAddingTheTest(Long testId) {
		LOG.trace("Starting tracing UserDAO#findUsersForAddingTheTest");
		List<User> users = new ArrayList<>();
		try (Connection connection = ConnectionPool.getConnection()) {
			if (connection != null) {
				try (PreparedStatement statement = connection.prepareStatement(Query.SELECT_USERS_WITHOUT_THIS_TEST, Statement.RETURN_GENERATED_KEYS)) {
					statement.setLong(1, testId);
					statement.execute();
					ResultSet rs = statement.getResultSet();
					while (rs.next()) {
						users.add(mapUser(rs));
					}
					rs.close();
				} catch (SQLException e) {
					LOG.info(e.getSQLState());
				}
			}
		} catch (SQLException e) {
			LOG.info(e.getLocalizedMessage());
		}
		return users;
	}
	
	@Override
	public List<User> findUsersWithThisTest(Long testId) {
		LOG.trace("Starting tracing UserDAO#findUsersForAddingTheTest");
		List<User> users = new ArrayList<>();
		try (Connection connection = ConnectionPool.getConnection()) {
			if (connection != null) {
				try (PreparedStatement statement = connection.prepareStatement(Query.SELECT_USERS_WITH_THIS_TEST, Statement.RETURN_GENERATED_KEYS)) {
					statement.setLong(1, testId);
					statement.execute();
					ResultSet rs = statement.getResultSet();
					while (rs.next()) {
						users.add(mapUser(rs));
					}
					rs.close();
				} catch (SQLException e) {
					LOG.info(e.getSQLState());
				}
			}
		} catch (SQLException e) {
			LOG.info(e.getLocalizedMessage());
		}
		return users;
	}

	@Override
	public void add(User user) {
		LOG.trace("Starting tracing UserDAO#add");
		try (Connection connection = ConnectionPool.getConnection()) {
			if (connection != null) {
				try (PreparedStatement statement = connection.prepareStatement(Query.CREATE_USER,
						Statement.RETURN_GENERATED_KEYS)) {
					connection.setAutoCommit(false);
					statement.setString(1, user.getName());
					statement.setString(2, user.getPassword());
					statement.setLong(3, user.getRole().getId());
					statement.executeUpdate();
					connection.commit();
				} catch (SQLException ex) {
					LOG.error(ex.getLocalizedMessage());
					connection.rollback();
				}
			}
		} catch (SQLException e) {
			LOG.error(e.getLocalizedMessage());
		}
	}
	
	@Override
	public void update(User user) {
		LOG.trace("Start tracing UserDAO#update");
		try (Connection connection = ConnectionPool.getConnection()) {
			if (connection != null) {
				try (PreparedStatement statement = connection.prepareStatement(Query.UPDATE_USER, Statement.RETURN_GENERATED_KEYS)) {
					connection.setAutoCommit(false);
					statement.setString(1, user.getName());
					statement.setString(2, user.getPassword());
					statement.setLong(3, user.getRole().getId());
					statement.setLong(4, user.getId());
					statement.executeUpdate();
					connection.commit();
				} catch (SQLException e) {
					LOG.error(e.getLocalizedMessage());
					connection.rollback();
				}
			}
		} catch (SQLException e) {
			LOG.error(e.getLocalizedMessage());
		}
	}
	
	@Override
	public void addUsersToTheTest(Long testId, List<User> users) {
		LOG.trace("Start tracing UserDAO#addUsersToTheTest");
		try (Connection connection = ConnectionPool.getConnection()) {
			if (connection != null) {
				try (PreparedStatement statement = connection.prepareStatement(Query.INSERT_USERS_TO_THE_TEST, Statement.RETURN_GENERATED_KEYS)) {
					connection.setAutoCommit(false);
					connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
					for (User user : users) {
						statement.setLong(1, user.getId());
						statement.setLong(2, testId);
						statement.executeUpdate();
					}
					connection.commit();
				} catch (SQLException e) {
					LOG.error(e.getLocalizedMessage());
					connection.rollback();
				}
			}
		} catch (SQLException e) {
			LOG.error(e.getLocalizedMessage());
		}
	}

	private User mapUser(ResultSet resultSet) throws SQLException {
		User user = new User();
		user.setId(resultSet.getLong("id"));
		user.setName(resultSet.getString("name"));
		user.setPassword(resultSet.getString("password"));
		Role role = DAOFactory.getRoleDAO().find(resultSet.getLong("role_id"));
		user.setRole(role);
		return user;
	}

}
