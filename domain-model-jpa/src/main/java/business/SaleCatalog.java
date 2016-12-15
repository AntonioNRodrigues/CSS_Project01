package business;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

import com.sun.org.apache.xml.internal.resolver.Catalog;

import business.entities.Transation;
import business.entities.TransationCatalog;

/**
 * A catalog for sales
 * 
 * @author fmartins
 * @version 1.1 (17/04/2015)
 * @modified by:
 * @author Antonio Rodrigues
 * @author Simão Neves
 * @author Joao Rodrigues
 * @Group:: css018
 * @Date 2016/04/28
 */

public class SaleCatalog {

	/**
	 * Entity manager factory for accessing the persistence service
	 */
	private EntityManagerFactory emf;

	/**
	 * Constructs a sale's catalog giving a entity manager factory
	 */
	public SaleCatalog(EntityManagerFactory emf) {
		this.emf = emf;
	}

	/**
	 * Creates a new sale and adds it to the repository
	 * 
	 * @param customer
	 *            The customer the sales belongs to
	 * @return The newly created sale
	 */
	public Sale newSale(Customer customer) throws ApplicationException {
		EntityManager em = emf.createEntityManager();
		try {
			em.getTransaction().begin();
			Sale sale = new Sale(new Date(), customer);
			em.persist(sale);
			em.getTransaction().commit();
			return sale;
		} catch (Exception e) {
			if (em.getTransaction().isActive())
				em.getTransaction().rollback();
			throw new ApplicationException("Error adding sale", e);
		} finally {
			em.close();
		}
	}

	/**
	 * @param sale
	 * @param product
	 * @param qty
	 * @throws ApplicationException
	 */
	public Sale addProductToSale(Sale sale, Product product, double qty) throws ApplicationException {
		EntityManager em = emf.createEntityManager();
		try {
			em.getTransaction().begin();
			sale = em.merge(sale);
			sale.addProductToSale(product, qty);
			em.merge(product);
			em.getTransaction().commit();
		} catch (Exception e) {
			if (em.getTransaction().isActive())
				em.getTransaction().rollback();
			throw new ApplicationException("Error adding product to sale", e);
		} finally {
			em.close();
		}
		return sale;
	}

	public void updateSale(Sale sale) throws ApplicationException {
		EntityManager em = emf.createEntityManager();
		try {
			em.getTransaction().begin();
			em.merge(sale);
			em.getTransaction().commit();
		} catch (Exception e) {
			if (em.getTransaction().isActive())
				em.getTransaction().rollback();
			throw new ApplicationException("Error updating sale", e);
		} finally {
			em.close();
		}

	}

	public Sale getSale(int idSale) throws ApplicationException {
		EntityManager em = emf.createEntityManager();
		TypedQuery<Sale> query = em.createNamedQuery(Sale.FIND_BY_ID, Sale.class);
		
		query.setParameter(Sale.FIND_BY_ID, idSale);
		try {
			return query.getSingleResult();
		} catch (Exception e) {
			throw new ApplicationException("Probling getting the Sale", e);
		} finally {
			em.close();
		}
	}

}
