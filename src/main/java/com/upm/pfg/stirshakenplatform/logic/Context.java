package com.upm.pfg.stirshakenplatform.logic;

import com.upm.pfg.stirshakenplatform.database.Country;
import com.upm.pfg.stirshakenplatform.database.SubscriberInfo;
import com.upm.pfg.stirshakenplatform.ws.messages.ReceivedInvite;
import lombok.Getter;
import lombok.Setter;

public class Context {

    @Getter
    private ReceivedInvite receivedInvite;

    @Getter
    private String fromNumber;

    @Getter
    private String toNumber;

    @Getter
    private Country fromCountry;

    @Getter
    private Country toCountry;

    @Getter
    private SubscriberInfo subscriberInfo;

    @Getter
    private boolean isEmergencyCall;

    @Getter
    @Setter
    private String callId;

    @Getter
    @Setter
    private AttestationLevel attestationLevel;

    @Getter
    @Setter
    private logicAction logicAction;

    public enum OperatorType {
        ORIGINATING,
        TERMINATING,
        TRANSIT
    }

    public enum AttestationLevel {
        A,
        B,
        C
    }

    public enum logicAction {
        SIGN,
        VERIFY,
        DROP,
        CONTINUE
    }

    @Getter
    @Setter
    private boolean isAbonado;

    @Getter
    @Setter
    private boolean isAuthenticated;


    @Getter
    @Setter
    private OperatorType operatorType;



    @Getter
    @Setter
    private String identity;

    public Context(ReceivedInvite receivedInvite, String fromNumber, String toNumber,
                   Country fromCountry, Country toCountry, SubscriberInfo subscriberInfo,
                   boolean isEmergencyCall, String callId, OperatorType operatorType, boolean isAbonado, boolean isAuthenticated) {
        this.receivedInvite = receivedInvite;
        this.fromNumber = fromNumber;
        this.toNumber = toNumber;
        this.fromCountry = fromCountry;
        this.toCountry = toCountry;
        this.subscriberInfo = subscriberInfo;
        this.isEmergencyCall = isEmergencyCall;
        this.callId = callId;
        this.operatorType = operatorType;
        this.isAbonado = isAbonado;
        this.isAuthenticated = isAuthenticated;
    }

    @Override
    public String toString() {
        return "Context{" +
                "receivedInvite=" + receivedInvite +
                ", fromNumber='" + fromNumber + '\'' +
                ", toNumber='" + toNumber + '\'' +
                ", fromCountry=" + fromCountry +
                ", toCountry=" + toCountry +
                ", subscriberInfo=" + subscriberInfo +
                ", isEmergencyCall=" + isEmergencyCall +
                ", callId='" + callId + '\'' +
                ", attestationLevel=" + attestationLevel +
                ", logicAction=" + logicAction +
                ", isAbonado=" + isAbonado +
                ", isAuthenticated=" + isAuthenticated +
                ", operatorType=" + operatorType +
                ", identity='" + identity + '\'' +
                '}';
    }
}
