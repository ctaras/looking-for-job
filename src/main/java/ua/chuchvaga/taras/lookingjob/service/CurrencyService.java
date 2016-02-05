package ua.chuchvaga.taras.lookingjob.service;

import ua.chuchvaga.taras.lookingjob.domain.Currency;

import java.util.List;

/**
 * Created by beaver on 1/22/16.
 */
public interface CurrencyService {
    List<Currency> findAll();

    Currency findById(Long id);

    Currency save(Currency currency);

    void delete(Long id);

    Currency findByCaption(String currencyCaption);

    Currency getSystemCurrency();
}
