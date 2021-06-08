package application;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.layout.*;
import javafx.event.*;
import javafx.scene.image.Image;
import main.ClypeClient;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.awt.*;
import java.io.IOException;

public class Main extends Application {

    public static String message;
    //globals here


    @Override
    public void start(Stage primaryStage) throws Exception {
        //create a new ClypeClient object
        //call ClypeClient start() method

        ClypeClient clyper = new ClypeClient();
        clyper.start();

        try {
            primaryStage.setTitle("Clype2.0 Messenger");

            AnchorPane root = new AnchorPane();
            VBox userToolsAnchor = new VBox();
            GridPane messageInterfaceAnchor = new GridPane();

            userToolsAnchor.getStyleClass().add("anchortheme");

            HBox userTools = new HBox();

            VBox messageInterface = new VBox();

            HBox row1 = new HBox();
            HBox row2 = new HBox();
            HBox row3 = new HBox();
            HBox row4 = new HBox();

            Button btnSend = new Button();
            Button btnAttachment = new Button();
            TextField messageField = new TextField();
            messageField.setPromptText("Type a message...");
            TextArea receivingField = new TextArea();
            receivingField.setEditable(false);
            TextArea friendsList = new TextArea();
            friendsList.setEditable(false);
            Label friendsLabel = new Label();


            friendsList.setPrefSize(90, 125);
            receivingField.setPrefSize(300, 125);
            root.getStyleClass().add("backgroundtheme");

            userTools.getStyleClass().add("anchortheme");
            messageInterface.getStyleClass().add("vboxtheme");

            row1.getStyleClass().add("hboxtheme");
            row2.getStyleClass().add("hboxtheme");
            row3.getStyleClass().add("hboxtheme");
            row4.getStyleClass().add("hboxtheme");

            btnSend.setText("Send");
            btnSend.getStyleClass().add("buttontheme");

            btnAttachment.setText("Attachment");
            btnAttachment.getStyleClass().add("buttonattatchmenttheme");

            friendsLabel.setText("Friends Online");
            friendsLabel.getStyleClass().add("labeltheme");


            row1.getChildren().add(messageField);
            row1.getChildren().add(btnSend);
            row2.getChildren().add(btnAttachment);
            row3.getChildren().add(friendsLabel);
            row4.getChildren().add(friendsList);
            row4.getChildren().add(receivingField);


            userTools.getChildren().add(row1);
            userTools.getChildren().add(row2);
            messageInterface.getChildren().add(row3);
            messageInterface.getChildren().add(row4);


            userToolsAnchor.getChildren().add(userTools);
            messageInterfaceAnchor.getChildren().add(messageInterface);

            root.getChildren().add(messageInterfaceAnchor);
            root.getChildren().add(userToolsAnchor);


            Scene scene = new Scene(root, 800, 250);
            scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.show();



            btnSend.setOnAction(new EventHandler<ActionEvent>() {

                String message;


                @Override
                public void handle(ActionEvent event) {
                    this.message = messageField.getText();
                    Image smileyEmoji = new Image("/pirate.emoji.gif", 32, 32, false, false);
                    receivingField.appendText(messageField.getText() + "\n");
                    messageField.clear();



                  //  try {
                  //      ClypeClient.sendData();
                  //  } catch (IOException e) {
                  //      e.printStackTrace();
                  //  }
                    //assign MessageClypeData to DataToSendToServer
                    //call sendData() from ClypeClient

                }
            });

            btnAttachment.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    receivingField.appendText(":) \n");


                }

            });


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static String getMessage() {
        return message;
    }

    public static void main(String[] args) {
        launch(args);
    }
}

