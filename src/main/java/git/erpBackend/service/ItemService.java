package git.erpBackend.service;

import git.erpBackend.dto.ItemDto;
import git.erpBackend.dto.ItemSumDto;
import git.erpBackend.dto.QuantityTypeDto;
import git.erpBackend.entity.Item;
import git.erpBackend.entity.ItemSum;
import git.erpBackend.entity.QuantityType;
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


    public ItemDto saveItem(ItemDto itemDto){

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

        Optional<ItemSum> itemSumOptional = itemSumRepository.findByNameWithWarehouses(itemDto.getName());
        ItemSum itemSum;
        Item item;
        QuantityType quantityType;

        Optional<QuantityType> quantityTypeOptional = quantityTypeRepository.findById(itemDto.getQuantityTypeDto().getIdQuantityType());
        if(quantityTypeOptional.isEmpty())
            throw new RuntimeException("zle QuantityType");
        else
            quantityType = quantityTypeOptional.get();

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

                item.setQuantityType(quantityType);
                item.setQuantity(itemDto.getQuantity());
                item.setWarehouse(warehouseWithItems);

                itemRepository.save(item);

                itemSum.addWarehouse(warehouseWithItemSums);
            }

        }else {

            item = new Item();
            item.setName(itemDto.getName());

            item.setQuantityType(quantityType);
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
        Optional<Item> optionalItem = itemRepository.findById(idItem);
        if(optionalItem.isEmpty())
            throw new RuntimeException("brak takiego przedmiotu");
        Item item = optionalItem.get();

        Optional<ItemSum> optionalItemSum = itemSumRepository.findByNameWithWarehouses(item.getName());

        if(optionalItemSum.isEmpty())
            throw new RuntimeException("cos poszlo nie tak");

        ItemSum itemSum = optionalItemSum.get();

        Optional<Warehouse> optionalWarehouseWithItems = warehouseRepository.findByNameWithItems(item.getWarehouse().getName());
        Optional<Warehouse> optionalWarehouseWithItemSums = warehouseRepository.findByNameWithItemSums(item.getWarehouse().getName());

        if(optionalWarehouseWithItems.isEmpty() || optionalWarehouseWithItemSums.isEmpty())
            throw new RuntimeException("cos nie tak ze sciaganiem magazynow");

        itemSum.removeWarehouse(optionalWarehouseWithItemSums.get());
        item.removeWarehouse(optionalWarehouseWithItems.get());

        itemSum.setQuantity(itemSum.getQuantity() - item.getQuantity());

        itemRepository.delete(item);

        if(itemSum.getQuantity() == 0)
            itemSumRepository.delete(itemSum);

        return ResponseEntity.ok().build();

    }

    public List<ItemSumDto> getListOfItemSum() {
        return itemSumRepository.findAll().stream().map(itemSum -> ItemSumDto.of(itemSum)).toList();
    }

    public ItemDto getItemById(Integer idItem){
        Optional<Item> optionalItem = itemRepository.findById(idItem);
        if (optionalItem.isEmpty())
            throw new RuntimeException("nie ma takiego itemu");
        return ItemDto.of(optionalItem.get());
    }

}
