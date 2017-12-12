package introsde.assignment3.soap.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import introsde.assignment3.soap.dao.LifeCoachDao;

@XmlRootElement(name="activity")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@Table(name ="Activity")
@NamedQuery(name="Activity.findAll", query="SELECT a FROM Activity a")
public class Activity implements Serializable{

	private static final long serialVersionUID = -445012277184098930L;



	@Id @GeneratedValue
	@Column(name="idActivity")
	@XmlAttribute
	private int idActivity;
	
	@Column
	private String name;
	
	@Column
	private String description;
	
	@Column
	private String startdate;
	
	@Column
	private String place;
	
	@Column
	private int rating;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idPerson")
	@XmlTransient
	private Person person;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="atName")
	@XmlElement(name="activity_type")
	private ActivityType type;
	
	public Activity() {}
    public Activity(Activity a) {
    	this.idActivity=a.idActivity;
    	this.name=a.name;
    	this.description=a.description;
    	this.place=a.place;
    	this.type=a.type;
    	this.startdate=a.startdate;
    	this.person=a.person;
    }

	public int getIdActivity() {
		return idActivity;
	}

	public void setIdActivity(int idActivity) {
		this.idActivity = idActivity;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStartdate() {
		return startdate;
	}

	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
		/*if(!person.getActivities().contains(this)) {
			person.getActivities().add(this);
		}*/
	}

	public ActivityType getType() {
		return type;
	}

	public void setType(ActivityType type) {
		this.type = type;
		/*if(!type.getActivities().contains(this)) {
			type.getActivities().add(this);
		}*/
	}
	
	// Database operations
	// Notice that, for this example, we create and destroy and entityManager on each operation. 
	// How would you change the DAO to not having to create the entity manager every time? 
	public static Activity getActivityById(int lifestatusId) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		Activity p = em.find(Activity.class, lifestatusId);
		LifeCoachDao.instance.closeConnections(em);
		return p;
	}
	
	public static List<Activity> getAll() {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
	    List<Activity> list = em.createNamedQuery("Activity.findAll", Activity.class).getResultList();
	    LifeCoachDao.instance.closeConnections(em);
	    return list;
	}
	
	public static Activity saveLifeStatus(Activity p) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.persist(p);
		tx.commit();
	    LifeCoachDao.instance.closeConnections(em);
	    return p;
	}
	
	public static Activity updateLifeStatus(Activity p) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		p=em.merge(p);
		tx.commit();
	    LifeCoachDao.instance.closeConnections(em);
	    return p;
	}
	
	public static int getNewId() {
	       EntityManager em = LifeCoachDao.instance.createEntityManager();
	       return (int) em.createQuery("select max(p.idActivity) from Activity p").getSingleResult()+1;
	     }
	
	public static void removeLifeStatus(Activity p) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
	    p=em.merge(p);
	    em.remove(p);
	    tx.commit();
	    LifeCoachDao.instance.closeConnections(em);
	}
	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}


}
