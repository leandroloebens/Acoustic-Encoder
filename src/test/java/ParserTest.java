import com.acoustic.encoder.features.conversion.config.ParserConfigLoader;
import com.acoustic.encoder.shared.model.MusicalInstruction;
import com.acoustic.encoder.features.conversion.parser.TextToInstructionParser;

import java.util.List;



void main() throws Exception {

    TextToInstructionParser parser = new TextToInstructionParser(
            new ParserConfigLoader(ParserConfigLoader.CONFIG_FILE_NAME).loadConfigMap()
    );
    List<MusicalInstruction> instructions = parser.parseText(
            "ABCDEFGHIK !0123456789?.;,\n"

    );
    for (MusicalInstruction instruction : instructions) {

        System.out.println(instruction);
    }
}


