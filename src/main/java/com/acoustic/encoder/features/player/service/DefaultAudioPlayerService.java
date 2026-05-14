package com.acoustic.encoder.features.player.service;

import com.acoustic.encoder.features.player.audio.AudioPlayer;
import com.acoustic.encoder.features.player.exception.MusicExportException;
import com.acoustic.encoder.features.player.export.MusicExporter;
import com.acoustic.encoder.shared.model.MusicModel;

import javax.sound.midi.InvalidMidiDataException;
import java.io.File;
import java.util.Objects;

public class DefaultAudioPlayerService implements AudioPlayerService {

    private final AudioPlayer player;

    private final MusicExporter exporter;

    public DefaultAudioPlayerService(AudioPlayer player, MusicExporter exporter) {

        this.player = Objects.requireNonNull(player, "Player cannot be null!");
        this.exporter = Objects.requireNonNull(exporter, "Exporter cannot be null!");
    }

    @Override
    public void setPlayerMusic(MusicModel musicModel) throws IllegalArgumentException, InvalidMidiDataException {
        player.loadMusic(musicModel);
    }

    @Override
    public void playMusic() {
        player.play();
    }

    @Override
    public void stopMusic() {
        player.stop();
    }

    @Override
    public void rewindMusic() {
        player.rewind();
    }

    @Override
    public void exportMusic(File destination) throws MusicExportException {
        exporter.export(destination);
    }

    @Override
    public void closePlayer() {
        player.close();
    }
}
