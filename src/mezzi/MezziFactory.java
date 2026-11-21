package mezzi;
import Esercizio.Mezzo;
import Esercizio.EAlimentazione;

public class MezziFactory {
	private static int mezziGenerati;
	
	private MezziFactory() {
		
	}
	
	public static Mezzo creaAutomobile( EAlimentazione alimentazione, double latitudine, double longitudine) {
		return new Automobile("AA-"+(mezziGenerati++),alimentazione, latitudine,longitudine);
	}
	
	public static Mezzo creaFurgone( double latitudine, double longitudine) {
		return new Furgone("FF-"+(mezziGenerati++),latitudine,longitudine);
	}
	
	public static Mezzo creaMoto( double latitudine, double longitudine) {
		return new Moto("MM-"+(mezziGenerati++),latitudine,longitudine);
	}
	
	public static Mezzo creaScooter( EAlimentazione alimentazione, double latitudine, double longitudine) {
		return new Scooter("SS-"+(mezziGenerati++), alimentazione,latitudine,longitudine);
	}

	public static int getMezziGenerati() {
		return mezziGenerati;
	}

	public static void setMezziGenerati(int mezziGenerati) {
		MezziFactory.mezziGenerati = mezziGenerati;
	}
	
}
