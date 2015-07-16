package test;

/**
 *@author Guillermo Gomez 
 * 
 */
public class ChequeaPrimo extends Thread {

	int numero;
	boolean isPrimo;

	/* 
	 * Constructor 
	 */
	public ChequeaPrimo(String str){
		super(str);
		numero = Integer.parseInt(str);
	}

	/* calcula si es primo */
	public void run()
	{
		isPrimo = true;
		for(int i = 2; i < numero; i++)
        {
            if(numero % i == 0)
            {
            	isPrimo = false;
            }
        }  
	}
	
	public boolean isPrimo()
	{
		return isPrimo;
	}
}
