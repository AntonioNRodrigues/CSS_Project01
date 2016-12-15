package business;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * The product units. Plays no role in the current iteration of the
 * application. Anyway, the type is already given.
 * 
 * @author fmartins
 * @version 1.0 (8/03/2015)
 *
 */
@Entity
public class Unit {

	/**
	 * Customer primary key. Needed by JPA. Notice that it is not part of the
	 * original domain model.
	 */
	@Id @GeneratedValue private int id;
	@SuppressWarnings("unused") private String description;
	@SuppressWarnings("unused") private String abbreviation;
	@Override
	public String toString() {
		return abbreviation;
	}	
	
	
}
 