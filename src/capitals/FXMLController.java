package capitals;

import capitals.user.Answer;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class FXMLController implements Initializable {
	
	@FXML
	public Text question;	
	@FXML
	private Button submitButton;
	@FXML
	private Button exitButton;
	@FXML
	private TextField userInput;
	@FXML
	private AnchorPane pane;
	private Country country;
	
	public void build(String countryChosen)
	{
		this.question.setFont(Font.font("Century Gothic", FontWeight.BOLD, 23));
		this.setCountry(countryChosen);
		this.setBackgroundImage();
	}
	
	@FXML
	private void handleSubmit(ActionEvent event) throws IOException
	{		
		switchToAnswer(event);
	}
	
	@FXML
	private void keyPressed(KeyEvent event) throws IOException {
        switch (event.getCode()) {
        case ENTER:
        	switchToAnswer(event);
            break;
        case ESCAPE:
    	    Stage stage = (Stage) exitButton.getScene().getWindow();
    	    stage.close();
        default:
            break;
        }
    }
	
	private void switchToAnswer(Event event) throws IOException
	{		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("answerPage.fxml"));
		Parent parent  = loader.load();
		Scene scene = new Scene(parent);
		FXMLAnswerController controller = (FXMLAnswerController)loader.getController();
		
		controller.build(Answer.isCorrect(this.country.getCapitalName(), this.userInput.getText()), this.country);
		
		Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		appStage.hide();
		appStage.setScene(scene);
		appStage.show();
	}
	
	private void setCountry(String countryChosen)
	{
		this.country = new Country(countryChosen);
		this.question.setText("What's the capital city of " + countryChosen + "?");
	}
	
	private void setBackgroundImage()
	{
		String bgImagePath = "worldmap.gif";
		this.pane.setStyle("-fx-background-image: url('" + bgImagePath + "'); " +
		           "-fx-background-position: center; " +
		           "-fx-background-repeat: repeat;");
	}
	
	@FXML
	private void exit(ActionEvent e)
	{
	    Stage stage = (Stage) exitButton.getScene().getWindow();
	    stage.close();
	}
	
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle)
	{
	}
	
}
