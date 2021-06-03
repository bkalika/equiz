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
import com.equiz.db.daos.interfaces.IQuestionDAO;
import com.equiz.db.dtos.Answer;
import com.equiz.db.dtos.Question;
import com.equiz.db.dtos.Test;

public class QuestionDAO implements IQuestionDAO {
	public static final Logger LOG = Logger.getLogger(QuestionDAO.class);

	@Override
	public Question find(Long id) {
		LOG.trace("Starting tracing QuestionDAO#find");
		try (Connection conn = ConnectionPool.getConnection();
				PreparedStatement ps = conn.prepareStatement(Query.SELECT_QUESTION_BY_ID)) {
			ps.setLong(1, id);
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					return mapQuestion(rs);
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
	public List<Question> findAll(Long testId) {
		List<Question> questions = new ArrayList<>();
		try(Connection connection = ConnectionPool.getConnection();
			PreparedStatement ps = connection.prepareStatement(Query.SELECT_QUESTIONS_BY_TEST_ID)) {
				ps.setLong(1, testId);
				try (ResultSet rs = ps.executeQuery()) {
					while (rs.next()) {
						questions.add(mapQuestion(rs));
					}
				} catch (SQLException e) {
					e.getSQLState();
				}
			} catch (SQLException e) {
				e.getSQLState();
			}
		return questions;
	}
	

	@Override
	public void insert(String name, Long score, Long testId, Boolean isSingle) {
		LOG.trace("Starting tracing QuestionDAO#insert");
		try (Connection connection = ConnectionPool.getConnection()) {
			if (connection != null) {
				try (PreparedStatement ps = connection.prepareStatement(Query.INSERT_QUESTION, Statement.RETURN_GENERATED_KEYS)) {
					ps.setString(1, name);
					ps.setLong(2, score);
					ps.setLong(3, testId);
					ps.setBoolean(4, isSingle);
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
	public void update(Long questionId, String questionName, Long score, Boolean isSingle) {
		LOG.trace("Starting tracing QuestionDAO#update");
		try (Connection connection = ConnectionPool.getConnection()) {
			if (connection != null) {
				try (PreparedStatement statement = connection.prepareStatement(Query.UPDATE_QUESTION, Statement.RETURN_GENERATED_KEYS)) {
					connection.setAutoCommit(false);
					statement.setString(1, questionName);
					statement.setLong(2, score);
					statement.setBoolean(3, isSingle);
					statement.setLong(4, questionId);
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
	public void delete(Long questionId) {
		LOG.trace("Starting tracing QuestionDAO#delete");
		try (Connection connection = ConnectionPool.getConnection()) {
			if (connection != null) {
				try (PreparedStatement statement = connection.prepareStatement(Query.DELETE_QUESTION, Statement.RETURN_GENERATED_KEYS)) {
					connection.setAutoCommit(false);
					statement.setLong(1, questionId);
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

	private Question mapQuestion(ResultSet rs) throws SQLException {
		Question question = new Question();
		question.setId(rs.getLong("id"));
		question.setName(rs.getString("name"));
		question.setScore(rs.getInt("score"));
		Test test = DAOFactory.getTestDAO().find(rs.getLong("test_id"));
		question.setTest(test);
		question.setSingle(rs.getBoolean("is_single"));
		List<Answer> answers = DAOFactory.getAnswerDAO().findByQuestionId(question.getId());
		question.setAnswers(answers);
		return question;
	}

}
