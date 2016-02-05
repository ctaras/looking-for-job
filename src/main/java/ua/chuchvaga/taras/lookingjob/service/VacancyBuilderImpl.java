package ua.chuchvaga.taras.lookingjob.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import ua.chuchvaga.taras.lookingjob.domain.Company;
import ua.chuchvaga.taras.lookingjob.domain.Vacancy;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by beaver on 1/27/16.
 */
@Service
@Slf4j
public class VacancyBuilderImpl implements VacancyBuilder {

    @Autowired
    private CurrencyService currencyService;

    @Autowired
    private VacancyService vacancyService;

    @Autowired
    private CompanyService companyService;

    @Autowired(required = false)
    private Validator validator;

    @Transactional
    void saveAllCompanies(List<Company> companies) {
        companyService.save(companies);
    }

    @Transactional
    void saveAllVacancies(List<Vacancy> vacancies) {
        vacancyService.save(vacancies);
    }

    void validateCompanies(List<VacancyTemplate> vacancyTemplates) {
        List<Company> companies = new ArrayList<>();
        Set<String> companyCaptions = new HashSet<String>();
        vacancyTemplates.forEach(vacancyTemplate -> companyCaptions.add(vacancyTemplate.getCompanyCaption()));

        companyCaptions.forEach(s -> {

            Company company = companyService.findByCaption(s);
            if (company == null) {
                company = new Company();
                company.setCaption(s);

                BeanPropertyBindingResult result =
                        new BeanPropertyBindingResult(company, s);

                ValidationUtils.invokeValidator(validator, company, result);
                if (result.hasErrors()) {
                    List<ObjectError> errors = result.getAllErrors();
                    StringBuilder builder = new StringBuilder();
                    builder.append(String.format("Validate company '%s' . No of validation errors: %s", s, errors.size()));
                    errors.forEach(objectError -> {
                        builder.append("\n");
                        builder.append(objectError.getDefaultMessage());
                    });
                    log.error(builder.toString());
                } else {
                    companies.add(company);
                }
            }
        });

        saveAllCompanies(companies);
    }

    List<Vacancy> validateVacancies(List<VacancyTemplate> vacancyTemplates) {
        List<Vacancy> vacancies = new ArrayList<>();
        for (VacancyTemplate vt : vacancyTemplates) {
            Company company = companyService.findByCaption(vt.getCompanyCaption());
            if (company == null)
                log.error("Job missing: '{}'. Reason: company '{}' not found", vt.getVacancyCaption(), vt.getCompanyCaption());
            else {
                Vacancy vacancy = new Vacancy();
                vacancy.setCaption(vt.getVacancyCaption());
                vacancy.setUrl(vt.getVacancyUrl());
                vacancy.setPriceMax(vt.getPriceMax());
                vacancy.setPriceMin(vt.getPriceMin());
                vacancy.setRegistrationDate(vt.getRegistrationDate());
                vacancy.setCurrency(currencyService.findByCaption(vt.getCurrencyCaption()));
                vacancy.setSite(vt.getSite());
                vacancy.setCity(vt.getCity());
                vacancy.setCompany(company);

                BeanPropertyBindingResult result =
                        new BeanPropertyBindingResult(vacancy, vacancy.getCaption());

                ValidationUtils.invokeValidator(validator, vacancy, result);
                if (result.hasErrors()) {
                    List<ObjectError> errors = result.getAllErrors();

                    StringBuilder builder = new StringBuilder();
                    builder.append(String.format("Validate vacancy '%s' . No of validation errors: %s", vacancy.getCaption(), errors.size()));
                    errors.forEach(objectError -> {
                        builder.append("\n");
                        builder.append(objectError.getDefaultMessage());
                    });
                    log.error(builder.toString());
                } else {
                    vacancies.add(vacancy);
                }
            }
        }

        return vacancies;
    }

    public void buildFromTemplates(List<VacancyTemplate> vacancyTemplates) {
        validateCompanies(vacancyTemplates);
        List<Vacancy> vacancies = validateVacancies(vacancyTemplates);
        saveAllVacancies(vacancies);
    }

    public void eraseAllVacancies() {
        vacancyService.truncateTable();
    }
}
