package org;

public class H2Queries
{
	
public	static final String CREATE_EMPLOYEE = ""
		+ "CREATE TABLE EMPLOYEEE "
		+ "  ( "
		+ "     ID           VARCHAR, "
		+ "     NAME         VARCHAR, "
		+ "     MANAGER_NAME VARCHAR, "
		+ "     DEPT_ID      VARCHAR, "
		+ "     SALARY       VARCHAR "
		+ "  )";
	
	public static final String INSERT_EMPLOYEE = ""
		+ "INSERT INTO EMPLOYEEE "
		+ "            ( "
		+ "                    ID           , "
		+ "                    NAME         , "
		+ "                    MANAGER_NAME , "
		+ "                    DEPT_ID      , "
		+ "                    SALARY "
		+ "            )";
	
   private H2Queries()
	{
	}
}
