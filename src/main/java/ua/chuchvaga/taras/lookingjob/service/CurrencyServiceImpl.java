package ua.chuchvaga.taras.lookingjob.service;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.chuchvaga.taras.lookingjob.domain.Currency;

import java.util.List;

@Slf4j
@Repository
@Transactional
@Service("currencyService")
public class CurrencyServiceImpl implements CurrencyService {

    @Value("${system.constant.currencyName}")
    private String systemCurrencyCaption;

    @Autowired
    private CurrencyRepository currencyRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Currency> findAll() {
        return Lists.newArrayList(currencyRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public Currency findById(Long id) {
        return currencyRepository.findOne(id);
    }

    @Override
    public Currency save(Currency currency) {
        return currencyRepository.save(currency);
    }

    @Override
    public void delete(Long id) {
        currencyRepository.delete(id);
    }

    @Override
    @Cacheable(value = "currency", unless = "#result == null")
    public Currency findByCaption(String currencyCaption) {
        return currencyRepository.findByCaption(currencyCaption);
    }

    @Override
    public Currency getSystemCurrency() {
        return currencyRepository.findByCaption(systemCurrencyCaption);
    }
}
