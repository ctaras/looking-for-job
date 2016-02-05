package ua.chuchvaga.taras.lookingjob

import groovy.util.logging.Slf4j
import org.xml.sax.InputSource
import ua.chuchvaga.taras.lookingjob.domain.City
import ua.chuchvaga.taras.lookingjob.domain.Site
import ua.chuchvaga.taras.lookingjob.service.VacancyBuilder
import ua.chuchvaga.taras.lookingjob.util.BaseParserImpl

import static java.util.Calendar.*

@Slf4j
class HHUAParser extends BaseParserImpl {

    def mobileSiteUrl = "https://m.hh.ua"

    def urlTemplate = '%1$s/vacancies?text=%2$s&currency_code=UAH&area=%3$s&page=%4$s'

    def currentTime = System.currentTimeMillis()

    def today = new Date(currentTime)

    def dates = [
            'сегодня': today,
            'вчера'  : today.minus(1)
    ]

    def months = [
            'янв': 0,
            'фев': 1,
            'мар': 2,
            'апр': 3,
            'мая': 4,
            'июн': 5,
            'июл': 6,
            'авг': 7,
            'сен': 8,
            'окт': 9,
            'ноя': 10,
            'дек': 11,
    ]

    def curUAH = "грн"
    def curUAH2 = "uah"
    def curUSD = "usd"
    def curEUR = "eur"

    def currencies = [curUAH, curUSD, curEUR, curUAH2]

    @Override
    void importVacancies() {
        info("Parser UA.HH")

        def rootURL = mobileSiteUrl //site.getUrl()
        def q = query.replace(" ", "+")

        def pageIndex = 1
        while (true) {

            def url = sprintf(urlTemplate, [rootURL, q, idRegion, pageIndex - 1])
            debug("url", url)

            pageIndex++;

            URL urlObject = new URL(url);
            HttpURLConnection urlConnection = (HttpURLConnection) urlObject.openConnection();
            InputStreamReader inputStreamReader = new InputStreamReader(urlConnection.getInputStream());
            def doc = parse(new InputSource(inputStreamReader))

            def index = 0
            doc.'**'.LI.grep { it.'@class'?.contains 'vacancy-list-item' }.grep {
                it.'@data-qa'?.contains 'search-vacancy-item'
            }.each {

                debug("Vacancy of number " + index++)

                def base = it

                tryCatchClosure({

                    // remove
                    //base.'**'.SPAN.grep { it.'@class'?.contains 'metro-station' }.each { it.parent().remove(it) }
                    //base.'**'.BR.each { it.parent().remove(it) }

                    cd(1)
                    def mainBlock = base.A.grep { it.'@class'?.contains 'vacancy-list-item-link' }.first()
                    cd(2)
                    vacancyUrl = mainBlock.attribute("href").replace(mobileSiteUrl, "")
                    cd(3)
                    vacancyCaption = mainBlock.DIV.grep {
                        it.'@class'?.contains 'vacancy-list-item__name'
                    }.first().text().trim()
                    cd(4)
                    description = mainBlock.DIV.grep {
                        it.'@class'?.contains 'vacancy-list-item__city'
                    }.first().text().trim()
                    cd(5)
                    if (description.indexOf(",") >= 0)
                        cityCaption = description.substring(0, description.indexOf(",")).trim()
                    else
                        cityCaption = description

                    description = description.replaceAll("\\s+", "").replaceAll(",", ", ").trim()
                    cd(6)
                    def pubdateBlock = mainBlock.'**'.SPAN.grep {
                        it.'@class'?.contains 'vacancy-list-item__pubdate'
                    }.first()
                    def pubdate = pubdateBlock.text().trim()
                    pubdateBlock.parent().remove(pubdateBlock)

                    def value = dates.find { it.key == pubdate }?.value
                    if (value != null) registrationDate = value
                    else {
                        registrationDate = today.clone()
                        value = months.find { pubdate.indexOf(it.key) >= 0 }?.value
                        if (value != null) {
                            registrationDate[MONTH] = value

                            def dayRegex = /^\d+/
                            def findDay = (pubdate =~ /$dayRegex/)

                            if (findDay.count > 0) registrationDate[DAY_OF_MONTH] = findDay[0].trim().toInteger()

                            if (registrationDate > today) registrationDate[YEAR] = registrationDate[YEAR] - 1
                        }
                    }
                    cd(7)
                    companyCaption = mainBlock.DIV.grep {
                        it.'@class'?.contains 'vacancy-list-item__company'
                    }.first().text()
                            .replaceAll("\\s+", " ")
                            .replaceAll(" +", " ").trim()
                    cd(8)
                    currencyCaption = curUAH

                    def salaryBlocks = mainBlock.DIV.grep { it.'@class'?.contains 'vacancy-list-item__salary' }
                    if (salaryBlocks.size() > 0) {
                        def salary = salaryBlocks.first().text().toLowerCase().replaceAll("\\s+", " ").trim()

                        def curRegex = /\w{3}$/
                        def findCur = (salary =~ /$curRegex/)

                        if (findCur.count > 0)
                            for (def cur : currencies)
                                if (findCur[0].indexOf(cur) >= 0) {
                                    currencyCaption = cur
                                    break
                                }

                        def salaryRegex = /\d+/
                        def findPrice = (salary =~ /$salaryRegex/)

                        if (findPrice.count == 1)
                            priceMax = priceMin = findPrice[0].toInteger()
                        else if (findPrice.count == 2) {
                            priceMin = findPrice[0].toInteger()
                            priceMax = findPrice[1].toInteger()
                        }
                    }
                    cd(9)
                })
            }

            if (index == 0) break
        }

        buildVacancies()
    }
}

def importVacancies(VacancyBuilder vacancyBuilder, Site site, String query, City city, Integer idsRegion) {
    parser = new HHUAParser()
    parser.setSite(site)
    parser.setQuery(query)
    parser.setCity(city)
    parser.setIdRegion(idsRegion)
    parser.setVacancyBuilder(vacancyBuilder)

    parser.importVacancies()
}