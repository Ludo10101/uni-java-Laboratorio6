package Esercizio;
import java.util.ArrayList;
import java.util.Iterator;

import eccezioni.ClienteGiaPresenteException;
import eccezioni.ClienteInesistenteException;
import eccezioni.MezzoGiaPrenotatoException;
import eccezioni.PrenotazioneInesistenteException;
import eccezioni.NoleggioNonTerminatoException;
import eccezioni.NoleggioInesistenteException;
import eccezioni.MezzoInesistenteException;
import eccezioni.MezzoGiaPresenteException;
public class Sistema {
	private ArrayList<Prenotazione> reservationsArray;
	private ArrayList<Cliente> clientsArray;
	private ArrayList<Mezzo> transportsArray;
	private ArrayList<Noleggio> rentsArray;
	
	public Sistema(ArrayList<Prenotazione> reservationsArray, ArrayList<Cliente> clientsArray,
			ArrayList<Mezzo> transportsArray, ArrayList<Noleggio> rentsArray) {
		super();
		this.reservationsArray = reservationsArray;
		this.clientsArray = clientsArray;
		this.transportsArray = transportsArray;
		this.rentsArray = rentsArray;
	}
	
	public void aggiungiCliente(Cliente c) {
		for(Cliente cl: clientsArray) {
			if(cl.getId().equals(c.getId())) {
				 throw new ClienteGiaPresenteException("Il cliente con id: "+c.getId()+"e' gia' presente nel registro",c);
			}
		}
		clientsArray.add(c);
	}
	
	public void modificaCliente(String id, Cliente cl) {
		boolean flag=false;
		Iterator<Cliente> it= clientsArray.iterator();
		while(it.hasNext()) {
			Cliente c=it.next();
			if(c.getId().equals(id)) {
				it.remove();;
				flag=true;
				break;
			}
		}
		if(flag==true) {
			clientsArray.add(cl);
		}else throw new ClienteInesistenteException("Il cliente con id: "+ id+" non e' nel registro.\n",id);
	}
	public void aggiungiMezzo(Mezzo c) {
		for(Mezzo me: transportsArray) {
			if(me.getTarga().equals(c.getTarga())) {
				throw new MezzoGiaPresenteException("Il mezzo con targa: "+c.getTarga()+"e' gia' presente nel registro",c);
			}
		}

		transportsArray.add(c);
	}
	
	public void modificaMezzo(String id, Mezzo cl) {
		boolean flag=false;
		Iterator<Mezzo> it= transportsArray.iterator();
		while(it.hasNext()) {
			Mezzo c=it.next();
			if(c.getTarga().equals(id)) {
				it.remove();;
				flag=true;
				break;
			}
		}
		if(flag==true) {
			transportsArray.add(cl);
		}else throw new MezzoInesistenteException("Il mezzo con targa: "+ id+" non e' nel registro.\n",id);
	}
	
	public void elencoMezzi(Cliente c) {

		transportsArray.sort(new DistanceMezzoComparator(c));;
		for(Mezzo m: transportsArray) {
			System.out.printf("Tipo: %s\nTarga: %s\nDistanza: %.2f\nDisponibilita': %B\nTariffa al minuto: %.2f.\n",m.getTipologiaMezzo(),m.getTarga(),m.calcolaDistanza(c),m.isDisponibilita(),m.getTariffaMinuto());
		}
	}
	
	public void elencoMezziFiltrato(Cliente c, String tipo) {
		transportsArray.sort(new DistanceMezzoComparator(c));;
		for(Mezzo m: transportsArray) {
			if(tipo.equals(m.getTipologiaMezzo())) {
				System.out.printf("Tipo: %s\nTarga: %s\nDistanza: %.2f\nDisponibilita': %B\nTariffa al minuto: %.2f.\n",m.getTipologiaMezzo(),m.getTarga(),m.calcolaDistanza(c),m.isDisponibilita(),m.getTariffaMinuto());
			}
		}
	}
	
	public void nuovaPrenotazione(Cliente c, Mezzo m) {
		m.verificaVincoli(c);
		if(!m.isDisponibilita()) {
			throw new MezzoGiaPrenotatoException("Il mezzo con targa: "+m.getTarga()+" non e' disponibile.\n",m );
		}
		for(Prenotazione p: reservationsArray) {
			if(p.getTransport().getTarga().equals(m.getTarga())){
				throw new MezzoGiaPrenotatoException("Il mezzo con targa: "+m.getTarga()+" non e' disponibile.\n",m );
			}
		}
		m.setDisponibilita(false);
		reservationsArray.add(new Prenotazione(c,m));
	}
	
	public void chiudiPrenotazione(String targa) {
		int size=reservationsArray.size();
		
		Iterator <Prenotazione> it=reservationsArray.iterator();
		while(it.hasNext()) {
			Prenotazione p=it.next();
			if(p.getTransport().getTarga().equals(targa)) it.remove();
		}
		
		if(size==reservationsArray.size()) throw new PrenotazioneInesistenteException("Non esiste una prenotazione registrata con targa del mezzo: "+targa);
	}
	
	
	public void nuovoNoleggio(String targa, double oraAttuale) {
		boolean flag=false;
		for(Prenotazione p: reservationsArray) {
			if(p.getTransport().getTarga().equals(targa)) {
				flag=true;
				rentsArray.add(new Noleggio(p.getClient(),p.getTransport(),oraAttuale));
			}
		}
		if(!flag) {
			throw new PrenotazioneInesistenteException("Non esiste una prenotazione registrata con targa del mezzo: "+targa);
		}
		chiudiPrenotazione(targa);
	}
	
	public void chiudiNoleggio(String targa, double oraAttuale, double nuovaLat, double nuovaLon) {
		boolean flag=false;
		for(Noleggio n: rentsArray) {
			if((n.getTransport().getTarga().equals(targa))&&n.getOraFine()==-1) { // il controllo su -1 serve per evitare che noleggi precedentemente conclusi invalidino la ricerca nel for, a me serve il noleggio di un mezzo con targa che non si è ancora concluso.
				flag=true;
				n.setOraFine(oraAttuale);
				n.getTransport().setDisponibilita(true);
				n.getTransport().setLatitudine(nuovaLat);
				n.getTransport().setLongitudine(nuovaLon);
				System.out.println("Costo: "+calcolaCosto(n));
			}
		}
		if(!flag) throw new NoleggioInesistenteException("Questo noleggio non esiste.\n");
	}
	
	public double calcolaCosto(Noleggio n) {
		if(!rentsArray.contains(n)) throw new NoleggioInesistenteException("Questo noleggio non esiste.\n");
		if(n.getOraFine()==-1) throw new NoleggioNonTerminatoException("Il noleggio con mezzo con targa: "+n.getTransport().getTarga()+" non e' ancora terminato.\n");
		return (n.getOraFine()-n.getOraInizio())*n.getTransport().getTariffaMinuto();
	}
}
