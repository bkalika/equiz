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
import com.equiz.db.daos.interfaces.ISubjectDAO;
import com.equiz.db.dtos.Role;
import com.equiz.db.dtos.Subject;

public class SubjectDAO implements ISubjectDAO {
	public static final Logger LOG = Logger.getLogger(SubjectDAO.class);

	@Override
	public List<Subject> findUserSubjectsById(Long userId) {
		LOG.trace("Starting tracing SubjectDAO#findUserSubjectsById");
		List<Subject> subjects = new ArrayList<>();
		try (Connection connection = ConnectionPool.getConnection()) {
			if (connection != null) {
				try (PreparedStatement statement = connection.prepareStatement(Query.SELECT_USER_SUBJECTS)) {
					connection.setAutoCommit(false);
					statement.setLong(1, userId);
					statement.execute();
					try (ResultSet rs = statement.executeQuery()) {
						while (rs.next()) {
							subjects.add(new Subject(rs.getLong(1), rs.getString(2)));
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
		return subjects;
	}
	
	@Override
	public List<Subject> find() {
		LOG.trace("Starting tracing SubjectDAO#find");
		List<Subject> subjects = new ArrayList<>();
		try (Connection connection = ConnectionPool.getConnection()) {
			if (connection != null) {
				try (Statement statement = connection.createStatement()) {
					try (ResultSet rs = statement.executeQuery(Query.SELECT_SUBJECTS)) {
						while (rs.next()) {
							subjects.add(new Subject(rs.getLong(1), rs.getString(2)));
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
		return subjects;
	}
	
	@Override
	public void insert(String subjectName) {
		LOG.trace("Starting tracing SubjectDAO#insert");
		try (Connection connection = ConnectionPool.getConnection()) {
			if (connection != null) {
				try (PreparedStatement ps = connection.prepareStatement(Query.INSERT_SUBJECT, Statement.RETURN_GENERATED_KEYS)) {
					ps.setString(1, subjectName);
					ps.executeUpdate();
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
	public void update(Long subjectId, String subjectName) {
		LOG.trace("Starting tracing SubjectDAO#update");
		try (Connection connection = ConnectionPool.getConnection()) {
			if (connection != null) {
				try (PreparedStatement statement = connection.prepareStatement(Query.UPDATE_SUBJECT, Statement.RETURN_GENERATED_KEYS)) {
					connection.setAutoCommit(false);
					statement.setString(1, subjectName);
					statement.setLong(2, subjectId);
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
	public void delete(Long subjectId) {
		LOG.trace("Starting tracing SubjectDAO#delete");
		try (Connection connection = ConnectionPool.getConnection()) {
			if (connection != null) {
				try (PreparedStatement statement = connection.prepareStatement(Query.DELETE_SUBJECT, Statement.RETURN_GENERATED_KEYS)) {
					connection.setAutoCommit(false);
					statement.setLong(1, subjectId);
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
	public Subject find(Long id) {
		LOG.trace("Star tracing SubjectDAO#find");
		Subject subject = null;
		try (Connection connection = ConnectionPool.getConnection()) {
			if (connection != null) {
				try (PreparedStatement statement = connection.prepareStatement(Query.SELECT_SUBJECT_BY_ID)) {
					connection.setAutoCommit(false);
					statement.setLong(1, id);
					statement.execute();
					ResultSet rs = statement.getResultSet();
					if (rs.next()) {
						subject = new Subject();
						subject.setId(rs.getLong("id"));
						subject.setName(rs.getString("name"));
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
		return subject;
	}

}
