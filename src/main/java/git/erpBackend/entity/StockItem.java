package git.erpBackend.entity;

import com.github.javafaker.Faker;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

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
    @ElementCollection(fetch = FetchType.EAGER)
    private List<Double> priceHistory;

    public void changePrice(){
        Faker faker = new Faker();
        int downOrUp = downOrUpRate(); // im wyzszy tym mniejsza szansa na wzrost ceny
        int i = faker.number().numberBetween(-100, 116); // od -100 do 100

        double change = price/100;

        if(i > downOrUp)
            price += change;
        else if(i < downOrUp)
            price -= change;


        price = (double) Math.round(price * 100) / 100;

        priceHistory.add(price);
    }

    private int downOrUpRate(){

        if(priceHistory.size() < 50)
            return 50;

        int position = priceHistory.size() - 50;

        Double historicalPrice = priceHistory.get(position);

        double priceChange = (price - historicalPrice) / historicalPrice * 100;

        int rate = ((int) priceChange);

        if(rate < 0)
            rate *= 1.25;

        return rate;

    }

}
