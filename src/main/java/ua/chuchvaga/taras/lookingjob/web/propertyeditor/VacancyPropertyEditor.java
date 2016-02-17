package ua.chuchvaga.taras.lookingjob.web.propertyeditor;

import ua.chuchvaga.taras.lookingjob.domain.Vacancy;
import ua.chuchvaga.taras.lookingjob.service.VacancyService;

import java.beans.PropertyEditorSupport;

public class VacancyPropertyEditor extends PropertyEditorSupport {

    private VacancyService vacancyService;

    public VacancyPropertyEditor(VacancyService vacancyService) {
        this.vacancyService = vacancyService;
    }

    @Override
    public String getAsText() {

        if (getValue() == null)
            return null;

        return ((Vacancy) super.getValue()).getId();
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        String id = text;
        super.setValue(vacancyService.findById(id));
    }
}
