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
import com.equiz.db.dtos.Subject;
import com.mysql.jdbc.jdbc2.optional.MysqlConnectionPoolDataSource;

public class TestSubjectDAO {
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
	public void findAllSubjectsTest() throws Exception {
		List<Subject> subjects = DAOFactory.getSubjectDAO().find();
		assertNotNull(subjects);
	}
	
	@Test
	public void findByIdTest() throws Exception {
		List<Subject> subjects = DAOFactory.getSubjectDAO().find();
		
		assertNotNull(subjects);
	}

	@Test
	public void findUserSubjectsByIdTest() throws Exception {
		Subject subject = DAOFactory.getSubjectDAO().find(39L);
		assertEquals(subject.getName(), "Music");
	}
	
	@Test
	public void insertSubjectTest() throws Exception {
		List<Subject> subjects = DAOFactory.getSubjectDAO().find();
		int subSize = subjects.size();
		DAOFactory.getSubjectDAO().insert("new Sub");
		List<Subject> subjectsAfter = DAOFactory.getSubjectDAO().find();
		assertEquals(subSize, subjectsAfter.size());
	}
	
	@Test
	public void deleteSubjectTest() throws Exception {
		List<Subject> subjects = DAOFactory.getSubjectDAO().find();
		int subSize = subjects.size();
		DAOFactory.getSubjectDAO().delete(51L);
		List<Subject> subjectsAfter = DAOFactory.getSubjectDAO().find();
		assertEquals(subSize, subjectsAfter.size());
	}

}
