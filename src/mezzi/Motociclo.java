package mezzi;
import Esercizio.EAlimentazione;
import Esercizio.EPatente;
import Esercizio.Mezzo;
public abstract class Motociclo extends Mezzo {
	public Motociclo(String targa, EAlimentazione alimentazione, double latitudine, double longitudine,
			boolean disponibilita, EPatente patenteRichiesta, int minEta, int maxEta, double tariffaMinuto) {
		super(targa,alimentazione,latitudine,longitudine, disponibilita,patenteRichiesta, minEta, maxEta,  tariffaMinuto);
	}
}