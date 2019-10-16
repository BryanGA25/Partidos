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

import java.time.format.DateTimeFormatter;

public class Tabla extends Application {


    private ObjectOutputStream ficheroPartidos;
    private int idPartido;

    public static void main(String[] args){

        launch();
    }




    @Override
    public void start(Stage stage) throws Exception {




        AnchorPane anchorPaneTabla = new AnchorPane();
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




        AnchorPane.setTopAnchor(tablaPartidos,30.0d);
        AnchorPane.setRightAnchor(tablaPartidos,25.0d);
        AnchorPane.setLeftAnchor(tablaPartidos,25.0d);
        anchorPaneTabla.getChildren().add(tablaPartidos);

        VBox vboxTabla=new VBox(anchorPaneTabla);
        
        
        
        tablaPartidos.getSelectionModel().setSelectionMode(
                SelectionMode.MULTIPLE

        );
        Button anadirPartido = new Button("Añadir partido");
        anadirPartido.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage newStage = new AltaPartido();
                newStage.show();
            }
        });

        Button modificarPartido = new Button("modificar partido");
        modificarPartido.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) {
                Alert alertaModificado = new Alert(Alert.AlertType.CONFIRMATION);
                alertaModificado.setTitle("Modificar partido");
                alertaModificado.setHeaderText("Confirmación");
                alertaModificado.setContentText("¿Seguro que quieres mdoficar el registro?");
                alertaModificado.showAndWait();
                if ((alertaModificado.getResult() == ButtonType.OK)) {
                    idPartido= tablaPartidos.getSelectionModel().getSelectedIndex();
                    if (idPartido!=-1) {

                        Partidos partidoSeleccionado =(Partidos) Logica.getINSTANCE().getListaPartidos().get(idPartido);
                        AltaPartido altaPartido =new AltaPartido(partidoSeleccionado,idPartido);
                        altaPartido.show();
                    }else {
                        Alert alertaNoSeleccionado=new Alert(Alert.AlertType.INFORMATION);
                        alertaNoSeleccionado.setTitle("Problema");
                        alertaNoSeleccionado.setContentText("No ha seleccionado un partido");
                        alertaNoSeleccionado.setHeaderText("Información ");
                        alertaNoSeleccionado.show();
                    }

            }
        }});

        Button borrarPartido = new Button("Borrar Partido");
        borrarPartido.setOnAction(new EventHandler<ActionEvent>() {

                public void handle(ActionEvent event) {
                    Alert alertaBorrar = new Alert(Alert.AlertType.CONFIRMATION);
                    alertaBorrar.setTitle("Alerta de Borrado");
                    alertaBorrar.setHeaderText("Confirmación");
                    alertaBorrar.setContentText("¿Seguro que quiere borrar el registro?");
                    alertaBorrar.showAndWait();
                    if ((alertaBorrar.getResult() == ButtonType.OK)) {
                            idPartido = tablaPartidos.getSelectionModel().getSelectedIndex();
                        if (idPartido!=-1) {
                            Logica.getINSTANCE().borraPartido(idPartido);
                        }else {
                            Alert alertaNoSeleccionado=new Alert(Alert.AlertType.INFORMATION);
                            alertaNoSeleccionado.setTitle("Problema");
                            alertaNoSeleccionado.setContentText("No ha seleccionado un partido");
                            alertaNoSeleccionado.setHeaderText("Información ");
                            alertaNoSeleccionado.show();
                        }

                    }

                }
            });




        AnchorPane anchorPaneAnadir = new AnchorPane();
        AnchorPane anchorPaneBorrar = new AnchorPane();
        AnchorPane anchorPaneModificar = new AnchorPane();
        AnchorPane.setTopAnchor(anadirPartido,30.0d);
        AnchorPane.setRightAnchor(anadirPartido,25.0d);
        AnchorPane.setLeftAnchor(anadirPartido,25.0d);
        AnchorPane.setTopAnchor(borrarPartido,30.0d);
        AnchorPane.setRightAnchor(borrarPartido,25.0d);
        AnchorPane.setLeftAnchor(borrarPartido,25.0d);
        AnchorPane.setTopAnchor(modificarPartido,30.0d);
        AnchorPane.setRightAnchor(modificarPartido,25.0d);
        AnchorPane.setLeftAnchor(modificarPartido,25.0d);
        anchorPaneAnadir.getChildren().add(anadirPartido);
        anchorPaneBorrar.getChildren().add(borrarPartido);
        anchorPaneModificar.getChildren().add(modificarPartido);
        ImageView imagenPartido = new ImageView(getClass().getResource("resources/1S.jpg").toExternalForm());
        imagenPartido.setPreserveRatio(true);
        imagenPartido.setFitHeight(150);

        HBox hboxBotones=new HBox(anchorPaneAnadir,anchorPaneBorrar,anchorPaneModificar,imagenPartido);

        Scene scene = new Scene(new VBox(vboxTabla,hboxBotones),750,750);
        stage.setScene(scene);
        stage.show();
        stage.setTitle("Tabla de Partidos");
        stage.setOnCloseRequest( event ->
        {

            try {
                    ficheroPartidos=new ObjectOutputStream(new FileOutputStream("partidos.txt"));
            } catch (IOException e) {
                e.printStackTrace();
            }for (int i=0; i< Logica.getINSTANCE().getListaPartidos().size();i++) {

            try {
                ficheroPartidos.writeObject(Logica.getINSTANCE().getArray().get(i));
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
            try {
                ficheroPartidos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
