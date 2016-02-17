package ua.chuchvaga.taras.lookingjob.domain;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

import static javax.persistence.GenerationType.IDENTITY;

@lombok.Getter
@lombok.Setter
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"id", "version"})
@ToString(exclude = {"id", "version"})
@Entity
@Table(name = "VIEWED_VACANCY")
@NamedQueries({
        @NamedQuery(name = "ViewedVacancy.findByUrlIn",
                query = "select v from ViewedVacancy v where v.url IN :urls")})
public class ViewedVacancy implements Serializable {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Version
    @Column(name = "VERSION")
    private int version;

    @NotBlank(message = "{object.url.notblank}")
    @Size(min = 2, max = 2000, message = "{vacancy.url.size}")
    @Column(name = "URL", nullable = false)
    private String url;

    @NotNull(message = "{object.url.notnull}")
    @Column(name = "VIEWED", columnDefinition = "Boolean default TRUE")
    private boolean viewed;
}
