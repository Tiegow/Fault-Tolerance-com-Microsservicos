package com.imdtravel.TravelService.service.Fidelity;

import com.imdtravel.TravelService.model.FidelityClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class FidelityClientFactory {

    private final FidelityClient withFT;
    private final FidelityClient withoutFT;

    public FidelityClientFactory(@Qualifier("fidelityClientWithFT") FidelityClient withFT, @Qualifier("fidelityClientWithoutFT") FidelityClient withoutFT) {
        this.withFT = withFT;
        this.withoutFT = withoutFT;
    }

    public FidelityClient get(Boolean ft) {
        return ft? withFT : withoutFT;
    }
}
