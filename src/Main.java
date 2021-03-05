public class Main {

    public static void main(String[] args) throws InterruptedException {
        CarControlServer server = new CarControlServer(2612);
        server.startServer();
    }
}
