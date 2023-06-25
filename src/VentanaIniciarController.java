
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

    /**
     * @param initialize Llena la lista 'abecedario' con las letras del alfabeto
     */
    public void initialize(URL url, ResourceBundle rb) {
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
            VentanaIniciarController siguienteController = loader.getController();
            Stage stage = (Stage) btnJugar.getScene().getWindow();
            Scene scene = new Scene(loader.getRoot());
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param handleIntroButtonAction metodo donde se llama la siguiente ventana
     * @param Stage instacia que Obtiene la ventana actual
     */
    @FXML
    private void handleIntroButtonAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(new File("C:/Desarrollos/Java/ProjectStopmania/src/Ventana.fxml").toURI().toURL());
            loader.load();
            VentanaIniciarController siguienteController = loader.getController();
            Stage stage = (Stage) btnIntro.getScene().getWindow();
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

    /**
     * @param configurarCronometro metodo donde se Crea una instancia de
     * Timeline con una duración de 1 segundo * aleatoria
     * @param setCycleCount Se Configura que el cronómetro se repita
     * indefinidamente
     * @param play Inicia el cronómetro
     */
    private void configurarCronometro() {
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            tiempoRestante--;
            tiempoLabel.setText(Integer.toString(tiempoRestante));

            if (tiempoRestante == 0) {
                detenerCronometro();
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);

        timeline.play();
    }

    private void detenerCronometro() {
        timeline.stop();
        stopButton.setDisable(true);
    }

    /**
     * @param handleGeneratorLetraButtonAction metodo donde se genera la letra
     * aleatoria
     * @param LetraLabel donde se muestra la Salida de la letra
     */
    @FXML
    private void handleGeneratorLetraButtonAction(ActionEvent event) {
        Random random = new Random();
        int indice = random.nextInt(abecedario.size());
        char letraAleatoria = abecedario.get(indice);

        LetraLabel.setText("Letra: " + letraAleatoria);

        /**
         * Asignar la letra aleatoria como el primer carácter de los TextField
         */
        NombreTextField.setText(Character.toString(letraAleatoria));
        ApellidoTextField.setText(Character.toString(letraAleatoria));
        AnimalTextField.setText(Character.toString(letraAleatoria));
        ObjetoTextField.setText(Character.toString(letraAleatoria));
        FrutaTextField.setText(Character.toString(letraAleatoria));
        PaisTextField.setText(Character.toString(letraAleatoria));
    }

    /**
     * @param handleValidatorPalabraButtonAction metodo donde se valida si cada
     * TextField está vacío o si el primer carácter no coincide con la letra
     * generada, Luego se agrega una nueva verificación para asegurarse de que
     * haya una palabra en cada TextField utilizando el método esPalabraValida()
     * @param puntaje Puntaje inicial de 600
     * @param detenerCronometro metodo que se utiliza para detener el cronómetro
     * @param LetraPuntaje donde se Asigna el puntaje en el Label LetraPuntaje
     */
    @FXML
    private void handleValidatorPalabraButtonAction(ActionEvent event) {
        String letraGenerada = LetraLabel.getText().substring(7); // Obtener la letra generada sin el prefijo "Letra: "
        int puntaje = 600;

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

        detenerCronometro();

        LetraPuntaje.setText("Puntaje: " + puntaje);

        if (puntaje == 600) {
            mostrarAlertaConImagenGanador("¡Ganaste!", "Has obtenido el puntaje máximo de 600 puntos.", "C:/Desarrollos/Java/ProjectStopmania/src/img/Ganador.png");
        } else {
            mostrarAlertaConImagenPerdedor("¡Sigue Intentando!", "No Has obtenido el puntaje máximo de 600 puntos.", "C:/Desarrollos/Java/ProjectStopmania/src/img/Ganador.png");
        }
    }

    /**
     * @param esPalabraValida metodo para Verificar si el texto es una palabra
     * válida (solo letras y al menos un carácter)
     */
    private boolean esPalabraValida(String texto) {
        return texto.matches("[A-Za-zÁ-ÿÑñ]+");
    }

    /**
     * @param mostrarAlertaConImagenGanador metodo ganador se realiza una
     * instancia de Alert y se configuran el título y el mensaje como antes.
     * @param Image, instanacia para cargar la imagen utilizada
     * @param imageView Se configura el tamaño de la imagen
     * @param DialogPane Se Crea un DialogPane personalizado
     */
    private void mostrarAlertaConImagenGanador(String titulo, String mensaje, String imagenPath) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);

        Image imagen = new Image("C:/Desarrollos/Java/ProjectStopmania/src/img/Ganador.png");
        ImageView imageView = new ImageView(imagen);

        imageView.setFitWidth(100);
        imageView.setFitHeight(100);

        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.setGraphic(imageView);

        alert.showAndWait();
    }

    /**
     * @param Cargar la imagen
     * @param Configurar el tamaño de la imagen
     * @param Crear un DialogPane personalizado.
     * @param Reiniciar el cronómetro
     */
    private void mostrarAlertaConImagenPerdedor(String titulo, String mensaje, String imagenPath) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);

        Image imagen = new Image("C:/Desarrollos/Java/ProjectStopmania/src/img/Intentando.png");
        ImageView imageView = new ImageView(imagen);

        imageView.setFitWidth(100);
        imageView.setFitHeight(100);

        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.setGraphic(imageView);

        alert.showAndWait();
    }

    /**
     * @param Reiniciar los valores de los campos de texto
     * @param Reiniciar la etiqueta de la letra generada
     * @param Reiniciar Reiniciar el puntaje.
     * @param Reiniciar el cronómetro
     */
    @FXML
    private void handleResetButtonAction(ActionEvent event) {

        NombreTextField.setText("");
        ApellidoTextField.setText("");
        AnimalTextField.setText("");
        ObjetoTextField.setText("");
        FrutaTextField.setText("");
        PaisTextField.setText("");

        LetraLabel.setText("Letra: ");

        LetraPuntaje.setText("Puntaje: ");

        tiempoRestante = 60;
        tiempoLabel.setText(Integer.toString(tiempoRestante));
        stopButton.setDisable(false);
        configurarCronometro();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
