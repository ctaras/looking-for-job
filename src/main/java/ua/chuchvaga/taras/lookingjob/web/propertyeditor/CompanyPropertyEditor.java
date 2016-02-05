package ua.chuchvaga.taras.lookingjob.web.propertyeditor;

import ua.chuchvaga.taras.lookingjob.domain.Company;
import ua.chuchvaga.taras.lookingjob.service.CompanyService;

import java.beans.PropertyEditorSupport;

public class CompanyPropertyEditor extends PropertyEditorSupport {

    private CompanyService companyService;

    public CompanyPropertyEditor(CompanyService companyService) {
        this.companyService = companyService;
    }

    @Override
    public String getAsText() {

        if (getValue() == null)
            return null;

        return String.valueOf(((Company) super.getValue()).getId());
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        Long id = new Long(text);
        super.setValue(companyService.findById(id));
    }
}
