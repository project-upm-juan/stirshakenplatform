package com.upm.pfg.stirshakenplatform.ws.messages;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

@Data@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@JsonTypeName("redirectionAction")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RedirectionAction {

    private static final String TO_STRING_SPACE = System.lineSeparator() + "    ";

    @NotNull
    @NotEmpty
    @Valid
    @JsonProperty("sip-response-code")
    private int responseCode;

    @NotNull
    @NotEmpty
    @Valid
    @JsonProperty("sip-response-text")
    private String responseText;

    @NotNull
    @NotEmpty
    @Valid
    @JsonProperty("contact")
    private String contact;

    @Valid
    @JsonProperty("identity")
    private String identity;

    @Override
    public String toString() {
        return "RedirectionAction:" + TO_STRING_SPACE +
                "responseCode: " + responseCode + TO_STRING_SPACE +
                "responseText: " + responseText + TO_STRING_SPACE +
                "contact: " + contact;
    }

}
