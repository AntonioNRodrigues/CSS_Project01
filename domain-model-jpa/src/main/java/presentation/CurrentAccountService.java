package presentation;

import java.util.List;

import business.ApplicationException;
import business.CurrentAccountHandler;
import business.entities.Transation;

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
public class CurrentAccountService {

	private CurrentAccountHandler currentAccountHandler;

	public CurrentAccountService(CurrentAccountHandler current) {
		this.currentAccountHandler = current;
	}

	public List<Transation> getAllTransations(int vat) throws ApplicationException {
		return currentAccountHandler.getAllTransations(vat);
	}

	public String seeTransation(Transation t) throws ApplicationException{
		return currentAccountHandler.seeTransation(t);
		
	}

	
	

}
