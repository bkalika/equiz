package com.equiz.db;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({TestAnswerDAO.class, TestQuestionDAO.class, TestRoleDAO.class, TestSubjectDAO.class, TestTestDAO.class, TestUserDAO.class})
public class AllTests {

}
