package git.erpBackend.controller;

import git.erpBackend.dto.ItemDto;
import git.erpBackend.dto.ItemSumDto;
import git.erpBackend.dto.TransportDto;
import git.erpBackend.dto.TransportItemDto;
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

//    @DeleteMapping("/items/{idItem}")
//    public ResponseEntity deteleItem(@PathVariable Integer idItem){
//        return itemService.deteleItem(idItem);
//    }

    @GetMapping("items/{idItem}")
    public ItemDto getItem(@PathVariable Integer idItem){
        return itemService.getItemById(idItem);
    }

    @GetMapping("/items_sum")
    public List<ItemSumDto> getListOfSumOfItems(){
        return itemService.getListOfItemSum();
    }

    @GetMapping("/items_sum/{idItemSum}")
    public ItemSumDto getListOfSumOfItems(@PathVariable Integer idItemSum){
        return itemService.getItemSumById(idItemSum);
    }

    @GetMapping("/transport/{idItem}")
    public TransportDto getTransportDto(@PathVariable Integer idItem){
        return itemService.getTransportDetails(idItem);
    }

    @PostMapping("/transport")
    public ResponseEntity transportItem(@RequestBody TransportItemDto transportItemDto){
        return itemService.transportItem(transportItemDto);
    }

}
