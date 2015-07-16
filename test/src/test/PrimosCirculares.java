package test;


import java.util.*;
import java.util.regex.*;

/**
 *@author Guillermo Gomez 
 * 
 */
public class PrimosCirculares {
	
	private static final int MAXIMO = 1000000;
	
	static List<String> noValidos = new ArrayList<String>(MAXIMO);
	static List<String> primos = new ArrayList<String>(MAXIMO);
	
	public static void main(String[] args) throws InterruptedException
	{
		int numero = MAXIMO;
		String strNbr = "1000000";
		if (args.length > 0)
		{
			strNbr = args[0];
			numero = Integer.parseInt(strNbr);
		}
		
		List<ChequeaPrimo> listOfThreads = new ArrayList<ChequeaPrimo>();				
		
		for (int i = numero; i > 0; i--)
		{
			if (strNbr.length() == 1 || !filtrarNumero(strNbr))
			{
				ChequeaPrimo chequeaPrimo = new ChequeaPrimo(strNbr);
				chequeaPrimo.start();
				listOfThreads.add(chequeaPrimo);
			}
			strNbr = i - 1 + "";
			
		}
		for (int i = 0; i < listOfThreads.size(); i++)
		{
			listOfThreads.get(i).join();
			// si el primo lo agregamos a la lista de primos
			if (listOfThreads.get(i).isPrimo())
			{
				primos.add(listOfThreads.get(i).getName());
			}
		}
		
		// imprimimos los primos
		System.out.println("Primos Cantidad: " + primos.size());
		System.out.println("Lista Primos: " + primos.toString());
		
		// filtramos en la lista que existan circulares de cada uno
		List<String> primosCirculares = new ArrayList<String>();
		
		for (int i = 0; i < primos.size(); i++)
		{
			boolean borrarPrimo = false;
			String[] circulares = getCirculares(primos.get(i));
			for (int j = 0; j < circulares.length; j++)
			{
				if (!primos.contains(circulares[j]))
				{
					// si no existe su circular en la lista entonces no es primo circular
					// y procederemos a marcarlo como NO valido
					borrarPrimo = true;
					break;
				}
			}
			// borra el primo y los circulares que esten en la lista
			if (borrarPrimo)
			{
				noValidos.add(primos.get(i));
				for (int j = 0; j < circulares.length; j++)
				{
					if (primos.contains(circulares[j]))
					{
						int index = primos.indexOf(circulares[j]);
						noValidos.add(primos.get(index));
					}
				}
			}
			else
			{
				primosCirculares.add(primos.get(i));
			}
		}
		
		System.out.println("\n**************************************************");
		System.out.println("Primos Circulares Cantidad: " + primosCirculares.size());
		System.out.println("Lista Primos Circulares: " + primosCirculares.toString());
		
	}
	
	/**
	 * Chequea previamente si el numero aplica para ser chequeado como primo
	 * De esta manera evitamos chequear numeros con digitos pares y el 5
	 * ya que nunca serás Primos circulares
	 * 
	 * @param numero
	 * @return boolean indica si el numero debe ser filtrado o no
	 */
	public static boolean filtrarNumero(String numero)
    {
		if (numero.length() > 1)
		{
			Pattern pattern = Pattern.compile("(0|2|4|5|6|8)");
			Matcher match = pattern.matcher(numero);
			if (match.find())
			{
				return true;
			}
		}
		return noValidos.contains(numero);
	}
	
	/**
	 * Obtenemos los circulares de un numero siempre que no este en la lista de NoValidos
	 * 
	 * @param numero
	 * @return String[] con los circulares de un numero
	 */
	public static String[] getCirculares(String numero)
    {
		int length = numero.length();
		String[] result = new String[length];
		if (!noValidos.contains(numero))
		{			
			for (int i = 0; i < length; i++)
			{
				numero = numero.substring(1, length) + numero.substring(0, 1);
				result[i] = numero;
			}
		}
		return result;
	}
		

}
