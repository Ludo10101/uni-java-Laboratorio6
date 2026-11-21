package Esercizio;
import java.util.Comparator;
public class DistanceMezzoComparator implements Comparator<Mezzo>{
	private Cliente client;

	public DistanceMezzoComparator(Cliente client) {
		super();
		this.client = client;
	}
	
	@Override
	public int compare(Mezzo m1, Mezzo m2){
		return Double.compare(m1.calcolaDistanza(client), m2.calcolaDistanza(client));
		
	}
}
