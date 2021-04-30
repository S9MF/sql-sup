package burp;

import java.io.PrintWriter;

public class BurpExtender implements IBurpExtender {

    public static IBurpExtenderCallbacks callbacks;
    public static IExtensionHelpers helpers;
    private String extensionName = "sql-sup(辅助)";
    private String version ="0.1";
    public static PrintWriter out;

    @Override
    public void registerExtenderCallbacks(IBurpExtenderCallbacks callbacks) {
        this.callbacks = callbacks;
        this.helpers = callbacks.getHelpers();
        callbacks.setExtensionName(String.format("%s %s",extensionName,version));
        out = new PrintWriter(callbacks.getStdout(), true);
        callbacks.registerContextMenuFactory(new Menu());
        callbacks.registerIntruderPayloadGeneratorFactory(new PayloadGenera());
        out.println(getBanner());
    }

    public String getBanner(){
        String bannerInfo =
                          "[+] ##############################################\n"
                        + "[+]    " + extensionName + " v" + version +"\n"
                        + "[+]    anthor: S9MF\n"
                        + "[+]    github: https://github.com/S9MF/sql-sup\n"
                        + "[+] ##############################################";
        return bannerInfo;
    }
}
