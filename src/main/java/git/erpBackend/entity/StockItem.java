package git.erpBackend.entity;

import com.github.javafaker.Faker;
import com.google.common.math.DoubleMath;
import git.erpBackend.util.DoubleRound;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.scheduling.annotation.Scheduled;

import javax.persistence.*;
import java.math.RoundingMode;
import java.text.DecimalFormat;

@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class StockItem {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idStockItem;
    private String name;
    private double price;

    @ManyToOne
    private QuantityType quantityType;

    public void changePrice(){
        Faker faker = new Faker();
        int downOrUp = 50;
        int i = faker.number().numberBetween(1, 101); // od 1 do 100

        double change = price/100;

        if(i > downOrUp)
            price += change;
        else if(i < downOrUp)
            price -= change;

        price = DoubleRound.round2Places(price);

    }


}
