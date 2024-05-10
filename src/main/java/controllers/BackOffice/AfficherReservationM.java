package controllers.BackOffice;

import entities.ReservationH;
import entities.ReservationM;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.text.Text;
import services.ServiceReservationM;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class AfficherReservationM implements Initializable  {

    @FXML
    private Circle circle;

    @FXML
    private HBox collectBtn;
    @FXML
    private Button utilisateurs;
    @FXML
    private HBox collectBtn1;

    @FXML
    private HBox collectBtn2;

    @FXML
    private HBox collectBtn3;

    @FXML
    private HBox collectBtn4;

    @FXML
    private HBox collectBtn5;

    @FXML
    private Label collectText2;

    @FXML
    private Label collectText3;

    @FXML
    private Label collectText4;

    @FXML
    private Label collectText5;

    @FXML
    private HBox commandsBtn1;

    @FXML
    private Pane content_area;

    @FXML
    private HBox dashboardBtn;

    @FXML
    private ImageView dashboardIcon;

    @FXML
    private Label dashboardText;

    @FXML
    private Button delete;

    @FXML
    private Button export;

    @FXML
    private HBox fundrisingBtn;

    @FXML
    private Button hotels;

    @FXML
    private ListView<ReservationM> listReservationM;

    @FXML
    private Button maisons;

    @FXML
    private HBox navBarLogout;

    @FXML
    private Text navFullname;

    @FXML
    private HBox productsBtn;

    @FXML
    private Button reservationsH;

    @FXML
    private Button reservationsM;

    @FXML
    private Button show;

    @FXML
    private HBox sideBarLogout;

    @FXML
    private HBox usersBtn;

    @FXML
    private Label usersText;


    ServiceReservationM srm = new ServiceReservationM();

    @FXML
    void delete(ActionEvent event) {
        ReservationM selectedreservation = listReservationM.getSelectionModel().getSelectedItem(); // Récupérer l'hôtel sélectionné
        if (selectedreservation != null) {
            try {
                srm.supprimer(selectedreservation.getId()); // Supprimer l'hôtel à partir du service
                refreshTable(); // Rafraîchir la table après la suppression
            } catch (SQLException ex) {
                System.out.println("Erreur lors de la suppression de la reservation: " + ex.getMessage());
                ex.printStackTrace(); // Affichez les détails de l'exception pour un débogage supplémentaire si nécessaire
            }
        } else {
            // Afficher un message d'erreur si aucun hôtel n'est sélectionné
            System.out.println("Veuillez sélectionner une reservation à supprimer.");
        }
    }

    @FXML
    void show(ActionEvent event) {
        try {
            // Load the view ShowHotelB.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/BackOffice/ShowReservationMB.fxml"));
            Parent root = loader.load();

            // Get the controller of ShowHotel
            ShowReservationMB controller = loader.getController();

            ReservationM selectedreservation = listReservationM.getSelectionModel().getSelectedItem();

            // Pass the selected hotel to setData method
            controller.setData(selectedreservation);

            // Check if the loading was successful
            if (root != null) {
                // Create a new scene with the loaded view
                Scene scene = new Scene(root);

                // Get the main stage from the event
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

                // Set the new scene on the stage
                stage.setScene(scene);
                stage.show();
            } else {
                System.err.println("Error: Loading ShowReservationM.fxml failed.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        refreshTable();
    }

    public void refreshTable() {
        // Implémentez votre code pour rafraîchir la ListView ici

        ObservableList<ReservationM> RMList;
        try {
            RMList = FXCollections.observableArrayList(srm.afficher());
        } catch (SQLException ex) {
            RMList = FXCollections.observableArrayList();
            System.out.println("Erreur lors de la récupération des hotels: " + ex.getMessage());
            ex.printStackTrace(); // Affichez les détails de l'exception pour un débogage supplémentaire si nécessaire
        }

        listReservationM.setCellFactory(param -> new ListCell<ReservationM>() {
            private final VBox vbox = new VBox();
            private final Label nbAdultesLabel = new Label();
            private final Label nbEnfantsLabel = new Label();
            private final Label arrangementLabel = new Label();
            private final Label repartitionLabel = new Label();
            private final Label dateDepartLabel = new Label();
            private final Label dateArriveeLabel = new Label();
            private final Label MaisonLabel = new Label();
            private final Label UserLabel = new Label();

            {
                // Personnalisez l'apparence des étiquettes (labels)
                nbAdultesLabel.setStyle("-fx-font-style: italic;");
                nbEnfantsLabel.setStyle("-fx-font-style: italic;");
                arrangementLabel.setStyle("-fx-font-style: italic;");
                repartitionLabel.setStyle("-fx-font-style: italic;");
                dateDepartLabel.setStyle("-fx-font-style: italic;");
                dateArriveeLabel.setStyle("-fx-font-style: italic;");
                MaisonLabel.setStyle("-fx-font-style: italic;");
                UserLabel.setStyle("-fx-font-style: italic;");

                // Ajoutez les étiquettes à la boîte verticale (VBox)
                vbox.getChildren().addAll(MaisonLabel,UserLabel,nbAdultesLabel, nbEnfantsLabel, arrangementLabel, repartitionLabel, dateDepartLabel,dateArriveeLabel);

                // Personnalisez le remplissage et l'espacement de la boîte verticale
                vbox.setSpacing(5);
                vbox.setPadding(new Insets(5));
            }

            @Override
            protected void updateItem(ReservationM reservationM, boolean empty) {
                super.updateItem(reservationM, empty);

                if (empty || reservationM == null) {
                    setGraphic(null);
                } else {
                    // Mettez à jour le texte des étiquettes avec les données de l'hôtel
                    nbAdultesLabel.setText("Nombre Adultes : " + reservationM.getNbAdultes());
                    nbEnfantsLabel.setText("Nombre Enfants : " + reservationM.getNbEnfants());
                    arrangementLabel.setText("Arrangement : " + reservationM.getArrangement());
                    repartitionLabel.setText("Repartition : " + reservationM.getRepartition());
                    dateDepartLabel.setText("DateDepart : " + reservationM.getDateDepart());
                    dateArriveeLabel.setText("DateArrivee : " + reservationM.getDateArrivee());
                    MaisonLabel.setText("Maison : " + reservationM.getMaison().getNom());
                    UserLabel.setText("User : " + reservationM.getUtilisateur().getNom() +" "+ reservationM.getUtilisateur().getPrenom());

                    // Affichez la VBox contenant les étiquettes
                    setGraphic(vbox);
                }
            }
        });

        // Définissez les éléments de la ListView avec la liste observable
        listReservationM.setItems(RMList);
    }

    @FXML
    void exportToExcel(ActionEvent event) {
        try {
            List<ReservationM> reservations = srm.afficher();

            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Reservations");

            // Créer la première ligne avec les titres de colonnes
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("NbAdultes");
            headerRow.createCell(1).setCellValue("DateArrivee");
            headerRow.createCell(2).setCellValue("DateDepart");
            headerRow.createCell(3).setCellValue("Repartition");
            headerRow.createCell(4).setCellValue("Arrangement");
            headerRow.createCell(5).setCellValue("NbEnfants");
            headerRow.createCell(6).setCellValue("NOM Maison");
            headerRow.createCell(7).setCellValue("NOM Utilisateur");
            headerRow.createCell(8).setCellValue("Prenom Utilisateur");

            // Remplir les données des participants dans le fichier Excel
            int rowNum = 1;


            for (ReservationM reservation : reservations) {
                Row row = sheet.createRow(rowNum++);
                LocalDate localDate = reservation.getDateDepart();
                Date dateDepart = java.sql.Date.valueOf(localDate);

                LocalDate localDate1 = reservation.getDateArrivee();
                Date dateArrivee = java.sql.Date.valueOf(localDate);


                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                String dateDepartFormatted = dateFormat.format(dateDepart);
                String dateFinFormatted = dateFormat.format(dateArrivee);

                row.createCell(0).setCellValue(reservation.getNbAdultes());
                row.createCell(1).setCellValue(dateDepartFormatted);
                row.createCell(2).setCellValue(dateFinFormatted);
                row.createCell(3).setCellValue(reservation.getRepartition());
                row.createCell(4).setCellValue(reservation.getArrangement());
                row.createCell(5).setCellValue(reservation.getNbEnfants());
                row.createCell(6).setCellValue(reservation.getMaison().getNom());
                row.createCell(7).setCellValue(reservation.getUtilisateur().getNom());
                row.createCell(8).setCellValue(reservation.getUtilisateur().getPrenom());
            }

            // Ouvrir une boîte de dialogue de sélection de fichier pour choisir l'emplacement de téléchargement
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save Excel File");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel Files", "*.xlsx"));
            File selectedFile = fileChooser.showSaveDialog(new Stage());

            // Vérifier si un fichier a été sélectionné
            if (selectedFile != null) {
                // Enregistrer le fichier Excel dans l'emplacement sélectionné
                try (FileOutputStream outputStream = new FileOutputStream(selectedFile)) {
                    workbook.write(outputStream);
                }
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText("Download Successful");
                alert.setContentText("The Excel file has been downloaded successfully.");
                alert.showAndWait();

                System.out.println("Excel file created successfully in the selected location!");
            } else {
                System.out.println("No download location selected.");
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void hotels(ActionEvent event) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/BackOffice/AfficherHotel.fxml"));
            Parent root = loader.load();


            AfficherHotel controller = loader.getController();



            // Check if the loading was successful
            if (root != null) {
                // Create a new scene with the loaded view
                Scene scene = new Scene(root);

                // Get the main stage from the event
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

                // Set the new scene on the stage
                stage.setScene(scene);
                stage.show();
            } else {
                System.err.println("Error: Loading ShowHotelB.fxml failed.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void maisons(ActionEvent event) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/BackOffice/AfficherMaison.fxml"));
            Parent root = loader.load();


            AfficherMaison controller = loader.getController();



            // Check if the loading was successful
            if (root != null) {
                // Create a new scene with the loaded view
                Scene scene = new Scene(root);

                // Get the main stage from the event
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

                // Set the new scene on the stage
                stage.setScene(scene);
                stage.show();
            } else {
                System.err.println("Error: Loading ShowHotelB.fxml failed.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void reservationsH(ActionEvent event) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/BackOffice/AfficherReservationH.fxml"));
            Parent root = loader.load();


            AfficherReservationH controller = loader.getController();



            // Check if the loading was successful
            if (root != null) {
                // Create a new scene with the loaded view
                Scene scene = new Scene(root);

                // Get the main stage from the event
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

                // Set the new scene on the stage
                stage.setScene(scene);
                stage.show();
            } else {
                System.err.println("Error: Loading ShowHotelB.fxml failed.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void reservationsM(ActionEvent event) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/BackOffice/AfficherReservationM.fxml"));
            Parent root = loader.load();


            AfficherReservationM controller = loader.getController();



            // Check if the loading was successful
            if (root != null) {
                // Create a new scene with the loaded view
                Scene scene = new Scene(root);

                // Get the main stage from the event
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

                // Set the new scene on the stage
                stage.setScene(scene);
                stage.show();
            } else {
                System.err.println("Error: Loading ShowHotelB.fxml failed.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private Button reclamations;

    @FXML
    void reclamations(ActionEvent event) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FrontUser.fxml"));
            Parent root = loader.load();


            AfficherReservationM controller = loader.getController();



            // Check if the loading was successful
            if (root != null) {
                // Create a new scene with the loaded view
                Scene scene = new Scene(root);

                // Get the main stage from the event
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

                // Set the new scene on the stage
                stage.setScene(scene);
                stage.show();
            } else {
                System.err.println("Error: Loading ShowHotelB.fxml failed.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
