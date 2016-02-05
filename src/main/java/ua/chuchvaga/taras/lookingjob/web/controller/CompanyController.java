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
import ua.chuchvaga.taras.lookingjob.domain.Company;
import ua.chuchvaga.taras.lookingjob.service.CompanyService;
import ua.chuchvaga.taras.lookingjob.web.util.Message;
import ua.chuchvaga.taras.lookingjob.web.util.UrlUtil;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Locale;

@lombok.extern.slf4j.Slf4j
@Controller
@RequestMapping("/companies")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @Autowired
    private MessageSource messageSource;

    @RequestMapping(method = RequestMethod.GET)
    public String list(Model uiModel) {
        log.info("Listing companies");

        List<Company> companies = companyService.findAll();
        uiModel.addAttribute("companies", companies);

        log.info("No. of companies: " + companies.size());

        return "companies/list";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String show(@PathVariable("id") Long id, Model uiModel) {
        Company company = companyService.findById(id);
        uiModel.addAttribute("company", company);

        return "companies/show";
    }

    @RequestMapping(value = "/{id}", params = "form", method = RequestMethod.POST)
    public String update(@Valid Company company, BindingResult bindingResult, Model uiModel,
                         HttpServletRequest httpServletRequest, RedirectAttributes redirectAttributes,
                         Locale locale) {
        log.info("Updating company");
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("message", new Message("error",
                    messageSource.getMessage("company_save_fail", new Object[]{}, locale)));
            uiModel.addAttribute("company", company);
            uiModel.addAttribute("titleOfPage", "form.edit.company");
            return "companies/update";
        }
        uiModel.asMap().clear();
        redirectAttributes.addFlashAttribute("message", new Message("success",
                messageSource.getMessage("company_save_success", new Object[]{}, locale)));
        companyService.save(company);
        return "redirect:/companies/" + UrlUtil.encodeUrlPathSegment(company.getId().toString(),
                httpServletRequest);
    }

    @RequestMapping(value = "/{id}", params = "form", method = RequestMethod.GET)
    public String updateForm(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("company", companyService.findById(id));
        uiModel.addAttribute("titleOfPage", "form.edit.company");
        return "companies/update";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String create(@Valid Company company, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest, RedirectAttributes redirectAttributes, Locale locale) {
        log.info("Creating company");
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("message", new Message("error",
                    messageSource.getMessage("company_save_fail", new Object[]{}, locale)));
            uiModel.addAttribute("company", company);
            uiModel.addAttribute("titleOfPage", "form.new.company");
            return "companies/update";
        }
        uiModel.asMap().clear();
        redirectAttributes.addFlashAttribute("message", new Message("success",
                messageSource.getMessage("company_save_success", new Object[]{}, locale)));

        log.info("Company id: " + company.getId());

        companyService.save(company);
        return "redirect:/companies/";
    }

    @RequestMapping(params = "form", method = RequestMethod.GET)
    public String createForm(Model uiModel) {
        Company company = new Company();
        uiModel.addAttribute("company", company);
        uiModel.addAttribute("titleOfPage", "form.new.company");

        return "companies/update";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String delete(@PathVariable("id") Long id) {
        companyService.delete(id);
        return "redirect:/companies/";
    }
}
