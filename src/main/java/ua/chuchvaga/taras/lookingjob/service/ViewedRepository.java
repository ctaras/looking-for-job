package ua.chuchvaga.taras.lookingjob.service;

import org.springframework.data.repository.CrudRepository;
import ua.chuchvaga.taras.lookingjob.domain.ViewedVacancy;

/**
 * Created by beaver on 1/22/16.
 */
public interface ViewedRepository extends CrudRepository<ViewedVacancy, Long> {
    ViewedVacancy findByUrl(String url);
}
