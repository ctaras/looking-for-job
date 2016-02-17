package ua.chuchvaga.taras.lookingjob.service;

import ua.chuchvaga.taras.lookingjob.domain.Vacancy;

import java.util.List;

/**
 * Created by beaver on 1/22/16.
 */
public interface VacancyService {
    List<Vacancy> findAll();

    Vacancy findById(String id);

    Vacancy save(Vacancy vacancy);

    void delete(String id);

    List<Vacancy> save(List<Vacancy> entities);

    void truncateTable();

    List<Vacancy> findAllWithViewedStatus();
}
