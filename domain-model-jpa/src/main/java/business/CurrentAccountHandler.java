package business;

import java.util.ArrayList;
import java.util.List;

import business.entities.Account;
import business.entities.AccountCatalog;
import business.entities.Credit;
import business.entities.Debit;
import business.entities.Transation;
import business.entities.TransationCatalog;

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

public class CurrentAccountHandler {

	private CustomerCatalog customerCatalog;

	private AccountCatalog accountCatalog;

	public CurrentAccountHandler(SaleCatalog saleCatalog, CustomerCatalog customerCatalog,
			TransationCatalog transationCatalog, AccountCatalog accountCatalog) {
		this.customerCatalog = customerCatalog;
		this.accountCatalog = accountCatalog;

	}

	/**
	 * method to get all transations of a customer
	 * 
	 * @param vat
	 * @return
	 * @throws ApplicationException
	 */
	public List<Transation> getAllTransations(int vat) {
		Customer c = null;
		Account a = null;
		List <Transation> list = new ArrayList<>();
		try {
			c = customerCatalog.getCustomer(vat);
			a = accountCatalog.getAccount(c.getAccount().getId());
			list = a.getTransations();
		} catch (ApplicationException e) {
			System.out.println("The custumer has not been found");
		}

		return list;
	}

	/**
	 * method to see the content of the transation depending of its type
	 * 
	 * @param t
	 * @return info of Sale if its the instance os Debit and the date and its id
	 *         if its of type Credit
	 * @throws ApplicationException
	 */
	public String seeTransation(Transation t) throws ApplicationException {

		List<SaleProduct> lista = null;

		StringBuilder sb = new StringBuilder();

		if (t instanceof Debit) {
			lista = ((Debit) t).getInfoSale();
			
			
		} else if (t instanceof Credit) {

			return sb.append("Date of Sale: ").append(t.getDate().toString())
					.append(" Sale ID: ").append(t.getSale().getIdSale()).toString();

		} else {

			throw new ApplicationException("Problem geting the Transation");
		}
		return lista.toString();
	}
}