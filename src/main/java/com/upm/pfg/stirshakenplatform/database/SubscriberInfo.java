package com.upm.pfg.stirshakenplatform.database;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class SubscriberInfo {

    @Id
    private String number;

    private String attestationLevel;

    private String pOriginationId;
}
