package ua.chuchvaga.taras.lookingjob.domain;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

@lombok.Getter
@lombok.Setter
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"id", "version"})
@ToString(exclude = {"id", "version"})
@Entity
@Table(name = "VACANCY")
public class Vacancy implements Serializable {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Version
    @Column(name = "VERSION")
    private int version;

    @NotBlank(message = "{object.caption.notblank}")
    @Size(min = 2, max = 255, message = "{vacancy.caption.size}")
    @Column(name = "CAPTION", nullable = false)
    private String caption;

    @NotBlank(message = "{object.url.notblank}")
    @Size(min = 2, max = 2000, message = "{vacancy.url.size}")
    @Column(name = "URL", nullable = false)
    private String url;

    @NotNull(message = "{vacancy.pricemin.notnull}")
    @Column(name = "PRICE_MIN")
    private int priceMin;

    @NotNull(message = "{vacancy.pricemax.notnull}")
    @Column(name = "PRICE_MAX")
    private int priceMax;

    @NotNull(message = "{vacancy.date.notnull}")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Temporal(TemporalType.DATE)
    @Column(name = "REG_DATE")
    private Date registrationDate;

    @NotNull(message = "{vacancy.site.notnull}")
    @ManyToOne
    @JoinColumn(name = "SITE_ID", nullable = false, referencedColumnName = "ID")
    private Site site;

    @NotNull(message = "{vacancy.company.notnull}")
    @ManyToOne
    @JoinColumn(name = "COMPANY_ID", nullable = false, referencedColumnName = "ID")
    private Company company;

    @NotNull(message = "{vacancy.city.notnull}")
    @ManyToOne
    @JoinColumn(name = "CITY_ID", nullable = false, referencedColumnName = "ID")
    private City city;

    @NotNull(message = "{vacancy.currency.notnull}")
    @ManyToOne
    @JoinColumn(name = "CURRENCY_ID", nullable = false, referencedColumnName = "ID")
    private Currency currency;
}
