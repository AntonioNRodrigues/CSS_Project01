package startup;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import presentation.AddCustomerService;
import presentation.CurrentAccountService;
import presentation.ProcessSaleService;
import business.ApplicationException;
import business.SaleSys;
import client.SimpleClient;

/**
 * Implements the startup use case. It creates the application, a simple client
 * that interacts with the application, passing it the application handlers.
 * 
 * @author fmartins
 * @version 1.0 (08/03/2015)
 *
 */
public class Startup {

	public static void main(String[] args) throws ApplicationException {
		// Creates the entity manager factory that will assist in persisting entities
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("domain-model-jpa");

		// Creates the application main object
		SaleSys app = new SaleSys(emf);

		// Creates the application services
		AddCustomerService cs = new AddCustomerService(app.getAddCustomerHandler());
		ProcessSaleService ss = new ProcessSaleService(app.getProcessSaleHandler());
		CurrentAccountService ca = new CurrentAccountService(app.getCurrentAccountHandler());

		// Creates the simple interaction client and
		// passes it the application handlers
		SimpleClient simpleClient = new SimpleClient(cs, ss, ca);
		
		simpleClient.createASale();
 		simpleClient.checkAccount();
 		
		emf.close();
	}
}
