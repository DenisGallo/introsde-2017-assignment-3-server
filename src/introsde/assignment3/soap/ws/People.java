package introsde.assignment3.soap.ws;
import introsde.assignment3.soap.model.*;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.WebResult;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;

@WebService
@SOAPBinding(style = Style.DOCUMENT, use=Use.LITERAL) //optional
public interface People {
	
	//method 1
    @WebMethod(operationName="readPersonList")
    @WebResult(name="people") 
    public List<Person> getPeople();
    
    //method 2
    @WebMethod(operationName="readPerson")
    @WebResult(name="person") 
    public Person readPerson(@WebParam(name="personId") int id);

    //method 3
    @WebMethod(operationName="updatePerson")
    @WebResult(name="person") 
    public Person updatePerson(@WebParam(name="personIn") Person person);

    //method 4
    @WebMethod(operationName="createPerson")
    @WebResult(name="person") 
    public Person addPerson(@WebParam(name="personIn") Person person);
 
    //method 5    
    @WebMethod(operationName="deletePerson")
    @WebResult(name="result") 
    public String deletePerson(@WebParam(name="personId") int id);
    
    //method 6
    @WebMethod(operationName="readPersonPreferences1")
    @WebResult(name="activities")
    public List<Activity> readPersonPreferences1(@WebParam(name="personId") int id, @WebParam(name="atName") String activity_type);
    
    //method 7    
    @WebMethod(operationName="readPreferences")
    @WebResult(name="activities") 
    public List<Activity> readPreferences();
    
    //method 8   
    @WebMethod(operationName="readPersonPreferences2")
    @WebResult(name="activity") 
    public Activity readPersonPreferences2(@WebParam(name="personId") int id, @WebParam(name="idActivity") int activityId);
    
    //method 9
    @WebMethod(operationName="savePersonPreferences")
    @WebResult(name="result")
    public String savePersonPreferences(@WebParam(name="personId") int id, @WebParam(name="activity") Activity activity); 

    //method 10
    @WebMethod(operationName="updatePersonPreferences")
    @WebResult(name="activity")
    public Activity updatePersonPreferences(@WebParam(name="personId") int id, @WebParam(name="activityIn") Activity activity); 
    
    //method 11
    @WebMethod(operationName="evaluatePersonPreferences")
    @WebResult(name="activity")
    public Activity evaluatePersonPreferences(@WebParam(name="personId") int id, @WebParam(name="activityIn") Activity activity, @WebParam(name="rating") int value);

    //method 12
    @WebMethod(operationName="getBestPersonPreference")
    @WebResult(name="activities")
    public List<Activity> getBestPersonPreference(@WebParam(name="personId") int id);
    
    //init db
    @WebMethod(operationName="initDB")
    @WebResult(name="people")
    public List<Person> initDB();
}
