
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.util.Duration;

/* Jim, Alek, Drew, Charles
 * CISP 401 Group Project
 */

public class MP3Player extends Application {
    //Setting up media player for use in control class
    private MediaPlayer mediaPlayer;
    private MP3Player.Control control;
    
    public void start(Stage primaryStage){
        //setting up a group for the display
        //Then Sending it to a scene for the stage
        Group group = new Group();
        primaryStage.setScene(new Scene(group));
        
        //The following call will start the media player off by adding a file URL
        //into the Media() call, this could be an onAction for the file button maybe
        mediaPlayer = new MediaPlayer(new Media());
        
        //this will create the control and send the instantiated mediaPlayer to it
        control = new MP3Player.Control(mediaPlayer);
        control.setMinSize(500, 300);       //min size for formating
        control.setPrefSize(500, 300);      //should help us with resizing based on media dimensions
        group.getChildren().add(control);
        
        
    }
    
    //potential for methods that handle checks for button actions to trim down
    //on action code to make it look neater
    
    public class Control extends BorderPane{
        //add attributes
        private MediaPlayer mp;      //Gives us the methods we need
        private MediaView mediaView; //node for displaying media
        private Duration duration;   //methods for returning duration values
        private Slider timeSlider;   //funtionality for time slider
        private Slider volSlider;    //funtionality for volume control
        private Label playTime;      //method for returing play time
        private HBox controlBar;     //HBox for laying elements out
        private Pane mediaPane;      //Pane to house the mediaView
        private Stage newStage;
        
        //methods for laying out elements based on whether or not media is playing
        //we need to resize the placement dynamically based on media size
        
        //constructor
        public Control(MediaPlayer mp){
            this.mp = mp; //sets the passed MediaPlayer instance to this one
            //will need to look into how to format the MediaPlayer object
            mediaView = new MediaView(mp); //whatever is in Media() when it's constructed gets passed here
            mediaPane = new Pane();
            mediaPane.getChildren().add(mediaView); // creating a pain and putting the media display on it
            setCenter(mediaPane); // centering it in pane
            // will likely need additional formating for the pane
            
            //creating the box for the controls and aligning it
            controlBar = new HBox(10);
            controlBar.setPadding(new Insets(5,10,5,10));
            controlBar.setAlignment(Pos.CENTER);
            BorderPane.setAlignment(controlBar, Pos.CENTER);
            
            
            
        }
        
    }
    
    public static void main(String[] args){
        launch(args);
    }
}
