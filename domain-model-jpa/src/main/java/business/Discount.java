package business;

import static javax.persistence.InheritanceType.SINGLE_TABLE;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 * A customer discount
 * 
 * @author fmartins
 * @version 1.1 (17/04/2015)
 */
@Entity    
@NamedQueries({
	@NamedQuery(name=Discount.FIND_BY_TYPE, query="SELECT d FROM Discount d WHERE d.id = :" + Discount.FIND_BY_TYPE_ID),
	@NamedQuery(name=Discount.FIND_ALL, query="SELECT d FROM Discount d")
})
@Inheritance(strategy = SINGLE_TABLE)
public abstract class Discount {
	
	// Named query name constants
	public static final String FIND_BY_TYPE = "Discount.findByType";
	public static final String FIND_ALL = "Discount.findAll";
	public static final String FIND_BY_TYPE_ID = "id";
	
	/**
	 * The discount type. 
	 */
	@Id	private int id;

	/**
	 * Discount description
	 */
	private String description;
	
	/**
	 * Constructor needed by JPA 
	 */
	Discount() {
	}

	/**
	 * Creates a discount type given its description
	 * 
	 * @param description The discount description
	 */
	public Discount(int id, String description) {
		this.id = id;
		this.description = description;
	}

	/**
	 * @return The discount description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param sale The sale to compute the discount on
	 * @return The discount amount using the current discount type
	 */
	public abstract double computeDiscount(Sale sale);
}
 