package mezzi;
import Esercizio.EAlimentazione;
import Esercizio.EPatente;
public class Scooter extends Motociclo {
	
	public Scooter(String targa, EAlimentazione alimentazione, double latitudine, double longitudine) {
		super(targa, alimentazione, latitudine, longitudine,true, EPatente.NULL,16, Integer.MAX_VALUE, 0.50 );
	}
	@Override
	public String getTipologiaMezzo() {
		return "Scooter";
	}

}
