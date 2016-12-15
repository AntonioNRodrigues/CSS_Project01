package client;

import presentation.AddCustomerService;
import presentation.CurrentAccountService;
import presentation.ProcessSaleService;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.omg.CORBA.INTERNAL;

import business.entities.Transation;

import business.ApplicationException;
import business.CurrentAccountHandler;
import business.Customer;
import business.CustomerCatalog;
import business.DiscountCatalog;
import business.entities.Account;
import business.entities.AccountCatalog;

/**
 * A simple application client that uses both application handlers.
 * 
 * @author fmartins
 * @version 1.1 (17/04/2015)
 * @modified by
 * @author Antonio Rodrigues
 * @author Simão Neves
 * @author Joao Rodrigues
 * @Group:: css018
 * @Date 2016/04/28
 */
public class SimpleClient {

	private AddCustomerService addCustomerService;
	private ProcessSaleService processSaleService;
	private CurrentAccountService currentAccountService;

	public SimpleClient(AddCustomerService addCustomerService, ProcessSaleService processSaleService,
			CurrentAccountService currentAccountService) {
		this.addCustomerService = addCustomerService;
		this.processSaleService = processSaleService;
		this.currentAccountService = currentAccountService;
	}

	/**
	 * A simple interaction with the application services
	 */
	public void createASale() {
		// the interaction
		try {
			// adds a customer.

			addCustomerService.addCustomer(168027852, "Customer 1", 217500255, 1, new Account());
			// starts a new sale
			Customer c = addCustomerService.getCustomer(168027852);
			processSaleService.newSale(c.getVATNumber());
			// adds two products to the sale
			processSaleService.addProductToSale(123, 3);
			processSaleService.addProductToSale(124, 1);
			processSaleService.addProductToSale(123, 1);

			processSaleService.getSaleDiscount();

			int idSale = processSaleService.closeSale(c.getVATNumber());

			if (idSale != -1) {
				System.out.println("The Sale id:" + idSale);
				System.out.println("The Sale has been closed");
				System.out.println("Sarting its payment");
				paySale(idSale, c.getVATNumber());

			} else {
				System.out.println("Something went Wrong");
			}

		} catch (ApplicationException e) {
			System.out.println("Error: " + e.getMessage());
			// for debugging purposes only. Typically, in the application
			// this information can be associated with a "details" button when
			// the error message is displayed.
			if (e.getCause() != null)
				System.out.println("Cause: ");
			e.printStackTrace();
		}
	}

	/**
	 * method of the use case checkAccount
	 * 
	 * @throws ApplicationException
	 */
	public void checkAccount() throws ApplicationException {
		Scanner sc = new Scanner(System.in);
		System.out.println("Running the CheckAccount use case");

		System.out.println("Insert vat number o client");
		String customer = sc.nextLine();
		int vat;

		try {
			vat = Integer.parseInt(customer);
		} catch (NumberFormatException e) {
			throw new ApplicationException("its not a valid Vat number", e);
		}

		String value = "";
		List<Transation> lista = currentAccountService.getAllTransations(vat);
		Map<Integer, Transation> mapa = new HashMap<>(lista.size());

		while (!(value.equalsIgnoreCase("quit"))) {

			System.out.println("List of Transations");
			if (lista.isEmpty()) {
				System.out.println("List is empty");
				break;
			}

			int i = 1;
			for (Transation t : lista) {
				mapa.put(i, t);
				System.out.println("Numero: " + i + " Conteudo: " + t);
				i++;
			}

			System.out.println("Insert the number of the transation to see more or quit to leave");
			do {
				int number;
				value = sc.nextLine();
				try {
					number = Integer.parseInt(value);
					String str = currentAccountService.seeTransation(mapa.get(number));
					System.out.println(str);

				} catch (NumberFormatException nfe) {
					break;
				}
				System.out.println("Write the number to choose another transation to see");

			} while (!(value.equalsIgnoreCase("done")));

		}
		sc.close();
	}

	/**
	 * method to pay the sale
	 * 
	 * @param idSale
	 * @param vat
	 * @throws ApplicationException
	 */
	public void paySale(int idSale, int vat) throws ApplicationException {
		processSaleService.paySale(idSale, vat);

	}
}
