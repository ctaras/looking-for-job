package ua.chuchvaga.taras.lookingjob.service;

import ua.chuchvaga.taras.lookingjob.domain.Site;

import java.util.List;

/**
 * Created by beaver on 1/22/16.
 */
public interface SiteService {
    List<Site> findAll();

    Site findById(Long id);

    Site save(Site site);

    void delete(Long id);
}
