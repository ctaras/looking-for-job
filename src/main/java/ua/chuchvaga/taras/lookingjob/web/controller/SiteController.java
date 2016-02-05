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
import ua.chuchvaga.taras.lookingjob.domain.Site;
import ua.chuchvaga.taras.lookingjob.service.SiteService;
import ua.chuchvaga.taras.lookingjob.web.util.Message;
import ua.chuchvaga.taras.lookingjob.web.util.UrlUtil;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Locale;

@lombok.extern.slf4j.Slf4j
@Controller
@RequestMapping("/sites")
public class SiteController {

    @Autowired
    private SiteService siteService;

    @Autowired
    private MessageSource messageSource;

    @RequestMapping(method = RequestMethod.GET)
    public String list(Model uiModel) {
        log.info("Listing sites");

        List<Site> sites = siteService.findAll();
        uiModel.addAttribute("sites", sites);

        log.info("No. of sites: " + sites.size());

        return "sites/list";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String show(@PathVariable("id") Long id, Model uiModel) {
        Site site = siteService.findById(id);
        uiModel.addAttribute("site", site);

        return "sites/show";
    }

    @RequestMapping(value = "/{id}", params = "form", method = RequestMethod.POST)
    public String update(@Valid Site site, BindingResult bindingResult, Model uiModel,
                         HttpServletRequest httpServletRequest, RedirectAttributes redirectAttributes,
                         Locale locale) {
        log.info("Updating site");
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("message", new Message("error",
                    messageSource.getMessage("site_save_fail", new Object[]{}, locale)));
            uiModel.addAttribute("site", site);
            uiModel.addAttribute("titleOfPage", "form.edit.site");
            return "sites/update";
        }
        uiModel.asMap().clear();
        redirectAttributes.addFlashAttribute("message", new Message("success",
                messageSource.getMessage("site_save_success", new Object[]{}, locale)));
        siteService.save(site);
        return "redirect:/sites/" + UrlUtil.encodeUrlPathSegment(site.getId().toString(),
                httpServletRequest);
    }

    @RequestMapping(value = "/{id}", params = "form", method = RequestMethod.GET)
    public String updateForm(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("site", siteService.findById(id));
        uiModel.addAttribute("titleOfPage", "form.edit.site");
        return "sites/update";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String create(@Valid Site site, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest, RedirectAttributes redirectAttributes, Locale locale) {
        log.info("Creating site");
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("message", new Message("error",
                    messageSource.getMessage("site_save_fail", new Object[]{}, locale)));
            uiModel.addAttribute("site", site);
            uiModel.addAttribute("titleOfPage", "form.new.site");
            return "sites/update";
        }
        uiModel.asMap().clear();
        redirectAttributes.addFlashAttribute("message", new Message("success",
                messageSource.getMessage("site_save_success", new Object[]{}, locale)));

        log.info("Site id: " + site.getId());

        siteService.save(site);
        return "redirect:/sites/";
    }

    @RequestMapping(params = "form", method = RequestMethod.GET)
    public String createForm(Model uiModel) {
        Site site = new Site();
        uiModel.addAttribute("site", site);
        uiModel.addAttribute("titleOfPage", "form.new.site");

        return "sites/update";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String delete(@PathVariable("id") Long id) {
        siteService.delete(id);
        return "redirect:/sites/";
    }
}
