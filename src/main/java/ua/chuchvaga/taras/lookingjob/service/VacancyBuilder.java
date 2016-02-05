package ua.chuchvaga.taras.lookingjob.service;

import java.util.List;

/**
 * Created by beaver on 1/27/16.
 */
public interface VacancyBuilder {
    void buildFromTemplates(List<VacancyTemplate> vacancies);

    void eraseAllVacancies();
}
