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
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Repository
@Transactional
@Service("vacancyService")
public class VacancyServiceImpl implements VacancyService {

    @Autowired
    private VacancyRepository vacancyRepository;

    @Autowired
    private ViewedService viewedService;

    @Autowired
    private EntityManager em;

    @Transactional
    public void truncateTable() {
        em.createNativeQuery("truncate table vacancy").executeUpdate();
        //em.createNativeQuery("DELETE FROM vacancy").executeUpdate();
    }

    @Override
    public List<Vacancy> findAllWithViewedStatus() {
        List<Vacancy> vacancies = Lists.newArrayList(vacancyRepository.findAll());

        Map<String, Vacancy> vacancyUrls =
                vacancies.parallelStream().collect(Collectors.toMap(Vacancy::getId,
                        Function.identity()));

        List<String> urls = Lists.newArrayList(vacancyUrls.keySet());

        List<ViewedVacancy> views = viewedService.findByUrlIn(urls);

        views.stream().forEach(v -> {
            Vacancy vacancy = vacancyUrls.get(v.getUrl());
            if (vacancy != null) vacancy.setViewed(v);
        });

        return vacancies;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Vacancy> findAll() {
        return Lists.newArrayList(vacancyRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public Vacancy findById(String id) {
        return vacancyRepository.findOne(id);
    }

    @Override
    public Vacancy save(Vacancy vacancy) {
        return vacancyRepository.save(vacancy);
    }

    @Override
    public void delete(String id) {
        vacancyRepository.delete(id);
    }

    @Override
    public List<Vacancy> save(List<Vacancy> entities) {
        return Lists.newArrayList(vacancyRepository.save(entities));
    }
}
