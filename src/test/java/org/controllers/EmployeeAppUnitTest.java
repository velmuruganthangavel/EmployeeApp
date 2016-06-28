package org.controllers;

import static org.junit.Assert.assertEquals;
import org.H2Queries;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import sample.org.controllers.EmployeeAppController;
import sample.org.dto.Employee;
import sample.org.util.ValidationException;
import com.sun.glass.ui.Application;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@TestPropertySource(locations = "classpath:test.properties")
public class EmployeeAppUnitTest
{
	   
    @Autowired
	private JdbcTemplate jdbcTemplate;
    
    @Autowired
    private EmployeeAppController employeeAppController;

		@Test
		@Ignore
		public void testEmployeeDetails() throws ValidationException
		{
			try
			{
				Employee employee=employeeAppController.getEmployeeDetails("222");
				assertEquals("Francis",employee.getName());
				assertEquals(8000,employee.getSalary());
				assertEquals("10",employee.getDept_id());
			}
			catch (sample.org.util.ValidationException e)
			{
				e.printStackTrace();
			}
        }

		@Before
		public void setup()
		{
			jdbcTemplate.execute("DROP TABLE EMPLOYEE IF EXISTS;");
			jdbcTemplate.execute(H2Queries.CREATE_EMPLOYEE);
			jdbcTemplate.execute(H2Queries.INSERT_EMPLOYEE
				+ "SELECT * FROM CSVREAD('classpath:Employeee.csv');");
		}

	public EmployeeAppUnitTest()
	{
		// TODO Auto-generated constructor stub
	}

}
