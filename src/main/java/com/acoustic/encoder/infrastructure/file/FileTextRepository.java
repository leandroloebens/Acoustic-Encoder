package com.acoustic.encoder.infrastructure.file;

import com.acoustic.encoder.domain.shared.Bpm;
import com.acoustic.encoder.domain.shared.InstrumentId;
import com.acoustic.encoder.domain.shared.Octave;
import com.acoustic.encoder.domain.shared.Volume;
import com.acoustic.encoder.domain.voice.VoiceConfig;
import com.acoustic.encoder.features.conversion.dto.MusicProject;
import com.acoustic.encoder.features.conversion.ports.TextRepository;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class FileTextRepository implements TextRepository {

    private static final String ERROR_WRITING_FILE_MSG = "Error writing file: ";
    private static final String ERROR_READING_FILE_MSG = "Error reading file: ";
    private static final String ERROR_EOF_MSG = "Premature end of file: ";

    public FileTextRepository() {}

    @Override
    public void saveText(String text, File file) {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(text);
        }
        catch (IOException e) {
            System.out.println(ERROR_WRITING_FILE_MSG + file.getAbsolutePath());
        }

    }

    @Override
    public String loadText(File file) {

        try {
            return Files.readString(file.toPath());
        }
        catch (IOException e) {
            System.out.println(ERROR_READING_FILE_MSG + file.getAbsolutePath());
        }

        return null;

    }

    @Override
    public void saveProject(MusicProject input, File file) {

        try (DataOutputStream out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(file)))) {
            out.writeUTF(input.text());
            out.writeInt(input.bpm().value());
            out.writeInt(input.voiceConfigList().size());
            for (VoiceConfig config : input.voiceConfigList()) {
                out.writeInt(config.defaultInstrument().value());
                out.writeInt(config.defaultOctave().value());
                out.writeInt(config.defaultVolume().value());
            }
        }
        catch (IOException e) {
            System.out.println(ERROR_WRITING_FILE_MSG + file.getAbsolutePath());
        }

    }

    @Override
    public MusicProject loadProject(File file) {

        try (DataInputStream in = new DataInputStream(new BufferedInputStream(new FileInputStream(file)))) {
            String text = in.readUTF();
            Bpm bpm = new Bpm(in.readInt());
            int voiceConfigCount = in.readInt();

            List<VoiceConfig> voices = new ArrayList<>();
            for (int i = 0; i < voiceConfigCount; i++) {
                voices.add(new VoiceConfig(
                        new InstrumentId(in.readInt()),
                        new Octave(in.readInt()),
                        new Volume(in.readInt())
                ));
            }

            return new MusicProject(text, bpm, voices);
        }
        catch (EOFException e) {
            System.out.println(ERROR_EOF_MSG + file.getAbsolutePath());
        }
        catch (IOException e) {
            System.out.println(ERROR_READING_FILE_MSG + file.getAbsolutePath());
        }

        return null;

    }

}

