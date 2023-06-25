
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.Duration;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DialogPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class VentanaIniciarController extends Application implements Initializable {

    @FXML
    private SiguienteController siguienteController;

    @FXML
    private Button btnJugar, btnIntro, stopButton, generarletraButton, ValPalabraButton;

    @FXML
    private Label tiempoLabel, LetraLabel, LetraPuntaje;

    private List<Character> abecedario = new ArrayList<>();

    @FXML
    private TextField NombreTextField, ApellidoTextField, AnimalTextField, ObjetoTextField, FrutaTextField, PaisTextField;

    private Timeline timeline;
    private int tiempoRestante = 61;

    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        // Llena la lista 'abecedario' con las letras del alfabeto
        for (char c = 'A'; c <= 'Z'; c++) {
            abecedario.add(c);
        }
        siguienteController = new SiguienteController();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Ventana Iniciar.fxml"));
        loader.setController(this);
        Parent root = loader.load();

        // Configuración adicional de la ventana y escena
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    @FXML
    private void handleJugarButtonAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(new File("C:/Desarrollos/Java/ProjectStopmania/src/Ventana Intro.fxml").toURI().toURL());
            loader.load();

            // Obtén la instancia existente de VentanaIniciarController
            VentanaIniciarController siguienteController = loader.getController();

            // Obtén la ventana actual
            Stage stage = (Stage) btnJugar.getScene().getWindow();

            // Configura la nueva escena en la ventana
            Scene scene = new Scene(loader.getRoot());
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleIntroButtonAction(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(new File("C:/Desarrollos/Java/ProjectStopmania/src/Ventana.fxml").toURI().toURL());
            loader.load();
            VentanaIniciarController siguienteController = loader.getController();

            // Obtén la ventana actual
            Stage stage = (Stage) btnIntro.getScene().getWindow();

            // Configura la nueva escena en la ventana
            Scene scene = new Scene(loader.getRoot());
            stage.setScene(scene);
            stage.show();

            if (siguienteController != null) {
                siguienteController.configurarCronometro();
            } else {
                System.out.println("El objeto siguienteController es nulo.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleStopButtonAction(ActionEvent event) {
        detenerCronometro();
    }

    private void configurarCronometro() {
        // Crea una instancia de Timeline con una duración de 1 segundo
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            tiempoRestante--;
            tiempoLabel.setText(Integer.toString(tiempoRestante));

            if (tiempoRestante == 0) {
                // Detener el cronómetro cuando el tiempo llega a cero
                detenerCronometro();
            }
        }));
        // Configura que el cronómetro se repita indefinidamente
        timeline.setCycleCount(Timeline.INDEFINITE);

        // Inicia el cronómetro
        timeline.play();
    }

    private void detenerCronometro() {
        timeline.stop();
        stopButton.setDisable(true);
    }

    @FXML
    private void handleGeneratorLetraButtonAction(ActionEvent event) {
        Random random = new Random();
        int indice = random.nextInt(abecedario.size());
        char letraAleatoria = abecedario.get(indice);

        // Salida de letra
        LetraLabel.setText("Letra: " + letraAleatoria);

        // Asignar la letra aleatoria como el primer carácter de los TextField
        NombreTextField.setText(Character.toString(letraAleatoria));
        ApellidoTextField.setText(Character.toString(letraAleatoria));
        AnimalTextField.setText(Character.toString(letraAleatoria));
        ObjetoTextField.setText(Character.toString(letraAleatoria));
        FrutaTextField.setText(Character.toString(letraAleatoria));
        PaisTextField.setText(Character.toString(letraAleatoria));
    }

    //como primer parametro Se valida si cada TextField está vacío o si el primer carácter no coincide con la letra generada
    //posterior se agrega una nueva verificación para asegurarse de que haya una palabra en cada TextField utilizando el método esPalabraValida()
    @FXML
    private void handleValidatorPalabraButtonAction(ActionEvent event) {
        String letraGenerada = LetraLabel.getText().substring(7); // Obtener la letra generada sin el prefijo "Letra: "
        int puntaje = 600; // Puntaje inicial

        if (!esPalabraValida(NombreTextField.getText()) || NombreTextField.getText().equalsIgnoreCase(letraGenerada)) {
            puntaje -= 100;
        }

        if (!esPalabraValida(ApellidoTextField.getText()) || ApellidoTextField.getText().equalsIgnoreCase(letraGenerada)) {
            puntaje -= 100;
        }

        if (!esPalabraValida(AnimalTextField.getText()) || AnimalTextField.getText().equalsIgnoreCase(letraGenerada)) {
            puntaje -= 100;
        }

        if (!esPalabraValida(ObjetoTextField.getText()) || ObjetoTextField.getText().equalsIgnoreCase(letraGenerada)) {
            puntaje -= 100;
        }

        if (!esPalabraValida(FrutaTextField.getText()) || FrutaTextField.getText().equalsIgnoreCase(letraGenerada)) {
            puntaje -= 100;
        }

        if (!esPalabraValida(PaisTextField.getText()) || PaisTextField.getText().equalsIgnoreCase(letraGenerada)) {
            puntaje -= 100;
        }

        // Detener el cronómetro, Al llamar a detenerCronometro(), se detendrá la animación del cronómetro y se desactivará el botón "Stop" para evitar que el tiempo siga avanzando
        detenerCronometro();

        // Asignar el puntaje en el Label LetraPuntaje
        LetraPuntaje.setText("Puntaje: " + puntaje);

        // Mostrar la alerta correspondiente según el puntaje obtenido
        if (puntaje == 600) {
            mostrarAlertaConImagenGanador("¡Ganaste!", "Has obtenido el puntaje máximo de 600 puntos.", "C:/Desarrollos/Java/ProjectStopmania/src/img/Ganador.png");
        } else {
            mostrarAlertaConImagenPerdedor("¡Sigue Intentando!", "No Has obtenido el puntaje máximo de 600 puntos.", "C:/Desarrollos/Java/ProjectStopmania/src/img/Ganador.png");
        }
    }

    private boolean esPalabraValida(String texto) {
        // Verificar si el texto es una palabra válida (solo letras y al menos un carácter)
        return texto.matches("[A-Za-zÁ-ÿÑñ]+");
    }

    //se crea un metodo para el ganador y para el sigue intentando, luego se realiza una instancia de Alert y se configuran el título y el mensaje como antes. 
    //Luego, se carga la imagen utilizando la ruta del archivo proporcionada en imagenPath y se crea un ImageView con la imagen cargada
    private void mostrarAlertaConImagenGanador(String titulo, String mensaje, String imagenPath) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);

        // Cargar la imagen
        Image imagen = new Image("C:/Desarrollos/Java/ProjectStopmania/src/img/Ganador.png");
        ImageView imageView = new ImageView(imagen);

        // Configurar el tamaño de la imagen
        imageView.setFitWidth(100);
        imageView.setFitHeight(100);

        // Crear un DialogPane personalizado
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.setGraphic(imageView);

        alert.showAndWait();
    }

    private void mostrarAlertaConImagenPerdedor(String titulo, String mensaje, String imagenPath) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);

        // Cargar la imagen
        Image imagen = new Image("C:/Desarrollos/Java/ProjectStopmania/src/img/Intentando.png");
        ImageView imageView = new ImageView(imagen);

        // Configurar el tamaño de la imagen
        imageView.setFitWidth(100);
        imageView.setFitHeight(100);

        // Crear un DialogPane personalizado
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.setGraphic(imageView);

        alert.showAndWait();
    }

    @FXML
    private void handleResetButtonAction(ActionEvent event) {
        // Reiniciar los valores de los campos de texto
        NombreTextField.setText("");
        ApellidoTextField.setText("");
        AnimalTextField.setText("");
        ObjetoTextField.setText("");
        FrutaTextField.setText("");
        PaisTextField.setText("");

        // Reiniciar la etiqueta de la letra generada
        LetraLabel.setText("Letra: ");

        // Reiniciar el puntaje
        LetraPuntaje.setText("Puntaje: ");

        // Reiniciar el cronómetro
        tiempoRestante = 60;
        tiempoLabel.setText(Integer.toString(tiempoRestante));
        stopButton.setDisable(false);
        configurarCronometro();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
