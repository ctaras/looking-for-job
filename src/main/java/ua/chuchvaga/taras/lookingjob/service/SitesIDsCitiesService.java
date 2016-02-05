package ua.chuchvaga.taras.lookingjob.service;

import ua.chuchvaga.taras.lookingjob.domain.City;
import ua.chuchvaga.taras.lookingjob.domain.Site;
import ua.chuchvaga.taras.lookingjob.domain.SitesIDsCities;

import java.util.List;

/**
 * Created by beaver on 1/22/16.
 */
public interface SitesIDsCitiesService {
    List<SitesIDsCities> findAll();

    SitesIDsCities findById(Long id);

    SitesIDsCities save(SitesIDsCities idsRegion);

    void delete(Long id);

    SitesIDsCities findBySiteAndCity(Site site, City city);
}
