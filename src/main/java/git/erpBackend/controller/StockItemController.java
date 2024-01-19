package git.erpBackend.controller;

import git.erpBackend.dto.StockItemDto;
import git.erpBackend.service.StockItemService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
public class StockItemController {

    @Autowired
    private StockItemService stockItemService;

    @GetMapping("/stock_item")
    public List<StockItemDto> getStockItemList(){
        return stockItemService.getStockItemList();
    }

}
