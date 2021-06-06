package com.equiz.db;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.apache.log4j.PropertyConfigurator;
import org.apache.naming.java.javaURLContextFactory;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.equiz.db.daos.DAOFactory;
import com.equiz.db.dtos.Answer;
import com.mysql.jdbc.jdbc2.optional.MysqlConnectionPoolDataSource;

public class TestAnswerDAO {
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
	public void findByQuestionIdTest() throws Exception {
		List<Answer> answers = DAOFactory.getAnswerDAO().findByQuestionId(54L);
		assertNotNull(answers.size());
	}

	@Test
	public void findAnswerTest() throws Exception {
		Answer answer = DAOFactory.getAnswerDAO().find(37L);
		assertEquals(answer.getName(), "Peregrine Falcon");
	}
	

	@Test
	public void updateAnswerTest() throws Exception {
		DAOFactory.getAnswerDAO().update(37L, "Peregrine Falcon", true);
		Answer answer = DAOFactory.getAnswerDAO().find(37L);
		assertEquals(answer.getName(), "Peregrine Falcon");
	}

	@Test
	public void insertAnswerTest() throws Exception {
		List<Answer> answers = DAOFactory.getAnswerDAO().findByQuestionId(54L);
		int size = answers.size();
		DAOFactory.getAnswerDAO().insert("Bald Eagle", 54L, false);
		List<Answer> answersAfter = DAOFactory.getAnswerDAO().findByQuestionId(54L);
		assertEquals(answersAfter.size(), size+1);
	}

	@Test
	public void deleteAnswerTest() throws Exception {
		List<Answer> answers = DAOFactory.getAnswerDAO().findByQuestionId(54L);
		int size = answers.size();
		DAOFactory.getAnswerDAO().delete(54L);
		List<Answer> answersAfter = DAOFactory.getAnswerDAO().findByQuestionId(54L);
		assertEquals(answersAfter.size(), size);
	}

}
