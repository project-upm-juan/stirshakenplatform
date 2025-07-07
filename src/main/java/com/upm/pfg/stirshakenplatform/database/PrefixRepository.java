package com.upm.pfg.stirshakenplatform.database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrefixRepository extends JpaRepository<Prefix, String> {
    Prefix findByPrefix(String prefix);
}