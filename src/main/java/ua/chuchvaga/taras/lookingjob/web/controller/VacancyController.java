package ua.chuchvaga.taras.lookingjob.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ua.chuchvaga.taras.lookingjob.domain.*;
import ua.chuchvaga.taras.lookingjob.model.VacancyDataList;
import ua.chuchvaga.taras.lookingjob.model.VacancyModel;
import ua.chuchvaga.taras.lookingjob.service.*;
import ua.chuchvaga.taras.lookingjob.web.propertyeditor.CityPropertyEditor;
import ua.chuchvaga.taras.lookingjob.web.propertyeditor.CompanyPropertyEditor;
import ua.chuchvaga.taras.lookingjob.web.propertyeditor.CurrencyPropertyEditor;
import ua.chuchvaga.taras.lookingjob.web.propertyeditor.SitePropertyEditor;
import ua.chuchvaga.taras.lookingjob.web.util.Message;
import ua.chuchvaga.taras.lookingjob.web.util.UrlUtil;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

@lombok.extern.slf4j.Slf4j
@Controller
@RequestMapping("/vacancies")
public class VacancyController {

    @Autowired
    private VacancyService vacancyService;

    @Autowired
    private SiteService siteService;

    @Autowired
    private CityService cityService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private CurrencyService currencyService;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private VacancyModelService vacancyModelService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
//        binder.registerCustomEditor(Date.class,
//                new CustomDateEditor(new SimpleDateFormat("dd/MM/yyyy"), true, 10));
        binder.registerCustomEditor(Site.class,
                new SitePropertyEditor(siteService));
        binder.registerCustomEditor(City.class,
                new CityPropertyEditor(cityService));
        binder.registerCustomEditor(Company.class,
                new CompanyPropertyEditor(companyService));
        binder.registerCustomEditor(Currency.class,
                new CurrencyPropertyEditor(currencyService));
    }

    @RequestMapping(method = RequestMethod.GET)
    public String list(Model uiModel) {
        log.info("Listing vacancies");

        List<Vacancy> vacancies = vacancyService.findAll();
        uiModel.addAttribute("allvacancies", vacancyModelService.getListModel(vacancies));

        log.info("No. of vacancies: " + vacancies.size());

        return "vacancies/list";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String show(@PathVariable("id") Long id, Model uiModel) {
        Vacancy vacancy = vacancyService.findById(id);
        uiModel.addAttribute("vacancy", vacancy);

        return "vacancies/show";
    }

    @RequestMapping(value = "/{id}", params = "form", method = RequestMethod.POST)
    public String update(@Valid Vacancy vacancy, BindingResult bindingResult, Model uiModel,
                         HttpServletRequest httpServletRequest, RedirectAttributes redirectAttributes,
                         Locale locale) {
        log.info("Updating vacancy");
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("message", new Message("error",
                    messageSource.getMessage("vacancy_save_fail", new Object[]{}, locale)));
            uiModel.addAttribute("vacancy", vacancy);
            uiModel.addAttribute("sites", siteService.findAll());
            uiModel.addAttribute("cities", cityService.findAll());
            uiModel.addAttribute("companies", companyService.findAll());
            uiModel.addAttribute("allcurrencies", currencyService.findAll());
            uiModel.addAttribute("titleOfPage", "form.edit.vacancy");
            return "vacancies/update";
        }
        uiModel.asMap().clear();
        redirectAttributes.addFlashAttribute("message", new Message("success",
                messageSource.getMessage("vacancy_save_success", new Object[]{}, locale)));
        vacancyService.save(vacancy);
        return "redirect:/vacancies/" + UrlUtil.encodeUrlPathSegment(vacancy.getId().toString(),
                httpServletRequest);
    }

    @RequestMapping(value = "/{id}", params = "form", method = RequestMethod.GET)
    public String updateForm(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("vacancy", vacancyService.findById(id));
        uiModel.addAttribute("sites", siteService.findAll());
        uiModel.addAttribute("cities", cityService.findAll());
        uiModel.addAttribute("companies", companyService.findAll());
        uiModel.addAttribute("allcurrencies", currencyService.findAll());
        uiModel.addAttribute("titleOfPage", "form.edit.vacancy");
        return "vacancies/update";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String create(@Valid Vacancy vacancy, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest, RedirectAttributes redirectAttributes, Locale locale) {
        log.info("Creating vacancy");
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("message", new Message("error",
                    messageSource.getMessage("vacancy_save_fail", new Object[]{}, locale)));
            uiModel.addAttribute("vacancy", vacancy);
            uiModel.addAttribute("sites", siteService.findAll());
            uiModel.addAttribute("cities", cityService.findAll());
            uiModel.addAttribute("companies", companyService.findAll());
            uiModel.addAttribute("allcurrencies", currencyService.findAll());
            uiModel.addAttribute("titleOfPage", "form.new.vacancy");
            return "vacancies/update";
        }
        uiModel.asMap().clear();
        redirectAttributes.addFlashAttribute("message", new Message("success",
                messageSource.getMessage("vacancy_save_success", new Object[]{}, locale)));

        log.info("Vacancy id: " + vacancy.getId());

        vacancyService.save(vacancy);
        return "redirect:/vacancies/";
    }

    @RequestMapping(params = "form", method = RequestMethod.GET)
    public String createForm(Model uiModel) {
        Vacancy vacancy = new Vacancy();
        uiModel.addAttribute("vacancy", vacancy);
        uiModel.addAttribute("sites", siteService.findAll());
        uiModel.addAttribute("cities", cityService.findAll());
        uiModel.addAttribute("companies", companyService.findAll());
        uiModel.addAttribute("allcurrencies", currencyService.findAll());
        uiModel.addAttribute("titleOfPage", "form.new.vacancy");
        return "vacancies/update";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String delete(@PathVariable("id") Long id) {
        vacancyService.delete(id);
        return "redirect:/vacancies/";
    }

    @RequestMapping(value = "/listdata", method = RequestMethod.GET)
    @ResponseBody
    public VacancyDataList listData(@RequestParam(value = "sort", required = false, defaultValue = "company") String sortColumn) {
        List<VacancyModel> vacancyModels = vacancyModelService.getListModel(vacancyService.findAll());
        Collections.sort(vacancyModels, (o1, o2) -> {

            int c = 0;

            if (sortColumn.equals("company")) {
                c = o1.getCompanyCaption().compareTo(o2.getCompanyCaption());
                if (c == 0)
                    c = o1.getCaption().compareTo(o2.getCaption());
            } else {
                c = o1.getCaption().compareTo(o2.getCaption());
                if (c == 0)
                    c = o1.getCompanyCaption().compareTo(o2.getCompanyCaption());
            }
            return c;
        });
        return new VacancyDataList(vacancyModels);
    }
}
