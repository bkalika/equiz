package com.equiz.db.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.equiz.db.Query;
import com.equiz.db.beans.UserTestBean;
import com.equiz.db.daos.interfaces.ITestDAO;
import com.equiz.db.dtos.Subject;
import com.equiz.db.dtos.Test;

public class TestDAO implements ITestDAO {
	public static final Logger LOG = Logger.getLogger(TestDAO.class);

	@Override
	public List<Test> find() {
		LOG.trace("Starting tracing TestDAO#find");
		List<Test> tests = new ArrayList<>();
		
		try (Connection connection = ConnectionPool.getConnection()) {
			if (connection != null) {
				try (PreparedStatement statement = connection.prepareStatement(Query.SELECT_ALL_TESTS)) {
					connection.setAutoCommit(false);
					statement.execute();
					ResultSet resultSet = statement.getResultSet();
					while(resultSet.next()) {
						tests.add(mapTest(resultSet));
					}
					resultSet.close();
					connection.commit();
				} catch (SQLException e) {
					LOG.error(e.getLocalizedMessage());
					connection.rollback();
				}
			}
		} catch (SQLException e) {
			LOG.error(e.getLocalizedMessage());
		}
		return tests;
	}
	
	@Override
	public Test find(Long testId) {
		LOG.trace("Starting tracing TestDAO#find");
		try (Connection conn = ConnectionPool.getConnection();
				PreparedStatement ps = conn.prepareStatement(Query.SELECT_TEST_BY_TEST_ID)) {
			ps.setLong(1, testId);
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					return mapTest(rs);
				}
			} catch (SQLException e) {
				LOG.error(e.getErrorCode());
			}
		} catch (SQLException e) {
			LOG.error(e.getErrorCode());
		}
		return null;
	}

	@Override
	public List<UserTestBean> findUserTestsBySubject(Long userId, Long subjectId) {
		List<UserTestBean> tests = new ArrayList<>();
		try (Connection connection = ConnectionPool.getConnection()) {
			if (connection != null) {
				try (PreparedStatement statement = connection.prepareStatement(Query.SELECT_USER_TEST_BY_SUBJECT)) {
					connection.setAutoCommit(false);
					statement.setLong(1, userId);
					statement.setLong(2, subjectId);
					statement.execute();
					try (ResultSet rs = statement.executeQuery()) {
						while (rs.next()) {
							tests.add(mapUserTestBean(rs));
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
		return tests;
	}

	@Override
	public List<Test> findBySubject(Long subjectId) {
		List<Test> tests = new ArrayList<>();
		try (Connection connection = ConnectionPool.getConnection()) {
			if (connection != null) {
				try (PreparedStatement statement = connection.prepareStatement(Query.SELECT_TESTS_BY_SUBJECT)) {
					connection.setAutoCommit(false);
					statement.setLong(1, subjectId);
					statement.execute();
					try (ResultSet rs = statement.executeQuery()) {
						while (rs.next()) {
							tests.add(mapTest(rs));
						}
					} catch (SQLException e) {
						LOG.info(e.getSQLState());
					}
				} catch (SQLException e) {
					LOG.info(e.getSQLState());
				}
			}
		} catch (SQLException e) {
			LOG.info(e.getLocalizedMessage());
		}
		return tests;
	}
	
	@Override
	public List<Test> findUsersTestBySubject(Long userId, Long subjectId) {
		List<Test> tests = new ArrayList<>();
		try (Connection connection = ConnectionPool.getConnection()) {
			if (connection != null) {
					try (PreparedStatement statement = connection.prepareStatement(Query.SELECT_USER_TEST_BY_SUBJECT_MOD)) {
					connection.setAutoCommit(false);
					statement.setLong(1, userId);
					statement.setLong(2, subjectId);
					statement.execute();
					try (ResultSet rs = statement.executeQuery()) {
						while (rs.next()) {
							tests.add(mapTest(rs));
						}
					} catch (SQLException e) {
						LOG.info(e.getSQLState());
					}
				} catch (SQLException e) {
					LOG.info(e.getSQLState());
				}
			}
		} catch (SQLException e) {
			LOG.info(e.getLocalizedMessage());
		}
		return tests;
	}

	@Override
	public UserTestBean findUserTestByTestId(Long userId, Long testId) {
		try (Connection conn = ConnectionPool.getConnection();
				PreparedStatement ps = conn.prepareStatement(Query.SELECT_USER_TEST_BY_TEST_ID)) {
			ps.setLong(1, userId);
			ps.setLong(2, testId);
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					return mapUserTestBean(rs);
				}
			} catch (SQLException e) {
				e.getErrorCode();
			}
		} catch (SQLException e) {
			e.getErrorCode();
		}
		return null;
	}

	@Override
	public void update(UserTestBean userTest) {
		try (Connection connection = ConnectionPool.getConnection();
				PreparedStatement ps = connection.prepareStatement(Query.UPDATE_USER_TEST)) {
			ps.setLong(1, userTest.getScore());
			ps.setTimestamp(2, Timestamp.valueOf(userTest.getPassedDate()));
			ps.setLong(3, userTest.getUserId());
			ps.setLong(4, userTest.getTestId());
			ps.executeUpdate();
		} catch (SQLException e) {
			System.err.format("SQL Stage: %s %s %s", e.getSQLState(), System.lineSeparator(), e.getMessage());
		} catch (Exception e) {
			e.getCause();
		}
	}
	
	@Override
	public void insert(Test test) {
		LOG.trace("Starting tracing TestDAO#insert");
		try (Connection connection = ConnectionPool.getConnection()) {
			if (connection != null) {
				try (PreparedStatement ps = connection.prepareStatement(Query.INSERT_TEST, Statement.RETURN_GENERATED_KEYS)) {
					ps.setString(1, test.getName());
					ps.setLong(2, test.getSubject().getId());
					ps.setTimestamp(3, Timestamp.valueOf(test.getDeadline()));
					ps.setLong(4, test.getScore());
					ps.setLong(5, test.getLevel());
					ps.setInt(6, test.getDuration());
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
	
	public void update(Long id, String name, LocalDateTime deadline, Long level, int duration) {
		try (Connection connection = ConnectionPool.getConnection();
				PreparedStatement ps = connection.prepareStatement(Query.UPDATE_TEST)) {
			ps.setString(1, name);
			ps.setTimestamp(2, Timestamp.valueOf(deadline));
			ps.setLong(3, level);
			ps.setInt(4, duration);
			ps.setLong(5, id);
			ps.executeUpdate();
		} catch (SQLException e) {
			LOG.error(e.getLocalizedMessage());
		} catch (Exception e) {
			LOG.error(e.getLocalizedMessage());
		}
	}

	@Override
	public List<UserTestBean> findUserPassedTest(Long userId) {
		List<UserTestBean> tests = new ArrayList<>();
		try (Connection connection = ConnectionPool.getConnection()) {
			if (connection != null) {
				try (PreparedStatement statement = connection.prepareStatement(Query.SELECT_USER_PASSED_TEST)) {
					statement.setLong(1, userId);
					statement.execute();
					try (ResultSet rs = statement.executeQuery()) {
						while (rs.next()) {
							tests.add(mapUserTestBean(rs));
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
		return tests;
	}
	
	@Override
	public void delete(Long testId) {
		LOG.trace("Starting tracing TestDAO#delete");
		try (Connection connection = ConnectionPool.getConnection()) {
			if (connection != null) {
				try (PreparedStatement statement = connection.prepareStatement(Query.DELETE_TEST, Statement.RETURN_GENERATED_KEYS)) {
					connection.setAutoCommit(false);
					statement.setLong(1, testId);
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
	public void increaseTestScore(Test test, Long score) {
		LOG.trace("Starting tracing TestDAO#increaseTestScore");
		test.setScore(test.getScore()+score);
		try (Connection connection = ConnectionPool.getConnection()) {
			if (connection != null) {
				try (PreparedStatement ps = connection.prepareStatement(Query.UPDATE_TEST_SCORE, Statement.RETURN_GENERATED_KEYS)) {
					ps.setLong(1, test.getScore());
					ps.setLong(2, test.getId());
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
	public void decreaseTestScore(Test test, Long score) {
		LOG.trace("Starting tracing TestDAO#decreaseTestScore");
		test.setScore(test.getScore()-score);
		try (Connection connection = ConnectionPool.getConnection()) {
			if (connection != null) {
				try (PreparedStatement ps = connection.prepareStatement(Query.UPDATE_TEST_SCORE, Statement.RETURN_GENERATED_KEYS)) {
					ps.setLong(1, test.getScore());
					ps.setLong(2, test.getId());
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
	public void increaseTestPopularity(Test test) {
		LOG.trace("Starting tracing TestDAO#increaseTestScore");
		test.setPopularity(test.getPopularity()+1);
		try (Connection connection = ConnectionPool.getConnection()) {
			if (connection != null) {
				try (PreparedStatement ps = connection.prepareStatement(Query.UPDATE_TEST_POPULARITY, Statement.RETURN_GENERATED_KEYS)) {
					ps.setLong(1, test.getPopularity());
					ps.setLong(2, test.getId());
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
	
	private Test mapTest(ResultSet rs) throws SQLException {
		Test test = new Test();
		test.setId(rs.getLong("id"));
		test.setName(rs.getString("test_name"));
		Subject subject = DAOFactory.getSubjectDAO().find(rs.getLong("subject_id"));
		test.setSubject(subject);
		test.setDeadline(rs.getTimestamp("deadline").toLocalDateTime());
		test.setScore(rs.getLong("score"));
		test.setLevel(rs.getLong("level"));
		test.setPopularity(rs.getLong("popularity"));
		test.setDuration(rs.getInt("duration"));
		return test;
	}

	private UserTestBean mapUserTestBean(ResultSet rs) throws SQLException {
		UserTestBean userTest = new UserTestBean();
		userTest.setTestId(rs.getLong("test_id"));
		userTest.setSubjectId(rs.getLong("subject_id"));
		userTest.setUserId(rs.getLong("user_id"));
		userTest.setTestName(rs.getString("test_name"));
		userTest.setTestScore(rs.getLong("test_score"));
		userTest.setSubjectName(rs.getString("subject_name"));
		userTest.setUserName(rs.getString("user_name"));
		userTest.setScore(rs.getLong("user_score"));
		userTest.setDeadline(rs.getTimestamp("deadline").toLocalDateTime());
		Timestamp tm = rs.getTimestamp("passed_date");
		if (tm != null) {
			userTest.setPassedDate(tm.toLocalDateTime());
		}
//		userTest.setPassedDate(rs.getTimestamp("passed_date").toLocalDateTime());
		userTest.setTestLevel(rs.getLong("test_level"));
		userTest.setTestPopularity(rs.getLong("test_popularity"));
		userTest.setTestDuration(rs.getInt("test_duration"));
		return userTest;
	}

}
