package ua.chuchvaga.taras.lookingjob.model;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class VacancyDataList implements Serializable {
    private List<VacancyModel> listModel;
}
