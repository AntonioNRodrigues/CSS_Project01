package business;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.EnumType.STRING;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import business.entities.Transation;

/**
 * A sale
 * 
 * @author fmartins
 * @version 1.1 (17/04/2015)
 * @modified by:
 * @author Antonio Rodrigues
 * @author Simão Neves
 * @author Joao Rodrigues
 * @Group:: css018
 * @Date 2016/04/28
 *
 */
@Entity      
@NamedQueries({ 
	@NamedQuery(name = Sale.FIND_BY_ID, query = "SELECT s FROM Sale s WHERE s.id_Sale=:" + Sale.FIND_BY_ID),
	@NamedQuery(name = Sale.FIND_ALL, query = "SELECT s FROM Sale s") 
	})

public class Sale {
	public static final String FIND_BY_ID = "id_sale";
	public static final String FIND_ALL = "Sale.findAll";
	/**
	 * Sale primary key. Needed by JPA. Notice that it is not part of the
	 * original domain model.
	 */
	@Id
	@GeneratedValue
	@Column(name = "id_Sale")
	private int id_Sale;

	/**
	 * The date the sale was made
	 */
	@Temporal(TemporalType.DATE)
	private Date date;

	/**
	 * Whether the sale is open or closed.
	 */
	@Enumerated(STRING)
	private SaleStatus status;

	/**
	 * 
	 */
	@ManyToOne
	private Customer customer;

	/**
	 * The products of the sale
	 */
	@OneToMany(cascade = ALL)
	@JoinColumn
	private List<SaleProduct> saleProducts;

	@OneToMany(cascade = ALL)
	@JoinColumn
	private List<Transation> listTransations;

	@Enumerated(EnumType.STRING)
	private PaymentStatus statusPayment;
	// 1. constructor

	/**
	 * Constructor needed by JPA.
	 */
	public Sale() {
	}

	/**
	 * Creates a new sale given the date it occurred and the customer that made
	 * the purchase.
	 * 
	 * @param date
	 *            The date that the sale occurred
	 * @param customer
	 *            The customer that made the purchase
	 */
	public Sale(Date date, Customer customer) {
		this.date = date;
		this.customer = customer;
		this.status = SaleStatus.OPEN;
		this.statusPayment = PaymentStatus.NOT_PAYDED;
		this.saleProducts = new LinkedList<SaleProduct>();
		this.listTransations = new LinkedList<Transation>();
	}

	// 2. getters and setters

	/**
	 * @return The sale's total
	 */
	public double total() {
		double total = 0;
		for (SaleProduct sp : saleProducts)
			total += sp.getSubTotal();
		return total;
	}

	/**
	 * @return The sale's amount eligible for discount
	 */
	public double eligibleDiscountTotal() {
		double total = 0;
		for (SaleProduct sp : saleProducts)
			total += sp.getEligibleSubtotal();
		return total;
	}

	/**
	 * @return Computes the sale's discount amount based on the discount type of
	 *         the customer.
	 */
	public double discount() {
		Discount discount = customer.getDiscountType();
		return discount.computeDiscount(this);
	}

	/**
	 * @return Whether the sale is open
	 */
	public boolean isOpen() {
		return status == SaleStatus.OPEN;
	}

	/**
	 * Adds a product to the sale
	 * 
	 * @param product
	 *            The product to sale
	 * @param qty
	 *            The amount of the product being sold
	 * @throws ApplicationException
	 */
	public void addProductToSale(Product product, double qty) throws ApplicationException {
		if (!isOpen())
			throw new ApplicationException("Cannot add products to a closed sale.");

		// if there is enough stock
		if (product.getQty() >= qty) {
			// adds product to sale
			product.setQty(product.getQty() - qty);
			saleProducts.add(new SaleProduct(product, qty));
		} else
			throw new ApplicationException("Product " + product.getProdCod() + " has stock (" + product.getQty()
					+ ") which is insuficient for the current sale");
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (SaleProduct sp : saleProducts)
			sb.append(" [" + sp.getProduct().getProdCod() + ":" + sp.getQty() + "]");
		return sb.toString();
	}

	public void setSatus(SaleStatus state) {
		this.status = state;
	}

	public void setTransation(Transation trans) {
		this.listTransations = new LinkedList<Transation>();
	}

	public void closeSale(Sale s) {
		setSatus(SaleStatus.CLOSED);
	}

	public void setCostumer(Customer customer) {
		this.customer = customer;
	}

	public PaymentStatus getStatusPayment() {
		return statusPayment;
	}

	public void setStatusPayment(PaymentStatus statusPayment) {
		this.statusPayment = statusPayment;
	}

	public List<SaleProduct> getSaleProducts() {
		return saleProducts;
	}

	public List<Transation> getSaleTransations() {
		return listTransations;
	}

	public boolean addTransationSale(Transation t) {
		return this.listTransations.add(t);
	}
	public int getIdSale(){
		return this.id_Sale;
	}
}
