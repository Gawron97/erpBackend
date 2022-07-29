package git.erpBackend.entity;


import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Employee {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idEmployee;
    private String name;
    private String surname;


    @OneToOne(mappedBy = "employee", cascade = CascadeType.ALL)
    private Operator operator;


}
