package ua.chuchvaga.taras.lookingjob.web.controller;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ua.chuchvaga.taras.lookingjob.model.report.VacancyReportBean;
import ua.chuchvaga.taras.lookingjob.service.VacancyModelService;
import ua.chuchvaga.taras.lookingjob.service.VacancyService;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/reports")
public class ReportController {

    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    private DataSource datasource;
    @Autowired
    private VacancyService vacancyService;
    @Autowired
    private VacancyModelService vacancyModelService;

    @RequestMapping(value = "/sample/{format}", method = RequestMethod.GET)
    public String sampleReport(@PathVariable("format") String format, Model uiModel
            , @RequestParam(value = "is_ignore_pagination", required = false, defaultValue = "true") boolean isIgnorePagination) {

        uiModel.addAttribute("datasource", getDataSource());

        uiModel.addAttribute("IS_IGNORE_PAGINATION", isIgnorePagination);

        String[] formats = {"html", "pdf", "xls", "csv", "odt", "rtf"};
        if (!Arrays.asList(formats).contains(format))
            format = formats[0];

        uiModel.addAttribute("format", format);

        return "reports/Vacancy_A4_Landscape_Table_Based";
    }

    private JRDataSource getDataSource() {
        VacancyReportBean vacancyReportBean = new VacancyReportBean();
        vacancyReportBean.setVacancyList(vacancyModelService.getListModel(vacancyService.findAll()));

        List<VacancyReportBean> reportDataSource = new ArrayList<>();
        reportDataSource.add(vacancyReportBean);

        return new JRBeanCollectionDataSource(reportDataSource);
    }

    @RequestMapping(value = "/export", method = RequestMethod.GET)
    public String vacancyExport(Model uiModel) {
        uiModel.addAttribute("datasource", getDataSource());
        uiModel.addAttribute("IS_IGNORE_PAGINATION", true);
        uiModel.addAttribute("format", "xls");

        return "reports/Vacancy_Export_A4_Landscape_Table_Based";
    }

    @RequestMapping(value = "/top", method = RequestMethod.GET)
    public String cross(Model uiModel) throws SQLException {

        uiModel.addAttribute("REPORT_CONNECTION", jdbcTemplate.getDataSource());
        uiModel.addAttribute("IS_IGNORE_PAGINATION", true);
        uiModel.addAttribute("format", "html");

        return "reports/TOP_Vacancy_AND_Company";
    }

//    @RequestMapping(value = "/export2", method = RequestMethod.GET)
//    @ResponseBody
//    public void export2(HttpServletResponse response) throws JRException, IOException {
//        InputStream jasperStream = this.getClass().getResourceAsStream("/reports/Vacancy_Export_A4_Landscape_Table_Based.jasper");
//        System.out.println("jasperStream = ");
//        System.out.println(jasperStream);
//        Map<String,Object> params = new HashMap<>();
//        params.put("IS_IGNORE_PAGINATION", true);
//        params.put("format", "xls");
//
//        JasperReport jasperReport = (JasperReport) JRLoader.loadObject(jasperStream);
//
//        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, getDataSource());
//
//        response.setContentType("application/xls");
//        response.setHeader("Content-disposition", "inline; filename=vacancies.xls");
//
//        final OutputStream outStream = response.getOutputStream();
//        JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);
//    }

    @RequestMapping(value = "/analysis/company", method = RequestMethod.GET)
    public String analysisCompany() throws SQLException {
        return "analysis-company";
    }

    @RequestMapping(value = "/analysis/vacancy", method = RequestMethod.GET)
    public String analysisVacancy() throws SQLException {
        return "analysis-vacancy";
    }
}
