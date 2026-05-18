import com.acoustic.encoder.features.conversion.parser.config.DefaultParserConfigFactory;
import com.acoustic.encoder.features.conversion.parser.config.DefaultParsingConfigLoader;
import com.acoustic.encoder.features.conversion.parser.DefaultInstructionParser;
import com.acoustic.encoder.domain.music.MusicalInstruction;

import java.util.List;



void main() throws Exception {

    DefaultInstructionParser parser = new DefaultInstructionParser(
            new DefaultParserConfigFactory().create(
                    new DefaultParsingConfigLoader(DefaultParsingConfigLoader.DEFAULT_ENCODER_MAPPING_PATH).loadConfigMap()
            )
    );

    List<MusicalInstruction> instructions = parser.parseText(
            "ABCDEFGHIK !0123456789?.;,\n[2]"

    );
    for (MusicalInstruction instruction : instructions) {

        System.out.println(instruction);
    }
}


