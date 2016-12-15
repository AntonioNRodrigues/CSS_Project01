package business.entities;

import java.util.Collection;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import org.omg.CORBA.TRANSACTION_MODE;

import business.ApplicationException;
import business.Customer;
import business.CustomerCatalog;
import business.Sale;
import business.SaleCatalog;

public class TransationCatalog {

	private EntityManagerFactory emf;

	/**
	 * 
	 * @param emf
	 */
	public TransationCatalog(EntityManagerFactory emf) {
		this.emf = emf;
	}

	/**
	 * 
	 * @param trans
	 * @throws ApplicationException
	 */
	public void addTransation(Transation trans) throws ApplicationException {
		EntityManager em = emf.createEntityManager();

		try {
			em.getTransaction().begin();
			// The object trans has passed to managed
			em.merge(trans);
			// commit the transation
			em.getTransaction().commit();
		} catch (Exception e) {
			if (em.getTransaction().isActive())
				em.getTransaction().rollback();
			throw new ApplicationException("Error adding the transation", e);
		} finally {
			em.close();
		}
	}
	/**
	 * method to build a new transations and persist 
	 * @param ctr 
	 * @param value
	 * @param date
	 * @throws ApplicationException if the transations is not added
	 */
	public void addTransation(String ctr, double value, Date date, Sale sale, Account account) throws ApplicationException {
		Transation trans = Transation.factory(ctr, value, date, sale, account);
		addTransation(trans);
	}

	/**
	 * Getter of a transation
	 * @param id id of the transation
	 * @return the transation
	 * @throws ApplicationException is there is a probling getting the Transation
	 */
	public Transation getTransation(int id) throws ApplicationException {
		EntityManager em = emf.createEntityManager();
		TypedQuery<Transation> query = em.createNamedQuery(Transation.FIND_ID, Transation.class);
		query.setParameter(Transation.FIND_ID, id);
		try {
			return query.getSingleResult();
		} catch (PersistenceException e) {
			throw new ApplicationException("Error adding the transation", e);
		} finally {
			em.close();
		}
	}
	
	/**
	 * getter of all transations
	 * @return
	 * @throws ApplicationException
	 */
	public Collection<Transation> getTransations() throws ApplicationException {
		EntityManager em = emf.createEntityManager();
		try {
			TypedQuery<Transation> query = em.createQuery(Transation.FIND_ALL, Transation.class);
			return query.getResultList();
		} catch (Exception e) {
			throw new ApplicationException("Error obtaining the Transation list", e);
		} finally {
			em.close();
		}
	}

	/**
	 * update the transation 
	 * @param trans
	 * @throws ApplicationException
	 */
	public void updateTransation(Transation trans) throws ApplicationException {
		EntityManager em = emf.createEntityManager();
		try {
			em.getTransaction().begin();
			em.merge(trans);
			em.getTransaction().commit();
		} catch (Exception e) {
			if (em.getTransaction().isActive())
				em.getTransaction().rollback();
			throw new ApplicationException("Error updating Trans", e);
		} finally {
			em.close();
		}

	}

	public void addTransationToSale(Transation transation, Sale currentSale, Customer customer) throws ApplicationException {
		EntityManager em = emf.createEntityManager();
		try {
			em.getTransaction().begin();
			em.merge(transation);
			em.merge(currentSale);
			em.merge(customer);
			em.getTransaction().commit();
			
		}catch (Exception e){
			if (em.getTransaction().isActive())
				em.getTransaction().rollback();

			throw new ApplicationException("Error adding Transation to Sale", e);
		}finally {
			em.close();
		}
	}
	


}
