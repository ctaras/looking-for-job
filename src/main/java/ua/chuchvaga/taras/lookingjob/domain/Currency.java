package ua.chuchvaga.taras.lookingjob.domain;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;

import static javax.persistence.GenerationType.IDENTITY;

@lombok.Getter
@lombok.Setter
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"id", "version"})
@ToString(exclude = {"id", "version"})
@Entity
@Table(name = "currency")
public class Currency implements Serializable {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Version
    @Column(name = "VERSION")
    private int version;

    @NotBlank(message = "{object.caption.notblank}")
    @Size(min = 3, max = 10, message = "{currency.caption.size}")
    @Column(name = "CAPTION", nullable = false, unique = true)
    private String caption;

    @NotBlank(message = "{object.caption.notblank}")
    @Column(name = "RATE", nullable = false, columnDefinition = "Decimal(10,6) default '1.00'")
    private double rate;
}
