package com.acoustic.encoder.features.player.service;

import com.acoustic.encoder.features.player.ports.AudioPlayer;
import com.acoustic.encoder.features.player.exception.MusicExportException;
import com.acoustic.encoder.features.player.ports.MusicExporter;
import com.acoustic.encoder.domain.music.MusicModel;

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

    @Override
    public long getMicrosecPosition() {
        return player.getMicrosecPosition();
    }

    @Override
    public long getMicrosecDuration() {
        return player.getMicrosecDuration();
    }

    @Override
    public void seekMusic(long microsecPosition) {
        player.seekMusic(microsecPosition);
    }

    @Override
    public boolean isPlayingAudio() {
        return player.isPlaying();
    }
}
