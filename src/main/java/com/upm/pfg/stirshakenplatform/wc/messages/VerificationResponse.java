package com.upm.pfg.stirshakenplatform.wc.messages;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VerificationResponse {

    private InnerVerificationResponse verificationResponse;

    @Getter
    @Setter
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class InnerVerificationResponse {
        private String verstat;
    }
}
