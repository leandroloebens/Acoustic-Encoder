import com.acoustic.encoder.config.ConfigLoader;
import com.acoustic.encoder.model.MusicalInstruction;
import com.acoustic.encoder.parser.TextToInstructionParser;

import java.util.List;



void main() throws Exception {

    TextToInstructionParser parser = new TextToInstructionParser(
            new ConfigLoader(ConfigLoader.CONFIG_FILE_NAME).loadConfigMap()
    );
    List<MusicalInstruction> instructions = parser.parseText(
            "ABCDEFGHIK !0123456789?.;,\n"

    );
    for (MusicalInstruction instruction : instructions) {

        System.out.println(instruction);
    }
}


