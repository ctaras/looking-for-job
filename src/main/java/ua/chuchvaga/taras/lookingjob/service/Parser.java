package ua.chuchvaga.taras.lookingjob.service;


import ua.chuchvaga.taras.lookingjob.domain.City;
import ua.chuchvaga.taras.lookingjob.domain.Site;

public interface Parser {
    String getQuery();

    void setQuery(String query);

    City getCity();

    void setCity(City city);

    void importVacancies();

    Site getSite();

    void setSite(Site site);

    int getIdRegion();

    void setIdRegion(int id);

    VacancyBuilder getVacancyBuilder();

    void setVacancyBuilder(VacancyBuilder vacancyBuilder);
}
