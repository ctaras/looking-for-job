package ua.chuchvaga.taras.lookingjob.service;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.chuchvaga.taras.lookingjob.domain.City;

import java.util.List;

@Repository
@Transactional
@Service("cityService")
public class CityServiceImpl implements CityService {

    @Autowired
    private CityRepository cityRepository;

    @Override
    @Transactional(readOnly = true)
    public List<City> findAll() {
        return Lists.newArrayList(cityRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public City findById(Long id) {
        return cityRepository.findOne(id);
    }

    @Override
    public City save(City city) {
        return cityRepository.save(city);
    }

    @Override
    public void delete(Long id) {
        cityRepository.delete(id);
    }
}
