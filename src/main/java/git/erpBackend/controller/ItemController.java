package git.erpBackend.controller;

import git.erpBackend.dto.*;
import git.erpBackend.service.ItemService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "user_password")
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;

    @PostMapping("add")
    public ItemDto addItem(@RequestBody ItemDto item) {
        return itemService.addItem(item);
    }

    @PostMapping("edit/{itemId}")
    public ItemDto editItem(@RequestBody ItemDto item, @PathVariable Integer itemId) {
        return itemService.editItem(item, itemId);
    }

    @PostMapping("/transport")
    public ItemDto transportItem(@RequestBody TransportItemDto transportItem) {
        return itemService.transportItem(transportItem);
    }

    @PostMapping("/sell")
    public ItemDto sellItem(@RequestBody SellItemDto sellItem) {
        return itemService.sellItem(sellItem);
    }

    @GetMapping
    public List<ItemDto> getListOfItems(){
        return itemService.getListOfItems();
    }

    @GetMapping("{idItem}")
    public ItemDto getItem(@PathVariable Integer idItem){
        return itemService.getItem(idItem);
    }

    @GetMapping("/items_sum")
    public List<ItemSumDto> getListOfSumOfItems(){
        return itemService.getListOfItemSum();
    }

    @GetMapping("/items_sum/{idItemSum}")
    public ItemSumDto GetItemSum(@PathVariable Integer idItemSum){
        return itemService.getItemSum(idItemSum);
    }

    @GetMapping("/transport/{idItem}")
    public TransportDto getTransportDto(@PathVariable Integer idItem){
        return itemService.getTransportDetails(idItem);
    }

}
