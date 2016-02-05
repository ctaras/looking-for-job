package ua.chuchvaga.taras.lookingjob.service;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.chuchvaga.taras.lookingjob.domain.City;
import ua.chuchvaga.taras.lookingjob.domain.Site;
import ua.chuchvaga.taras.lookingjob.domain.SitesIDsCities;

import java.util.List;

@Repository
@Transactional
@Service("sitesIDsCitiesService")
public class SitesIDsCitiesServiceImpl implements SitesIDsCitiesService {

    @Autowired
    private SitesIDsCitiesRepository sitesIDsCitiesRepository;

    @Override
    @Transactional(readOnly = true)
    public List<SitesIDsCities> findAll() {
        return Lists.newArrayList(sitesIDsCitiesRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public SitesIDsCities findById(Long id) {
        return sitesIDsCitiesRepository.findOne(id);
    }

    @Override
    public SitesIDsCities save(SitesIDsCities idsRegion) {
        return sitesIDsCitiesRepository.save(idsRegion);
    }

    @Override
    public void delete(Long id) {
        sitesIDsCitiesRepository.delete(id);
    }

    @Override
    @Transactional(readOnly = true)
    public SitesIDsCities findBySiteAndCity(Site site, City city) {
        return sitesIDsCitiesRepository.findBySiteAndCity(site, city);
    }
}
