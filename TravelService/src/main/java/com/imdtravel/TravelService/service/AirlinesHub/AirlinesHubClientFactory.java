package com.imdtravel.TravelService.service.AirlinesHub;

import com.imdtravel.TravelService.model.AirlinesHubClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class AirlinesHubClientFactory {

    private final AirlinesHubClient withFT;
    private final AirlinesHubClient withoutFT;

    public AirlinesHubClientFactory(@Qualifier("airlinesHubClientWithFT") AirlinesHubClient withFT, @Qualifier("airlinesHubClientWithoutFT") AirlinesHubClient withoutFT) {
        this.withFT = withFT;
        this.withoutFT = withoutFT;
    }

    public AirlinesHubClient get(Boolean ft) {
        return ft ? withFT : withoutFT;
    }
}
