package com.upm.pfg.stirshakenplatform.wc.messages;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VerificationRequest {

    private String destinationNumber;
    private String originationNumber;
    private String identity;
    private long iat;

    // Constructor
    public VerificationRequest(String originationNumber, String destinationNumber, String identity, long iat) {
        this.originationNumber = originationNumber;
        this.destinationNumber = destinationNumber;
        this.identity = identity;
        this.iat = iat;
    }

    @Override
    public String toString() {
        return "VerifyRequest{" +
                ", destinationNumber='" + destinationNumber + '\'' +
                ", identity='" + identity + '\'' +
                ", originationNumber='" + originationNumber + '\'' +
                ", iat=" + iat +
                '}';
    }

}
