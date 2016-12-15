package presentation;

import business.ApplicationException;
import business.ProcessSaleHandler;

/**
 * Entity implementation class for Entity: Account
 * 
 * @author Antonio Rodrigues
 * @author Simão Neves
 * @author Joao Rodrigues
 * @Group:: css018
 * @Date 2016/04/28
 *
 */
public class ProcessSaleService {

	private ProcessSaleHandler saleHandler;

	public ProcessSaleService(ProcessSaleHandler saleHandler) {
		this.saleHandler = saleHandler;
	}

	public void newSale(int vat) throws ApplicationException {
		saleHandler.newSale(vat);
	}

	public void addProductToSale(int productCode, int qty) throws ApplicationException {
		saleHandler.addProductToSale(productCode, qty);
	}

	public double getSaleDiscount() throws ApplicationException {
		return saleHandler.getSaleDiscount();
	}

	public int closeSale(int vat) throws ApplicationException {
		return saleHandler.closeSale(vat);

	}

	public void paySale(int idSale, int vat) throws ApplicationException {
		saleHandler.paySale(idSale, vat);
	}
}
