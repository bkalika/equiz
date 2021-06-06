package com.equiz.db;

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
import com.equiz.db.dtos.Question;
import com.mysql.jdbc.jdbc2.optional.MysqlConnectionPoolDataSource;

public class TestQuestionDAO {
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
	public void findAllQuestionsTest() throws Exception {
		List<Question> questions = DAOFactory.getQuestionDAO().findAll(68L);
		assertNotNull(questions);
	}

	@Test
	public void findQuestionTest() throws Exception {
		Question question = DAOFactory.getQuestionDAO().find(54L);
		assertEquals(question.getName(), "What is the fastest bird in the world?");
	}

	@Test
	public void insertQuestionTest() throws Exception {
		List<Question> questions = DAOFactory.getQuestionDAO().findAll(68L);
		int size = questions.size();
		DAOFactory.getQuestionDAO().insert("What is the fastest bird in the world?", 1L, 68L, true);
		List<Question> questionsAfter = DAOFactory.getQuestionDAO().findAll(68L);
		assertEquals(questionsAfter.size(), size+1);
	}

	@Test
	public void deleteQuestionTest() throws Exception {
		List<Question> questions = DAOFactory.getQuestionDAO().findAll(68L);
		int size = questions.size();
		DAOFactory.getQuestionDAO().delete(51L);
		List<Question> questionsAfter = DAOFactory.getQuestionDAO().findAll(68L);
		assertEquals(questionsAfter.size(), size);
	}

	@Test
	public void updateQuestionTest() throws Exception {
		DAOFactory.getQuestionDAO().update(54L, "What is the fastest bird in the world?", 2L, true);
		Question question = DAOFactory.getQuestionDAO().find(54L);
		assertEquals(question.getScore(), 2L);
	}
}
