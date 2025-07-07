package com.upm.pfg.stirshakenplatform.database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriberInfoRepository extends JpaRepository<SubscriberInfo, String> {

    SubscriberInfo findByNumber(String number);
}