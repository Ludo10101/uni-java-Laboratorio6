package Esercizio;

import java.util.ArrayList;
import clienti.ClientePrivato;
import clienti.ClienteAziendale;
import clienti.Dipendente;
import mezzi.Automobile;
import mezzi.Furgone;
import mezzi.Moto;
import mezzi.Scooter;

public class Main{

    public static void main(String[] args) {
        // Inizializzazione registri e Sistema
        ArrayList<Prenotazione> prenotazioni = new ArrayList<>();
        ArrayList<Cliente> clienti = new ArrayList<>();
        ArrayList<Mezzo> mezzi = new ArrayList<>();
        ArrayList<Noleggio> noleggi = new ArrayList<>();

        Sistema bikar = new Sistema(prenotazioni, clienti, mezzi, noleggi);

        System.out.println("==========================================");
        System.out.println("TEST UC-03: GESTIONE ANAGRAFICA CLIENTI");
        System.out.println("==========================================");

        // Adatta i costruttori alle tue sottoclassi di Cliente (Privato / Aziendale)
        Cliente mario = new ClientePrivato("C01", "Mario", "Rossi",45.4642, 9.1900, EPatente.B,25  );
        Cliente lucaGiovane = new ClientePrivato("C02", "Luca", "Bianchi", 45.4650, 9.1910, EPatente.NULL,17 );
        Cliente aziendaTech = new ClienteAziendale("C03", "TechSrl", new Dipendente(35, EPatente.B, 45.4600, 9.1850));

        bikar.aggiungiCliente(mario);
        bikar.aggiungiCliente(lucaGiovane);
        bikar.aggiungiCliente(aziendaTech);
        System.out.println("[OK] Clienti inseriti correttamente.");

        // Test Eccezione: Cliente già presente
        try {
            bikar.aggiungiCliente(new ClientePrivato("C01", "Mario", "Duplicato",45.0, 9.0 , EPatente.B,30 ));
            System.out.println("[ERRORE] Doveva lanciare ClienteGiaPresenteException!");
        } catch (RuntimeException e) {
            System.out.println("[OK - Eccezione catturata] " + e.getClass().getSimpleName() + ": " + e.getMessage());
        }

        // Test Aggiornamento Cliente e Eccezione Cliente inesistente
        Cliente marioAggiornato = new ClientePrivato("C01", "Mario", "Rossi", 45.4645, 9.1905, EPatente.B,26 );
        bikar.modificaCliente("C01", marioAggiornato);
        System.out.println("[OK] Cliente C01 aggiornato.");

        try {
            bikar.modificaCliente("C99", marioAggiornato);
            System.out.println("[ERRORE] Doveva lanciare ClienteInesistenteException!");
        } catch (RuntimeException e) {
            System.out.println("[OK - Eccezione catturata] " + e.getClass().getSimpleName() + ": " + e.getMessage());
        }


        System.out.println("\n==========================================");
        System.out.println("TEST UC-02: GESTIONE PARCO MEZZI");
        System.out.println("==========================================");

        // Adatta i costruttori alle tue sottoclassi di Mezzo (Automobile, Furgone, Scooter, Moto)
        Mezzo auto1 = new Automobile("AA111AA", EAlimentazione.ELETTRICA, 45.4640, 9.1902);
        Mezzo scooter1 = new Scooter("SC222SC", EAlimentazione.ELETTRICA, 45.4655, 9.1915);
        Mezzo moto1 = new Moto("MT333MT",  45.4700, 9.2000);
        Mezzo furgone1 = new Furgone("FG444FG", 45.4800, 9.2100);

        bikar.aggiungiMezzo(auto1);
        bikar.aggiungiMezzo(scooter1);
        bikar.aggiungiMezzo(moto1);
        bikar.aggiungiMezzo(furgone1);
        System.out.println("[OK] Mezzi inseriti correttamente.");

        // Test Eccezione: Mezzo già presente
        try {
            bikar.aggiungiMezzo(new Automobile("AA111AA", EAlimentazione.BENZINA, 45.0, 9.0));
            System.out.println("[ERRORE] Doveva lanciare MezzoGiaPresenteException!");
        } catch (RuntimeException e) {
            System.out.println("[OK - Eccezione catturata] " + e.getClass().getSimpleName() + ": " + e.getMessage());
        }

        // Test Aggiornamento Mezzo e Eccezione Mezzo inesistente
        Mezzo auto1Aggiornata = new Automobile("AA111AA", EAlimentazione.ELETTRICA,  45.4641, 9.1903);
        bikar.modificaMezzo("AA111AA", auto1Aggiornata);
        System.out.println("[OK] Mezzo AA111AA aggiornato.");

        try {
            bikar.modificaMezzo("ZZ999ZZ", auto1Aggiornata);
            System.out.println("[ERRORE] Doveva lanciare MezzoInesistenteException!");
        } catch (RuntimeException e) {
            System.out.println("[OK - Eccezione catturata] " + e.getClass().getSimpleName() + ": " + e.getMessage());
        }


        System.out.println("\n==========================================");
        System.out.println("TEST UC-01: CONSULTAZIONE, PRENOTAZIONE, NOLEGGIO");
        System.out.println("==========================================");

        // 1. Consultazione ordinata e filtrata (RF-03, RF-04)
        System.out.println("--- Elenco completo per Mario (ordinato per distanza) ---");
        bikar.elencoMezzi(marioAggiornato);

        System.out.println("--- Elenco filtrato (Scooter) per Luca ---");
        bikar.elencoMezziFiltrato(lucaGiovane, "Scooter");

        // 2. Test Flussi Alternativi Prenotazione (RF-06, VD-02..VD-05)
        // 2a. Luca (17 anni, senza patente) prova a prenotare un'Automobile -> rifiutato
        try {
            bikar.nuovaPrenotazione(lucaGiovane, auto1Aggiornata);
            System.out.println("[ERRORE] Doveva rifiutare prenotazione auto a 17enne!");
        } catch (RuntimeException e) {
            System.out.println("[OK - Vincolo età/patente auto] " + e.getClass().getSimpleName() + ": " + e.getMessage());
        }

        // 2b. Mario (patente B) prova a prenotare una Moto (serve A2) -> rifiutato
        try {
            bikar.nuovaPrenotazione(marioAggiornato, moto1);
            System.out.println("[ERRORE] Doveva rifiutare prenotazione moto con patente B!");
        } catch (RuntimeException e) {
            System.out.println("[OK - Vincolo patente moto] " + e.getClass().getSimpleName() + ": " + e.getMessage());
        }

        // 3. Prenotazione valida (RF-05)
        bikar.nuovaPrenotazione(marioAggiornato, auto1Aggiornata);
        System.out.println("[OK] Prenotazione confermata per Mario su AA111AA. Disponibilita' attuale: " + auto1Aggiornata.isDisponibilita());

        // 3a. Azienda prova a prenotare la stessa auto già prenotata -> rifiutato (VD-06)
        try {
            bikar.nuovaPrenotazione(aziendaTech, auto1Aggiornata);
            System.out.println("[ERRORE] Doveva lanciare MezzoGiaPrenotatoException!");
        } catch (RuntimeException e) {
            System.out.println("[OK - Mezzo già prenotato] " + e.getClass().getSimpleName() + ": " + e.getMessage());
        }

        // 4. Test Noleggio senza prenotazione (RF-10, VD-06)
        try {
            bikar.nuovoNoleggio("FG444FG", 10.0);
            System.out.println("[ERRORE] Doveva lanciare PrenotazioneInesistenteException!");
        } catch (RuntimeException e) {
            System.out.println("[OK - Noleggio senza prenotazione] " + e.getClass().getSimpleName() + ": " + e.getMessage());
        }

        // 5. Avvio noleggio valido (RF-07) - es. minuto 600 (ore 10:00)
        bikar.nuovoNoleggio("AA111AA", 600.0);
        System.out.println("[OK] Noleggio avviato per AA111AA al minuto 600.0.");

        // 6. Test calcolo costo su noleggio non ancora terminato
        try {
            bikar.calcolaCosto(noleggi.get(0));
            System.out.println("[ERRORE] Doveva lanciare NoleggioNonTerminatoException!");
        } catch (RuntimeException e) {
            System.out.println("[OK - Calcolo costo prematuro] " + e.getClass().getSimpleName() + ": " + e.getMessage());
        }

        // 7. Chiusura noleggio non avviato (RF-10, VD-06)
        try {
            bikar.chiudiNoleggio("FG444FG", 630.0, 45.49, 9.22);
            System.out.println("[ERRORE] Doveva lanciare NoleggioInesistenteException!");
        } catch (RuntimeException e) {
            System.out.println("[OK - Chiusura noleggio inesistente] " + e.getClass().getSimpleName() + ": " + e.getMessage());
        }

        // 8. Chiusura noleggio valido (RF-08, RF-09) - es. 25 minuti dopo (minuto 625)
        System.out.print("[OK] Chiusura noleggio AA111AA -> ");
        bikar.chiudiNoleggio("AA111AA", 625.0, 45.4750, 9.1950);
        System.out.println("Nuova disponibilita' AA111AA: " + auto1Aggiornata.isDisponibilita()
                + " | Nuova posizione: (" + auto1Aggiornata.getLatitudine() + ", " + auto1Aggiornata.getLongitudine() + ")");

        // 9. Verifica riutilizzo del mezzo dopo la chiusura (test del controllo oraFine == -1)
        bikar.nuovaPrenotazione(aziendaTech, auto1Aggiornata);
        bikar.nuovoNoleggio("AA111AA", 700.0);
        System.out.print("[OK] Secondo noleggio sullo stesso mezzo chiuso -> ");
        bikar.chiudiNoleggio("AA111AA", 710.0, 45.4800, 9.2000);
    }
}