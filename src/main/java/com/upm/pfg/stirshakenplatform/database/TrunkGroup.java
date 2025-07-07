package com.upm.pfg.stirshakenplatform.database;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class TrunkGroup {

    @Id
    private String name;

    private String type;

    private boolean roaming;
}
