package sample.org.constants;

/**
 * Created by vel on 6/22/16.
 */
public class EmployeeAppQueries
{
    private EmployeeAppQueries()
    {
    }

    public static final String FIND_EMPLOYEE =
    	"SELECT id,name,manager_name,dept_id,salary FROM EMPLOYEEE WHERE id = :employeeID";
    
    public static final String DELETE_EMPLOYEE =
    	"DELETE FROM EMPLOYEEE WHERE id = :employeeID";
    
    public static final String ADD_EMPLOYEE = 
     "INSERT INTO EMPLOYEEE "
    + "            (ID, "
    + "             NAME, "
    + "             MANAGER_NAME, "
    + "             DEPT_ID, "
    + "             SALARY) "
    + "VALUES     (:employeeID, "
    + "            :employeeName, "
    + "            :managerName, "
    + "            :deptId, "
    + "            :salary)";
    
    public static final String UPDATE_MANAGER_NAME = "" +  
    	 "UPDATE EMPLOYEEE "
    	+ "SET    MANAGER_NAME = :managerName "
    	+ "WHERE  ID = :employeeId";
    
    public static final String UPDATE_SALARY = "" 
    	+ "UPDATE EMPLOYEEE "
    	+ "SET    SALARY = :salary "
    	+ "WHERE  ID = :employeeId";
    
}