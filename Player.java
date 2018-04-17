/*
Final Group Project
*/

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;



public class Player extends Application {
    @Override
    public void start(Stage primaryStage){
        
        //Setting up box with buttons
        HBox pane = new HBox(100);
        pane.setAlignment(Pos.CENTER);
        Button btPlay = new Button("Play");
        Button btStop = new Button("Stop");
        Button btSkipFW = new Button("Skip Song");
        Button btSkipBW = new Button("Prev. Song");
        
        //Setting up handlers for each button and actions
        PlayHandlerClass pHandler = new PlayHandlerClass();
        btPlay.setOnAction(pHandler);
        
        StopHandlerClass sHandler = new StopHandlerClass();
        btStop.setOnAction(sHandler);
        
        SkipFWHandlerClass fwHandler = new SkipFWHandlerClass();
        btSkipFW.setOnAction(fwHandler);
        
        SkipBWHandlerClass bwHandler = new SkipBWHandlerClass();
        btSkipBW.setOnAction(bwHandler);
        
        //Adding buttons to the HBox
        pane.getChildren().addAll(btPlay, btStop, btSkipFW, btSkipBW);
        
        //Creating a scene to go onto the stage
        Scene scene = new Scene(pane);
        primaryStage.setTitle("MP3 Player"); //Stage title
        primaryStage.setScene(scene); //places our scene in the staGE
        primaryStage.show(); // Display the stage
        
    }
    
    public static void main(String[] args){
        launch(args);
    }
    
}

//Defining Handler classes for the buttons
class PlayHandlerClass implements EventHandler<ActionEvent>{
    //Insert code for play button
    @Override
    public void handle(ActionEvent e){  //absract from EvenHandler
        System.out.println("Play the track");
    }
}
class StopHandlerClass implements EventHandler<ActionEvent>{
    //Insert code for play button
        @Override
    public void handle(ActionEvent e){
        System.out.println("Stop the track");
    }
}
class SkipFWHandlerClass implements EventHandler<ActionEvent>{
    //Insert code for play button
    @Override
    public void handle(ActionEvent e){
        System.out.println("Skip to the next track");
    }
}
class SkipBWHandlerClass implements EventHandler<ActionEvent>{
    //Insert code for play button
    @Override
    public void handle(ActionEvent e){
        System.out.println("Play the previous track");
    }
}
