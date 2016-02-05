package ua.chuchvaga.taras.lookingjob.web.propertyeditor;

import ua.chuchvaga.taras.lookingjob.domain.Site;
import ua.chuchvaga.taras.lookingjob.service.SiteService;

import java.beans.PropertyEditorSupport;

public class SitePropertyEditor extends PropertyEditorSupport {

    private SiteService siteService;

    public SitePropertyEditor(SiteService siteService) {
        this.siteService = siteService;
    }

    @Override
    public String getAsText() {

        if (getValue() == null)
            return null;

        return String.valueOf(((Site) super.getValue()).getId());
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        Long id = new Long(text);
        super.setValue(siteService.findById(id));
    }
}
