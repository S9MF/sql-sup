package burp;

public class Config {
    private static Integer params_len = 1;
    private static Integer key_len = 1;
    private static Integer value_len = 1;
    private static Integer number_len = 1;
    private static Integer fuzz_number = 1;
    private static String filePath;

    public static String getFilePath() {
        String val = BurpExtender.callbacks.loadExtensionSetting("filePath");
        try {
            return String.valueOf(val);
        }catch (Exception e) {
            return Config.filePath;
        }
    }

    public static void setFilePath(String filePath) {
        BurpExtender.callbacks.saveExtensionSetting("filePath", String.valueOf(filePath));
        Config.filePath = filePath;
    }

    public static Integer getFuzz_number() {
        String val = BurpExtender.callbacks.loadExtensionSetting("fuzz_number");
        try {
            return Integer.valueOf(val);
        }catch (Exception e) {
            return Config.fuzz_number;
        }
    }

    public static void setFuzz_number(Integer fuzz_number) {
        BurpExtender.callbacks.saveExtensionSetting("fuzz_number", String.valueOf(fuzz_number));
        Config.fuzz_number = fuzz_number;
    }

    public static Integer getParams_len() {
        String val = BurpExtender.callbacks.loadExtensionSetting("params_len");
        try {
            return Integer.valueOf(val);
        }catch (Exception e) {
            return Config.params_len;
        }
    }

    public static void setParams_len(Integer params_len) {
        BurpExtender.callbacks.saveExtensionSetting("params_len", String.valueOf(params_len));
        Config.params_len = params_len;
    }

    public static Integer getKey_len() {
        String val = BurpExtender.callbacks.loadExtensionSetting("key_len");
        try {
            return Integer.valueOf(val);
        }catch (Exception e) {
            return Config.key_len;
        }
    }

    public static void setKey_len(Integer key_len) {
        BurpExtender.callbacks.saveExtensionSetting("key_len", String.valueOf(key_len));
        Config.key_len = key_len;
    }

    public static Integer getValue_len() {
        String val = BurpExtender.callbacks.loadExtensionSetting("value_len");
        try {
            return Integer.valueOf(val);
        }catch (Exception e) {
            return Config.value_len;
        }
    }

    public static void setValue_len(Integer value_len) {
        BurpExtender.callbacks.saveExtensionSetting("value_len", String.valueOf(value_len));
        Config.value_len = value_len;
    }

    public static Integer getNumber_len() {
        String val = BurpExtender.callbacks.loadExtensionSetting("number_len");
        try {
            return Integer.valueOf(val);
        }catch (Exception e) {
            return Config.number_len;
        }
    }

    public static void setNumber_len(Integer number_len) {
        BurpExtender.callbacks.saveExtensionSetting("number_len", String.valueOf(number_len));
        Config.number_len = number_len;
    }
}
