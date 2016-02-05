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
import ua.chuchvaga.taras.lookingjob.domain.SitesIDsCities;
import ua.chuchvaga.taras.lookingjob.service.SitesIDsCitiesService;
import ua.chuchvaga.taras.lookingjob.web.util.UrlUtil;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Locale;

@lombok.extern.slf4j.Slf4j
@Controller
@RequestMapping("/regions")
public class SitesIDsCitiesController {

    @Autowired
    private SitesIDsCitiesService sitesIDsCitiesServices;

    @Autowired
    private MessageSource messageSource;

    @RequestMapping(method = RequestMethod.GET)
    public String list(Model uiModel) {
        log.info("Listing sites ids cities");

        List<SitesIDsCities> sitesIDsCities = sitesIDsCitiesServices.findAll();
        uiModel.addAttribute("sitesIDsCities", sitesIDsCities);

        log.info("No. of sites ids cities: " + sitesIDsCities.size());

        return "regions/list";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String show(@PathVariable("id") Long id, Model uiModel) {
        SitesIDsCities siteIDCity = sitesIDsCitiesServices.findById(id);
        uiModel.addAttribute("siteIDCity", siteIDCity);

        return "regions/show";
    }

    @RequestMapping(value = "/{id}", params = "form", method = RequestMethod.POST)
    public String update(@Valid SitesIDsCities siteIDCity, BindingResult bindingResult, Model uiModel,
                         HttpServletRequest httpServletRequest, RedirectAttributes redirectAttributes,
                         Locale locale) {
        log.info("Updating site id city");
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("siteIDCity", siteIDCity);
            uiModel.addAttribute("titleOfPage", "form.edit.siteIDCity");
            return "regions/update";
        }
        uiModel.asMap().clear();
        sitesIDsCitiesServices.save(siteIDCity);
        return "redirect:/regions/" + UrlUtil.encodeUrlPathSegment(siteIDCity.getId().toString(),
                httpServletRequest);
    }

    @RequestMapping(value = "/{id}", params = "form", method = RequestMethod.GET)
    public String updateForm(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("siteIDCity", sitesIDsCitiesServices.findById(id));
        uiModel.addAttribute("titleOfPage", "form.edit.siteIDCity");
        return "regions/update";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String create(@Valid SitesIDsCities siteIDCity, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest, RedirectAttributes redirectAttributes, Locale locale) {
        log.info("Creating siteIDCity");
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("siteIDCity", siteIDCity);
            uiModel.addAttribute("titleOfPage", "form.new.siteIDCity");
            return "regions/update";
        }
        uiModel.asMap().clear();

        log.info("siteIDCity id: " + siteIDCity.getId());

        sitesIDsCitiesServices.save(siteIDCity);
        return "redirect:/regions/";
    }

    @RequestMapping(params = "form", method = RequestMethod.GET)
    public String createForm(Model uiModel) {
        SitesIDsCities siteIDCity = new SitesIDsCities();
        uiModel.addAttribute("siteIDCity", siteIDCity);
        uiModel.addAttribute("titleOfPage", "form.new.siteIDCity");

        return "regions/update";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String delete(@PathVariable("id") Long id) {
        sitesIDsCitiesServices.delete(id);
        return "redirect:/regions/";
    }
}
