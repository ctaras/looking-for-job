package ua.chuchvaga.taras.lookingjob

import groovy.util.logging.Slf4j
import org.xml.sax.InputSource
import ua.chuchvaga.taras.lookingjob.domain.City
import ua.chuchvaga.taras.lookingjob.domain.Site
import ua.chuchvaga.taras.lookingjob.service.VacancyBuilder
import ua.chuchvaga.taras.lookingjob.util.BaseParserImpl

@Slf4j
class RabotaUAParser extends BaseParserImpl {

    def urlTemplate = '%1$s/mobile/list?keyWords=%2$s&regionId=%3$s&ss=1&pg=%4$s'

    def currentTime = System.currentTimeMillis()
    def interval = [
            'Горячая'      : 0,
            'Вчера'        : 1000 * 60 * 60 * 24,
            'сек. назад'   : 1000,
            'минуту назад' : 1000 * 60,
            'минуты назад' : 1000 * 60,
            'минут назад'  : 1000 * 60,
            'час назад'    : 1000 * 60 * 60,
            'часа назад'   : 1000 * 60 * 60,
            'часов назад'  : 1000 * 60 * 60,
            'день назад'   : 1000 * 60 * 60 * 24,
            'дня назад'    : 1000 * 60 * 60 * 24,
            'дней назад'   : 1000 * 60 * 60 * 24,
            'неделю назад' : 1000 * 60 * 60 * 24 * 7,
            'недели назад' : 1000 * 60 * 60 * 24 * 7,
            'недель назад' : 1000 * 60 * 60 * 24 * 7,
            'месяц назад'  : 1000 * 60 * 60 * 24 * 7 * 31,
            'месяца назад' : 1000 * 60 * 60 * 24 * 7 * 31,
            'месяцев назад': 1000 * 60 * 60 * 24 * 7 * 31,
    ]

    def curUAH = "грн"
    def curUAH2 = "uah"
    def curUSD = "usd"
    def curEUR = "eur"

    def currencies = [curUAH, curUSD, curEUR, curUAH2]

    @Override
    void importVacancies() {
        info("Parser UA_RABOTA")

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

            doc.'**'.SECTION.grep { it.'@itemtype'?.contains 'http://schema.org/JobPosting' }.each {

                debug("Vacancy of number " + index++)

                def base = it

                tryCatchClosure({

                    cd(1)
                    def captionBlock = base.'**'.A.first();
                    cd(2)
                    vacancyCaption = captionBlock.text().trim()
                    cd(3)
                    vacancyUrl = captionBlock.attribute("href")
                    cd(4)
                    def ulBlock = base.UL.first()
                    def companyBlock = ulBlock.LI.first()
                    companyCaption = companyBlock.text().trim()
                    ulBlock.remove(companyBlock)
                    cd(5)
                    def cityBlock = ulBlock.LI.first()
                    cityCaption = cityBlock.text().trim()
                    ulBlock.remove(cityBlock)
                    cd(6)
                    currencyCaption = curUAH
                    def salaryBlock = ulBlock.LI.first().B
                    if (salaryBlock.size() > 0) {
                        def salary = salaryBlock.first().text().replaceAll("\\s+", "").replaceAll("\\u00A0", "")
                        ulBlock.remove(salaryBlock.first().parent())

                        def curRegex = /\w{3}$/
                        def findCur = (salary =~ /$curRegex/)

                        if (findCur.count > 0)
                            for (def cur : currencies)
                                if (findCur[0].indexOf(cur) >= 0) {
                                    currencyCaption = cur
                                    break
                                }

                        priceMin = priceMax = salary.replace(currencyCaption, "").trim().toInteger()
                    }
                    cd(7)
                    def timeBlock = ulBlock.LI.first()
                    def time = timeBlock.text().trim()
                    ulBlock.remove(cityBlock)

                    for (def i : interval)
                        if (time.indexOf(i.key) >= 0) {
                            registrationDate = new Date(currentTime - i.value
                                    * time.replace(i.key, "").replaceAll("\\s+", "").replaceAll("\\u00A0", "").toInteger())
                            break
                        }

                    cd(8)
                })
            }

            if (index == 0) break
        }

        buildVacancies()
    }
}

def importVacancies(VacancyBuilder vacancyBuilder, Site site, String query, City city, Integer idsRegion) {
    parser = new RabotaUAParser()
    parser.setSite(site)
    parser.setQuery(query)
    parser.setCity(city)
    parser.setIdRegion(idsRegion)
    parser.setVacancyBuilder(vacancyBuilder)

    parser.importVacancies()
}