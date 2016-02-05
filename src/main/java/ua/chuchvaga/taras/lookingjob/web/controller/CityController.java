package ua.chuchvaga.taras.lookingjob.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ua.chuchvaga.taras.lookingjob.domain.City;
import ua.chuchvaga.taras.lookingjob.service.CityService;
import ua.chuchvaga.taras.lookingjob.web.util.Message;
import ua.chuchvaga.taras.lookingjob.web.util.UrlUtil;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Locale;

@lombok.extern.slf4j.Slf4j
@Controller
@RequestMapping("/cities")
public class CityController {

    @Autowired
    private CityService cityService;

    @Autowired
    private MessageSource messageSource;

    @RequestMapping(method = RequestMethod.GET)
    public String list(Model uiModel) {
        log.info("Listing Cities");

        List<City> cities = cityService.findAll();
        uiModel.addAttribute("cities", cities);

        log.info("No. of cities: " + cities.size());

        return "cities/list";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String show(@PathVariable("id") Long id, Model uiModel) {
        City city = cityService.findById(id);
        uiModel.addAttribute("city", city);

        return "cities/show";
    }

    @RequestMapping(value = "/{id}", params = "form", method = RequestMethod.POST)
    public String update(@Valid City city, BindingResult bindingResult, Model uiModel,
                         HttpServletRequest httpServletRequest, RedirectAttributes redirectAttributes,
                         Locale locale) {
        log.info("Updating city");
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("message", new Message("error",
                    messageSource.getMessage("city_save_fail", new Object[]{}, locale)));
            uiModel.addAttribute("city", city);
            uiModel.addAttribute("titleOfPage", "form.edit.city");
            return "cities/update";
        }
        uiModel.asMap().clear();
        redirectAttributes.addFlashAttribute("message", new Message("success",
                messageSource.getMessage("city_save_success", new Object[]{}, locale)));
        cityService.save(city);
        return "redirect:/cities/" + UrlUtil.encodeUrlPathSegment(city.getId().toString(),
                httpServletRequest);
    }

    @RequestMapping(value = "/{id}", params = "form", method = RequestMethod.GET)
    public String updateForm(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("city", cityService.findById(id));
        uiModel.addAttribute("titleOfPage", "form.edit.city");
        return "cities/update";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String create(@Valid City city, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest, RedirectAttributes redirectAttributes, Locale locale) {
        log.info("Creating city");
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("message", new Message("error",
                    messageSource.getMessage("city_save_fail", new Object[]{}, locale)));
            uiModel.addAttribute("city", city);
            uiModel.addAttribute("titleOfPage", "form.new.city");
            return "cities/update";
        }
        uiModel.asMap().clear();
        redirectAttributes.addFlashAttribute("message", new Message("success",
                messageSource.getMessage("city_save_success", new Object[]{}, locale)));

        log.info("city id: " + city.getId());

        cityService.save(city);
        return "redirect:/cities/";
    }

    @RequestMapping(params = "form", method = RequestMethod.GET)
    public String createForm(Model uiModel) {
        City city = new City();
        uiModel.addAttribute("city", city);
        uiModel.addAttribute("titleOfPage", "form.new.city");

        return "cities/update";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String delete(@PathVariable("id") Long id) {
        cityService.delete(id);
        return "redirect:/cities/";
    }
}
