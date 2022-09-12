package git.erpBackend.service;

import git.erpBackend.dto.StockItemDto;
import git.erpBackend.entity.StockItem;
import git.erpBackend.repository.StockItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockItemService {

    private StockItemRepository stockItemRepository;

    @Autowired
    public StockItemService(StockItemRepository stockItemRepository){
        this.stockItemRepository = stockItemRepository;
    }

    public List<StockItemDto> getStockItemList() {
        return stockItemRepository.findAll().stream().map(stockItem -> StockItemDto.of(stockItem)).toList();
    }

    @Scheduled(fixedDelay = 10000, initialDelay = 2000)
    private void changePrices(){
        List<StockItem> stockItems = stockItemRepository.findAll();
        stockItems.forEach(stockItem -> {
            stockItem.changePrice();
            stockItemRepository.save(stockItem);
        });
    }
}
