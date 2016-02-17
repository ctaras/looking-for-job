package ua.chuchvaga.taras.lookingjob.service;

import org.springframework.data.repository.CrudRepository;
import ua.chuchvaga.taras.lookingjob.domain.Company;

/**
 * Created by beaver on 1/22/16.
 */
public interface CompanyRepository extends CrudRepository<Company, Long> {
    Company findByCaption(String caption);
}
