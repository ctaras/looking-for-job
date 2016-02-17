package ua.chuchvaga.taras.lookingjob.service;

import org.springframework.data.repository.CrudRepository;
import ua.chuchvaga.taras.lookingjob.domain.Currency;

/**
 * Created by beaver on 1/22/16.
 */
public interface CurrencyRepository extends CrudRepository<Currency, Long> {
    Currency findByCaption(String currencyCaption);
}
