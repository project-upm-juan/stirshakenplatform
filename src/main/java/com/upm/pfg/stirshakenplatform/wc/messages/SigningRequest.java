package com.upm.pfg.stirshakenplatform.wc.messages;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SigningRequest {

    private String attestationLevel;
    private String destinationNumber;
    private String pOriginationId;
    private String originationNumber;
    private long iat;

    // Constructor
    public SigningRequest(String attestationLevel, String destinationNumber, String pOriginationId,
                          String originationNumber, long iat) {
        this.attestationLevel = attestationLevel;
        this.destinationNumber = destinationNumber;
        this.pOriginationId = pOriginationId;
        this.originationNumber = originationNumber;
        this.iat = iat;
    }

    @Override
    public String toString() {
        return "SigningRequest{" +
                "attestationLevel='" + attestationLevel + '\'' +
                ", destinationNumber='" + destinationNumber + '\'' +
                ", pOriginationId='" + pOriginationId + '\'' +
                ", originationNumber='" + originationNumber + '\'' +
                ", iat=" + iat +
                '}';
    }
}
