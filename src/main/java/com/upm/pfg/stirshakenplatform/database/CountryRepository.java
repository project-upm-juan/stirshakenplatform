package com.upm.pfg.stirshakenplatform.database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends JpaRepository<Country, String> {
    Country findByCountryId(String countryId);
}