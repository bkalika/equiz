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
import com.equiz.db.daos.interfaces.IAnswerDAO;
import com.equiz.db.dtos.Answer;

public class AnswerDAO implements IAnswerDAO {
	public static final Logger LOG = Logger.getLogger(AnswerDAO.class);

	@Override
	public void insert(String name, Long questionId, Boolean isCorrect) {
		LOG.trace("Starting tracing AnswerDAO#insert");
		try (Connection connection = ConnectionPool.getConnection()) {
			if (connection != null) {
				try (PreparedStatement ps = connection.prepareStatement(Query.INSERT_ANSWER, Statement.RETURN_GENERATED_KEYS)) {
					ps.setString(1, name);
					ps.setLong(2, questionId);
					ps.setBoolean(3, isCorrect);
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
	public Answer find(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Answer> findByQuestionId(Long questionId) {
		List<Answer> answers = new ArrayList<>();
		try (Connection connection = ConnectionPool.getConnection();
				PreparedStatement ps = connection.prepareStatement(Query.SELECT_ANSWERS_BY_QUESTION_ID)) {
			ps.setLong(1, questionId);
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					answers.add(mapAnswer(rs));
				}
			} catch (SQLException e) {
				e.getSQLState();
			}
		} catch (SQLException e) {
			e.getSQLState();
		}
		return answers;
	}

	@Override
	public void update(Long id, String name, Boolean isCorrect) {
		LOG.trace("Starting tracing AnswerDAO#update");
		try (Connection connection = ConnectionPool.getConnection()) {
			if (connection != null) {
				try (PreparedStatement statement = connection.prepareStatement(Query.UPDATE_ANSWER, Statement.RETURN_GENERATED_KEYS)) {
					connection.setAutoCommit(false);
					statement.setString(1, name);
					statement.setBoolean(2, isCorrect);
					statement.setLong(3, id);
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
	public void delete(Long id) {
		LOG.trace("Starting tracing AnswerDAO#delete");
		try (Connection connection = ConnectionPool.getConnection()) {
			if (connection != null) {
				try (PreparedStatement statement = connection.prepareStatement(Query.DELETE_ANSWER, Statement.RETURN_GENERATED_KEYS)) {
					connection.setAutoCommit(false);
					statement.setLong(1, id);
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

	private Answer mapAnswer(ResultSet resultSet) throws SQLException {
		Answer answer = new Answer();
		answer.setId(resultSet.getLong("id"));
		answer.setName(resultSet.getString("name"));
		answer.setCorrect(resultSet.getBoolean("is_correct"));
		return answer;
	}
}
