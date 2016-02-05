package ua.chuchvaga.taras.lookingjob.service;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.chuchvaga.taras.lookingjob.domain.Site;

import java.util.List;

@Repository
@Transactional
@Service("siteService")
public class SiteServiceImpl implements SiteService {

    @Autowired
    private SiteRepository siteRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Site> findAll() {
        return Lists.newArrayList(siteRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public Site findById(Long id) {
        return siteRepository.findOne(id);
    }

    @Override
    public Site save(Site site) {
        return siteRepository.save(site);
    }

    @Override
    public void delete(Long id) {
        siteRepository.delete(id);
    }
}
