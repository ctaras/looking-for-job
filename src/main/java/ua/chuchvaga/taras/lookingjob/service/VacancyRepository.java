package ua.chuchvaga.taras.lookingjob.service;

import org.springframework.data.repository.CrudRepository;
import ua.chuchvaga.taras.lookingjob.domain.Vacancy;

/**
 * Created by beaver on 1/22/16.
 */
public interface VacancyRepository extends CrudRepository<Vacancy, String> {
}
