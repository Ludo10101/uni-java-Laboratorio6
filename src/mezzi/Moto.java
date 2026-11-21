package mezzi;
import Esercizio.EAlimentazione;
import Esercizio.EPatente;
public class Moto extends Motociclo {
	
	public Moto(String targa, double latitudine, double longitudine) {
		super(targa, EAlimentazione.BENZINA, latitudine, longitudine,true, EPatente.A2,18, Integer.MAX_VALUE, 0.80 );
	}
	@Override
	public String getTipologiaMezzo() {
		return "Moto";
	}

}
