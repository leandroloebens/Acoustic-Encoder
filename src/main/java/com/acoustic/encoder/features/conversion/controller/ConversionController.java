package com.acoustic.encoder.features.conversion.controller;

import com.acoustic.encoder.features.conversion.dto.UserConversionInput;

public interface ConversionController {

    void handleConvertAction(UserConversionInput input);


}
