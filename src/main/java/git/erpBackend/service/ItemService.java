package git.erpBackend.service;

import git.erpBackend.dto.ItemDto;
import git.erpBackend.dto.ItemSumDto;
import git.erpBackend.entity.Item;
import git.erpBackend.entity.ItemSum;
import git.erpBackend.entity.Warehouse;
import git.erpBackend.enums.QuantityEnum;
import git.erpBackend.repository.ItemRepository;
import git.erpBackend.repository.ItemSumRepository;
import git.erpBackend.repository.QuantityTypeRepository;
import git.erpBackend.repository.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ItemService {

    private final ItemRepository itemRepository;
    private final WarehouseRepository warehouseRepository;
    private final ItemSumRepository itemSumRepository;
    private final QuantityTypeRepository quantityTypeRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository, WarehouseRepository warehouseRepository, ItemSumRepository itemSumRepository,
                       QuantityTypeRepository quantityTypeRepository){
        this.itemRepository = itemRepository;
        this.warehouseRepository = warehouseRepository;
        this.itemSumRepository = itemSumRepository;
        this.quantityTypeRepository = quantityTypeRepository;
    }


    public ItemDto saveOrUpdateItem(ItemDto itemDto){

        Optional<Warehouse> warehouseOptional = warehouseRepository.findByNameWithItems(itemDto.getWarehouseName());
        Optional<Warehouse> warehouseOptional2 = warehouseRepository.findByNameWithItemSums(itemDto.getWarehouseName());
        Warehouse warehouseWithItems;
        Warehouse warehouseWithItemSums;

        if(warehouseOptional.isPresent()){
            warehouseWithItems = warehouseOptional.get();
        }else{
            throw new RuntimeException("nie ma takiego magazynu");
        }

        if(warehouseOptional2.isPresent()){
            warehouseWithItemSums = warehouseOptional2.get();
        }else{
            throw new RuntimeException("nie ma takiego magazynu");
        }

        QuantityEnum quantityEnum;


        if(itemDto.getQuantityType().equalsIgnoreCase("KILOGRAMS"))
            quantityEnum = QuantityEnum.KILOGRAMS;
        else
            quantityEnum = QuantityEnum.UNIT;

        Optional<ItemSum> itemSumOptional = itemSumRepository.findByNameWithWarehouses(itemDto.getName());
        ItemSum itemSum;
        Item item;

        if(itemSumOptional.isPresent()){
            itemSum = itemSumOptional.get();
            itemSum.setQuantity(itemSum.getQuantity() + itemDto.getQuantity());

            List<Item> items = warehouseWithItems.getItems();
            List<Item> possibleItem = items.stream().filter(itemFilter -> itemFilter.getName().equals(itemDto.getName())).toList();

            if(possibleItem.size() == 1){

                item = possibleItem.get(0);
                item.setQuantity(item.getQuantity() + itemDto.getQuantity());

            }else {

                item = new Item();
                item.setName(itemDto.getName());

                item.setQuantityType(quantityTypeRepository.findByQuantityType(quantityEnum));
                item.setQuantity(itemDto.getQuantity());
                item.setWarehouse(warehouseWithItems);

                itemRepository.save(item);

                itemSum.addWarehouse(warehouseWithItemSums);
            }

        }else {

            item = new Item();
            item.setName(itemDto.getName());

            item.setQuantityType(quantityTypeRepository.findByQuantityType(quantityEnum));
            item.setQuantity(itemDto.getQuantity());
            item.setWarehouse(warehouseWithItems);

            itemSum = new ItemSum();
            itemSum.setName(item.getName());
            itemSum.setQuantityType(item.getQuantityType());
            itemSum.setQuantity(item.getQuantity());
            itemSum.addWarehouse(warehouseWithItemSums);

        }

        itemRepository.save(item);
        itemSumRepository.save(itemSum);


        return itemDto;
    }

    public List<ItemDto> getListOfItems(){
        List<ItemDto> collect = itemRepository.findAll().stream().map(item -> ItemDto.of(item)).collect(Collectors.toList());
        return collect;
    }

    public ResponseEntity deteleItem(Integer idItem){
        itemRepository.deleteById(idItem);
        return ResponseEntity.ok().build();
    }

    public List<ItemSumDto> getListOfItemSum() {
        return itemSumRepository.findAll().stream().map(itemSum -> ItemSumDto.of(itemSum)).toList();
    }
}
