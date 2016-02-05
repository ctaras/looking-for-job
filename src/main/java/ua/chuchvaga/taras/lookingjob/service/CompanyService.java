package ua.chuchvaga.taras.lookingjob.service;

import ua.chuchvaga.taras.lookingjob.domain.Company;

import java.util.List;

/**
 * Created by beaver on 1/22/16.
 */
public interface CompanyService {
    List<Company> findAll();

    Company findById(Long id);

    Company save(Company company);

    void delete(Long id);

    Company findByCaption(String caption);

    void save(Iterable<Company> companies);
}
