package ru.ars.ncedu.task7.gui;

import java.io.IOException;
import java.net.InetSocketAddress;

import com.fasterxml.jackson.databind.ObjectMapper;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ru.ars.ncedu.task7.chat.users.User;
import ru.ars.ncedu.task7.exeception.UserExistException;

import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.*;


public class Client extends Page {
    private static Set<String> setNicknames = new HashSet<>();
    private InetSocketAddress hostAddress;

    public Client(String address, int port) {
        this.hostAddress = new InetSocketAddress(address, port);
    }

    public void startClient(User user, Stage stage, TextField msgTo, TextArea readMsg,
                            TextArea msg, Button send, Scene sceneChat) throws UserExistException {
        try (SocketChannel client = SocketChannel.open();
             Selector selector = Selector.open()) {
            ObjectMapper objectMapper = new ObjectMapper();
            client.configureBlocking(false);
            client.connect(hostAddress);
            client.register(selector, SelectionKey.OP_CONNECT);
            Platform.runLater(() -> stage.setTitle("Chat: " + user.getNickname()));
            System.out.println("Connecting to Server...");
            while (true) {
                selector.select();
                for (SelectionKey key : selector.selectedKeys()) {
                    final ByteBuffer[] buffer = {ByteBuffer.allocate(1024)};
                    if (key.isConnectable()) {
                        connect(key, selector, objectMapper, user, buffer);
                    } else if (key.isReadable()) {
                        read(key, selector, objectMapper, user, buffer, stage, sceneChat, readMsg);
                    }
                    Platform.runLater(() -> send.setOnAction(event1 -> {
                        try {
                            sendMessage(key, selector, objectMapper, user, buffer, stage, sceneChat, readMsg, msgTo, msg);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }));

                    Platform.runLater(() -> stage.setOnCloseRequest(event -> {
                        try {
                            SocketChannel channel = (SocketChannel) key.channel();
                            channel.write(ByteBuffer.wrap("exit".getBytes()));
                            System.exit(0);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void connect(SelectionKey key, Selector selector, ObjectMapper objectMapper,
                                User user, ByteBuffer[] buffer) throws IOException {
        SocketChannel channel = (SocketChannel) key.channel();
        channel.configureBlocking(false);
        channel.register(selector, SelectionKey.OP_READ);
        System.out.println("Connection established: " + channel.getRemoteAddress() + "\n");
        while (!channel.finishConnect()) ;

        buffer[0] = ByteBuffer.wrap(objectMapper.writeValueAsBytes(user));
        channel.write(buffer[0]);
        buffer[0].clear();
    }

    private static void read(SelectionKey key, Selector selector, ObjectMapper objectMapper,
                             User user, ByteBuffer[] buffer, Stage stage,
                             Scene sceneChat, TextArea readMsg) throws UserExistException, IOException {
        SocketChannel channel = (SocketChannel) key.channel();
        channel.configureBlocking(false);
        channel.register(selector, SelectionKey.OP_READ);
        channel.read(buffer[0]);
        if (user.getTo() == null && user.getMassage() == null) {
            boolean existLogin = Boolean.valueOf(new String(buffer[0].array()).trim());
            if (existLogin) {
                channel.write(ByteBuffer.wrap("exit".getBytes()));
                throw new UserExistException();
            } else {
                Platform.runLater(() -> stage.setScene(sceneChat));
            }
        }
        String answer = new String(buffer[0].array()).trim();
        if (!answer.equals("false")) {
            if (answer.equals("true")) {
                Platform.runLater(() -> FxDialogs.showInformation("info", "Данный пользователь не зарегистрирован"));
            } else {
                if (!readMsg.getText().equals("")) {
                    Platform.runLater(() -> readMsg.setText(readMsg.getText() + "\n" + answer.substring(1, answer.length() - 1)));
                } else {
                    Platform.runLater(() -> readMsg.setText(answer.substring(1, answer.length() - 1)));
                }
            }
        }
    }

    private static void sendMessage(SelectionKey key, Selector selector, ObjectMapper objectMapper,
                                    User user, ByteBuffer[] buffer, Stage stage, Scene sceneChat,
                                    TextArea readMsg, TextField msgTo, TextArea msg) throws IOException {
        SocketChannel channel = (SocketChannel) key.channel();
        channel.configureBlocking(false);
        channel.register(selector, SelectionKey.OP_READ);
        if (!msgTo.getText().matches("^[A-Za-z0-9_-]{3,16}$")) {
            FxDialogs.showInformation("info", "Некорректный адрес сообщения");
        } else if (msg.getText().replaceAll(" ","").equals("")) {
            FxDialogs.showInformation("info", "Введите сообщение");
        } else {
            user.setTo(msgTo.getText());
            user.setMassage(msg.getText());
            if (!user.getNickname().equals(user.getTo())) {
                buffer[0] = ByteBuffer.wrap(objectMapper.writeValueAsBytes(user));
                channel.write(buffer[0]);
                buffer[0].clear();
            } else {
                if (!readMsg.getText().equals("")) {
                    readMsg.setText(readMsg.getText() + "\n" + user.getNickname() + ": " + user.getMassage());
                    msg.setText("");
                } else {
                    readMsg.setText(user.getNickname() + ": " + user.getMassage());
                    msg.setText("");
                }
            }
        }
    }
}