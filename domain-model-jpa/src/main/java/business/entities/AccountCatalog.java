package business.entities;
/**
 * Entity implementation class for Entity: Account
 * 
 * @author Antonio Rodrigues
 * @author Simão Neves
 * @author Joao Rodrigues
 * @Group:: css018
 * @Date 2016/04/28
 *
 */
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

import business.ApplicationException;

public class AccountCatalog {
	private EntityManagerFactory emf;

	/**
	 * 
	 * @param emf
	 */
	public AccountCatalog(EntityManagerFactory emf) {
		this.emf = emf;
	}

	/**
	 * 
	 * @param balance
	 * @throws ApplicationException
	 */

	public void addAccount(double balance) throws ApplicationException {
		EntityManager em = emf.createEntityManager();
		try {
			em.getTransaction().begin();
			Account c = new Account(balance);
			em.persist(c);
			em.getTransaction().commit();
		} catch (Exception e) {
			throw new ApplicationException("Adding Account has failed", e);
		} finally {
			em.close();
		}
	}

	/**
	 * 
	 * @throws ApplicationException
	 */
	public void addAccount() throws ApplicationException {
		addAccount(0.0);
	}

	/**
	 * 
	 * @param id
	 * @return
	 * @throws ApplicationException
	 */
	public Account getAccount(int id) throws ApplicationException {
		EntityManager em = emf.createEntityManager();
		TypedQuery<Account> query = em.createNamedQuery(Account.FIND_BY_ID, Account.class);
		query.setParameter(Account.FIND_BY_ID, id);
		try {
			return query.getSingleResult();
		} catch (Exception e) {
			throw new ApplicationException("Getting the account failed", e);
		} finally {
			em.close();
		}
	}
	/**
	 * 
	 * @param account
	 * @throws ApplicationException
	 */
	public void updateAccount(Account account) throws ApplicationException {
		EntityManager em = emf.createEntityManager();

		try {
			em.getTransaction().begin();
			em.merge(account);
			em.getTransaction().commit();

		} catch (Exception e) {
			if (em.getTransaction().isActive())
				em.getTransaction().rollback();
			throw new ApplicationException("Error Updating the Account", e);
		} finally {
			em.close();
		}
	}

}
