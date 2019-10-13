package Partidos.View;

import Partidos.logica.Logica;
import Partidos.model.Partidos;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.Date;

public class Tabla extends Application {

    private ObjectOutputStream ficheroPartidos;

    public static void main(String[] args){

        launch();
    }




    @Override
    public void start(Stage stage) throws Exception {




        AnchorPane anchorPane = new AnchorPane();
        TableView tablaPartidos = new TableView(Logica.getINSTANCE().getListaPartidos());

        TableColumn<String, Partidos> cLocal = new TableColumn<>("Local");
        cLocal.setCellValueFactory(new PropertyValueFactory<>("local"));

        TableColumn<String, Partidos> cVisitante = new TableColumn<>("Visitante");
        cVisitante.setCellValueFactory(new PropertyValueFactory<>("visitante"));

        TableColumn<String, Partidos> cResultado = new TableColumn<>("Resultado");
        cResultado.setCellValueFactory(new PropertyValueFactory<>("resul"));

        TableColumn<String, Partidos> cFecha = new TableColumn<>("Fecha");
        cFecha.setCellValueFactory((new PropertyValueFactory("date")));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        TableColumn<String, Partidos> cDivisio = new TableColumn<>("Division");
        cDivisio.setCellValueFactory(new PropertyValueFactory<>("division"));



        tablaPartidos.getColumns().add(cLocal);
        tablaPartidos.getColumns().add(cVisitante);
        tablaPartidos.getColumns().add(cResultado);
        tablaPartidos.getColumns().add(cFecha);
        tablaPartidos.getColumns().add(cDivisio);

        ImageView imagenPartido = new ImageView(getClass().getResource("resources/1S.jpg").toExternalForm());
        imagenPartido.setPreserveRatio(true);
        imagenPartido.setFitHeight(250);


        AnchorPane.setTopAnchor(tablaPartidos,30.0d);
        AnchorPane.setRightAnchor(tablaPartidos,25.0d);
        AnchorPane.setLeftAnchor(tablaPartidos,25.0d);
        anchorPane.getChildren().add(tablaPartidos);

        VBox vboxTabla=new VBox(anchorPane);
        
        
        
        tablaPartidos.getSelectionModel().setSelectionMode(
                SelectionMode.MULTIPLE

        );
        Button anadirPartido = new Button("Añadir partido");
        anadirPartido.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage newStage = newStage = new AltaPartido();

                newStage.show();


            }
        });

        Button modificarPartido = new Button("modificar partido");
        modificarPartido.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) {
                int indice = tablaPartidos.getSelectionModel().getSelectedIndex();
                Partidos partidoSeleccionado =(Partidos) Logica.getINSTANCE().getListaPartidos().get(indice);
                AltaPartido altaPartido =new AltaPartido(partidoSeleccionado,indice);
                altaPartido.show();
            }
        });

        Button borrarPartido = new Button("Borrar Partido");
        borrarPartido.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) {

                int idPartido =tablaPartidos.getSelectionModel().getSelectedIndex();
                Logica.getINSTANCE().borraPartido(idPartido);


            }
        });


        HBox hboxBotones=new HBox(anadirPartido,borrarPartido,modificarPartido,imagenPartido);

        Scene scene = new Scene(new VBox(vboxTabla,hboxBotones),750,750);
        stage.setScene(scene);
        stage.show();
        stage.setTitle("Tabla de Partidos");
        stage.setOnCloseRequest( event ->
        {

            try {
                    ficheroPartidos=new ObjectOutputStream(new FileOutputStream("resources/partidos.txt") );
            } catch (IOException e) {
                e.printStackTrace();
            }for (int i=0; i< Logica.getINSTANCE().getListaPartidos().size();i++) {

            try {
                ficheroPartidos.writeObject(Logica.getINSTANCE().getArray());
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        });
    }
}
