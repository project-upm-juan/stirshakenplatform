package com.upm.pfg.stirshakenplatform.wc.messages;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SigningResponse {

    private InnerSigningResponse signingResponse;

    @Getter
    @Setter
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class InnerSigningResponse {
        private String identity;
    }
}
