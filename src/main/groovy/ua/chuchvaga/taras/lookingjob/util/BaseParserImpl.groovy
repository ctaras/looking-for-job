package ua.chuchvaga.taras.lookingjob.util

import groovy.util.logging.Slf4j
import org.cyberneko.html.parsers.SAXParser
import ua.chuchvaga.taras.lookingjob.domain.City
import ua.chuchvaga.taras.lookingjob.domain.Site
import ua.chuchvaga.taras.lookingjob.service.Parser
import ua.chuchvaga.taras.lookingjob.service.VacancyBuilder
import ua.chuchvaga.taras.lookingjob.service.VacancyTemplate

@Slf4j
abstract class BaseParserImpl implements Parser {

    String query
    Site site
    City city
    int idRegion

    def parser

    def vacancyUrl
    def vacancyCaption
    def companyCaption
    def registrationDate
    def cityCaption
    def priceMin
    def priceMax
    def currencyCaption
    def description

    def strCodeDebug

    def List<VacancyTemplate> vacancies = [];

    def vacancyBuilder

    BaseParserImpl() {
        parser = new XmlParser(new SAXParser())
    }

    def debug(info) {
        log.debug(info)
    }

    def info(info) {
        log.info(info)
    }

    def warn(info) {
        log.warn(info)
    }

    def error(info) {
        log.error(info)
    }

    def cd(line) {
        strCodeDebug += line + ","
    }

    def debug(k, v) {
        log.debug(sprintf('%1$20s : %2$s', [k, v]))
    }

    def tryCatchClosure(Closure closure) {
        try {

            strCodeDebug = "Code: "

            vacancyUrl = ""
            vacancyCaption = ""
            companyCaption = ""
            registrationDate = new Date()
            cityCaption = ""
            priceMin = 0
            priceMax = 0
            currencyCaption = ""
            description = ""

            closure()

            VacancyTemplate vacancyTemplate = VacancyTemplate.builder()
                    .vacancyUrl(vacancyUrl)
                    .vacancyCaption(vacancyCaption.replaceAll("\\u00A0", "").trim())
                    .companyCaption(companyCaption.replaceAll("\\u00A0", "").trim())
                    .registrationDate(registrationDate)
                    .site(site)
                    .city(city)
                    .priceMin(priceMin)
                    .priceMax(priceMax)
                    .currencyCaption(currencyCaption)
                    .description(description)
                    .build()

            vacancies.add(vacancyTemplate)

            if (log.isDebugEnabled()) {

                debug(new ErrorLogBuilder()
                        .add(strCodeDebug)
                        .add("************** Vacancy (begin) **************")
                        .add("vacancyUrl", vacancyUrl)
                        .add("vacancyCaption", vacancyCaption)
                        .add("companyCaption", companyCaption)
                        .add("registrationDate", registrationDate)
                        .add("cityCaption", cityCaption)
                        .add("priceMin", priceMin)
                        .add("priceMax", priceMax)
                        .add("currencyCaption", currencyCaption)
                        .add("description", description)
                        .add("************** Vacancy ( end ) **************")
                        .build())
            }

        } catch (Exception exc) {

            error(new ErrorLogBuilder()
                    .add(exc.localizedMessage)
                    .add(strCodeDebug)
                    .add("site", site.getAlias())
                    .add("************** Vacancy (begin) **************")
                    .add("vacancyUrl", vacancyUrl)
                    .add("vacancyCaption", vacancyCaption)
                    .add("companyCaption", companyCaption)
                    .add("registrationDate", registrationDate)
                    .add("cityCaption", cityCaption)
                    .add("priceMin", priceMin)
                    .add("priceMax", priceMax)
                    .add("currencyCaption", currencyCaption)
                    .add("description", description)
                    .add("************** Vacancy ( end ) **************")
                    .build())
        }
    }

    def buildVacancies() {
        debug("Imported " + vacancies.size() + " jobs")

        vacancyBuilder.buildFromTemplates(vacancies)
    }

    @Override
    void setQuery(String query) {
        this.query = query
    }

    @Override
    String getQuery() {
        query
    }

    @Override
    void setCity(City city) {
        this.city = city
    }

    @Override
    City getCity() {
        city
    }

    @Override
    void setSite(Site site) {
        this.site = site
    }

    @Override
    Site getSite() {
        site
    }

    abstract void importVacancies()

    def parse(url) {
        parser.parse(url)
    }

    @Override
    void setIdRegion(int id) {
        idRegion = id
    }

    @Override
    int getIdRegion() {
        idRegion
    }

    @Override
    void setVacancyBuilder(VacancyBuilder vacancyBuilder) {
        this.vacancyBuilder = vacancyBuilder
    }

    @Override
    VacancyBuilder getVacancyBuilder() {
        vacancyBuilder
    }
}
