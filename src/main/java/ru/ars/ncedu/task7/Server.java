package ru.ars.ncedu.task7;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.ars.ncedu.task7.chat.users.User;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.*;

public class Server {
    private InetSocketAddress listenAddress;
    public Map<String, SocketChannel> users = new HashMap<>();

    public Server(String address, int port) {
        this.listenAddress = new InetSocketAddress("localhost", 8080);
    }

    public static void main(String[] args) {
        new Server("localhost", 8090).startServer();
    }

    public void startServer() {
        try (Selector selector = Selector.open();
             ServerSocketChannel server = ServerSocketChannel.open()) {
            ObjectMapper objectMapper = new ObjectMapper();
            User user = null;
            server.configureBlocking(false);
            server.bind(listenAddress);
            server.register(selector, server.validOps());
            System.out.println("Server started...");
            while (true) {
                selector.select();
                Iterator<SelectionKey> keys = selector.selectedKeys().iterator();
                while (keys.hasNext()) {
                    SelectionKey key = keys.next();
                    ByteBuffer[] buffer = {ByteBuffer.allocate(1024)};
                    if (key.isAcceptable()) {
                        accept(server, selector);
                    } else if (key.isReadable()) {
                        SocketChannel channel = (SocketChannel) key.channel();
                        channel.configureBlocking(false);
                        channel.register(selector, SelectionKey.OP_WRITE);
                        channel.read(buffer);
                        String hasExit = new String(buffer[0].array()).trim();
                        if (hasExit.equals("exit")) {
                            users.remove(user.getNickname());
                            channel.close();
                        } else {
                            user = objectMapper.readValue(buffer[0].array(), User.class);
                            buffer[0].flip();
                        }
                    } else if (key.isWritable()) {
                        write(key, selector, user, buffer, users, objectMapper);
                    }
                    keys.remove();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void accept(ServerSocketChannel server, Selector selector) throws IOException {
        SocketChannel channel = server.accept();
        channel.configureBlocking(false);
        channel.register(selector, SelectionKey.OP_READ);
        System.out.println("Connection Accepted: " + channel.getLocalAddress() + "\n");
    }

    private static void write(SelectionKey key, Selector selector, User user, ByteBuffer[] buffer,
                              Map<String, SocketChannel> users, ObjectMapper objectMapper) throws IOException {
        SocketChannel channel = (SocketChannel) key.channel();
        channel.configureBlocking(false);
        channel.register(selector, SelectionKey.OP_READ);
        if (user.getTo() == null && user.getMassage() == null) {
            if (users.containsKey(user.getNickname())) {
                buffer[0] = ByteBuffer.wrap(objectMapper.writeValueAsBytes(Boolean.TRUE));
                channel.write(buffer);
                buffer[0].flip();
                buffer[0].clear();
            } else {
                users.put(user.getNickname(), channel);
                buffer[0] = ByteBuffer.wrap(objectMapper.writeValueAsBytes(Boolean.FALSE));
                channel.write(buffer);
                buffer[0].flip();
                buffer[0].clear();
            }
        } else {
            if (!users.containsKey(user.getTo())) {
                if (user.getTo().equals("@all")) {
                    buffer[0] = ByteBuffer.wrap(objectMapper.writeValueAsBytes(user.getNickname() + ": " + user.getMassage()));
                    ByteBuffer finalBuffer = buffer[0];
                    users.forEach((nickname, chan) -> {
                        try {
                            chan.write(finalBuffer);
                            finalBuffer.clear();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                } else {
                    buffer[0] = ByteBuffer.wrap(objectMapper.writeValueAsBytes(Boolean.TRUE));
                    channel.write(buffer);
                    buffer[0].clear();
                }
            } else {
                buffer[0] = ByteBuffer.wrap(objectMapper.writeValueAsBytes(user.getNickname() + ": " + user.getMassage()));
                channel.write(buffer);
                buffer[0].clear();
                users.get(user.getTo()).write(buffer);
                buffer[0].clear();
            }
        }
    }
}