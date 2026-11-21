package mezzi;
import Esercizio.EAlimentazione;
import Esercizio.EPatente;
public class Automobile extends Autoveicolo {
	
	public Automobile(String targa, EAlimentazione alimentazione, double latitudine, double longitudine) {
		super(targa, alimentazione, latitudine, longitudine,true, EPatente.B,18, Integer.MAX_VALUE, 1.00 );
	}
	@Override
	public String getTipologiaMezzo() {
		return "Automobile";
	}

}
