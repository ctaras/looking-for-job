package ua.chuchvaga.taras.lookingjob.service;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.chuchvaga.taras.lookingjob.domain.Vacancy;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@Transactional
@Service("vacancyService")
public class VacancyServiceImpl implements VacancyService {

    @Autowired
    private VacancyRepository vacancyRepository;

    @Autowired
    private EntityManager em;

    @Transactional
    public void truncateTable() {
        em.createNativeQuery("truncate table vacancy").executeUpdate();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Vacancy> findAll() {
        return Lists.newArrayList(vacancyRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public Vacancy findById(Long id) {
        return vacancyRepository.findOne(id);
    }

    @Override
    public Vacancy save(Vacancy vacancy) {
        return vacancyRepository.save(vacancy);
    }

    @Override
    public void delete(Long id) {
        vacancyRepository.delete(id);
    }

    @Override
    public Iterable<Vacancy> save(Iterable<Vacancy> entities) {
        return vacancyRepository.save(entities);
    }
}
