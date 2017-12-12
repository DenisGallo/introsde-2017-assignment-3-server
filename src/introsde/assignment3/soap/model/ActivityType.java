package introsde.assignment3.soap.model;
import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;


import introsde.assignment3.soap.dao.LifeCoachDao;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@Table(name="ActivityType")
@NamedQuery(name="ActivityType.findAll", query="SELECT at FROM ActivityType at")
public class ActivityType implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -747400587968483953L;

	@Id
	@Column(name="atName")
	private String atName;

	@OneToMany(mappedBy="type", cascade = CascadeType.ALL)
	@XmlTransient
	private List<Activity> activities;
	
	public ActivityType() {
		
	}

	public String getAtName() {
		return atName;
	}

	public void setAtName(String atName) {
		this.atName = atName;
	}

	public List<Activity> getActivities() {
		return activities;
	}

	public void setActivities(List<Activity> activities) {
		this.activities = activities;
	}
	
	public void addActivity(Activity activity) {
        this.activities.add(activity);
        if (activity.getType() != this) {
            activity.setType(this);
        }
    }
	
	// Database operations
	// Notice that, for this example, we create and destroy and entityManager on each operation. 
	// How would you change the DAO to not having to create the entity manager every time? 
	public static ActivityType getPersonById(String atName) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		ActivityType p = em.find(ActivityType.class, atName);
		LifeCoachDao.instance.closeConnections(em);
		return p;
	}
	
	public static List<ActivityType> getAll() {

		System.out.println("--> Initializing Entity manager...");
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		System.out.println("--> Querying the database for all the people...");
	    List<ActivityType> list = em.createNamedQuery("ActivityType.findAll", ActivityType.class).getResultList();
		System.out.println("--> Closing connections of entity manager...");
	    LifeCoachDao.instance.closeConnections(em);
	    return list;
	}
	
	public static ActivityType savePerson(ActivityType p) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.persist(p);
		tx.commit();
	    LifeCoachDao.instance.closeConnections(em);
	    return p;
	}
	
	public static ActivityType updatePerson(ActivityType p) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		p=em.merge(p);
		tx.commit();
	    LifeCoachDao.instance.closeConnections(em);
	    return p;
	}
	
	public static void removePerson(ActivityType p) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
	    p=em.merge(p);
	    em.remove(p);
	    tx.commit();
	    LifeCoachDao.instance.closeConnections(em);
	}




}
