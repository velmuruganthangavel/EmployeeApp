package sample.org.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import sample.org.constants.EmployeeAppQueries;
import sample.org.dto.Employee;


/**
 * Created by vel on 06/22/16.
 */
@Component
public class EmployeeAppDAO
{
    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;
    
    public Employee findEmployee(String employeeID)
    {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("employeeID", employeeID);

        return jdbcTemplate.query(EmployeeAppQueries.FIND_EMPLOYEE, parameters, new ResultSetExtractor<Employee>()
        {
            @Override
            public Employee extractData(ResultSet rs) throws SQLException, DataAccessException
            {
                Employee employee = new Employee();
                while(rs.next())
                {
                    employee.setId(rs.getString("id"));
                    employee.setName(rs.getString("name"));
                    employee.setManager_name(rs.getString("manager_name"));
                    employee.setDept_id(rs.getString("dept_id"));
                    employee.setSalary(Integer.parseInt(rs.getString("salary")));
                }
                return employee;
            }
        });
    }
    
    public void deleteEmployee(String employeeID)
    {
    	MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("employeeID", employeeID);

        jdbcTemplate.update(EmployeeAppQueries.DELETE_EMPLOYEE, parameters);
        return;
        
    }
    
    public void addEmployee(Employee employeeReq)
    {
    	MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("employeeID", employeeReq.getId());
        parameters.addValue("employeeName", employeeReq.getName());
        parameters.addValue("managerName", employeeReq.getManager_name());
        parameters.addValue("deptId", employeeReq.getDept_id());
        parameters.addValue("salary", employeeReq.getSalary());

        jdbcTemplate.update(EmployeeAppQueries.ADD_EMPLOYEE, parameters);
        return;
        
    }
    
    public void updateEmployee(Employee employeeReq)
    {
    	MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("employeeId", employeeReq.getId());
        parameters.addValue("managerName", employeeReq.getManager_name());
        jdbcTemplate.update(EmployeeAppQueries.UPDATE_MANAGER_NAME, parameters);
        return;
    }
    
    public void updateSalary(Employee employeeReq)
    {
    	MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("employeeId", employeeReq.getId());
        parameters.addValue("salary", employeeReq.getSalary());
        jdbcTemplate.update(EmployeeAppQueries.UPDATE_SALARY, parameters);
        return;
    }
    
}

