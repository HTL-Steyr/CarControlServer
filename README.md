# CarControlServer

##Example
```java
public class Example {

    public static void main(String[] args) {
        /**
         * Create a new CarSocketConnection
         */
        CarSocketConnection carSocket = new CarSocketConnection("yourhost", 2612);

        /**
         * Create a subscriber that listens for new messages from the server
         */
        ICarControlSubscriber sub = new ICarControlSubscriber() {
            @Override
            public void messageReceived(ICarMessage msg) {
                /**
                 * Log message to console
                 */
                System.out.println(msg.getMessage());
            }
        };

        /**
         * Add subscriber to client
         */
        carSocket.addSubscriber(sub);

        /**
         * Sending some messges to the server
         */

        /**
         * turn right
         */
        carSocket.sendMessage("R");

        /**
         * turn left
         */
        carSocket.sendMessage("L");

        /**
         * drive forward
         */
        carSocket.sendMessage("F");

        /**
         * drive backward
         */
        carSocket.sendMessage("B");

        /**
         * stop
         */
        carSocket.sendMessage("S");

        /**
         * right wheels: 50% of max speed backward
         * left wheels: 75% of max speed forward
         */
        carSocket.sendMessage("D;-50;75");
    }
}
```
