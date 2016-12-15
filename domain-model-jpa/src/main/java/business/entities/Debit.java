/**
 * 
 */
package business.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;

import business.Sale;
import business.SaleProduct;

/**
 * @author Antonio Rodrigues
 * @author Simão Neves
 * @author Joao Rodrigues
 * @Group:: css018
 * @Date 2016/04/28
 *
 */
@Entity
public class Debit extends Transation {

	public Debit() {
		super();
	}

	/**
	 * @param value
	 * @param date
	 * @param idSale
	 */
	public Debit(double value, Date date, Sale sale, Account account) {
		super(value, date, sale, account);
	}

	public List<SaleProduct> getInfoSale() {
		return super.getSale().getSaleProducts();
	}

	@Override
	public String toString() {
		return "DEBIT:: "+ super.toString();
	}

}
