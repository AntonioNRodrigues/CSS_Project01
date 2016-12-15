package business.entities;

import static javax.persistence.InheritanceType.SINGLE_TABLE;

import java.util.Date;

import javax.annotation.PostConstruct;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import business.Sale;

/**
 * Entity implementation class for Entity: Transation
 * 
 * @author Antonio Rodrigues
 * @author Simão Neves
 * @author Joao Rodrigues
 * @Group:: css018
 * @Date 2016/04/28
 * 
 */
@Entity  
@Inheritance(strategy = SINGLE_TABLE)
@NamedQueries({ @NamedQuery(name = Transation.FIND_ID, query = "SELECT t FROM Transation t WHERE t.id_trans = :"
		+ Transation.FIND_ID), 
	@NamedQuery(name = Transation.FIND_ALL, query = "SELECT t FROM Transation t") })
public abstract class Transation {
	public static final String FIND_ALL = "Transation.findAll";
	public static final String FIND_ID = "id_trans";
	
	private static final String DEBIT = "debit";
	private static final String CREDIT = "credit";
	@Id
	@GeneratedValue
	@Column(name = "id_trans")
	private int id_trans;

	@Column
	private double value;

	@Temporal(TemporalType.DATE)
	private Date date;

	@ManyToOne
	private Sale sale;
	
	@ManyToOne
	private Account account;

	public Transation() {
	}

	public Transation(double value, Date d, Sale sale, Account account) {
		this.date = d;
		this.value = value;
		this.sale = sale;
		this.account = account;
	}

	/**
	 * Method to build Transations of different types
	 * 
	 * @param control
	 * @param value
	 * @param date
	 * @return
	 * @required String control as
	 */
	public static Transation factory(String control, double value, Date date, Sale sale, Account account) {
		return (control.equalsIgnoreCase(DEBIT)) ? new Debit(value, date, sale, account)
				: (control.equalsIgnoreCase(CREDIT)) ? new Credit(value, date, sale, account) : null;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public Sale getSale() {
		return sale;
	}

	public void setSale(Sale sale) {
		this.sale = sale;
	}

	public int getId() {
		return this.id_trans;
	}

	@Override
	public String toString() {
		return "[id_trans=" + id_trans + ", value=" + value + ", date=" + date + ", sale=" + sale + "]";
	}
}
