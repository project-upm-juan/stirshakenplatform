package com.upm.pfg.stirshakenplatform.ws.messages;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@JsonTypeName("receivedInvite")
public class ReceivedInvite {

    @NotNull
    @NotEmpty
    @Valid
    @JsonProperty("r-uri")
    private String reqUri;

    @NotNull
    @NotEmpty
    @Valid
    @JsonProperty("from")
    private String from;

    @NotNull
    @NotEmpty
    @Valid
    @JsonProperty("to")
    private String to;

    @NotNull
    @NotEmpty
    @Valid
    @JsonProperty("contact")
    private String contact;

    @NotNull
    @NotEmpty
    @Valid
    @JsonProperty("call-id")
    private String callId;

    @NotNull
    @NotEmpty
    @Valid
    @JsonProperty("identity")
    private String identity;

    @NotNull
    @NotEmpty
    @Valid
    @JsonProperty("p-asserted-identity")
    private String pAssertedIdentity;

    @Override
    public String toString() {
        return "ReceivedInvite{" +
                "reqUri='" + reqUri + '\'' +
                ", from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", contact='" + contact + '\'' +
                ", callId='" + callId + '\'' +
                ", identity='" + identity + '\'' +
                ", pAssertedIdentity='" + pAssertedIdentity + '\'' +
                '}';
    }
}
