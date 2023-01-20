package raspi.device;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Scanner;

public abstract class AbstractDevice {

    private static final String PATH_PWM = "/sys/class/pwm/pwmchip0";
    private static final String PATH_GPIO = "/sys/class/gpio";

    private static final String TYPE_GPIO = "gpio";
    private static final String TYPE_PWM = "pwm";

    enum TYPE {GPIO, PWM}

    protected int num = 0;
    protected String type = "";
    protected String path = "";

    public AbstractDevice(int num, TYPE type) {
        this.num = num;

        if (type == TYPE.GPIO) {
            this.path = PATH_GPIO;
            this.type = TYPE_GPIO;
        } else if (type == TYPE.PWM) {
            this.path = PATH_PWM;
            this.type = TYPE_PWM;
        }
    }

    public void export() {
        try {
            if (!new File(this.path + "/" + type + num).exists()) {
                FileWriter myWriter = new FileWriter(this.path + "/export");
                myWriter.write(String.valueOf(num));
                myWriter.close();
            }

            Thread.sleep(200);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void unexport() {
        try {
            if ((new File(this.path).exists())) {
                FileWriter myWriter = new FileWriter(this.path + "/unexport");
                myWriter.write(String.valueOf(num));
                myWriter.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void write(String field, String value) {
        System.out.println(path + "/" + type + num + "/" + field + " => " + value);

        if (new File(path + "/" + type + num + "/" + field).exists()) {
            try {
                FileWriter myWriter = new FileWriter(path + "/" + type + num + "/" + field);
                myWriter.write(value);
                myWriter.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public boolean read(String field) {
        boolean result = false;

        try {
            Scanner s = new Scanner(new FileReader(this.path + "/" + type + num + "/" + field));

            result = s.nextInt() == 1;

            s.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}
