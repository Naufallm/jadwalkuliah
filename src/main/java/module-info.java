module com.example.jadwalkuliahh {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.jadwalkuliahh to javafx.fxml;
    exports com.example.jadwalkuliahh;
}