package ua.chuchvaga.taras.lookingjob.model;

import lombok.Value;

import java.util.Date;

@Value
public class VacancyModel {
    private final String id;
    private final int version;
    private final String caption;
    private final String siteCaption;
    private final String url;
    private final String companyCaption;
    private final String cityCaption;
    private final Date registrationDate;
    private final int priceMin;
    private final int priceMax;
    private final String currencyCaption;
    private final int priceMinForCourseSystemCurrency;
    private final int priceMaxForCourseSystemCurrency;
    private final String systemCurrencyCaption;
    private final boolean viewed;
}
