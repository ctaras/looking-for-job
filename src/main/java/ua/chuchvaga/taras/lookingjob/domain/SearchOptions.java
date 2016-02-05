package ua.chuchvaga.taras.lookingjob.domain;

import java.util.Set;

public interface SearchOptions {
    String getQuery();

    void setQuery(String query);

    City getCity();

    void setCity(City city);

    Set<Site> getSites();

    void setSites(Set<Site> sites);
}
