package business;

import javax.persistence.EntityManagerFactory;

import business.entities.AccountCatalog;
import business.entities.TransationCatalog;

/**
 * The initial object. The first domain object built during the startup.
 * 
 * @author fmartins
 * @version 1.0 (17/04/2015)
 *
 */
public class SaleSys {

	/**
	 * The add customer use case handler
	 */
	private AddCustomerHandler addCustomerHandler;

	/**
	 * The process sale use case handler
	 */
	private ProcessSaleHandler processSaleHandler;

	private CurrentAccountHandler currentAccountHandler;

	/**
	 * Performs the start up use case
	 */
	public SaleSys(EntityManagerFactory emf) {
		CustomerCatalog customerCatalog = new CustomerCatalog(emf);
		this.addCustomerHandler = new AddCustomerHandler(customerCatalog, new DiscountCatalog(emf));
		this.processSaleHandler = new ProcessSaleHandler(new SaleCatalog(emf), customerCatalog, new ProductCatalog(emf),
				new TransationCatalog(emf), new AccountCatalog(emf));
		this.currentAccountHandler = new CurrentAccountHandler(new SaleCatalog(emf), customerCatalog, new TransationCatalog(emf), new AccountCatalog(emf));
	}

	/**
	 * @return The add customer use case handler
	 */
	public AddCustomerHandler getAddCustomerHandler() {
		return addCustomerHandler;
	}

	/**
	 * @return The process sale use case handler
	 */
	public ProcessSaleHandler getProcessSaleHandler() {
		return processSaleHandler;
	}

	public CurrentAccountHandler getCurrentAccountHandler() {
		return currentAccountHandler;
	}
}