package com.equiz.db;

public class Query {
	public static final String SELECT_USER_BY_NAME = "SELECT * FROM user WHERE name = ?";
	public static final String SELECT_USER_BY_ID = "SELECT * FROM user WHERE id = ?";
	public static final String SELECT_USERS = "SELECT * FROM user ORDER BY id;";
	public static final String SELECT_USERS_WITH_THIS_TEST = "SELECT * FROM user u WHERE id IN (SELECT user_id FROM user_test ut WHERE ut.test_id = ?);";
	public static final String SELECT_USERS_WITHOUT_THIS_TEST = "SELECT * FROM user u WHERE id NOT IN (SELECT user_id FROM user_test ut WHERE ut.test_id = ?);";
	public static final String SELECT_ROLE_BY_ID = "SELECT * FROM role WHERE id = ?";
	public static final String SELECT_ROLES = "SELECT * FROM role ORDER BY id;";
	public static final String SELECT_SUBJECT_BY_ID = "SELECT * FROM subject WHERE id = ?;";
	public static final String SELECT_USER_SUBJECTS = "SELECT s.id, s.name FROM user_subject us "
			+ "JOIN subject s ON us.subject_id = s.id WHERE user_id = ? ORDER BY s.name;";
	public static final String SELECT_SUBJECTS = "SELECT s.id, s.name FROM subject s ORDER BY s.id;";
	public static final String SELECT_USER_TEST_BY_SUBJECT = "SELECT ut.test_id test_id, "
			+ "s.id subject_id, "
			+ "ut.user_id user_id, "
			+ "t.name test_name, "
			+ "t.score test_score, "
			+ "s.name subject_name, "
			+ "u.name user_name, "
			+ "ut.score user_score, "
			+ "t.deadline deadline, "
			+ "t.level test_level, "
			+ "t.duration test_duration, "
			+ "t.popularity test_popularity, "
			+ "ut.passed_date passed_date "
			+ "FROM user_test ut "
			+ "RIGHT JOIN test t ON t.id= ut.test_id "
			+ "JOIN user u ON u.id=ut.user_id "
			+ "JOIN subject s ON t.subject_id=s.id "
			+ "WHERE u.id = ? AND s.id = ? "
			+ "AND ut.passed_date is null "
			+ "ORDER BY deadline;";
	public static final String SELECT_USER_TEST_BY_SUBJECT_MOD = "SELECT t.id, t.name test_name, t.subject_id, t.deadline deadline, t.score score, t.level level, t.popularity popularity, t.duration duration "
			+ "FROM test t "
			+ "JOIN subject s "
			+ "ON s.id = t.subject_id "
			+ "WHERE t.id NOT IN (SELECT test_id FROM user_test ut "
			+ "JOIN test t "
			+ "ON ut.test_id = t.id "
			+ "JOIN subject s2 "
			+ "ON s2.id = t.subject_id "
			+ "WHERE user_id = ? "
			+ "AND s2.id = s.id) "
			+ "AND s.id = ? "
			+ "AND DATE(deadline) > CURDATE();";
	
	public static final String SELECT_USER_PASSED_TEST = "SELECT ut.test_id test_id, "
			+ "s.id subject_id, "
			+ "ut.user_id user_id, "
			+ "t.name test_name, "
			+ "t.score test_score, "
			+ "t.level test_level, "
			+ "t.popularity test_popularity, "
			+ "s.name subject_name, "
			+ "u.name user_name, "
			+ "ut.score user_score, "
			+ "t.deadline deadline, "
			+ "t.duration test_duration, "
			+ "ut.passed_date passed_date "
			+ "FROM user_test ut "
			+ "RIGHT JOIN test t ON t.id= ut.test_id "
			+ "JOIN user u ON u.id=ut.user_id "
			+ "JOIN subject s ON t.subject_id=s.id "
			+ "WHERE u.id = ? "
			+ "AND ut.passed_date is not null "
			+ "ORDER BY deadline;";
	public static final String SELECT_TESTS_BY_SUBJECT = "SELECT t.id, t.name test_name, "
			+ "t.subject_id, t.deadline deadline, t.score score, t.level level, t.popularity popularity, t.duration duration "
			+ "FROM test t "
			+ "WHERE t.subject_id = ? "
			+ "ORDER BY deadline;";
	public static final String SELECT_ALL_TESTS = "SELECT t.id, t.name test_name, "
			+ "t.subject_id, t.deadline deadline, t.score score, t.level level, t.popularity popularity, t.duration duration "
			+ "FROM test t "
			+ "ORDER BY t.id;";
	public static final String SELECT_USER_TEST_BY_TEST_ID = "SELECT t.id test_id, "
			+ "s.id subject_id, "
			+ "u.id user_id, "
			+ "t.name test_name, "
			+ "t.score test_score, "
			+ "s.name subject_name, "
			+ "u.name user_name, "
			+ "ut.score user_score, "
			+ "t.deadline deadline, "
			+ "ut.passed_date passed_date, "
			+ "t.level test_level, "
			+ "t.popularity test_popularity, "
			+ "t.duration test_duration "
			+ "FROM user_test ut "
			+ "RIGHT JOIN test t ON t.id= ut.test_id "
			+ "JOIN user u ON u.id=ut.user_id "
			+ "JOIN subject s ON t.subject_id=s.id "
			+ "WHERE u.id = ? AND t.id = ? "
			+ "ORDER BY deadline;";
	public static final String SELECT_QUESTIONS_BY_TEST_ID = "SELECT * FROM question WHERE test_id = ? ORDER BY name;";
	public static final String SELECT_ANSWERS_BY_QUESTION_ID = "SELECT * FROM answer WHERE question_id = ?;";
	public static final String CREATE_USER = "INSERT INTO user (name, password, role_id) VALUES (?, ?, ?);";
//	public static final String UPDATE_USER_TEST = "UPDATE user_test SET score = ?, passed_date = ? WHERE user_id = ? and test_id = ?;";
	public static final String UPDATE_USER_TEST = "INSERT INTO user_test (score, passed_date, user_id, test_id) VALUES (?, ?, ?, ?);";
	public static final String INSERT_SUBJECT = "INSERT INTO subject (name) VALUES (?);";
	public static final String UPDATE_SUBJECT = "UPDATE subject SET name = ? WHERE id = ?;";
	public static final String DELETE_SUBJECT = "DELETE FROM subject WHERE id = ?";

	public static final String SELECT_TEST_BY_TEST_ID = "SELECT t.id id, t.name test_name, t.subject_id subject_id, t.deadline deadline, t.score score, t.level level, t.popularity popularity, t.duration duration FROM test t WHERE t.id = ?;";
	public static final String SELECT_TEST_BY_TEST_ID_ORDER_BY_NAME = "SELECT t.id id, t.name test_name, t.subject_id subject_id, t.deadline deadline, t.score score, t.level level, t.popularity popularity, t.duration duration FROM test t WHERE t.id = ? ORDER BY t.name;";
	public static final String SELECT_TEST_BY_TEST_ID_ORDER_BY_LEVEL = "SELECT t.id id, t.name test_name, t.subject_id subject_id, t.deadline deadline, t.score score, t.level level, t.popularity popularity, t.duration duration FROM test t WHERE t.id = ? ORDER BY t.level DESC;";
	public static final String SELECT_TEST_BY_TEST_ID_ORDER_BY_POPULARITY = "SELECT t.id id, t.name test_name, t.subject_id subject_id, t.deadline deadline, t.score score, t.level level, t.popularity popularity, t.duration duration FROM test t WHERE t.id = ? ORDER BY t.popularity DESC;";
	public static final String INSERT_TEST = "INSERT INTO test (name, subject_id, deadline, score, level, duration) VALUES (?, ?, ?, ?, ?, ?);";
	public static final String UPDATE_TEST_SCORE = "UPDATE test SET score = ? WHERE id = ?;";
	public static final String UPDATE_TEST_POPULARITY = "UPDATE test SET popularity = ? WHERE id = ?;";
	public static final String UPDATE_USER = "UPDATE user SET name = ?, password = ?, role_id = ? WHERE id = ?;";
	public static final String UPDATE_TEST = "UPDATE test SET name = ?, deadline = ?, level = ?, duration = ? WHERE id = ?;";
	public static final String DELETE_TEST = "DELETE FROM test WHERE id = ?";

	public static final String SELECT_QUESTION_BY_ID = "SELECT * FROM question WHERE id = ?;";
	public static final String INSERT_QUESTION = "INSERT INTO question (name, score, test_id, is_single) VALUES (?, ?, ?, ?);";
	public static final String UPDATE_QUESTION = "UPDATE question SET name = ?, score = ?, is_single = ? WHERE id = ?;";
	public static final String DELETE_QUESTION = "DELETE FROM question WHERE id = ?";
	
	public static final String INSERT_ANSWER = "INSERT INTO answer (name, question_id, is_correct) VALUES (?, ?, ?);";
	public static final String UPDATE_ANSWER = "UPDATE answer SET name = ?, is_correct = ? WHERE id = ?;";
	public static final String DELETE_ANSWER = "DELETE FROM answer WHERE id = ?";
	
	public static final String INSERT_USERS_TO_THE_TEST = "INSERT INTO user_test (user_id, test_id) VALUES (?, ?);";
}
