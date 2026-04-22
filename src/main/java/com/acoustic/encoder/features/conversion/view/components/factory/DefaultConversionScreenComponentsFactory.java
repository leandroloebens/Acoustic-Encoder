package com.acoustic.encoder.features.conversion.view.components.factory;

import com.acoustic.encoder.features.conversion.view.components.ConversionButton;
import com.acoustic.encoder.features.conversion.view.components.ConversionScreenComponentsWrapper;
import com.acoustic.encoder.shared.view.Button;

public class DefaultConversionScreenComponentsFactory implements ConversionScreenComponentsFactory {

    @Override
    public ConversionScreenComponentsWrapper createComponents() {

        Button conversionButton = new ConversionButton();

        return new ConversionScreenComponentsWrapper(
                conversionButton
        );
    }

}
