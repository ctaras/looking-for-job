package ua.chuchvaga.taras.lookingjob.service;

import groovy.lang.GroovyClassLoader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.chuchvaga.taras.lookingjob.domain.City;
import ua.chuchvaga.taras.lookingjob.domain.SearchOptions;
import ua.chuchvaga.taras.lookingjob.domain.Site;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

@Service
@Slf4j
public class SearchEngineImpl implements SearchEngine {

    @Autowired
    private SitesIDsCitiesService sitesIDsCitiesService;

    @Autowired
    private VacancyBuilder vacancyBuilder;

    @Override
    public void search(SearchOptions searchOptions) {
        String query = searchOptions.getQuery();
        City city = searchOptions.getCity();

        vacancyBuilder.eraseAllVacancies();

        for (Site site : searchOptions.getSites()) {
            Parser parser = null;
            try {

                importVacancies(site, query, city, sitesIDsCitiesService.findBySiteAndCity(site, city).getIdRegion());

            } catch (IOException | InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    @SuppressWarnings("unchecked")
    @Transactional
    private void importVacancies(Site site, String query, City city, int idRegion)
            throws IOException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {

        String nameGroovyScript = String.format("groovy/%s.groovy", site.getAlias());

        File f = new File(nameGroovyScript);

        if (f.exists()) {
            Class scriptClass = new GroovyClassLoader().parseClass(f);
            Object scriptInstance = scriptClass.newInstance();

            scriptClass.getDeclaredMethod("importVacancies", new Class[]{VacancyBuilder.class, Site.class, String.class, City.class, Integer.class})
                    .invoke(scriptInstance, vacancyBuilder, site, query, city, idRegion);
        } else {
            log.error("File not found: {}", nameGroovyScript);
        }
    }
}
