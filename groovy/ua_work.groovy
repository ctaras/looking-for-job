package ua.chuchvaga.taras.lookingjob

import groovy.util.logging.Slf4j
import org.xml.sax.InputSource
import ua.chuchvaga.taras.lookingjob.domain.City
import ua.chuchvaga.taras.lookingjob.domain.Site
import ua.chuchvaga.taras.lookingjob.service.VacancyBuilder
import ua.chuchvaga.taras.lookingjob.util.BaseParserImpl

@Slf4j
class WorkUaParser extends BaseParserImpl {

    def urlTemplate = '%1$s/ua/jobs/?search=%2$s&region=%3$s&ss=1&page=%4$s&days=125'
    def currentTime = System.currentTimeMillis()
    def interval = [
            'Гаряча'   : 0,
            'Вчора'    : 1000 * 60 * 60 * 24,
            'сек. тому': 1000,
            'хв. тому' : 1000 * 60,
            'г. тому'  : 1000 * 60 * 60,
            'дні тому' : 1000 * 60 * 60 * 24,
            'днів тому': 1000 * 60 * 60 * 24,
            'тиж. тому': 1000 * 60 * 60 * 24 * 7,
            'міс. тому': 1000 * 60 * 60 * 24 * 7 * 31
    ]

    def yesterday = "Вчора"

    def curUAH = "грн"
    def curUAH2 = "uah"
    def curUSD = "usd"
    def curEUR = "eur"

    def currencies = [curUAH, curUSD, curEUR, curUAH2]

    @Override
    void importVacancies() {
        info("Parser UA_WORK")

        def rootURL = site.getUrl()
        def q = query.replace(" ", "+")

        def pageIndex = 1
        while (true) {

            def url = sprintf(urlTemplate, [rootURL, q, idRegion, pageIndex])
            debug("url", url)

            pageIndex++;

            URL urlObject = new URL(url);
            HttpURLConnection urlConnection = (HttpURLConnection) urlObject.openConnection();
            InputStreamReader inputStreamReader = new InputStreamReader(urlConnection.getInputStream());
            def doc = parse(new InputSource(inputStreamReader))

            def index = 0

            doc.'**'.DIV.grep { it.'@class'?.contains 'job-link' }.each {

                debug("Vacancy of number " + index++)

                def base = it

                tryCatchClosure({

                    def hot = false

                    cd(1)
                    base.'**'.SUP.each { it.parent().remove(it) }
                    cd(2)
                    base.'**'.SPAN.grep { it.'@class'?.contains 'nowrap' }.each {
                        priceMin = it.text().trim().toLowerCase()
                    }
                    cd(3)
                    base.'**'.DIV.grep { it.'@class'?.contains 'logo-img' }.each { it.parent().remove(it) }
                    cd(4)
                    base.'**'.SPAN.grep { it.'@class'?.contains 'text-muted' }.each { it.parent().remove(it) }
                    cd(5)
                    base.'**'.SPAN.grep { it.'@data-toggle'?.contains 'popover' }.each { it.parent().remove(it) }
                    cd(6)
                    base.'**'.SPAN.grep { it.'@class'?.contains 'label-hot' }.each {
                        hot = true
                        it.parent().remove(it)
                    }
                    cd(7)
                    vacancyCaption = base.'**'.A.first().text().trim()
                    cd(8)
                    vacancyUrl = base.'**'.A.first().attribute("href")
                    cd(9)
                    companyCaption = base.DIV.first().SPAN.first().text().trim()
                    cd(10) // price
                    if (priceMin == 0)
                        currencyCaption = curUAH
                    else
                        for (def cur : currencies) {
                            if (priceMin.indexOf(cur) >= 0) {
                                currencyCaption = cur
                                priceMin = priceMin.replace(cur, "")
                                        .replaceAll("\\u00A0", "")
                                        .replaceAll("\\s", "")
                                        .trim().toInteger()
                                break;
                            }
                        }

                    priceMax = priceMin
                    cd(11) // registrationDate // cityCaption
                    if (hot) {
                        cd(12)
                        registrationDate = new Date(currentTime)
                        cityCaption = base.DIV.first().SPAN.last().text().trim()
                    } else {
                        cd(13)
                        def publicDate = base.DIV.first().SPAN.last().text().trim()
                        if (publicDate.indexOf(yesterday) >= 0) {
                            cd(14)
                            cityCaption = publicDate.replace(yesterday, "").trim()
                            registrationDate = new Date(currentTime - interval[yesterday])
                        } else {
                            cd(15)
                            def cityRegex = /^\D+/
                            def findCity = (publicDate =~ /$cityRegex/)
                            if (findCity.count > 0) {
                                cd(16)
                                cityCaption = findCity[0].trim()
                                publicDate = publicDate.replace(cityCaption, "").trim()
                                for (def i : interval)
                                    if (publicDate.indexOf(i.key) >= 0) {
                                        cd(17)
                                        registrationDate = new Date(currentTime - i.value
                                                * publicDate.replace(i.key, "").trim().toInteger())
                                        break
                                    }
                            }
                        }

                    }
                })
            }

            urlConnection.disconnect();

            if (index == 0) break
        }
        buildVacancies()
    }
}

def importVacancies(VacancyBuilder vacancyBuilder, Site site, String query, City city, Integer idsRegion) {
    parser = new WorkUaParser()
    parser.setSite(site)
    parser.setQuery(query)
    parser.setCity(city)
    parser.setIdRegion(idsRegion)
    parser.setVacancyBuilder(vacancyBuilder)

    parser.importVacancies()
}