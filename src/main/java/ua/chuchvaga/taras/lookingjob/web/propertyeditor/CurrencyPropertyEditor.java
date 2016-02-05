package ua.chuchvaga.taras.lookingjob.web.propertyeditor;

import ua.chuchvaga.taras.lookingjob.domain.Currency;
import ua.chuchvaga.taras.lookingjob.service.CurrencyService;

import java.beans.PropertyEditorSupport;

public class CurrencyPropertyEditor extends PropertyEditorSupport {

    private CurrencyService currencyService;

    public CurrencyPropertyEditor(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @Override
    public String getAsText() {

        if (getValue() == null)
            return null;

        return String.valueOf(((Currency) super.getValue()).getId());
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        Long id = new Long(text);
        super.setValue(currencyService.findById(id));
    }

}
