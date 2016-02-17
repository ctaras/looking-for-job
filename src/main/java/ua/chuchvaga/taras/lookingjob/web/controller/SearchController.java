package ua.chuchvaga.taras.lookingjob.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ua.chuchvaga.taras.lookingjob.domain.*;
import ua.chuchvaga.taras.lookingjob.service.*;
import ua.chuchvaga.taras.lookingjob.web.propertyeditor.CityPropertyEditor;
import ua.chuchvaga.taras.lookingjob.web.propertyeditor.SitePropertyEditor;

import javax.validation.Valid;
import java.util.List;

@lombok.extern.slf4j.Slf4j
@Controller
@RequestMapping("/search")
public class SearchController {

    @Autowired
    private SearchEngine searchEngine;
    @Autowired
    private SiteService siteService;
    @Autowired
    private VacancyService vacancyService;
    @Autowired
    private CityService cityService;
    @Autowired
    private SearchOptions searchOptions;
    @Autowired
    private VacancyModelService vacancyModelService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Site.class,
                new SitePropertyEditor(siteService));
        binder.registerCustomEditor(City.class,
                new CityPropertyEditor(cityService));
//        binder.registerCustomEditor(Date.class,
//                new CustomDateEditor(new SimpleDateFormat("dd/MM/yyyy"), true, 10));
    }

    @RequestMapping(method = RequestMethod.GET)
    String searchForm(Model uiModel) {
        searchOptions.getSites().addAll(siteService.findAll());

        uiModel.addAttribute("allsites", siteService.findAll());
        uiModel.addAttribute("allcities", cityService.findAll());
        uiModel.addAttribute("options", searchOptions);

        return "search";
    }

    @RequestMapping(value = "/result", method = RequestMethod.GET)
    String search(@Valid @ModelAttribute("options") SearchOptionsImpl searchOptions, BindingResult bindingResult, Model uiModel) {
        log.info("Start searching");

        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("allsites", siteService.findAll());
            uiModel.addAttribute("allcities", cityService.findAll());
            uiModel.addAttribute("options", searchOptions);
            return "search";
        }
        uiModel.asMap().clear();

        searchEngine.search(searchOptions);

        List<Vacancy> vacancies = vacancyService.findAll();
        uiModel.addAttribute("allvacancies", vacancyModelService.getListModel(vacancyService.findAllWithViewedStatus()));
        uiModel.addAttribute("allsites", siteService.findAll());
        uiModel.addAttribute("allcities", cityService.findAll());
        uiModel.addAttribute("options", searchOptions);

        log.info("No. of vacancies: " + vacancies.size());
        return "search";
    }
}
