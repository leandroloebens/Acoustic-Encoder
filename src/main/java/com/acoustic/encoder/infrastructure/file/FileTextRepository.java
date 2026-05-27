package com.acoustic.encoder.infrastructure.file;

import com.acoustic.encoder.domain.shared.Bpm;
import com.acoustic.encoder.domain.shared.InstrumentId;
import com.acoustic.encoder.domain.shared.Octave;
import com.acoustic.encoder.domain.shared.Volume;
import com.acoustic.encoder.domain.voice.VoiceConfig;
import com.acoustic.encoder.features.conversion.dto.MusicProject;
import com.acoustic.encoder.features.conversion.ports.TextRepository;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FileTextRepository implements TextRepository {

    private static final String ERROR_WRITING_FILE_MSG = "Error writing file: ";
    private static final String ERROR_READING_FILE_MSG = "Error reading file: ";
    private static final String ERROR_EOF_MSG = "Premature end of file: ";

    public FileTextRepository() {}

    @Override
    public void saveText(String text, File file) throws IOException {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, StandardCharsets.UTF_8))) {
            writer.write(text);
        }
        catch (IOException e) {
            throw new IOException(ERROR_WRITING_FILE_MSG + file.getAbsolutePath(), e);
        }

    }

    @Override
    public String loadText(File file) throws IOException {

        if (!file.getName().toLowerCase(Locale.ROOT).endsWith(".txt")) {
            throw new IllegalArgumentException("only .txt files are supported");
        }

        try {
            return Files.readString(file.toPath(), StandardCharsets.UTF_8);
        }
        catch (IOException e) {
            System.out.println(ERROR_READING_FILE_MSG + file.getAbsolutePath());
            throw new IOException(ERROR_READING_FILE_MSG + file.getAbsolutePath(), e);
        }

    }

    @Override
    public void saveProject(MusicProject input, File file) throws IOException {

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
            throw new  IOException(ERROR_WRITING_FILE_MSG + file.getAbsolutePath(), e);
        }

    }

    @Override
    public MusicProject loadProject(File file) throws IOException {

        if (!file.getName().toLowerCase(Locale.ROOT).endsWith(".aef")) {
            throw new IllegalArgumentException("only .aef files are supported");
        }

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
            throw new IOException(ERROR_EOF_MSG + file.getAbsolutePath(), e);
        }
    }

}