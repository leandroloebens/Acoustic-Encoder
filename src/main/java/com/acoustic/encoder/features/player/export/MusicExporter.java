package com.acoustic.encoder.features.player.export;

import com.acoustic.encoder.features.player.exception.MusicExportException;

import java.io.File;

public interface MusicExporter {

    void export(File file) throws MusicExportException;
}
