package ua.chuchvaga.taras.lookingjob.web.propertyeditor;

import ua.chuchvaga.taras.lookingjob.domain.City;
import ua.chuchvaga.taras.lookingjob.service.CityService;

import java.beans.PropertyEditorSupport;

public class CityPropertyEditor extends PropertyEditorSupport {

    private CityService cityService;

    public CityPropertyEditor(CityService cityService) {
        this.cityService = cityService;
    }

    @Override
    public String getAsText() {

        if (getValue() == null)
            return null;

        return String.valueOf(((City) super.getValue()).getId());
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        Long id = new Long(text);
        super.setValue(cityService.findById(id));
    }

}
