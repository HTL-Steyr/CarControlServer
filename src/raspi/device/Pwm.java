package raspi.device;

public class Pwm extends AbstractDevice {

    public Pwm(int num) {
        super(num, TYPE.PWM);
    }

}
