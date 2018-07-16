package ru.ars.ncedu.task7.gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import ru.ars.ncedu.task7.chat.users.User;


public class Page extends Application {
    private Button join = new Button();
    private Label textLogin = new Label();
    private TextField login = new TextField();

    private Stage stage;
    private TextArea readMsg = new TextArea();
    private Button send = new Button();
    private Label textTo = new Label();
    private TextField msgTo = new TextField();
    private TextArea msg = new TextArea();
    private Scene sceneChat;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;
        textLogin.setText("Имя пользователя:");
        join.setText("Войти");

        join.setOnAction(event -> {
            User user = new User();
            user.setNickname(login.getText());
            if (!user.getNickname().equals("") && user.getNickname() != null) {
                new Thread(() -> {
                    try {
                        new Client("localhost", 8080).startClient(user, stage, msgTo, readMsg, msg, send, sceneChat);
                    } catch (UserExistException e) {
                        Platform.runLater(() -> FxDialogs.showInformation("info", "Имя существует"));
                    }
                }).start();
            } else {
                FxDialogs.showInformation("info", "Введите имя");
            }
        });

        Pane rootChat = new Pane();
        send.setLayoutY(410);
        send.setLayoutX(5);
        send.setMinWidth(478);
        send.setText("Отправить");

        msg.setLayoutY(250);
        msg.setLayoutX(5);
        msg.setMaxHeight(150);

        textTo.setText("Кому:");
        textTo.setLayoutY(200);
        textTo.setLayoutX(5);

        msgTo.setLayoutY(220);
        msgTo.setLayoutX(5);
        msgTo.setMinWidth(478);

        readMsg.setLayoutX(5);
        readMsg.setLayoutY(10);
        readMsg.setEditable(false);

        rootChat.getChildren().addAll(send, msg, textTo, msgTo, readMsg);
        sceneChat = new Scene(rootChat, 488, 450);

        Pane rootJoin = new Pane();
        join.setLayoutX(50);
        join.setLayoutY(130);
        join.setMinWidth(200);

        login.setLayoutX(50);
        login.setLayoutY(90);
        login.setMinWidth(200);

        textLogin.setLayoutX(50);
        textLogin.setLayoutY(70);

        rootJoin.getChildren().addAll(join, login, textLogin);
        Scene sceneJoin = new Scene(rootJoin, 300, 250);

        stage.setScene(sceneJoin);
        stage.setTitle("Chat");
        stage.show();
    }
}