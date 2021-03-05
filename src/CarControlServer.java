import raspi.device.Car;
import socket.CarSocketConnection;
import socket.ICarControlSubscriber;
import socket.ICarMessage;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class CarControlServer {
    private int port = 0;
    private boolean running = false;
    private ServerSocket socket = null;
    private Car car = new Car();

    public CarControlServer(int port) {
        this.port = port;

        try {
            socket = new ServerSocket(port);
        } catch (IOException e) {
        }

        this.car = new Car();
    }

    public void startServer() {
        running = true;

        car.init();

        handleConnections();
    }

    public void stopServer() {
        car.exit();

        running = false;

        try {
            socket.close();
        } catch (IOException e) {
        }
    }

    private void handleConnections() {
        new Thread() {
            @Override
            public void run() {
                while (running) {
                    try {
                        Socket connection = socket.accept();
                        CarSocketConnection con = new CarSocketConnection(connection);

                        ICarControlSubscriber subscriber = new ICarControlSubscriber() {
                            @Override
                            public void messageReceived(ICarMessage answer) {
                                String msg = answer.getMessage();
                                String[] spMsg = msg.split(";");

                                switch (spMsg[0]) {
                                    case "L":
                                        car.left();

                                        break;

                                    case "R":
                                        car.right();

                                        break;

                                    case "F":
                                        car.forward();

                                        break;

                                    case "B":
                                        car.backward();

                                        break;

                                    case "S":
                                        car.stop();
                                        break;

                                    case "D":
                                        int right = 0;
                                        int left = 0;

                                        try {
                                            right = Integer.parseInt(spMsg[1]);
                                            left = Integer.parseInt(spMsg[2]);
                                        } catch(Exception e) {
                                        }

                                        car.drive(right, left);

                                        break;
                                }


                                System.out.println("Message received! " + msg);
                                con.sendMessage("Response from Server to Client: " + msg);
                            }
                        };

                        con.addSubscriber(subscriber);
                    } catch (IOException e) {
                        running = false;
                    }
                }
            }
        }.start();
    }
}