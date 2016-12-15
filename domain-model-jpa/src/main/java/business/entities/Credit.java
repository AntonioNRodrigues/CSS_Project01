package business.entities;

import business.Sale;
import business.entities.Transation;

import java.util.Date;

import javax.persistence.Entity;

/**
 * Entity implementation class for Entity: Credit
 * 
 * @author Antonio Rodrigues
 * @author Simão Neves
 * @author Joao Rodrigues
 * @Group:: css018
 * @Date 2016/04/28
 */
@Entity

public class Credit extends Transation {
	
	public Credit() {
		super();
	}

	public Credit(double v, Date d, Sale s, Account c) {
		super(v, d, s, c);
	}
	

	@Override
	public String toString() {
		return "CREDIT:: "+ super.toString();
	}

}