package business;

import business.entities.Account;

/**
 * Handles the add customer use case. This represents a very 
 * simplified use case with just one operation: addCustomer.
 * 
 * @author fmartins
 * @version 1.1 (17/04/2015)
 *
 */
public class AddCustomerHandler {
	
	/**
	 * The customer's catalog
	 */
	private CustomerCatalog customerCatalog;
	
	/**
	 * The discount's catalog 
	 */
	private DiscountCatalog discountCatalog;
	
	/**
	 * Creates a handler for the add customer use case given
	 * the customer and the discount catalogs.
	 *  
	 * @param customerCatalog A customer's catalog
	 * @param discountCatalog A discount's catalog
	 */
	public AddCustomerHandler(CustomerCatalog customerCatalog, DiscountCatalog discountCatalog) {
		this.customerCatalog = customerCatalog;
		this.discountCatalog = discountCatalog;
	}

	/**
	 * Adds a new customer with a valid Number. It checks that there is no other 
	 * customer in the database with the same VAT.
	 * 
	 * @param vat The VAT of the customer
	 * @param denomination The customer's name
	 * @param phoneNumber The customer's phone 
	 * @param discountType The type of discount applicable to the customer
	 * @throws ApplicationException When the VAT number is invalid (we check it according 
	 * to the Portuguese legislation).
	 */
	public void addCustomer (int vat, String denomination, 
			int phoneNumber, int discountType, Account account) 
			throws ApplicationException {
		Discount discount = discountCatalog.getDiscount(discountType);
		customerCatalog.addCustomer(vat, denomination, phoneNumber, discount, account);
	}

	public Customer getCustomer(int vat) throws ApplicationException {
		return customerCatalog.getCustomer(vat);
	}	
}
