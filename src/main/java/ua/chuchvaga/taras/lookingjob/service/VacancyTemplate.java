package ua.chuchvaga.taras.lookingjob.service;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import ua.chuchvaga.taras.lookingjob.domain.City;
import ua.chuchvaga.taras.lookingjob.domain.Site;

import java.util.Date;

@Getter
@ToString
@RequiredArgsConstructor
@Builder
public class VacancyTemplate {
    private final String vacancyUrl;
    private final String vacancyCaption;
    private final String companyCaption;
    private final Date registrationDate;
    private final City city;
    private final Site site;
    private final int priceMin;
    private final int priceMax;
    private final String currencyCaption;
    private final String description;

    private static class VacancyTemplateBuilder {
        private String vacancyUrl;
        private String vacancyCaption;
        private String companyCaption;

        VacancyTemplateBuilder companyCaption(String companyCaption) {
            this.companyCaption = companyCaption.substring(0, Math.min(companyCaption.length(), 255)).trim();
            return this;
        }

        VacancyTemplateBuilder vacancyUrl(String vacancyUrl) {
            this.vacancyUrl = vacancyUrl.substring(0, Math.min(vacancyUrl.length(), 2000)).trim();
            return this;
        }

        VacancyTemplateBuilder vacancyCaption(String vacancyCaption) {
            this.vacancyCaption = vacancyCaption.substring(0, Math.min(vacancyCaption.length(), 255)).trim();
            return this;
        }
    }
}
