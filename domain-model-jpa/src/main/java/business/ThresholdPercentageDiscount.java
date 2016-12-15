package business;

import javax.persistence.Entity;

/**
 * A discount based on the amount of sale being greater than a threshold
 * 
 * @author fmartins
 * @version 1.1 (17/04/2015)
 *
 */
@Entity
public class ThresholdPercentageDiscount extends Discount {

	/**
	 * The amount threshold. If the total sale is below this threshold, then
	 * the no discount is applied. Otherwise, the discount percentage is
	 * applied to the total sale. 
	 */
	private double threshold;
	
	/**
	 * The discount percentage 
	 */
	private double percentage;

	/**
	 * Constructor needed by JPA 
	 */
	ThresholdPercentageDiscount() {
	}
	
	/**
	 * Creates a discount that will apply a percentage over the total amount
	 * of products in case this amount is above a threshold.
	 * 
	 * @param discountId The id of the discount
	 * @param description The discount description
	 * @param threshold The amount threshold
	 * @param percentage The percentage to be applied
	 */
	public ThresholdPercentageDiscount(int discountId, String description, double threshold, double percentage) {
		super (discountId, description);
		this.threshold = threshold;
		this.percentage = percentage;
	}

	@Override
	public double computeDiscount(Sale sale) {
		double saleTotal = sale.total();
		return saleTotal > threshold ? saleTotal * percentage : 0;
	}
	
}
