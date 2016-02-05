package ua.chuchvaga.taras.lookingjob.service;

import org.springframework.data.repository.CrudRepository;
import ua.chuchvaga.taras.lookingjob.domain.City;
import ua.chuchvaga.taras.lookingjob.domain.Site;
import ua.chuchvaga.taras.lookingjob.domain.SitesIDsCities;

/**
 * Created by beaver on 1/22/16.
 */
public interface SitesIDsCitiesRepository extends CrudRepository<SitesIDsCities, Long> {
    SitesIDsCities findBySiteAndCity(Site site, City city);
}
