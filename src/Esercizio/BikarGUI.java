package Esercizio;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.ArrayList;
import clienti.ClientiFactory;
import mezzi.MezziFactory;

public class BikarGUI extends Application {

    private Sistema bikar;
    private ArrayList<Cliente> clienti;
    private ArrayList<Mezzo> mezzi;
    private ArrayList<Noleggio> noleggi;

    // Liste osservabili per aggiornare le tabelle automaticamente
    private ObservableList<Cliente> obsClienti = FXCollections.observableArrayList();
    private ObservableList<Mezzo> obsMezzi = FXCollections.observableArrayList();

    // Elementi UI da aggiornare dinamicamente
    private TextArea areaLog;
    private Label lblStatClienti, lblStatMezziDisp, lblStatNoleggi;
    private StackPane contentArea;
    private VBox viewClienti, viewMezzi, viewOperazioni;
    private Button navBtnClienti, navBtnMezzi, navBtnOperazioni;

    @Override
    public void start(Stage primaryStage) {
        // 1. Inizializzazione modello
        ArrayList<Prenotazione> prenotazioni = new ArrayList<>();
        clienti = new ArrayList<>();
        mezzi = new ArrayList<>();
        noleggi = new ArrayList<>();

        bikar = new Sistema(prenotazioni, clienti, mezzi, noleggi);
        caricaDatiDemo();

        // 2. Struttura principale (Sidebar a sinistra + Area centrale a destra)
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #f1f5f9; -fx-font-family: 'Ubuntu', 'Segoe UI', sans-serif;");

        // --- SIDEBAR SINISTRA ---
        VBox sidebar = creaSidebar();
        root.setLeft(sidebar);

        // --- CORPO CENTRALE ---
        BorderPane mainBody = new BorderPane();

        // Barra superiore con 3 Card Statistiche
        HBox topStats = creaTopStatsBar();
        mainBody.setTop(topStats);

        // Le 3 viste principali
        viewClienti = creaViewClienti();
        viewMezzi = creaViewMezzi();
        viewOperazioni = creaViewOperazioni();

        contentArea = new StackPane(viewOperazioni, viewMezzi, viewClienti);
        mostraVista(viewClienti, navBtnClienti); // Vista iniziale
        mainBody.setCenter(contentArea);

        // Console inferiore compatta
        mainBody.setBottom(creaConsoleLog());
        root.setCenter(mainBody);

        aggiornaDatiUI();
        scriviLog("Sistema Bikar avviato. Seleziona una voce dal menu a sinistra.");

        Scene scene = new Scene(root, 1080, 740);
        primaryStage.setTitle("Bikar Mobility — Dashboard");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // =========================================================================
    // SIDEBAR DI NAVIGAZIONE MODERNA
    // =========================================================================
    private VBox creaSidebar() {
        VBox sidebar = new VBox(10);
        sidebar.setPrefWidth(230);
        sidebar.setPadding(new Insets(25, 15, 25, 15));
        sidebar.setStyle("-fx-background-color: #0f172a;");

        // Logo / Brand
        Label lblLogo = new Label("BIKAR");
        lblLogo.setStyle("-fx-text-fill: #38bdf8; -fx-font-size: 26px; -fx-font-weight: bold; -fx-padding: 0 0 0 10;");
        Label lblSub = new Label("Smart Fleet Sharing");
        lblSub.setStyle("-fx-text-fill: #94a3b8; -fx-font-size: 12px; -fx-padding: 0 0 25 10;");

        navBtnClienti = creaNavButton("  Clienti & Aziende");
        navBtnMezzi = creaNavButton("  Parco Mezzi");
        navBtnOperazioni = creaNavButton("  Prenota & Noleggia");

        navBtnClienti.setOnAction(e -> mostraVista(viewClienti, navBtnClienti));
        navBtnMezzi.setOnAction(e -> mostraVista(viewMezzi, navBtnMezzi));
        navBtnOperazioni.setOnAction(e -> {
            aggiornaDatiUI();
            mostraVista(viewOperazioni, navBtnOperazioni);
        });

        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        Label lblFooter = new Label("v1.0 • JavaFX UI");
        lblFooter.setStyle("-fx-text-fill: #475569; -fx-font-size: 11px; -fx-padding: 0 0 0 10;");

        sidebar.getChildren().addAll(lblLogo, lblSub, navBtnClienti, navBtnMezzi, navBtnOperazioni, spacer, lblFooter);
        return sidebar;
    }

    private Button creaNavButton(String testo) {
        Button btn = new Button(testo);
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.setAlignment(Pos.CENTER_LEFT);
        btn.setStyle(stileNavInattivo());
        return btn;
    }

    private void mostraVista(VBox vistaAttiva, Button btnAttivo) {
        viewClienti.setVisible(false);
        viewMezzi.setVisible(false);
        viewOperazioni.setVisible(false);
        vistaAttiva.setVisible(true);

        navBtnClienti.setStyle(stileNavInattivo());
        navBtnMezzi.setStyle(stileNavInattivo());
        navBtnOperazioni.setStyle(stileNavInattivo());
        btnAttivo.setStyle(stileNavAttivo());
    }

    private String stileNavInattivo() {
        return "-fx-background-color: transparent; -fx-text-fill: #cbd5e1; -fx-font-size: 14px; "
             + "-fx-padding: 12 15 12 15; -fx-background-radius: 8; -fx-cursor: hand;";
    }

    private String stileNavAttivo() {
        return "-fx-background-color: #1e293b; -fx-text-fill: #38bdf8; -fx-font-size: 14px; -fx-font-weight: bold; "
             + "-fx-padding: 12 15 12 15; -fx-background-radius: 8; -fx-border-color: #38bdf8; -fx-border-width: 0 0 0 3;";
    }

    // =========================================================================
    // BARRA SUPERIORE KPI (CONTATORI LIVE)
    // =========================================================================
    private HBox creaTopStatsBar() {
        HBox bar = new HBox(18);
        bar.setPadding(new Insets(20, 25, 10, 25));

        lblStatClienti = new Label("0");
        lblStatMezziDisp = new Label("0 / 0");
        lblStatNoleggi = new Label("0");

        VBox card1 = creaStatCard("CLIENTI REGISTRATI", lblStatClienti, "#3b82f6");
        VBox card2 = creaStatCard("MEZZI DISPONIBILI", lblStatMezziDisp, "#10b981");
        VBox card3 = creaStatCard("NOLEGGI TOTALI", lblStatNoleggi, "#f59e0b");

        HBox.setHgrow(card1, Priority.ALWAYS);
        HBox.setHgrow(card2, Priority.ALWAYS);
        HBox.setHgrow(card3, Priority.ALWAYS);

        bar.getChildren().addAll(card1, card2, card3);
        return bar;
    }

    private VBox creaStatCard(String titolo, Label lblValore, String accentHex) {
        VBox card = new VBox(4);
        card.setPadding(new Insets(14, 18, 14, 18));
        card.setStyle("-fx-background-color: white; -fx-background-radius: 10; "
                    + "-fx-border-color: " + accentHex + "; -fx-border-width: 0 0 0 4; "
                    + "-fx-effect: dropshadow(three-pass-box, rgba(15,23,42,0.06), 8, 0, 0, 2);");

        Label lblTit = new Label(titolo);
        lblTit.setStyle("-fx-text-fill: #64748b; -fx-font-size: 11px; -fx-font-weight: bold;");
        lblValore.setStyle("-fx-text-fill: #0f172a; -fx-font-size: 22px; -fx-font-weight: bold;");

        card.getChildren().addAll(lblTit, lblValore);
        return card;
    }

    // =========================================================================
    // VISTA 1: CLIENTI PRIVATI E AZIENDALI
    // =========================================================================
    private VBox creaViewClienti() {
        VBox wrapper = new VBox(15);
        wrapper.setPadding(new Insets(15, 25, 15, 25));

        HBox cardsRow = new HBox(20);

        // --- CARD PRIVATO ---
        VBox cardPriv = creaCardBox("Registra Cliente Privato");
        GridPane gPriv = creaFormGrid();
        TextField txtNome = creaInput("Es. Giulia");
        TextField txtCognome = creaInput("Es. Verdi");
        TextField txtEtaPriv = creaInput("25");
        ComboBox<EPatente> cmbPatPriv = new ComboBox<>(FXCollections.observableArrayList(EPatente.values()));
        cmbPatPriv.setValue(EPatente.B);
        cmbPatPriv.setMaxWidth(Double.MAX_VALUE);
        TextField txtLatPriv = creaInput("45.4642");
        TextField txtLonPriv = creaInput("9.1900");
        Button btnAddPriv = creaBottonePrimario("Aggiungi Privato", "#2563eb", "#1d4ed8");

        gPriv.add(new Label("Nome"), 0, 0);        gPriv.add(txtNome, 1, 0);
        gPriv.add(new Label("Cognome"), 0, 1);     gPriv.add(txtCognome, 1, 1);
        gPriv.add(new Label("Età"), 0, 2);         gPriv.add(txtEtaPriv, 1, 2);
        gPriv.add(new Label("Patente"), 0, 3);     gPriv.add(cmbPatPriv, 1, 3);
        gPriv.add(new Label("Latitudine"), 0, 4);  gPriv.add(txtLatPriv, 1, 4);
        gPriv.add(new Label("Longitudine"), 0, 5); gPriv.add(txtLonPriv, 1, 5);

        btnAddPriv.setOnAction(e -> {
            try {
                Cliente c = ClientiFactory.creaClientePrivato(
                    txtNome.getText(), txtCognome.getText(),
                    Double.parseDouble(txtLatPriv.getText()), Double.parseDouble(txtLonPriv.getText()),
                    cmbPatPriv.getValue(), Integer.parseInt(txtEtaPriv.getText())
                );
                bikar.aggiungiCliente(c);
                aggiornaDatiUI();
                scriviLog("[OK] Registrato Privato -> " + c.getId() + " (" + txtNome.getText() + " " + txtCognome.getText() + ")");
                txtNome.clear(); txtCognome.clear();
            } catch (Exception ex) { mostraErrore(ex); }
        });

        cardPriv.getChildren().addAll(gPriv, btnAddPriv);

        // --- CARD AZIENDALE ---
        VBox cardAz = creaCardBox("Registra Cliente Aziendale");
        GridPane gAz = creaFormGrid();
        TextField txtRagione = creaInput("Es. Acme S.p.A.");
        TextField txtEtaDip = creaInput("34");
        ComboBox<EPatente> cmbPatDip = new ComboBox<>(FXCollections.observableArrayList(EPatente.values()));
        cmbPatDip.setValue(EPatente.B);
        cmbPatDip.setMaxWidth(Double.MAX_VALUE);
        TextField txtLatAz = creaInput("45.4600");
        TextField txtLonAz = creaInput("9.1850");
        Button btnAddAz = creaBottonePrimario("Aggiungi Azienda", "#0d9488", "#0f766e");

        gAz.add(new Label("Ragione Sociale"), 0, 0);    gAz.add(txtRagione, 1, 0);
        gAz.add(new Label("Età Conducente"), 0, 1);     gAz.add(txtEtaDip, 1, 1);
        gAz.add(new Label("Patente Conducente"), 0, 2); gAz.add(cmbPatDip, 1, 2);
        gAz.add(new Label("Latitudine"), 0, 3);         gAz.add(txtLatAz, 1, 3);
        gAz.add(new Label("Longitudine"), 0, 4);        gAz.add(txtLonAz, 1, 4);

        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        btnAddAz.setOnAction(e -> {
            try {
                Cliente c = ClientiFactory.creaClienteAziendale(
                    txtRagione.getText(), Integer.parseInt(txtEtaDip.getText()),
                    cmbPatDip.getValue(), Double.parseDouble(txtLatAz.getText()), Double.parseDouble(txtLonAz.getText())
                );
                bikar.aggiungiCliente(c);
                aggiornaDatiUI();
                scriviLog("[OK] Registrata Azienda -> " + c.getId() + " (" + txtRagione.getText() + ")");
                txtRagione.clear();
            } catch (Exception ex) { mostraErrore(ex); }
        });

        cardAz.getChildren().addAll(gAz, spacer, btnAddAz);

        HBox.setHgrow(cardPriv, Priority.ALWAYS);
        HBox.setHgrow(cardAz, Priority.ALWAYS);
        cardsRow.getChildren().addAll(cardPriv, cardAz);
        wrapper.getChildren().add(cardsRow);
        return wrapper;
    }

    // =========================================================================
    // VISTA 2: PARCO MEZZI
    // =========================================================================
    private VBox creaViewMezzi() {
        VBox wrapper = new VBox(15);
        wrapper.setPadding(new Insets(15, 25, 15, 25));

        HBox split = new HBox(20);

        // Form inserimento mezzo
        VBox cardForm = creaCardBox("Nuovo Veicolo in Flotta");
        cardForm.setPrefWidth(360);
        GridPane grid = creaFormGrid();

        ComboBox<String> cmbTipo = new ComboBox<>(FXCollections.observableArrayList("Automobile", "Scooter", "Moto", "Furgone"));
        cmbTipo.setValue("Automobile");
        cmbTipo.setMaxWidth(Double.MAX_VALUE);

        ComboBox<EAlimentazione> cmbAlim = new ComboBox<>(FXCollections.observableArrayList(EAlimentazione.values()));
        cmbAlim.setValue(EAlimentazione.ELETTRICA);
        cmbAlim.setMaxWidth(Double.MAX_VALUE);

        TextField txtLat = creaInput("45.4640");
        TextField txtLon = creaInput("9.1902");
        Button btnAdd = creaBottonePrimario("Inserisci Mezzo", "#2563eb", "#1d4ed8");

        grid.add(new Label("Tipologia"), 0, 0);     grid.add(cmbTipo, 1, 0);
        grid.add(new Label("Alimentazione"), 0, 1); grid.add(cmbAlim, 1, 1);
        grid.add(new Label("Latitudine"), 0, 2);    grid.add(txtLat, 1, 2);
        grid.add(new Label("Longitudine"), 0, 3);   grid.add(txtLon, 1, 3);

        btnAdd.setOnAction(e -> {
            try {
                String tipo = cmbTipo.getValue();
                EAlimentazione alim = cmbAlim.getValue();
                double lat = Double.parseDouble(txtLat.getText());
                double lon = Double.parseDouble(txtLon.getText());

                Mezzo m = switch (tipo) {
                    case "Automobile" -> MezziFactory.creaAutomobile(alim, lat, lon);
                    case "Scooter" -> MezziFactory.creaScooter(alim, lat, lon);
                    case "Moto" -> MezziFactory.creaMoto(lat, lon);
                    default -> MezziFactory.creaFurgone(lat, lon);
                };
                bikar.aggiungiMezzo(m);
                aggiornaDatiUI();
                scriviLog("[OK] Aggiunto " + tipo + " -> Targa: " + m.getTarga());
            } catch (Exception ex) { mostraErrore(ex); }
        });
        cardForm.getChildren().addAll(grid, btnAdd);

        // Tabella live della flotta
        VBox cardTabella = creaCardBox("Flotta Attuale");
        TableView<Mezzo> tableMezzi = creaTabellaMezzi();
        HBox.setHgrow(cardTabella, Priority.ALWAYS);
        cardTabella.getChildren().add(tableMezzi);

        split.getChildren().addAll(cardForm, cardTabella);
        wrapper.getChildren().add(split);
        return wrapper;
    }

    // =========================================================================
    // VISTA 3: PRENOTAZIONE E NOLEGGIO INTERATTIVO
    // =========================================================================
    private VBox creaViewOperazioni() {
        VBox wrapper = new VBox(15);
        wrapper.setPadding(new Insets(15, 25, 15, 25));

        HBox topRow = new HBox(20);

        // --- STEP 1: PRENOTAZIONE CON SELEZIONE RAPIDA ---
        VBox cardPren = creaCardBox("1. Prenota Mezzo");
        GridPane gPren = creaFormGrid();

        ComboBox<String> cmbCliente = new ComboBox<>();
        cmbCliente.setMaxWidth(Double.MAX_VALUE);
        cmbCliente.setPromptText("Seleziona Cliente...");

        TextField txtTarga = creaInput("Clicca un mezzo sotto o scrivi targa");
        Button btnPrenota = creaBottonePrimario("Conferma Prenotazione", "#d97706", "#b45309");

        // Aggiorna menu clienti ogni volta che si apre
        cmbCliente.setOnShowing(e -> {
            cmbCliente.getItems().clear();
            for (Cliente c : clienti) {
                cmbCliente.getItems().add(c.getId() + " (Pat. " + c.getConducentePatente() + ", " + c.getConducenteEta() + " anni)");
            }
        });

        gPren.add(new Label("Cliente"), 0, 0);     gPren.add(cmbCliente, 1, 0);
        gPren.add(new Label("Targa Mezzo"), 0, 1); gPren.add(txtTarga, 1, 1);

        btnPrenota.setOnAction(e -> {
            try {
                if (cmbCliente.getValue() == null) throw new RuntimeException("Seleziona prima un cliente dal menu!");
                String idCliente = cmbCliente.getValue().split(" ")[0];
                Cliente c = trovaCliente(idCliente);
                Mezzo m = trovaMezzo(txtTarga.getText().trim());
                bikar.nuovaPrenotazione(c, m);
                aggiornaDatiUI();
                scriviLog("[OK] Prenotazione confermata: " + c.getId() + " -> " + m.getTarga());
            } catch (Exception ex) { mostraErrore(ex); }
        });

        cardPren.getChildren().addAll(gPren, btnPrenota);

        // --- STEP 2: AVVIO E CHIUSURA NOLEGGIO ---
        VBox cardNol = creaCardBox("2. Avvia & Chiudi Noleggio");
        GridPane gNol = creaFormGrid();

        TextField txtTargaNol = creaInput("Targa mezzo prenotato");
        TextField txtMinuto = creaInput("600.0");
        TextField txtLatArr = creaInput("45.4750");
        TextField txtLonArr = creaInput("9.1950");

        Button btnAvvia = creaBottonePrimario("Avvia Noleggio", "#16a34a", "#15803d");
        Button btnChiudi = creaBottonePrimario("Chiudi Noleggio", "#dc2626", "#b91c1c");
        HBox btnBox = new HBox(10, btnAvvia, btnChiudi);
        HBox.setHgrow(btnAvvia, Priority.ALWAYS);
        HBox.setHgrow(btnChiudi, Priority.ALWAYS);

        gNol.add(new Label("Targa Mezzo"), 0, 0);      gNol.add(txtTargaNol, 1, 0);
        gNol.add(new Label("Minuto Attuale"), 0, 1);   gNol.add(txtMinuto, 1, 1);
        gNol.add(new Label("Lat. Rilascio"), 0, 2);    gNol.add(txtLatArr, 1, 2);
        gNol.add(new Label("Lon. Rilascio"), 0, 3);    gNol.add(txtLonArr, 1, 3);

        btnAvvia.setOnAction(e -> {
            try {
                String targa = txtTargaNol.getText().trim();
                double min = Double.parseDouble(txtMinuto.getText());
                bikar.nuovoNoleggio(targa, min);
                aggiornaDatiUI();
                scriviLog("[OK] Noleggio AVVIATO su " + targa + " al minuto " + min);
                txtMinuto.setText(String.valueOf(min + 25.0));
            } catch (Exception ex) { mostraErrore(ex); }
        });

        btnChiudi.setOnAction(e -> {
            try {
                String targa = txtTargaNol.getText().trim();
                double min = Double.parseDouble(txtMinuto.getText());
                double lat = Double.parseDouble(txtLatArr.getText());
                double lon = Double.parseDouble(txtLonArr.getText());
                bikar.chiudiNoleggio(targa, min, lat, lon);
                aggiornaDatiUI();
                scriviLog("[OK] Noleggio CHIUSO su " + targa + " al minuto " + min);
            } catch (Exception ex) { mostraErrore(ex); }
        });

        cardNol.getChildren().addAll(gNol, btnBox);

        HBox.setHgrow(cardPren, Priority.ALWAYS);
        HBox.setHgrow(cardNol, Priority.ALWAYS);
        topRow.getChildren().addAll(cardPren, cardNol);

        // --- TABELLA INTERATTIVA SOTTO: CLICCA UN MEZZO PER COMPILARE LE TARGHE ---
        VBox cardLista = creaCardBox("Clicca su un mezzo nella tabella per selezionarlo automaticamente:");
        TableView<Mezzo> tabellaInterattiva = creaTabellaMezzi();
        tabellaInterattiva.setPrefHeight(165);
        tabellaInterattiva.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {
                txtTarga.setText(newSel.getTarga());
                txtTargaNol.setText(newSel.getTarga());
            }
        });
        cardLista.getChildren().add(tabellaInterattiva);

        wrapper.getChildren().addAll(topRow, cardLista);
        return wrapper;
    }

    // =========================================================================
    // TABELLA MEZZI E CONSOLE LOG
    // =========================================================================
    private TableView<Mezzo> creaTabellaMezzi() {
        TableView<Mezzo> table = new TableView<>(obsMezzi);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<Mezzo, String> colTarga = new TableColumn<>("Targa");
        colTarga.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getTarga()));

        TableColumn<Mezzo, String> colTipo = new TableColumn<>("Tipologia");
        colTipo.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getClass().getSimpleName()));

        TableColumn<Mezzo, String> colPos = new TableColumn<>("Posizione (Lat, Lon)");
        colPos.setCellValueFactory(d -> new SimpleStringProperty(
                String.format("%.4f, %.4f", d.getValue().getLatitudine(), d.getValue().getLongitudine())));

        TableColumn<Mezzo, String> colStato = new TableColumn<>("Stato");
        colStato.setCellValueFactory(d -> new SimpleStringProperty(
                d.getValue().isDisponibilita() ? "DISPONIBILE" : "OCCUPATO"));

        colStato.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(item);
                    if (item.equals("DISPONIBILE")) {
                        setStyle("-fx-text-fill: #16a34a; -fx-font-weight: bold;");
                    } else {
                        setStyle("-fx-text-fill: #dc2626; -fx-font-weight: bold;");
                    }
                }
            }
        });

        table.getColumns().addAll(colTarga, colTipo, colPos, colStato);
        return table;
    }

    private VBox creaConsoleLog() {
        VBox box = new VBox(4);
        box.setPadding(new Insets(5, 25, 18, 25));

        areaLog = new TextArea();
        areaLog.setEditable(false);
        areaLog.setPrefRowCount(4);
        areaLog.setStyle("-fx-control-inner-background: #0f172a; -fx-text-fill: #38bdf8; "
                       + "-fx-font-family: 'Monospace'; -fx-font-size: 12px; -fx-background-radius: 8;");
        box.getChildren().add(areaLog);
        return box;
    }

    // =========================================================================
    // COMPONENTI UI RIUTILIZZABILI (CARD, INPUT, BOTTONI CON HOVER)
    // =========================================================================
    private VBox creaCardBox(String titolo) {
        VBox card = new VBox(12);
        card.setPadding(new Insets(18));
        card.setStyle("-fx-background-color: white; -fx-background-radius: 12; "
                    + "-fx-effect: dropshadow(three-pass-box, rgba(15,23,42,0.06), 10, 0, 0, 3);");

        Label lbl = new Label(titolo);
        lbl.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: #0f172a;");
        card.getChildren().add(lbl);
        return card;
    }

    private GridPane creaFormGrid() {
        GridPane g = new GridPane();
        g.setHgap(12);
        g.setVgap(10);
        ColumnConstraints c1 = new ColumnConstraints();
        c1.setPercentWidth(38);
        ColumnConstraints c2 = new ColumnConstraints();
        c2.setPercentWidth(62);
        g.getColumnConstraints().addAll(c1, c2);
        return g;
    }

    private TextField creaInput(String placeholder) {
        TextField tf = new TextField();
        tf.setPromptText(placeholder);
        if (placeholder.matches("-?\\d+(\\.\\d+)?")) {
            tf.setText(placeholder); // Se è un numero demo, lo precompila
        }
        tf.setStyle("-fx-background-color: #f8fafc; -fx-border-color: #cbd5e1; "
                  + "-fx-border-radius: 6; -fx-background-radius: 6; -fx-padding: 6 10 6 10;");
        return tf;
    }

    private Button creaBottonePrimario(String testo, String colorHex, String hoverHex) {
        Button btn = new Button(testo);
        btn.setMaxWidth(Double.MAX_VALUE);
        String baseStyle = "-fx-background-color: " + colorHex + "; -fx-text-fill: white; "
                         + "-fx-font-weight: bold; -fx-padding: 9 16 9 16; -fx-background-radius: 7; -fx-cursor: hand;";
        String hoverStyle = "-fx-background-color: " + hoverHex + "; -fx-text-fill: white; "
                          + "-fx-font-weight: bold; -fx-padding: 9 16 9 16; -fx-background-radius: 7; -fx-cursor: hand;";
        btn.setStyle(baseStyle);
        btn.setOnMouseEntered(e -> btn.setStyle(hoverStyle));
        btn.setOnMouseExited(e -> btn.setStyle(baseStyle));
        return btn;
    }

    // =========================================================================
    // LOGICA DI AGGIORNAMENTO E RICERCA
    // =========================================================================
    private void aggiornaDatiUI() {
        obsClienti.setAll(clienti);
        obsMezzi.setAll(mezzi);

        long disp = mezzi.stream().filter(Mezzo::isDisponibilita).count();
        lblStatClienti.setText(String.valueOf(clienti.size()));
        lblStatMezziDisp.setText(disp + " / " + mezzi.size());
        lblStatNoleggi.setText(String.valueOf(noleggi.size()));
    }

    private Cliente trovaCliente(String id) {
        for (Cliente c : clienti) {
            if (c.getId().equalsIgnoreCase(id)) return c;
        }
        throw new RuntimeException("Cliente '" + id + "' non trovato!");
    }

    private Mezzo trovaMezzo(String targa) {
        for (Mezzo m : mezzi) {
            if (m.getTarga().equalsIgnoreCase(targa)) return m;
        }
        throw new RuntimeException("Mezzo '" + targa + "' non trovato!");
    }

    private void mostraErrore(Exception ex) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Avviso Bikar");
        alert.setHeaderText("Operazione non consentita");
        alert.setContentText(ex.getMessage());
        alert.showAndWait();
        scriviLog("[ERRORE] " + ex.getMessage());
    }

    private void scriviLog(String testo) {
        areaLog.appendText(testo + "\n");
    }

    private void caricaDatiDemo() {
        Cliente mario = ClientiFactory.creaClientePrivato("Mario", "Rossi", 45.4642, 9.1900, EPatente.B, 25);
        Cliente luca = ClientiFactory.creaClientePrivato("Luca", "Bianchi", 45.4650, 9.1910, EPatente.NULL, 17);
        Cliente azienda = ClientiFactory.creaClienteAziendale("TechSrl", 35, EPatente.B, 45.4600, 9.1850);

        Mezzo auto1 = MezziFactory.creaAutomobile(EAlimentazione.ELETTRICA, 45.4640, 9.1902);
        Mezzo scooter1 = MezziFactory.creaScooter(EAlimentazione.ELETTRICA, 45.4655, 9.1915);
        Mezzo moto1 = MezziFactory.creaMoto(45.4700, 9.2000);

        bikar.aggiungiCliente(mario);
        bikar.aggiungiCliente(luca);
        bikar.aggiungiCliente(azienda);
        bikar.aggiungiMezzo(auto1);
        bikar.aggiungiMezzo(scooter1);
        bikar.aggiungiMezzo(moto1);
    }

    public static void main(String[] args) {
        launch(args);
    }
}