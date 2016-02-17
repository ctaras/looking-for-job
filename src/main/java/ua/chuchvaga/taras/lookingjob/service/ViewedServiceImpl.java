package ua.chuchvaga.taras.lookingjob.service;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.chuchvaga.taras.lookingjob.domain.Vacancy;
import ua.chuchvaga.taras.lookingjob.domain.ViewedVacancy;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@Transactional
@Service("viewedService")
public class ViewedServiceImpl implements ViewedService {

    @Autowired
    private ViewedRepository viewedRepository;

    @Autowired
    private EntityManager em;

    @Override
    @Transactional(readOnly = true)
    public ViewedVacancy findByUrl(String url) {
        return viewedRepository.findByUrl(url);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ViewedVacancy> findAll() {
        return Lists.newArrayList(viewedRepository.findAll());
    }

    @Override
    public void create(Vacancy vacancy, boolean viewed) {
        ViewedVacancy viewedVacancy = new ViewedVacancy();
        viewedVacancy.setUrl(vacancy.getId());
        viewedVacancy.setViewed(viewed);
        viewedRepository.save(viewedVacancy);
    }

    @Override
    public List<ViewedVacancy> findByUrlIn(List<String> urls) {
        return em.createNamedQuery("ViewedVacancy.findByUrlIn", ViewedVacancy.class)
                .setParameter("urls", urls)
                .getResultList();

    }
}
