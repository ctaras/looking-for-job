package ua.chuchvaga.taras.lookingjob.service;

import ua.chuchvaga.taras.lookingjob.domain.Vacancy;
import ua.chuchvaga.taras.lookingjob.model.VacancyModel;

import java.util.List;

/**
 * Created by beaver on 2/1/16.
 */
public interface VacancyModelService {
    VacancyModel getModel(Vacancy vacancy);

    List<VacancyModel> getListModel(List<Vacancy> vacancies);
}
