package ua.chuchvaga.taras.lookingjob.service;

import ua.chuchvaga.taras.lookingjob.domain.Vacancy;

import java.util.List;

/**
 * Created by beaver on 1/22/16.
 */
public interface VacancyService {
    List<Vacancy> findAll();

    Vacancy findById(Long id);

    Vacancy save(Vacancy vacancy);

    void delete(Long id);

    Iterable<Vacancy> save(Iterable<Vacancy> entities);

    void truncateTable();
}
