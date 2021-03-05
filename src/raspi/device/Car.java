package raspi.device;

public class Car {

    private int[] channel = new int[4];

    public void init() {
        try {
            Pin pin = new Pin(4);
            pin.export();
            pin.output();
            pin.write(true);

            pin = new Pin(18);
            pin.export();
            pin.output();
            pin.write(true);

            for (int i = 0; i < 16; i++) {
                Pwm pwm = new Pwm(i);
                pwm.export();
            }
        } catch (Exception e) {
        }
    }

    public void exit() {
        try {
            Pin pin = new Pin(4);
            pin.unexport();

            pin = new Pin(18);
            pin.unexport();

            for (int i = 0; i < 16; i++) {
                Pwm pwm = new Pwm(i);
                pwm.unexport();
            }
        } catch (Exception e) {
        }
    }

    private void setPwm(int channel, int dutyCycle) {
        // if something changes!
        if (dutyCycle != this.channel[channel]) {
            int period = 25000000;
            Pwm pwm = new Pwm(channel);
            pwm.write("enable", "1");

            pwm.write("period", String.valueOf(period + 1));
            pwm.write("period", String.valueOf(period));
            pwm.write("enable", "1");
            pwm.write("duty_cycle", String.valueOf((int) (period * (dutyCycle / 100.0))));
            this.channel[channel] = dutyCycle;
        }

        /*Pin pin4 = new Pin(4);
        pin4.export();
        pin4.output();
        pin4.write(true);*/
    }

    private void driveMotor(int motor, int dutyCyle) {
        setPwm(motor * 2, (100 + dutyCyle) / 2);
        setPwm((motor * 2) + 1, (100 - dutyCyle) / 2);
    }

    public void drive(int right, int left) {
        driveMotor(0, right);
        driveMotor(1, -left);
    }

    public void left() {
        driveMotor (0, 100); // --> rechts vorwärts
        driveMotor(1, 0); // --> links stop
    }

    public void right() {
        driveMotor(1, -100); //--> links vorwärts
        driveMotor(0, 0); // --> rechts stop
    }

    public void forward() {
        driveMotor(1, -100); //--> links vorwärts
        driveMotor (0, 100); // --> rechts vorwärts
    }

    public void backward() {
        driveMotor(0, -100); // --> rechts rückwärts
        driveMotor(1, 100); // --> links rückwärts
    }

    public void stop() {
        driveMotor(0, 0); // stop
        driveMotor(1, 0); // stop
    }

}
