package ua.chuchvaga.taras.lookingjob.service;

import org.springframework.data.repository.PagingAndSortingRepository;
import ua.chuchvaga.taras.lookingjob.domain.Site;

/**
 * Created by beaver on 1/22/16.
 */
public interface SiteRepository extends PagingAndSortingRepository<Site, Long> {
}
