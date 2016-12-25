package capitals;

import capitals.data.FactReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontSmoothingType;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class FXMLAnswerController implements Initializable {
	
	@FXML
	private Button playAgainButton;
	@FXML
	private Button exitButton;
	@FXML 
	private Text resultStatement;
	@FXML
	private VBox factBox;
	@FXML
	private ImageView flag;
	@FXML
	private AnchorPane pane;
	
	private String[] greetings = {"Well done!", "Good job!", "Nice!",
			"Correct!", "Nice one!", "Awesome!", "Here you go!", "You rock!"};
	private String[] consolations = {"What a pity!", "Wrong answer!",
			"False,", "Try again,", "Nope,"};
	
	@FXML
	private void exit(ActionEvent e)
	{
	    Stage stage = (Stage) exitButton.getScene().getWindow();
	    stage.close();
	}
	
	@FXML 
	private void restart(ActionEvent event) throws IOException
	{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLDocument.fxml"));
		Parent parent  = loader.load();
		Scene scene = new Scene(parent);
		
		FXMLController controller = (FXMLController)loader.getController();
		String countryNameChosen = Country.getCountry().getCountryName();
		controller.build(countryNameChosen);
		
		Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		appStage.setScene(scene);
		appStage.show();
	}
	
	public void build(boolean answerIsCorrect, Country country) throws FileNotFoundException
	{
		setAnswerStatement(answerIsCorrect, country);
		setDisplay(country);	
		setBackgroundImage();
	}
	
	public void setAnswerStatement(boolean answerIsCorrect, Country country)
	{
		if (answerIsCorrect)
		{
			resultStatement.setText(greetings[new Random().nextInt(greetings.length)]);
			resultStatement.setFill(Color.CORAL);
		}
		else
		{
			resultStatement.setText(consolations[new Random().nextInt(consolations.length)] + " it's " + country.getCapitalName());
			resultStatement.setFill(Color.BROWN);
		}
		setResultStatementStyle();
	}
	
	private void setResultStatementStyle() 
	{
		resultStatement.setFont(Font.font("Century Gothic", FontWeight.EXTRA_BOLD, 28));
	}
	
	private void setDisplay(Country country) throws FileNotFoundException
	{	
		if (!setFactBox(country))
			setFlagCentered(country);
		else 
			setFlag(country);
	}
	
	private boolean setFactBox(Country country) throws FileNotFoundException
	{
		this.factBox.setPadding(new Insets(10));
		
		List<Text> factListText = new LinkedList<Text>();
		String currentFact;
		List<String> readFacts = FactReader.getFacts(country);
		if (readFacts.isEmpty())
		{
			return false;
		}	
		setFactBoxSpacing(readFacts);
		Iterator<String> factIterator = readFacts.iterator();
		while (factIterator.hasNext())
		{
			currentFact = factIterator.next();
			Text addedFact = new Text(currentFact);
			addedFact.setWrappingWidth(380);
			factListText.add(addedFact);
		}
		
		for (Text factText : factListText)
		{
			factText.setFont(Font.font("Century Gothic", FontWeight.BOLD, 21));
			factText.autosize();
			this.factBox.getChildren().add(factText);
		}
		return true;
	}
	
	/* this methods calculates the spacings and layout of the factbox
	 * according to the number of facts read and their total size
	 */
	private void setFactBoxSpacing(List<String> readFacts)
	{
		int totalNumberOfCharacters = 0;
		for (String fact : readFacts) totalNumberOfCharacters += fact.length();
		int numberOfFacts = readFacts.size();		

		if (totalNumberOfCharacters < 150)
		{
			if (numberOfFacts < 5)
			{
				this.factBox.setLayoutY(this.flag.getLayoutY()); 
				this.factBox.setSpacing(3);
			}
			else 
			{
				this.factBox.setLayoutY(this.flag.getLayoutY() - 8); 
				this.factBox.setSpacing(5);
			}
		}
		else if (totalNumberOfCharacters < 200)
		{
			if (numberOfFacts > 4)
			{
				this.factBox.setLayoutY(this.flag.getLayoutY() - 10); 
				this.factBox.setSpacing(3);
			}
			else
			{
				this.factBox.setLayoutY(this.flag.getLayoutY() - 5); 
				this.factBox.setSpacing(5);				
			}
		}
		else if (totalNumberOfCharacters >= 200)
		{
			this.factBox.setLayoutY(this.flag.getLayoutY() - 40);
		}
	}	
	
	private void setFlag(Country country)
	{
		File flagFile = new File("data\\flags\\" + country.getCountryName().toLowerCase() + ".gif");
		this.flag.setImage(new Image(flagFile.toURI().toString()));
	}
	
	private void setFlagCentered(Country country)
	{
		setFlag(country);
		this.flag.setLayoutX((this.pane.getPrefWidth() - 250) / 2);
		this.flag.setLayoutY((this.pane.getPrefHeight() - 154) / 2);
	}
	
	private void setBackgroundImage()
	{
		String bgImagePath = "answer_background.png";
		this.pane.setStyle("-fx-background-image: url('" + bgImagePath + "'); " +
	           "-fx-background-position: center; " +
	           "-fx-background-repeat: repeat;");
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

	}

}