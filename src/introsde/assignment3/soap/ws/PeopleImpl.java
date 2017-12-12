package introsde.assignment3.soap.ws;

import java.util.ArrayList;
import java.util.List;

//import javax.jws.WebParam;
import javax.jws.WebService;

import introsde.assignment3.soap.model.Activity;
import introsde.assignment3.soap.model.ActivityType;
import introsde.assignment3.soap.model.Person;

//Service Implementation
@WebService(
		endpointInterface = "introsde.assignment3.soap.ws.People"
		)
public class PeopleImpl implements People {

	//method 1
	@Override
	public List<Person> getPeople() {
		return Person.getAll();
	}
	
	//method 2
	@Override
	public Person readPerson(int id) {
		System.out.println("---> Reading Person by id = "+id);
		Person p = Person.getPersonById(id);
		if (p!=null) {
			System.out.println("---> Found Person by id = "+id+" => "+p.getFirstname());
		} else {
			System.out.println("---> Didn't find any Person with  id = "+id);
		}
		return p;
	}

	//method 3
	@Override
	public Person updatePerson(Person person) {
		List<Person> people=Person.getAll();
		for(Person p:people) {
			if(p.getIdPerson()==person.getIdPerson()) {
				Person.updatePerson(person);
		        return person;
			}
		}
		return null;
	}

	//method 4
	@Override
	public Person addPerson(Person person) {
		int aId=Activity.getNewId();
		List<ActivityType> atList=ActivityType.getAll();
		for(Activity a:person.getActivities()) {
			a.setIdActivity(aId);
			aId++;
			int flag=0;
			ActivityType at=a.getType();
			for(ActivityType existingAt:atList) {
				if (at.getAtName().equals(existingAt.getAtName())) {
					//existingAt.addActivity(a);
					ActivityType.updatePerson(existingAt);
					flag=1;
				}
			}
			if (flag==0) {
				ActivityType newAt=ActivityType.savePerson(at);
				//newAt.addActivity(a);
				ActivityType.updatePerson(newAt);
			}
		}
		person.setIdPerson(Person.getNewId());
		Person.savePerson(person);
		return person;
	}

	//method 5
	@Override
	public String deletePerson(int id) {
		Person p = Person.getPersonById(id);
		if (p!=null) {
			Person.removePerson(p);
			return "Removed";
		} else {
			return "Person don't exist";
		}
	}
	
	//method 6
	@Override
	public List<Activity> readPersonPreferences1(int id, String activity_type){
		Person p=Person.getPersonById(id);
		List<Activity> personActivities=new ArrayList<Activity>();
		for (Activity a: p.getActivities()) {
			if(a.getType().getAtName().equals(activity_type))
				personActivities.add(a);
		}
		return personActivities;
	}
	
	//method 7
	@Override
	public List<Activity> readPreferences(){
		return Activity.getAll();
	}

	//method 8
	public Activity readPersonPreferences2(int id, int activityId) {
		Person p=Person.getPersonById(id);
		Activity a=new Activity();
		for(Activity temp:p.getActivities())
			if(temp.getIdActivity()==activityId)
				return temp;
		return a;
	}
	
	//method 9
	@Override
	public String savePersonPreferences(int id, Activity activity) {
		Person p=Person.getPersonById(id);
		List<ActivityType> atList=ActivityType.getAll();
		for (ActivityType at:atList) {
			if(at.getAtName().equals(activity.getType().getAtName())) {
				activity.setIdActivity(Activity.getNewId());
				p.getActivities().add(activity);
				Person.updatePerson(p);
				return "ok";
			}			
		}
		ActivityType newAt=new ActivityType();
		newAt.setAtName(activity.getType().getAtName());
		ActivityType.savePerson(newAt);
		activity.setIdActivity(Activity.getNewId());
		p.getActivities().add(activity);
		Person.updatePerson(p);
		return "ok";
	}
	
	//method 10
	@Override
	public Activity updatePersonPreferences(int id, Activity activity) {
		Person p=Person.getPersonById(id);
		Activity old=Activity.getActivityById(activity.getIdActivity());
		if(activity.getName()!=null)
			old.setName(activity.getName());
		if(activity.getDescription()!=null)
			old.setDescription(activity.getDescription());
		if(activity.getPlace()!=null)
			old.setPlace(activity.getPlace());
		if(activity.getStartdate()!=null)
			old.setStartdate(activity.getStartdate());
		p.getActivities().add(activity);
		Activity.updateLifeStatus(old);
		return old;
	}
	
	//method 11
	@Override
	public Activity evaluatePersonPreferences(int id, Activity activity, int value) {
		Person p=Person.getPersonById(id);
		for(Activity a:p.getActivities())
			if(a.getIdActivity()==activity.getIdActivity()) {
				a.setRating(value);
				Person.updatePerson(p);
				return a;
			}
		return new Activity();
	}

	//method 12
	@Override
    public List<Activity> getBestPersonPreference(int id){
		List<Activity> returnlist=new ArrayList<Activity>();
		Person p=Person.getPersonById(id);
		int max=-1;
		for(Activity a:p.getActivities()) {
			if(a.getRating()>max) {
				max=a.getRating();
				returnlist=new ArrayList<Activity>();
				returnlist.add(a);
			}
			else if(a.getRating()==max)
				returnlist.add(a);
		}
		return returnlist;
	}
	
	//init DB
	public List<Person> initDB(){
		if(ActivityType.getAll().size()>0) {
			for(ActivityType at:ActivityType.getAll())
				ActivityType.removePerson(at);
		}
		if(Activity.getAll().size()>0) {
			for(Activity a:Activity.getAll())
				Activity.removeLifeStatus(a);
		}
		if(Person.getAll().size()>0) {
			for(Person p:Person.getAll())
				Person.removePerson(p);
		}
		
		ActivityType at;
    	at=new ActivityType();
    	at.setAtName("Videogaming");
    	ActivityType.savePerson(at);
    	at=new ActivityType();
    	at.setAtName("Sport");
    	ActivityType.savePerson(at);
    	at=new ActivityType();
    	at.setAtName("Studying");
    	ActivityType.savePerson(at);
    	
    	Person p;
    	List<Activity> activitylist=new ArrayList<Activity>();
    	p=new Person();
    	p.setIdPerson(Person.getNewId());
    	p.setFirstname("Mattia");
    	p.setLastname("Buffa");
    	p.setBirthdate("1992-02-15");
    	Activity a;
    	a=new Activity();
    	a.setIdActivity(Activity.getNewId());
    	a.setName("Playing PUBG");
    	a.setDescription("Playing the PUBG videogame on PC");
    	a.setPlace("Home");
    	a.setStartdate("2017-11-24-15:00");
    	a.setRating(4);
    	a.setPerson(p);
    	a.setType(ActivityType.getPersonById("Videogaming"));
    	activitylist.add(a);
    	p.setActivities(activitylist);
    	Person.savePerson(p);
    	//ActivityDAO.saveActivity(a);
    	
    	a=new Activity();
    	p=new Person();
    	activitylist=new ArrayList<Activity>();
    	p.setIdPerson(Person.getNewId());
    	p.setFirstname("Denis");
    	p.setLastname("Gallo");
    	p.setBirthdate("1993-04-15");
    	a.setIdActivity(Activity.getNewId());
    	a.setName("Playing LOL");
    	a.setDescription("Playing League Of Legends on PC");
    	a.setPlace("Home");
    	a.setStartdate("2017-11-25-18:25");
    	a.setRating(3);
    	a.setPerson(p);
    	a.setType(ActivityType.getPersonById("Videogaming"));
    	//ActivityDAO.saveActivity(a);
    	activitylist.add(a);
    	p.setActivities(activitylist);
    	Person.savePerson(p);
    	
    	a=new Activity();
    	p=new Person();
    	activitylist=new ArrayList<Activity>();
    	p.setIdPerson(Person.getNewId());
    	p.setFirstname("Linda");
    	p.setLastname("Bertolli");
    	p.setBirthdate("1994-05-24");
    	a.setIdActivity(Activity.getNewId());
    	a.setName("Studying SDE");
    	a.setDescription("Studying the Theory ot SDE Course");
    	a.setPlace("Library");
    	a.setStartdate("2017-12-01-08:22");
    	a.setRating(3);
    	a.setPerson(p);
    	a.setType(ActivityType.getPersonById("Studying"));
    	//ActivityDAO.saveActivity(a);
    	activitylist.add(a);
    	p.setActivities(activitylist);
    	Person.savePerson(p);
    	
    	a=new Activity();
    	p=new Person();
    	activitylist=new ArrayList<Activity>();
    	p.setIdPerson(Person.getNewId());
    	p.setFirstname("Enrico");
    	p.setLastname("Tomiello");
    	p.setBirthdate("1994-11-24");
    	a.setIdActivity(Activity.getNewId());
    	a.setName("Jogging");
    	a.setDescription("Running in the morning");
    	a.setPlace("Trento");
    	a.setStartdate("2017-12-12-08:44");
    	a.setRating(1);
    	a.setPerson(p);
    	a.setType(ActivityType.getPersonById("Sport"));
    	//ActivityDAO.saveActivity(a);
      	activitylist.add(a);
    	p.setActivities(activitylist);
    	Person.savePerson(p);
    	
    	a=new Activity();
    	p=new Person();
    	activitylist=new ArrayList<Activity>();
    	p.setIdPerson(Person.getNewId());
    	p.setFirstname("Christian");
    	p.setLastname("Corso");
    	p.setBirthdate("1995-08-22");
    	a.setIdActivity(Activity.getNewId());
    	a.setName("Tennis");
    	a.setDescription("Playing Tennis");
    	a.setPlace("Trento");
    	a.setStartdate("2017-11-30-17:33");
    	a.setRating(2);
    	a.setPerson(p);
    	a.setType(ActivityType.getPersonById("Sport"));
    	//ActivityDAO.saveActivity(a);
    	activitylist.add(a);
    	p.setActivities(activitylist);
    	Person.savePerson(p);
    	
    	System.out.println("Persondb size is "+Person.getAll().size()+" and activitydb size is "+Activity.getAll().size()+" and activitytypesdb size is "+ActivityType.getAll().size());   	
	    List<Person> people = Person.getAll();
		return people;
	}

	

	/*@Override
	public int updatePersonHP(int id, LifeStatus hp) {
		LifeStatus ls = LifeStatus.getLifeStatusById(hp.getIdMeasure());
		if (ls.getPerson().getIdPerson() == id) {
			LifeStatus.updateLifeStatus(hp);
			return hp.getIdMeasure();
		} else {
			return -1;
		}
	}*/

}
