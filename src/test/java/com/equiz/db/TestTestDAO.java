package com.equiz.db;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import static org.junit.Assert.*;

import org.apache.log4j.PropertyConfigurator;
import org.apache.naming.java.javaURLContextFactory;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.equiz.db.daos.DAOFactory;
import com.equiz.db.daos.TestDAO;
import com.equiz.db.dtos.Subject;
import com.equiz.db.dtos.User;
import com.mysql.jdbc.jdbc2.optional.MysqlConnectionPoolDataSource;

public class TestTestDAO {
	static Context context;

	@BeforeClass
	public static void setupBeforeClass() throws Exception {
		MysqlConnectionPoolDataSource ds = new MysqlConnectionPoolDataSource();
		PropertyConfigurator.configure("E:/JAVA/eclipse-workspace/equiz/src/main/webapp/WEB-INF/log4j.properties");
		ds.setURL("jdbc:mysql://localhost:3306/testingsysdb");
		ds.setUser("site");
		ds.setPassword("site");

		DataSource dataSource = ds;
		System.setProperty(Context.INITIAL_CONTEXT_FACTORY, javaURLContextFactory.class.getName());
		context = new InitialContext();
		Context ctx = context.createSubcontext("java");
		ctx.createSubcontext("comp").createSubcontext("env").createSubcontext("jdbc")
				.bind("testingsysdb", dataSource);
		context.bind("java:", ctx);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		context.destroySubcontext("java");
		context.unbind("java:");
		context.close();
	}

	@Test
	public void updateTestTest() throws Exception {
		String deadline = "2021-07-30 10:30";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		LocalDateTime formatDateTime = LocalDateTime.parse(deadline, formatter);
		new TestDAO().update(1L, "InformaticaTest", formatDateTime, 4L, 2);
		com.equiz.db.dtos.Test expectedTest = new com.equiz.db.dtos.Test();
		expectedTest.setId(1L);
		expectedTest.setName("InformaticaTest");
		assertEquals(expectedTest.getName(), new TestDAO().find(1L).getName());
	}
	
	@Test
	public void findTest() throws Exception {
		com.equiz.db.dtos.Test expectedTest = new com.equiz.db.dtos.Test();
		expectedTest.setId(1L);
		expectedTest.setName("BigData");
		assertEquals(expectedTest.getName(), new TestDAO().find(2L).getName());
	}
	
	@Test
	public void findAllTests() throws Exception {
		assertNotNull(DAOFactory.getTestDAO().find());
	}
	
	@Test
	public void findUserTestsBySubjectTest() {
		User user = DAOFactory.getUserDAO().findUserByLogin("test");
		Subject subject = DAOFactory.getSubjectDAO().find(1L);
		List<com.equiz.db.dtos.Test> expectedTest = DAOFactory.getTestDAO().findUsersTestBySubject(user.getId(), subject.getId());

		assertNotNull(expectedTest);
	}
	
	@Test
	public void findTestsBySubjectTest() throws Exception {
		assertEquals(1, DAOFactory.getTestDAO().findBySubject(50L).size());
	}
	
	@Test
	public void findUsersTestBySubjectTest() throws Exception {
		assertEquals(new ArrayList<>(), DAOFactory.getTestDAO().findUserTestsBySubject(42L, 50L));
	}
	
	@Test
	public void findUsersPassedTestsTest() throws Exception {
		assertEquals(new ArrayList<>(), DAOFactory.getTestDAO().findUserPassedTest(42L));
	}
	
	@Test
	public void findUsersTestByTestIdTest() throws Exception {
		assertNull(DAOFactory.getTestDAO().findUserTestByTestId(42L, 50L));
	}
	
	@Test
	public void insertTestTest() throws Exception {
		com.equiz.db.dtos.Test test = new com.equiz.db.dtos.Test();
		test.setName("Test Test");
		Subject subject = DAOFactory.getSubjectDAO().find(50L);
		test.setSubject(subject);
		
		String deadline = "2021-07-30 10:30";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		LocalDateTime formatDateTime = LocalDateTime.parse(deadline, formatter);

		test.setDeadline(formatDateTime);
		test.setScore(10L);
		test.setLevel(2L);
		test.setDuration(4);
		
		int sizeTest = DAOFactory.getTestDAO().find().size();
		DAOFactory.getTestDAO().insert(test);
		assertEquals(sizeTest, DAOFactory.getTestDAO().find().size());
	}
	
	@Test
	public void deleteTestTest() throws Exception {
		DAOFactory.getTestDAO().delete(61L);
		assertNull(DAOFactory.getTestDAO().find(61L));
	}
	
	@Test
	public void decreaseTestScoreTest() throws Exception {
		List<com.equiz.db.dtos.Test> tests = DAOFactory.getTestDAO().find();
		com.equiz.db.dtos.Test test = tests.get(1);
		Long testScore = test.getScore() - 1;
		DAOFactory.getTestDAO().decreaseTestScore(test, 1L);
		assertEquals(testScore, DAOFactory.getTestDAO().find(test.getId()).getScore());
	}
	
	@Test
	public void increaseTestScoreTest() throws Exception {
		List<com.equiz.db.dtos.Test> tests = DAOFactory.getTestDAO().find();
		com.equiz.db.dtos.Test test = tests.get(1);
		Long testScore = test.getScore() + 1;
		DAOFactory.getTestDAO().increaseTestScore(test, 1L);
		assertEquals(testScore, DAOFactory.getTestDAO().find(test.getId()).getScore());
	}

	@Test
	public void increaseTestPopularityTest() throws Exception {
		List<com.equiz.db.dtos.Test> tests = DAOFactory.getTestDAO().find();
		com.equiz.db.dtos.Test test = tests.get(1);
		Long testPopularity = test.getPopularity() + 1;
		DAOFactory.getTestDAO().increaseTestPopularity(test);
		assertEquals(testPopularity, DAOFactory.getTestDAO().find(test.getId()).getPopularity());
	}
}
