package com.acoustic.encoder.features.conversion.controller;

import com.acoustic.encoder.features.conversion.dto.UserConversionInput;

import java.io.File;
import java.io.IOException;

public interface ConversionController {

    void handleConvertAction(UserConversionInput input);

    void handleSaveTextAction(String textInput, File file) throws IOException;

    String handleLoadTextAction(File file) throws IOException;

}
