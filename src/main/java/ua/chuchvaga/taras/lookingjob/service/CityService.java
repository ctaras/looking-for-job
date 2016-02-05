package ua.chuchvaga.taras.lookingjob.service;

import ua.chuchvaga.taras.lookingjob.domain.City;

import java.util.List;

/**
 * Created by beaver on 1/22/16.
 */
public interface CityService {
    List<City> findAll();

    City findById(Long id);

    City save(City city);

    void delete(Long id);
}
