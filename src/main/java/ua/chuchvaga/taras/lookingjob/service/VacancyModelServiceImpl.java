package ua.chuchvaga.taras.lookingjob.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.chuchvaga.taras.lookingjob.domain.Currency;
import ua.chuchvaga.taras.lookingjob.domain.Vacancy;
import ua.chuchvaga.taras.lookingjob.model.VacancyModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class VacancyModelServiceImpl implements VacancyModelService {

    @Autowired
    private CurrencyService currencyService;

    @Override
    public VacancyModel getModel(Vacancy v) {
        Currency systemCurrency = currencyService.getSystemCurrency();
        Objects.requireNonNull(systemCurrency);

        double rate = v.getCurrency().getRate() * systemCurrency.getRate();

        return new VacancyModel(v.getId()
                , v.getVersion()
                , v.getCaption()
                , v.getSite().getCaption()
                , v.getSite().getUrl() + v.getUrl()
                , v.getCompany().getCaption()
                , v.getCity().getCaption()
                , v.getRegistrationDate()
                , v.getPriceMin()
                , v.getPriceMax()
                , v.getCurrency().getCaption()
                , new Double(v.getPriceMin() * rate).intValue()
                , new Double(v.getPriceMax() * rate).intValue()
                , systemCurrency.getCaption()
                , v.getViewed() != null && v.getViewed().isViewed());
    }

    @Override
    public List<VacancyModel> getListModel(List<Vacancy> vacancies) {
        List<VacancyModel> listModel = new ArrayList<>();
        for (Vacancy v : vacancies)
            listModel.add(getModel(v));
        return listModel;
    }
}
