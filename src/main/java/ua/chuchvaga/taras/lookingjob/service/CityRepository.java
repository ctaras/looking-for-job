package ua.chuchvaga.taras.lookingjob.service;

import org.springframework.data.repository.PagingAndSortingRepository;
import ua.chuchvaga.taras.lookingjob.domain.City;

/**
 * Created by beaver on 1/22/16.
 */
public interface CityRepository extends PagingAndSortingRepository<City, Long> {
}
