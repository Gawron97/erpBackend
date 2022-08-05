package git.erpBackend.controller;

import git.erpBackend.dto.ItemDto;
import git.erpBackend.entity.Item;
import git.erpBackend.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ItemController {

    private final ItemRepository itemRepository;

    @PostMapping("/items")
    public Item newItem(@RequestBody Item item){
        return itemRepository.save(item);
    }

    @GetMapping("/items")
    public List<ItemDto> listItems(){
        List<ItemDto> collect = itemRepository.findAll().stream().map(item -> ItemDto.of(item)).collect(Collectors.toList());
        return collect;
    }

    @DeleteMapping("/items")
    public ResponseEntity deteleItem(@RequestBody Integer idItem){
        itemRepository.deleteById(idItem);
        return ResponseEntity.ok().build();
    }

}
