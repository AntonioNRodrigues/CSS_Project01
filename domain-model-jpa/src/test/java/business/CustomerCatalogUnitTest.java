package business;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import business.ApplicationException;
import business.Customer;
import business.CustomerCatalog;
import business.Discount;

@RunWith(MockitoJUnitRunner.class)
public class CustomerCatalogUnitTest {

	@Mock private EntityManagerFactory emf;
	@Mock private EntityManager em;
	@Mock private TypedQuery<Customer> query;
	@Mock private Customer customer;
	private CustomerCatalog customerCatalog;
           
    @Before
    public void prepare() {
    	customerCatalog = new CustomerCatalog(emf);
    	given(emf.createEntityManager()).willReturn(em);
    }
  
    @Test
    public void getCustomerSuccess() throws ApplicationException {
    	// given
    	given(em.createNamedQuery(Customer.FIND_BY_VAT_NUMBER, Customer.class)).willReturn(query);
    	given(query.getSingleResult()).willReturn(customer);
    	
    	// when
    	Customer result = customerCatalog.getCustomer(168027852);
    	
    	// then
    	verify(query).setParameter(Customer.NUMBER_VAT_NUMBER, 168027852);
    	assertEquals("Cliente com número de pessoa coletiva inválido", result, customer);
    }

    @Test(expected=ApplicationException.class)
    public void getCustomerNotFound() throws ApplicationException {
    	// given
    	given(em.createNamedQuery(Customer.FIND_BY_VAT_NUMBER, Customer.class)).willReturn(query);
    	given(query.getSingleResult()).willThrow(new PersistenceException());

    	// then
    	customerCatalog.getCustomer(99999);
    	verify(query).setParameter(Customer.NUMBER_VAT_NUMBER, 99999);
    }

    /*
     * For testing the addCustomer method we must refactor it so it asks 
     * a factory the customer to create, instead of creating the customer itself.
     * Notice that we cannot mock the customer if the method has a "new Customer(..)"
     * explicitly. Compare CustomerCatalog and CustomerCatalogOriginal. Also the
     * factory did not exist in the version before testing.
     */
    @Test
    public void addCustomerSuccess() throws ApplicationException {
    	// given
    	Discount discount = mock(Discount.class);
    	EntityTransaction transaction = mock(EntityTransaction.class);
    	given(new Customer(189663286, "Uma super empresa", 123, discount)).willReturn(customer);
    	given(em.getTransaction()).willReturn(transaction);
    	
    	// when 
    	customerCatalog.addCustomer(189663286, "Uma super empresa", 123, discount);
    	
    	// then
    	verify(em).persist(customer);
    }

    @Test(expected=ApplicationException.class) 
    public void addCustomerAlreadyExist() throws ApplicationException {
    	// given
    	Discount discount = mock(Discount.class);
    	EntityTransaction transaction = mock(EntityTransaction.class);
    	given(new Customer(168027852, "Uma super empresa", 123, discount)).willReturn(customer);
    	given(em.getTransaction()).willReturn(transaction);
    	willThrow(new PersistenceException()).given(transaction).commit();
    	
    	// when 
    	customerCatalog.addCustomer(189663286, "Uma super empresa", 123, discount);
    	
    	// then
    	verify(em).persist(customer);
    }

}
