package ua.chuchvaga.taras.lookingjob.service;

import ua.chuchvaga.taras.lookingjob.domain.Vacancy;
import ua.chuchvaga.taras.lookingjob.domain.ViewedVacancy;

import java.util.List;

/**
 * Created by beaver on 1/22/16.
 */
public interface ViewedService {
    ViewedVacancy findByUrl(String url);

    List<ViewedVacancy> findAll();

    void create(Vacancy vacancy, boolean viewed);

    List<ViewedVacancy> findByUrlIn(List<String> urls);
}
