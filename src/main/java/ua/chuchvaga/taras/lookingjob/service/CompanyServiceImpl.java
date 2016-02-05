package ua.chuchvaga.taras.lookingjob.service;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.chuchvaga.taras.lookingjob.domain.Company;

import java.util.List;

@Repository
@Transactional
@Service("companyService")
@Slf4j
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Company> findAll() {
        return Lists.newArrayList(companyRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public Company findById(Long id) {
        return companyRepository.findOne(id);
    }

    @Override
    public Company save(Company company) {
        return companyRepository.save(company);
    }

    @Override
    public void delete(Long id) {
        companyRepository.delete(id);
    }

    @Cacheable(value = "companies", unless = "#result == null")
    public Company findByCaption(String caption) {
        log.info("findByCaption. Caption = {}", caption);
        return companyRepository.findByCaption(caption);
    }

    @Override
    public void save(Iterable<Company> companies) {
        companyRepository.save(companies);
    }
}
