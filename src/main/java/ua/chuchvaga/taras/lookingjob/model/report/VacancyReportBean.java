package ua.chuchvaga.taras.lookingjob.model.report;

import ua.chuchvaga.taras.lookingjob.model.VacancyModel;

import java.util.List;

/**
 * Created by beaver on 2/2/16.
 */
public class VacancyReportBean {
    private List<VacancyModel> vacancyList;

    public List<VacancyModel> getVacancyList() {
        return vacancyList;
    }

    public void setVacancyList(List<VacancyModel> vacancyList) {
        this.vacancyList = vacancyList;
    }
}
