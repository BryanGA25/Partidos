package Partidos.View;



import Partidos.logica.Logica;
import Partidos.logica.Utils;
import Partidos.model.Division;
import Partidos.model.Partidos;
import Partidos.model.Resultado;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;


public class AltaPartido extends Stage {

        private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        private Accordion accordion;
        private  TextField local;
        private  TextField localResultado;
        private  TextField visitante;
        private  TextField visitanteResultado;
        private  ObservableList<Division> options;
        private  DatePicker fechaPartido;
        private Button botonAceptar;
        private  ComboBox<Division> comboBox;

    public AltaPartido()  {

        InicializaVista();
        botonAceptar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                LocalDate localDate = fechaPartido.getValue();
                String visitante2=visitante.getText();
                String local2=local.getText();
                String resultadoL=localResultado.getText();
                String resultadoV=visitanteResultado.getText();
                Division division=comboBox.getValue();
                Date date = Utils.convertirToDate(localDate);
                int resultadoLocal=Integer.parseInt(resultadoL);
                int resultadoVisitante=Integer.parseInt(resultadoV);
                Resultado resultado=new Resultado(resultadoLocal,resultadoVisitante);
                Partidos partidos=new Partidos(visitante2,local2,resultado,date,division);
                Logica.getINSTANCE().addPartido(partidos);
                close();
            }
        }) ;



    }

    public AltaPartido(Partidos partido, int indice){

        InicializaVista();
        local.setText(partido.getLocal());
        localResultado.setText(String.valueOf( partido.getResul().getLocal()));
        visitante.setText(partido.getVisitante());
        visitanteResultado.setText(String.valueOf(partido.getResul().getVisitante()));
        comboBox.setValue(partido.getDivision());
        fechaPartido.setValue(Utils.convertirToLocalDate(partido.getDate()));

        botonAceptar.setOnAction(new EventHandler<ActionEvent>() {
        public void handle(ActionEvent event) {
            String nuevoL=local.getText();
            int nuevoResultadoL=Integer.parseInt((String.valueOf(localResultado.getText())));
            String nuevoV=visitante.getText();
            int nuevoVisitanteResultado= Integer.parseInt((String.valueOf(visitanteResultado.getText())));
            Division nuevaDiv=comboBox.getValue();
            Date nuevaFecha=Utils.convertirToDate(fechaPartido.getValue());
            Resultado nuevoResultado=new Resultado(nuevoResultadoL,nuevoVisitanteResultado);
            Partidos nuevoPartido=new Partidos(nuevoV,nuevoL,nuevoResultado,nuevaFecha,nuevaDiv);
            Logica.getINSTANCE().modificarPartido(nuevoPartido,indice);
            close();
        }
    }) ;

    }

    private  void InicializaVista(){
         accordion = new Accordion();


        local = new TextField();
        local.setPromptText("Nombre del equipo local ");
        localResultado = new TextField();
        localResultado.setPromptText("Resultado del equipo local ");
        visitante = new TextField();
        visitante.setPromptText("Introduce el nombre del equipo visitante ");
        visitanteResultado = new TextField();
        visitanteResultado.setPromptText("Introduce el resultado del equipo visitante ");
        options = FXCollections.observableArrayList(Division.primera,Division.segunda,Division.tercera);
        comboBox = new ComboBox(options);

        GridPane gridPane=new GridPane();
        gridPane.add(local,0,0,1,1);
        gridPane.add(localResultado,1,0,1,1);

        HBox vbox1 = new HBox(gridPane);
        VBox vbox2 = new VBox(visitante, visitanteResultado);


        TitledPane titlePane1 = new TitledPane("Primer Equipo", vbox1);
        TitledPane titlePane2 = new TitledPane("Segundo Equipo", vbox2);


        accordion.getPanes().add(titlePane1);
        accordion.getPanes().add(titlePane2);

        fechaPartido = new DatePicker();

        botonAceptar = new Button("Aceptar");



        VBox vboxcompleta = new VBox(titlePane1, titlePane2, fechaPartido, comboBox, botonAceptar);

        TitledPane titled = new TitledPane("PARTIDOS", vboxcompleta);
        initModality(Modality.APPLICATION_MODAL);
        Scene scene = new Scene(titled, 500, 500);
        setScene(scene);
        setTitle("Formulario Partidos");

    }

}

