package git.erpBackend.controller;

import git.erpBackend.dto.ItemDto;
import git.erpBackend.dto.ItemSumDto;
import git.erpBackend.repository.ItemRepository;
import git.erpBackend.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @PostMapping("/items")
    public ItemDto newItem(@RequestBody ItemDto item){
        return itemService.saveItem(item);
    }

    @GetMapping("/items")
    public List<ItemDto> getListOfItems(){
        return itemService.getListOfItems();
    }

    @DeleteMapping("/items")
    public ResponseEntity deteleItem(@RequestBody Integer idItem){
        return itemService.deteleItem(idItem);
    }

    @GetMapping("/items_sum")
    public List<ItemSumDto> getListOfSumOfItems(){
        return itemService.getListOfItemSum();
    }

}
