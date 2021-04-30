package burp;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static burp.Util.getFileContent;

public class PayloadGenera implements IIntruderPayloadGeneratorFactory {
    public static final byte[][] PAYLOADS = getPayloads();
    @Override
    public String getGeneratorName() {
        return "parameter fuzz";
    }

    @Override
    public IIntruderPayloadGenerator createNewInstance(IIntruderAttack attack) {
        return new ParameterFuzz();
    }

    public static byte[][] getPayloads() {
        File file = new File(Config.getFilePath());
        if (!file.exists()) {
            try {
                file = new File("parameter.txt");
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        List<String> parameterList = getFileContent(file);
        List<String> list2 = Util.permutation(parameterList, Config.getFuzz_number());
        byte[][] letter = new byte[list2.size()][];
        for (int i = 0; i < list2.size(); i++) {
            letter[i] = list2.get(i).getBytes();
        }
        return letter;
    }


    class ParameterFuzz implements IIntruderPayloadGenerator {
        int payloadIndex;
        @Override
        public void reset() {
            payloadIndex = 0;
        }

        @Override
        public boolean hasMorePayloads() {
            return payloadIndex < PAYLOADS.length;
        }

        @Override
        public byte[] getNextPayload(byte[] baseValue) {
            byte[] payloads = PAYLOADS[payloadIndex];
            payloadIndex++;
            return payloads;
        }
    }
}
