package git.erpBackend.controller;

import git.erpBackend.entity.Item;
import git.erpBackend.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ItemController {

    private final ItemRepository itemRepository;

    @PostMapping("/item")
    Item newItem(@RequestBody Item item){
        return itemRepository.save(item);
    }

    @GetMapping("/item")
    List<Item> listItems(){
        return itemRepository.findAll();
    }

    @DeleteMapping("/item")
    ResponseEntity deteleItem(@RequestBody Integer idItem){
        itemRepository.deleteById(idItem);
        return ResponseEntity.ok().build();
    }

}
