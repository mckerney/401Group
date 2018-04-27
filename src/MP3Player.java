import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.swing.JFileChooser;
import javax.swing.UIManager;

/* Jim, Alek, Drew, Charles
 * CISP 401 Group Project
 */

public class MP3Player extends Application {
    //Setting up media player for use in control class
    private MediaPlayer mediaPlayer;
    private MP3Player.Control control;
    private static String MEDIA_URL = "file:/D:/CISP401/Group401Project/src/SampVid480.mp4";
    static int rCount = 0;
    static int lCount = 0;
    
    public void initialize(Stage primaryStage){
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
        control.setMinSize(620, 480);       //min size for formating
        control.setPrefSize(620, 480);      //should help us with resizing based on media dimensions
        control.setMaxSize(620, 480);
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
        private GridPane controlGrid;
        private Pane mediaPane;      //Pane to house the mediaView
        private Stage newStage;
        
        //Setting button images for use in creation
        private Image btPlayImage = new Image(MP3Player.class.getResourceAsStream("Play_30x30.png"));
        ImageView ivPlay = new ImageView(btPlayImage);
        private Image btPauseImage = new Image(MP3Player.class.getResourceAsStream("Pause.png"));
        ImageView ivPause = new ImageView(btPauseImage);
        
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
            return controlGrid.prefWidth(-1);
        }
 
        @Override protected double computeMinHeight(double width) {
            return 200;
        }
 
        @Override protected double computePrefWidth(double height) {
            return Math.max(mp.getMedia().getWidth(), controlGrid.prefWidth(height));
        }
 
        @Override protected double computePrefHeight(double width) {
            return mp.getMedia().getHeight() + controlGrid.prefHeight(width);
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
            
            controlGrid = new GridPane();
            controlGrid.setPadding(new Insets(5, 5, 5, 5));
            controlGrid.setAlignment(Pos.CENTER);
            BorderPane.setAlignment(controlGrid, Pos.CENTER);
            
            /*creating the box for the controls and aligning it
            controlBar = new HBox(1);
            controlBar.setPadding(new Insets(10,10,10,10));
            controlBar.setAlignment(Pos.CENTER);
            BorderPane.setAlignment(controlBar, Pos.CENTER);
            */
            
            DropShadow shadow = new DropShadow();            
            
            Button btPlay = new Button();
            btPlay.setGraphic(ivPlay);
            btPlay.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent e) {
                    Status status = mediaPlayer.getStatus();
                    
                    if (status == Status.UNKNOWN || status == Status.HALTED) {
                    return;
                    }
                    if (status == Status.PAUSED || status == Status.STOPPED || status == Status.READY) {                        
                        mp.play();
                        btPlay.setGraphic(ivPause);
                    }
                    if (status == Status.PLAYING) {
                        mp.pause();
                        btPlay.setGraphic(ivPlay);
                    }                    
                }
            });
            
            Button btLoop = new Button("Loop");
            btLoop.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent e) {
                    switch(lCount) {
                        case 0:
                            lCount = 1;
                            btLoop.setEffect(shadow);
                        break;
                        case 1:
                            lCount = 0;
                            btLoop.setEffect(null);
                        break;                    
                        }                     
                }
            });
            
            Button btRepeat = new Button("Repeat");            
            btRepeat.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent e) {
                    
                switch(rCount) {
                    case 0:
                        rCount = 1;
                        btRepeat.setEffect(shadow);
                    break;
                    case 1:
                        rCount = 0;
                        btRepeat.setEffect(null);
                    break;                    
                    }                   
                }                
            });
            
            Button btFile = new Button("File");
            btFile.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent e){
                    
                    try {
                         UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
                    } catch (Exception ex) { System.err.println("Error"); }
                    
                    JFileChooser chooser = new JFileChooser("D:\\CISP401\\Group401Project\\src");
        
                    int returnVal = chooser.showOpenDialog(null);        
                    if (returnVal == JFileChooser.APPROVE_OPTION) {
                        MEDIA_URL = chooser.getSelectedFile().toString();                        
                        System.out.println(MEDIA_URL);            
        }        
    }
});
                
          
            
            /*
            controlBar.getChildren().add(btFile);
            controlBar.getChildren().add(btLoop);
            controlBar.getChildren().add(btPlay);
            controlBar.getChildren().add(btRepeat);
            */
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
            
            //Sliders
            
            //time slider
            Label tLabel =  new Label("Time");
            tLabel.setMinWidth(Control.USE_PREF_SIZE);
            timeSlider = new Slider();
            timeSlider.minWidth(30);
            timeSlider.maxWidth(Double.MAX_VALUE);
            // time slider listener for change in position
            
            //controlBar.getChildren().add(tLabel);
            //controlBar.getChildren().add(timeSlider);
            controlGrid.getChildren().add(tLabel);
            controlGrid.getChildren().add(timeSlider);
            
            controlGrid.setRowIndex(tLabel, 0);
            controlGrid.setColumnIndex(tLabel, 0);
            
            controlGrid.setRowIndex(timeSlider, 0);
            controlGrid.setColumnIndex(timeSlider, 1);
            
            // volume slider
            Label volLabel = new Label("Vol");
            volLabel.setMinWidth(Control.USE_PREF_SIZE);
            volSlider = new Slider();
            volSlider.prefWidth(15);
            volSlider.minWidth(10);
            volSlider.maxWidth(Region.USE_PREF_SIZE);
            // volume slider listener for change in poisition to modiy mp volume
            
            /*
            controlBar.getChildren().add(volLabel);
            controlBar.getChildren().add(volSlider);
            */
            controlGrid.getChildren().add(volLabel);
            controlGrid.getChildren().add(volSlider);
            
            controlGrid.setRowIndex(volLabel, 1);
            controlGrid.setColumnIndex(volLabel, 5);
            
            controlGrid.setRowIndex(volSlider, 1);
            controlGrid.setColumnIndex(volSlider, 6);
            
            //setBottom(controlBar);
            setBottom(controlGrid);
            
            
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
