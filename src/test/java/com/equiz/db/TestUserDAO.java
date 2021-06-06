package com.equiz.db;

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
import com.equiz.db.dtos.Role;
import com.equiz.db.dtos.User;
import com.mysql.jdbc.jdbc2.optional.MysqlConnectionPoolDataSource;

public class TestUserDAO {
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
	public void findUserByLoginTest() throws Exception {
		User user = DAOFactory.getUserDAO().findUserByLogin("test");
		Long userId = DAOFactory.getUserDAO().find(user.getId()).getId();
		assertEquals(user.getName(), DAOFactory.getUserDAO().find(userId).getName());
	}
	
	@Test
	public void findAllUsersTest() throws Exception {
		List<User> users = DAOFactory.getUserDAO().find();
		assertNotNull(users);
	}
	
	@Test
	public void findUsersForAddingTheTestTest() throws Exception {
		List<User> users = DAOFactory.getUserDAO().findUsersForAddingTheTest(1L);
		assertNotNull(users);
	}
	
	@Test
	public void findUsersWithThisTestTest() throws Exception {
		List<User> users = DAOFactory.getUserDAO().findUsersWithThisTest(1L);
		assertNotNull(users);
	}
	
	@Test
	public void addUserTest() throws Exception {
		User user = new User();
		user.setName("test2");
		user.setPassword("test2");
		Role role = DAOFactory.getRoleDAO().find(2L);
		user.setRole(role);
		List<User> users = DAOFactory.getUserDAO().find();
		int oldUsersSize = users.size();
		DAOFactory.getUserDAO().add(user);
		int newUsersSize = users.size();
		assertEquals(newUsersSize, oldUsersSize);
	}
	
	@Test
	public void updateUser() throws Exception {
		User user = DAOFactory.getUserDAO().findUserByLogin("test2");
		user.setPassword("test22");
		DAOFactory.getUserDAO().update(user);
		User userAfterUpdate = DAOFactory.getUserDAO().findUserByLogin("test2");
		assertEquals("test22", userAfterUpdate.getPassword());
	}
	
	@Test
	public void addUsersToTheTestTest() throws Exception {
		User user = DAOFactory.getUserDAO().findUserByLogin("test2");
		List<User> users = new ArrayList<>();
		users.add(user);
		DAOFactory.getUserDAO().addUsersToTheTest(68L, users);
		List<User> usersAfter = DAOFactory.getUserDAO().findUsersWithThisTest(68L);
		int usersSize = usersAfter.size();
		assertEquals(usersSize, usersAfter.size());
		
	}
}
