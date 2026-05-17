package com.acoustic.encoder.infrastructure.file;

import com.acoustic.encoder.domain.voice.VoiceConfig;
import com.acoustic.encoder.features.conversion.dto.UserConversionInput;
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
    public void saveProject(UserConversionInput input, File file) {

        try (DataOutputStream out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(file)))) {
            out.writeUTF(input.text());
            out.writeInt(input.bpm());
            out.writeInt(input.voiceConfigList().size());
            for (VoiceConfig config : input.voiceConfigList()) {
                out.writeInt(config.defaultInstrument());
                out.writeInt(config.defaultOctave());
                out.writeInt(config.defaultVolume());
            }
        }
        catch (IOException e) {
            System.out.println(ERROR_WRITING_FILE_MSG + file.getAbsolutePath());
        }

    }

    @Override
    public UserConversionInput loadProject(File file) {

        try (DataInputStream in = new DataInputStream(new BufferedInputStream(new FileInputStream(file)))) {
            String text = in.readUTF();
            int bpm = in.readInt();
            int voiceConfigCount = in.readInt();

            List<VoiceConfig> voices = new ArrayList<>();
            for (int i = 0; i < voiceConfigCount; i++) {
                voices.add(new VoiceConfig(in.readInt(), in.readInt(), in.readInt()));
            }

            return new UserConversionInput(text, bpm, voices);
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

