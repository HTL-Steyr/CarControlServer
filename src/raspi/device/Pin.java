package raspi.device;

public class Pin extends AbstractDevice {

    public Pin(int number) {
        super(number, TYPE.GPIO);
    }

    public void write(boolean state) {
        String value = state ? "1" : "0";

        super.write("value", value);
    }

    public boolean read() {
        return super.read("value");
    }

    public void input() {
        super.write("direction", "in");
    }

    public void output() {
        super.write("direction", "out");
    }

}
