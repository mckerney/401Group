import javafx.application.Application;
import javafx.application.Platform;
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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javax.swing.JFileChooser;	
import javax.swing.UIManager;
import javafx.scene.paint.Color;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.effect.DropShadow;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.scene.effect.InnerShadow;
import javafx.scene.layout.ColumnConstraints;
import javax.swing.BorderFactory;
 

/* Jim, Alek, Drew, Charles
 * CISP 401 Group Project
 */

public class MP3Player extends Application {
    //Setting up media player for use in control class
    private MediaPlayer mediaPlayer;
    private MP3Player.Control control;
    private static String MEDIA_URL = "file:/D:/CISP401/Group401Project/src/TestLoopVid.mp4";
    private Group group = new Group();
    
    static int rCount = 0;
    static int lCount = 0;
    
    private void initialize(Stage primaryStage){
        //setting up a group for the display
        //Then Sending it to a scene for the stage
        Scene scene = new Scene(group);
        scene.getStylesheets().add("MPStyle.css");
       
   
        primaryStage.setScene(scene);
        primaryStage.setTitle("Group 3 MP3 and MP4 Player");
        primaryStage.setResizable(false);
        primaryStage.sizeToScene();

        
        //The following call will start the media player off by adding a file URL
        mediaPlayer = new MediaPlayer(new Media(MEDIA_URL));

      
        //this will create the control and send the instantiated mediaPlayer to it
        control = new MP3Player.Control(mediaPlayer);
        control.setMinSize(600, 560);       //min size for formating
        control.setPrefSize(600, 560);      //should help us with resizing based on media dimensions
        control.setMaxSize(Double.MAX_VALUE,  Double.MAX_VALUE);
        group.getChildren().add(control);       
    }
    
    double playerHeight(){
        return mediaPlayer.getMedia().getHeight()+ control.controlBarTop.getPrefHeight() + control.controlGrid.getPrefHeight();
    }    
    
    //potential for methods that handle checks for button actions to trim down
    //on action code to make it look neater
    
    public class Control extends BorderPane{
        //add attributes
        public MediaPlayer mp;      //Gives us the methods we need
        public MediaView mediaView; //node for displaying media
        private Duration duration;   //methods for returning duration values
        private Slider timeSlider;   //funtionality for time slider
        private Slider volSlider;    //funtionality for volume control
        private HBox controlBarTop;  //for the time slider
        private GridPane controlGrid;//for the buttons on the bottom
        private Pane mediaPane;      //Pane to house the mediaView
        
        //Setting button images for use in creation
        private Image btPlayImage = new Image(MP3Player.class.getResourceAsStream("Play_30x30.png"));
        ImageView ivPlay = new ImageView(btPlayImage);
        private Image btPauseImage = new Image(MP3Player.class.getResourceAsStream("Pause_30x30.png"));
        ImageView ivPause = new ImageView(btPauseImage);
        private Image btFileImage = new Image(MP3Player.class.getResourceAsStream("File_30x30.PNG"));
        ImageView ivFile = new ImageView(btFileImage);
        private Image btGoBackImage = new Image(MP3Player.class.getResourceAsStream("GoBack_30x30.png"));
        ImageView ivGoBack = new ImageView(btGoBackImage);
        private Image btLoopImage = new Image(MP3Player.class.getResourceAsStream("Loop_30x30.png"));
        ImageView ivLoop = new ImageView(btLoopImage);
        
        //methods for laying out elements based on whether or not media is playing
        //we need to resize the placement dynamically based on media size

         @Override protected void layoutChildren() {
            if (mediaView != null && getBottom() != null) {
                mediaView.setFitWidth(getWidth());
                mediaView.setFitHeight(getHeight() - getBottom().prefHeight(-1) - getTop().prefHeight(-1));
            }
            super.layoutChildren();
            if (mediaView != null && getCenter() != null) {
                mediaView.setTranslateX((((Pane)getCenter()).getWidth() - mediaView.prefWidth(-1)) / 2);
                mediaView.setTranslateY((((Pane)getCenter()).getHeight() - mediaView.prefHeight(-1)) / 2);
            }
        }

         
        //constructor
        public Control(MediaPlayer mp){
            this.mp = mp; //sets the passed MediaPlayer instance to this one
            setStyle("-fx-background-color: #373b41;");
            mediaView = new MediaView(mp); //whatever is in Media() when it's constructed gets passed here
            mediaPane = new Pane();
            mediaPane.getChildren().add(mediaView); // creating a pain and putting the media display on it
            setCenter(mediaPane); // centering it in border pane

            
            //for bottom control bar
            controlGrid = new GridPane();
            controlGrid.setPadding(new Insets(5, 5, 5, 5));
            controlGrid.setHgap(10);
            controlGrid.setPrefSize(mp.getMedia().getWidth(), 50);
            controlGrid.setAlignment(Pos.CENTER);
            BorderPane.setAlignment(controlGrid, Pos.CENTER_LEFT);
            setBottom(controlGrid);
      
            //for the Time slider
            controlBarTop = new HBox(1);
            controlBarTop.setPadding(new Insets(10,10,10,10));
            controlBarTop.setAlignment(Pos.CENTER);
            controlBarTop.setPrefSize(mp.getMedia().getWidth(), 30);
            BorderPane.setAlignment(controlBarTop, Pos.CENTER);
            setTop(controlBarTop);
            
            
            //Button setup
            Button btPlay = new Button();
            btPlay.setGraphic(ivPlay);
            btPlay.setPrefSize(30, 30);
            btPlay.setStyle("-fx-background-radius: 50");
            btPlay.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    Status status = mediaPlayer.getStatus();
                    if (status == Status.UNKNOWN || status == Status.HALTED) {
                        return;
                    }
                    if (status == Status.PAUSED || status == Status.STOPPED || status == Status.READY){
                        mp.play();
                        btPlay.setGraphic(ivPause);
                        
                    }
                     if (status == Status.PLAYING) {
                         mp.pause();
                         btPlay.setGraphic(ivPlay);
                     }                    
                }
            });
            
            InnerShadow shadow = new InnerShadow();
            shadow.setColor(Color.GREEN);
            shadow.setChoke(1.0);
            shadow.setRadius(3.0);
            
            Button btLoop = new Button();
            btLoop.setGraphic(ivLoop);
            btLoop.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    switch(lCount) {
                        case 0:
                            lCount = 1;
                            btLoop.setEffect(shadow);
                            mp.setOnEndOfMedia(new Runnable() {
                                @Override
                                public void run() {
                                    if (lCount == 1){
                                        mp.seek(Duration.ZERO);
                                        mp.play();
                                    }
                                }
                            });
                            break;
                        case 1:
                            lCount = 0;
                            btLoop.setEffect(null);
                            break;
                    }
                }
            }); 
            
            Button btGoBack = new Button();            
            btGoBack.setGraphic(ivGoBack);
            btGoBack.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    mp.seek(Duration.ZERO);
                    updateSliders();
                    System.out.println(btLoop.getWidth());
                    System.out.println(btLoop.getHeight());
                }                
            });            
            
            Button btFile = new Button();
            btFile.setGraphic(ivFile);
            btFile.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e){
                    try {
                        UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
                    }catch (Exception ex) { System.err.println("Error"); }
                    
                    JFileChooser chooser = new JFileChooser("D:\\CISP401\\Group401Project\\src");
                    
                    int returnVal = chooser.showOpenDialog(null);
                    if (returnVal == JFileChooser.APPROVE_OPTION) {
                        
                        setCenter(null);
                        setBottom(null);
                        setTop(null);
                        String FILE_APPEND = "file:/";
                        String convertURL = chooser.getSelectedFile().toString();
                        convertURL = convertURL.replace("\\", "/");        
                        FILE_APPEND += convertURL;        
                        MEDIA_URL = FILE_APPEND;
                        
           
                        mediaPlayer = new MediaPlayer(new Media(MEDIA_URL));
                        control = new MP3Player.Control(mediaPlayer);
                        
                     
                        control.setMinSize(600, 560);       //min size for formating
                        control.setPrefSize(600, 560);      //should help us with resizing based on media dimensions
                        control.setMaxSize(600, 560);
                       
                        group.getChildren().add(control);
                    }
                }
            });
            
            //configuration for bottom control grid
            controlGrid.getChildren().add(btFile);
            controlGrid.getChildren().add(btLoop);
            controlGrid.getChildren().add(btPlay);
            controlGrid.getChildren().add(btGoBack);
            
            controlGrid.setRowIndex(btFile, 0);
            controlGrid.setColumnIndex(btFile, 0);
           
          
            controlGrid.setRowIndex(btGoBack, 0);
            controlGrid.setColumnIndex(btGoBack, 2);
            
            controlGrid.setRowIndex(btPlay, 0);
            controlGrid.setColumnIndex(btPlay, 3);
            
            controlGrid.setRowIndex(btLoop, 0);
            controlGrid.setColumnIndex(btLoop, 4);
            
            ColumnConstraints col1 = new ColumnConstraints();
            ColumnConstraints col2 = new ColumnConstraints();
            ColumnConstraints col3 = new ColumnConstraints();
            ColumnConstraints col4 = new ColumnConstraints();
            ColumnConstraints col5 = new ColumnConstraints();
            ColumnConstraints col6 = new ColumnConstraints();
            ColumnConstraints col7 = new ColumnConstraints();

            col1.setPercentWidth(20);
            col2.setPercentWidth(15);
            col3.setPercentWidth(10);
            col4.setPercentWidth(10);
            col5.setPercentWidth(25);
            col6.setPercentWidth(5);
            col7.setPercentWidth(15);
            
            controlGrid.getColumnConstraints().addAll(col1,col2,col3,col4,col5,col6,col7);
            
            //Media player listeners
            mp.setOnReady(new Runnable() {
                @Override
                public void run() {
                    duration = mp.getMedia().getDuration();
                    updateSliders();
                }
            });
                   
            mp.currentTimeProperty().addListener(new ChangeListener<Duration>() {
                @Override
                public void changed(ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) {
                    updateSliders();
                }
            });
            

           
            //Sliders
            
            //time slider
            Label tLabel =  new Label("Time");
            tLabel.setId("bold-label"); //ID label that references the style sheet
            tLabel.setMinWidth(Control.USE_PREF_SIZE);
            tLabel.setStyle("-fx-text-fill: #ffffff");
            timeSlider = new Slider();
            timeSlider.minWidth(30);
            timeSlider.maxWidth(Double.MAX_VALUE);
            HBox.setHgrow(timeSlider, Priority.ALWAYS);
            timeSlider.valueProperty().addListener(new InvalidationListener(){
                @Override
                public void invalidated(Observable ov) {
                    if (timeSlider.isValueChanging()){
                        if(duration != null){
                            mp.seek(duration.multiply(timeSlider.getValue() / 100));
                        }
                        updateSliders();
                    }
                }                
            });
            
            controlBarTop.getChildren().add(tLabel);
            controlBarTop.getChildren().add(timeSlider);
            BorderPane.setMargin(timeSlider, new Insets(12, 12, 12, 12));

            // volume slider
            Label volLabel = new Label("Vol");
            volLabel.setId("bold-label");
            volLabel.setMinWidth(Control.USE_PREF_SIZE);
            volSlider = new Slider();
            volSlider.prefWidth(15);
            volSlider.minWidth(10);
            volSlider.maxWidth(Region.USE_PREF_SIZE);
            volSlider.valueProperty().addListener(new InvalidationListener() {
                @Override
                public void invalidated(Observable ov){   
                }  
            });
            volSlider.valueProperty().addListener(new ChangeListener<Number> () {
                @Override
                public void changed(ObservableValue<? extends Number> observable, Number oldvalue, Number newValue) {
                    if(volSlider.isValueChanging()) {
                        mp.setVolume(volSlider.getValue() / 100);
                    }
                }
            });      
            
            controlGrid.getChildren().add(volLabel);
            controlGrid.getChildren().add(volSlider);
            
            controlGrid.setRowIndex(volLabel, 0);
            controlGrid.setColumnIndex(volLabel, 5);
            
            controlGrid.setRowIndex(volSlider, 0);
            controlGrid.setColumnIndex(volSlider, 6);
         
            
            Label minus = new Label("-     +");
            minus.setStyle("-fx-text-fill: #ffffff");
            controlGrid.setConstraints(minus, 1, 5);
            Label plus = new Label("+");
            plus.setStyle("-fx-text-fill: #ffffff");
            controlGrid.setConstraints(plus, 6, 1);
        }
        
        protected void updateSliders() {
            if (timeSlider != null && volSlider != null && duration != null){
                Platform.runLater(new Runnable() {
                    public void run() {
                        Duration currentRunTime = mp.getCurrentTime();
                        timeSlider.setDisable(duration.isUnknown());
                        if (duration.greaterThan(Duration.ZERO) && !timeSlider.isDisabled() && !timeSlider.isValueChanging()) {
                            timeSlider.setValue(currentRunTime.divide(duration.toMillis()).toMillis() * 100);
                        }
                        if (!volSlider.isValueChanging()) {
                            volSlider.setValue((int) Math.round(mp.getVolume() * 100));
                        }    
                    }
                });
            }
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