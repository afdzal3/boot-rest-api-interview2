package posmy.interview.boot.domain;

import lombok.Data;
import lombok.ToString;
import javax.persistence.*;

@Entity
@Table(name = "books")
@Data
@ToString
public class Books {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String status;

    @ManyToOne
    private Users users;
}
