
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
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
    private static String MEDIA_URL = "file:/C:/Users/jimbo/OneDrive/Documents/NetBeansProjects/401GroupMediaPlayer/src/SampleVideo_720x480_5mb.mp4";
    
    private void initialize(Stage primaryStage){
        //setting up a group for the display
        //Then Sending it to a scene for the stage
        Group group = new Group();
        primaryStage.setScene(new Scene(group));
        primaryStage.setTitle("Group 3 MP3 and MP4 Player");                
        
        //The following call will start the media player off by adding a file URL
        //into the Media() call, this could be an onAction for the file button maybe
        mediaPlayer = new MediaPlayer(new Media(MEDIA_URL));
        
        //this will create the control and send the instantiated mediaPlayer to it
        control = new MP3Player.Control(mediaPlayer);
        control.setMinSize(600, 600);       //min size for formating
        control.setPrefSize(600, 600);      //should help us with resizing based on media dimensions
        control.setMaxSize(600, 600);
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
        private HBox controlBarBottom;     //HBox for laying elements out
        private HBox controlBarTop;
        //private GridPane controlGrid;
        private Pane mediaPane;      //Pane to house the mediaView
        private Stage newStage;
        
        //Setting button images for use in creation
        private Image btPlayImage = new Image(MP3Player.class.getResourceAsStream("Play_30x30.png"));
        ImageView ivPlay = new ImageView(btPlayImage);
        
        //methods for laying out elements based on whether or not media is playing
        //we need to resize the placement dynamically based on media size
         @Override protected void layoutChildren() {
            if (mediaView != null && getBottom() != null) {
                mediaView.setFitWidth(getWidth());
                mediaView.setFitHeight(getHeight() - getBottom().prefHeight(-1));
            }
            super.layoutChildren();
            if (mediaView != null && getCenter() != null) {
                mediaView.setTranslateX((((Pane)getCenter()).getWidth() - mediaView.prefWidth(-1)) / 2);
                mediaView.setTranslateY((((Pane)getCenter()).getHeight() - mediaView.prefHeight(-1)) / 2);
            }
        }
         
        //These set sentinel values for use in resizing the controlGrid
        @Override protected double computeMinWidth(double height) {
            return controlBarBottom.prefWidth(-1);
        }
 
        @Override protected double computeMinHeight(double width) {
            return 200;
        }
 
        @Override protected double computePrefWidth(double height) {
            return Math.max(mp.getMedia().getWidth(), controlBarBottom.prefWidth(height));
        }
 
        @Override protected double computePrefHeight(double width) {
            return mp.getMedia().getHeight() + controlBarBottom.prefHeight(width);
        }
 
        @Override protected double computeMaxWidth(double height) { 
            return Double.MAX_VALUE; 
        }
 
        @Override protected double computeMaxHeight(double width) { 
            return Double.MAX_VALUE; 
        }
 
        
        //constructor
        public Control(MediaPlayer mp){
            this.mp = mp; //sets the passed MediaPlayer instance to this one
            //will need to look into how to format the MediaPlayer object
            mediaView = new MediaView(mp); //whatever is in Media() when it's constructed gets passed here
            mediaPane = new Pane();
            mediaPane.getChildren().add(mediaView); // creating a pain and putting the media display on it
            setCenter(mediaPane); // centering it in pane
            // will likely need additional formating for the pane
            /*
            controlGrid = new GridPane();
            controlGrid.setPadding(new Insets(5, 5, 5, 5));
            controlGrid.setAlignment(Pos.CENTER);
            BorderPane.setAlignment(controlGrid, Pos.CENTER);
            */
            
            //creating the box for the controls and aligning it
            controlBarBottom = new HBox(1);
            controlBarBottom.setPadding(new Insets(10,10,10,10));
            controlBarBottom.setAlignment(Pos.CENTER);
            BorderPane.setAlignment(controlBarBottom, Pos.CENTER);
            
            controlBarTop = new HBox(1);
            controlBarTop.setPadding(new Insets(10,10,10,10));
            controlBarTop.setAlignment(Pos.CENTER);
            BorderPane.setAlignment(controlBarTop, Pos.CENTER);
            
            
            //Button setup
            Button btPlay = new Button();
            btPlay.setGraphic(ivPlay);
            //btPlay.setOnAction();
            
            Button btLoop = new Button("Loop");
            //btLoop.setOnAction();
            // this and repeat will likely need listeners for end of file
            
            Button btRepeat = new Button("Repeat");
            //btRepeat.setOnAction();
            //this and loop will likely need listeners for end of file
            
            Button btFile = new Button("File");
            //btFile.setOnAction();
            
            controlBarBottom.getChildren().add(btFile);
            controlBarBottom.getChildren().add(btLoop);
            controlBarBottom.getChildren().add(btPlay);
            controlBarBottom.getChildren().add(btRepeat);
            
            /*
            controlGrid.getChildren().add(btFile);
            controlGrid.getChildren().add(btLoop);
            controlGrid.getChildren().add(btPlay);
            controlGrid.getChildren().add(btRepeat);
            
            controlGrid.setRowIndex(btFile, 1);
            controlGrid.setColumnIndex(btFile, 0);
            
            controlGrid.setRowIndex(btLoop, 1);
            controlGrid.setColumnIndex(btLoop, 2);
            
            controlGrid.setRowIndex(btPlay, 1);
            controlGrid.setColumnIndex(btPlay, 3);
            
            controlGrid.setRowIndex(btRepeat, 1);
            controlGrid.setColumnIndex(btRepeat, 4);
            */
                    
            //Sliders
            
            //time slider
            Label tLabel =  new Label("Time");
            tLabel.setMinWidth(Control.USE_PREF_SIZE);
            timeSlider = new Slider();
            timeSlider.minWidth(controlBarBottom.getWidth());
            timeSlider.maxWidth(Double.MAX_VALUE);
            GridPane.setHgrow(timeSlider, Priority.ALWAYS);
            // time slider listener for change in position
        
            controlBarTop.getChildren().add(tLabel);
            controlBarTop.getChildren().add(timeSlider);
            BorderPane.setMargin(timeSlider, new Insets(12, 12, 12, 12));
           
            /*
            controlGrid.getChildren().add(tLabel);
            controlGrid.getChildren().add(timeSlider);
            
            controlGrid.setRowIndex(tLabel, 0);
            controlGrid.setColumnIndex(tLabel, 0);
            
            controlGrid.setRowIndex(timeSlider, 0);
            controlGrid.setColumnIndex(timeSlider, 1);
            */
            
            // volume slider
            Label volLabel = new Label("Vol");
            volLabel.setMinWidth(Control.USE_PREF_SIZE);
            volSlider = new Slider();
            volSlider.prefWidth(15);
            volSlider.minWidth(10);
            volSlider.maxWidth(Region.USE_PREF_SIZE);
            // volume slider listener for change in poisition to modiy mp volume
            
            
            controlBarBottom.getChildren().add(volLabel);
            controlBarBottom.getChildren().add(volSlider);
            
            /*
            controlGrid.getChildren().add(volLabel);
            controlGrid.getChildren().add(volSlider);
            
            controlGrid.setRowIndex(volLabel, 1);
            controlGrid.setColumnIndex(volLabel, 5);
            
            controlGrid.setRowIndex(volSlider, 1);
            controlGrid.setColumnIndex(volSlider, 6);
            */
            setBottom(controlBarBottom);
            setTop(controlBarTop);
            //setBottom(controlGrid);
            
        }
    
        
    }
    
    public void start(Stage primaryStage){
        initialize(primaryStage); // the method for setting the stage
        primaryStage.show();      // Showing the stage
        
    }
    public static void main(String[] args){
        launch(args);
    }
}
