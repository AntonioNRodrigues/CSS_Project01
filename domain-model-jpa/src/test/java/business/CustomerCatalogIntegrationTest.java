package business;

import static com.ninja_squad.dbsetup.Operations.deleteAllFrom;
import static com.ninja_squad.dbsetup.Operations.insertInto;
import static com.ninja_squad.dbsetup.Operations.sequenceOf;
import static org.junit.Assert.assertEquals;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import test.DatabaseConnection;
import business.ApplicationException;
import business.Customer;
import business.CustomerCatalog;
import business.Discount;
import business.DiscountCatalog;

import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.DbSetupTracker;
import com.ninja_squad.dbsetup.operation.Operation;

public class CustomerCatalogIntegrationTest {

	private static DbSetupTracker dbSetupTracker;
	private static DbSetup dbSetup;
	private static EntityManagerFactory emf;
	private CustomerCatalog customerCatalog;
        
    @BeforeClass
    public static void setup() {
    	Operation operations = sequenceOf(
                deleteAllFrom("SALEPRODUCT", "SALE", "CUSTOMER", "DISCOUNT"),
                insertInto("DISCOUNT"). 
                	columns("ID", "DTYPE", "DESCRIPTION", "PERCENTAGE", "THRESHOLD"). 
                	values(1, "NoDiscount", "Sem desconto", 0, 0).
                	values(2, "ThresholdPercentageDiscount", "Percentagem do Total (acima de limiar)", 0.1, 50).
                	values(3, "EligibleProductsDiscount", "Percentagem do Total Elegivel", 0.1, 0).build(),
                insertInto("CUSTOMER"). 
                	columns("VATNUMBER", "DESIGNATION", "PHONENUMBER", "DISCOUNT_ID"). 
                	values(168027852, "Empresa de Informática, LDA", 21221123, 1).
                	values(123456789, "Mais uma empresa", 2211111, 2).build());
        dbSetup = new DbSetup(DatabaseConnection.DESTINATION, operations);
        dbSetupTracker = new DbSetupTracker();
        dbSetupTracker.launchIfNecessary(dbSetup);
        emf = Persistence.createEntityManagerFactory("domain-model-jpa");
    }

    @AfterClass
    public static void tearDown() {
    	emf.close();
    }
    
    @Before
    public void prepare() {
        dbSetupTracker.launchIfNecessary(dbSetup);
    	customerCatalog = new CustomerCatalog(emf);
    }
  
    @Test
    public void getCustomerSuccess() throws ApplicationException {
    	dbSetupTracker.skipNextLaunch();
    	Customer customer = customerCatalog.getCustomer(168027852);
    	assertEquals("Cliente com número de pessoa coletiva inválido", 168027852, customer.getVATNumber());
    }

    @Test(expected=ApplicationException.class)
    public void getCustomerNotFound() throws ApplicationException {
    	dbSetupTracker.skipNextLaunch();
    	customerCatalog.getCustomer(99999);
    }

    @Test
    public void addCustomerSuccess() throws ApplicationException {
    	DiscountCatalog discountCatalog = new DiscountCatalog(emf);
    	Discount discount = discountCatalog.getDiscount(1);
    	customerCatalog.addCustomer(189663286, "Uma super empresa", 123, discount);
    	Customer customer = customerCatalog.getCustomer(189663286);
    	assertEquals("Cliente com número de pessoa coletiva inválido", 189663286, customer.getVATNumber());
    }

    // Notice the internal message printed by JPQ because of the database constraint violation error
    // due to duplicate customer VAT number (duplicate key - unique = true)
    @Test(expected=ApplicationException.class) 
    public void addCustomerAlreadyExist() throws ApplicationException {
    	DiscountCatalog discountCatalog = new DiscountCatalog(emf);
    	Discount discount = discountCatalog.getDiscount(1);
    	customerCatalog.addCustomer(168027852, "Uma super empresa", 123, discount);
    }

}
