package sample.org.controllers;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import sample.org.constants.EmployeeAppConstants;
import sample.org.dao.EmployeeAppDAO;
import sample.org.dto.Department;
import sample.org.dto.Employee;
import sample.org.dto.ErrorResponse;
import sample.org.manager.InvocationManager;
import sample.org.util.ValidationException;


/**
 * Created by Vel on 06/22/16.
 */
@RestController
@RequestMapping("/employee")
public class EmployeeAppController {

    @Autowired
    public HttpServletRequest request;

    @Autowired
    public EmployeeAppDAO employeeAppDAO;
    
    @Autowired
    public InvocationManager invocationManager;
    
    

     @RequestMapping(value = "/details", method = RequestMethod.GET)
     @ApiResponses(value = {
     @ApiResponse(code = 200, message = "Success", response = Employee.class),
     @ApiResponse(code = 204, message = "No Content"),
	 @ApiResponse(code = 400, message = "Bad Request", response = ErrorResponse.class)
	})
    public @ResponseBody
    Employee getEmployeeDetails(@RequestParam("employeeId") String employeeId) throws ValidationException
        {
    	Employee employee=null;
    	if(null == employeeId)
    	{
    		throw new ValidationException(EmployeeAppConstants.EMPLOYEE_ID_MISSING);
    	}
    	employee=employeeAppDAO.findEmployee(employeeId);
    	return employee;
        
        }
     
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public @ResponseBody String deleteEmployee(@RequestParam("employeeId") String employeeId) throws ValidationException
    {
    	String response= null;
    	if(null == employeeId)
    	{
    		throw new ValidationException(EmployeeAppConstants.EMPLOYEE_ID_MISSING);
    	}
    	employeeAppDAO.deleteEmployee(employeeId);
    	response= "Deletion completed Successfully";
    	return response; 
    
    }
    
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public @ResponseBody String addEmployee(@RequestBody Employee employeeRequest) throws sample.org.util.ValidationException
    {
    	String response= null;
    	boolean isDepartmentValidationPassed;
    	if(null == employeeRequest)
    	{
    		throw new ValidationException(EmployeeAppConstants.EMPLOYEE_REQUEST_MISSING);
    	}
    	
    	if(null==employeeRequest.getId())
    	{
    		throw new ValidationException(EmployeeAppConstants.EMPLOYEE_ID_MISSING);
    	}
    	
    	if(null==employeeRequest.getName())
    	{
    		throw new ValidationException(EmployeeAppConstants.EMPLOYEE_NAME_MISSING);
    	}
    	
    	if(null==employeeRequest.getManager_name())
    	{
    		throw new ValidationException(EmployeeAppConstants.MANAGER_NAME_MISSING);
    	}
    	
    	if(null==employeeRequest.getDept_id())
    	{
    		throw new ValidationException(EmployeeAppConstants.DEPARTMENT_ID_MISSING);
    	}
    	if(!(employeeRequest.getSalary() >0))
    	{
    		throw new ValidationException(EmployeeAppConstants.SALARY_MISSING);	
    	}
    	
        isDepartmentValidationPassed=validateDepartment(employeeRequest.getDept_id(),employeeRequest.getSalary());
    	
    	if(isDepartmentValidationPassed)
    	{
    	employeeAppDAO.addEmployee(employeeRequest);
    	response = "Employee Details Added Successfully";
    	}
    	else
    	{
    	response = "Department Salary Validation failed";	
    	}
    	
    	return response; 
    
    }
    
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public @ResponseBody String updateEmployee(@RequestBody Employee employeeRequest) throws ValidationException
    {
    	String response= null;
    	boolean isDepartmentValidationPassed;
    	if(null == employeeRequest)
    	{
    		throw new ValidationException(EmployeeAppConstants.EMPLOYEE_REQUEST_MISSING);
    	}
    	
    	if(null==employeeRequest.getId())
    	{
    		throw new ValidationException(EmployeeAppConstants.EMPLOYEE_ID_MISSING);
    	}
    	
    	if(null==employeeRequest.getManager_name())
    	{
    		throw new ValidationException(EmployeeAppConstants.MANAGER_NAME_MISSING);
    	}
    	
    	isDepartmentValidationPassed=validateDepartment(employeeRequest.getDept_id(),employeeRequest.getSalary());
    	
    	if(isDepartmentValidationPassed)
    	{
    	employeeAppDAO.updateEmployee(employeeRequest);
    	response = "Employee Details Updated Successfully";
    	}
    	else
    	{
    	response = "Department Salary Validation failed";	
    	}
    	 
    	return response;
    }
    
    @RequestMapping(value = "/updateSalary", method = RequestMethod.POST)
    public @ResponseBody String updateSalary(@RequestBody Employee employeeRequest) throws ValidationException
    {
    	String response= null;
    	if(null == employeeRequest)
    	{
    		throw new ValidationException(EmployeeAppConstants.EMPLOYEE_REQUEST_MISSING);
    	}
    	
    	if(null==employeeRequest.getId())
    	{
    		throw new ValidationException(EmployeeAppConstants.EMPLOYEE_ID_MISSING);
    	}
    	
    	if(!(employeeRequest.getSalary()>0))
    	{
    		throw new ValidationException(EmployeeAppConstants.SALARY_MISSING);
    	}
    	
    	employeeAppDAO.updateSalary(employeeRequest);
    	response = "Salary Details Updated Successfully";
    	return response; 
    	
    }
    
    public boolean validateDepartment(String deptId,int salaryAmt) throws sample.org.util.ValidationException
    {
    	Department department =null;
    	boolean validity=false;
    	department=invocationManager.invokeMicroservice(deptId);
    	if(null !=department)
    	{
    	if(salaryAmt > Integer.parseInt(department.getMinSalary()) && salaryAmt < Integer.parseInt(department.getMaxSalary()))
    	{
    		validity= true;
    	}
    	else
    		validity= false;
    	}
    	else
    		validity= false;
    	return validity;
    }

    
}