package git.erpBackend.entity;

import javax.persistence.*;

@Entity
public class StockItem {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idStockItem;
    private String name;
    private double price;


}
