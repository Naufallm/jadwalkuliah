package com.example.jadwalkuliahh;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class JadwalKuliahApp extends Application {

    private TableView<JadwalKuliah> table = new TableView<>();
    private ObservableList<JadwalKuliah> data = FXCollections.observableArrayList();

    private TextField namaDosenField = new TextField();
    private TextField mataKuliahField = new TextField();
    private TextField gkbField = new TextField();
    private TextField waktuField = new TextField();
    private TextField ruanganField = new TextField();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Jadwal Kuliah Table");

        TableColumn<JadwalKuliah, String> namaDosenCol = new TableColumn<>("Nama Dosen");
        namaDosenCol.setCellValueFactory(new PropertyValueFactory<>("namaDosen"));

        TableColumn<JadwalKuliah, String> mataKuliahCol = new TableColumn<>("Mata Kuliah");
        mataKuliahCol.setCellValueFactory(new PropertyValueFactory<>("mataKuliah"));

        TableColumn<JadwalKuliah, Integer> gkbCol = new TableColumn<>("GKB");
        gkbCol.setCellValueFactory(new PropertyValueFactory<>("gkb"));

        TableColumn<JadwalKuliah, String> waktuCol = new TableColumn<>("Waktu");
        waktuCol.setCellValueFactory(new PropertyValueFactory<>("waktu"));

        TableColumn<JadwalKuliah, String> ruanganCol = new TableColumn<>("Ruangan");
        ruanganCol.setCellValueFactory(new PropertyValueFactory<>("ruangan"));

        table.getColumns().addAll(namaDosenCol, mataKuliahCol, gkbCol, waktuCol, ruanganCol);
        table.setItems(data);

        GridPane inputGrid = new GridPane();
        inputGrid.setAlignment(javafx.geometry.Pos.CENTER);
        inputGrid.setHgap(10);
        inputGrid.setVgap(10);
        inputGrid.setPadding(new Insets(25, 25, 25, 25));

        inputGrid.add(new Label("Nama Dosen:"), 0, 0);
        inputGrid.add(namaDosenField, 1, 0);
        inputGrid.add(new Label("Mata Kuliah:"), 0, 1);
        inputGrid.add(mataKuliahField, 1, 1);
        inputGrid.add(new Label("GKB:"), 0, 2);
        inputGrid.add(gkbField, 1, 2);
        inputGrid.add(new Label("Waktu:"), 0, 3);
        inputGrid.add(waktuField, 1, 3);
        inputGrid.add(new Label("Ruangan:"), 0, 4);
        inputGrid.add(ruanganField, 1, 4);

        Button createButton = new Button("Create");
        Button updateButton = new Button("Update");
        Button deleteButton = new Button("Delete");

        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(javafx.geometry.Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().addAll(createButton, updateButton, deleteButton);
        inputGrid.add(hbBtn, 1, 5);

        createButton.setOnAction(event -> {
            if (validateInput()) {
                JadwalKuliah jadwal = new JadwalKuliah(
                        namaDosenField.getText(),
                        mataKuliahField.getText(),
                        Integer.parseInt(gkbField.getText()),
                        waktuField.getText(),
                        ruanganField.getText()
                );

                data.add(jadwal);
                clearInputFields();
            }
        });

        updateButton.setOnAction(event -> {
            JadwalKuliah selectedJadwal = table.getSelectionModel().getSelectedItem();
            if (selectedJadwal != null) {
                if (validateInput()) {
                    selectedJadwal.setNamaDosen(namaDosenField.getText());
                    selectedJadwal.setMataKuliah(mataKuliahField.getText());
                    selectedJadwal.setGkb(Integer.parseInt(gkbField.getText()));
                    selectedJadwal.setWaktu(waktuField.getText());
                    selectedJadwal.setRuangan(ruanganField.getText());

                    table.refresh();
                    clearInputFields();
                }
            } else {
                showAlert("Pilih jadwal untuk diperbarui.");
            }
        });

        deleteButton.setOnAction(event -> {
            JadwalKuliah selectedJadwal = table.getSelectionModel().getSelectedItem();
            if (selectedJadwal != null) {
                data.remove(selectedJadwal);
                clearInputFields();
            } else {
                showAlert("Pilih jadwal untuk dihapus.");
            }
        });

        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10, 10, 10, 10));
        vbox.getChildren().addAll(inputGrid, table);

        Scene scene = new Scene(vbox, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private boolean validateInput() {
        if (namaDosenField.getText().isEmpty() || mataKuliahField.getText().isEmpty() || gkbField.getText().isEmpty() ||
                waktuField.getText().isEmpty() || ruanganField.getText().isEmpty()) {
            showAlert("Input tidak boleh kosong!");
            return false;
        }

        try {
            Integer.parseInt(gkbField.getText());
        } catch (NumberFormatException e) {
            showAlert("GKB harus berupa angka!");
            return false;
        }

        return true;
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void clearInputFields() {
        namaDosenField.clear();
        mataKuliahField.clear();
        gkbField.clear();
        waktuField.clear();
        ruanganField.clear();
    }

    public static class JadwalKuliah {
        private String namaDosen;
        private String mataKuliah;
        private int gkb;
        private String waktu;
        private String ruangan;

        public JadwalKuliah(String namaDosen, String mataKuliah, int gkb, String waktu, String ruangan) {
            this.namaDosen = namaDosen;
            this.mataKuliah = mataKuliah;
            this.gkb = gkb;
            this.waktu = waktu;
            this.ruangan = ruangan;
        }

        public String getNamaDosen() {
            return namaDosen;
        }

        public void setNamaDosen(String namaDosen) {
            this.namaDosen = namaDosen;
        }

        public String getMataKuliah() {
            return mataKuliah;
        }

        public void setMataKuliah(String mataKuliah) {
            this.mataKuliah = mataKuliah;
        }

        public int getGkb() {
            return gkb;
        }

        public void setGkb(int gkb) {
            this.gkb = gkb;
        }

        public String getWaktu() {
            return waktu;
        }

        public void setWaktu(String waktu) {
            this.waktu = waktu;
        }

        public String getRuangan() {
            return ruangan;
        }

        public void setRuangan(String ruangan) {
            this.ruangan = ruangan;
        }
    }
}




