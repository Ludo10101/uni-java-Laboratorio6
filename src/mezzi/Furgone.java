package mezzi;
import Esercizio.EAlimentazione;
import Esercizio.EPatente;
public class Furgone extends Autoveicolo {
	
	public Furgone(String targa, double latitudine, double longitudine) {
		super(targa, EAlimentazione.BENZINA, latitudine, longitudine,true, EPatente.B,18, 70, 2.00 );
	}
	@Override
	public String getTipologiaMezzo() {
		return "Furgone";
	}

}
