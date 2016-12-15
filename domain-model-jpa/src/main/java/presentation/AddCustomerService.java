package presentation;

import business.AddCustomerHandler;
import business.ApplicationException;
import business.entities.Account;
import business.Customer;



public class AddCustomerService {

	private AddCustomerHandler customerHandler;

	public AddCustomerService(AddCustomerHandler customerHandler) {
		this.customerHandler = customerHandler;
	}
	public void addCustomer(int vat, String denomination, int phoneNumber, 
			int discountType, Account account) throws ApplicationException {
		customerHandler.addCustomer(vat, denomination, phoneNumber, discountType, account);
	}
	public Customer getCustomer (int vat) throws ApplicationException {
		return customerHandler.getCustomer(vat);
	}
}
