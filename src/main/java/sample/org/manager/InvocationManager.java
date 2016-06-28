package sample.org.manager;

import javax.annotation.PostConstruct;
import org.apache.commons.lang3.time.StopWatch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import sample.org.dto.Department;
import sample.org.util.ValidationException;


/**
 * Created by associate on 2/12/16.
 */

@Component
public class InvocationManager
{
    @Value("${departmentApp.url}")
    private String departmentAppURL;
    
    private String serviceUrl;

    @Autowired
    RestTemplate restTemplate;

    @PostConstruct
    public void postConstruct()
    {
    	serviceUrl = departmentAppURL + "/department/details";
    }

    public Department invokeMicroservice(String deptId) throws ValidationException
    {
    	Department department=null;
        try
        {
        	
            StopWatch stopWatch = new StopWatch();
            stopWatch.start();
            
                       
            RestTemplate restTemplate = new RestTemplate();

            department= restTemplate.getForObject(serviceUrl + "/" + deptId
                , Department.class);

            stopWatch.stop();
        }
        catch (RestClientException rexp)
        {
           // LogManager.stdErr(rexp);
            //throw new Exception();
        }
        catch (Exception exp)
		{
			System.out.println("Exception while invoking microservice. Exception message - " + exp.getMessage());
		}
        return department;
    }

    public void setRestTemplate(RestTemplate restTemplate)
    {
        this.restTemplate = restTemplate;
    }

   
}
