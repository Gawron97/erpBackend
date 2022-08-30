package git.erpBackend.service;

import git.erpBackend.dto.*;
import git.erpBackend.entity.*;
import git.erpBackend.enums.QuantityEnum;
import git.erpBackend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ItemService {

    private ItemRepository itemRepository;
    private WarehouseRepository warehouseRepository;
    private ItemSumRepository itemSumRepository;
    private QuantityTypeRepository quantityTypeRepository;
    private TruckRepository truckRepository;

    public ItemService(){
    }

    @Autowired
    public ItemService(ItemRepository itemRepository, WarehouseRepository warehouseRepository, ItemSumRepository itemSumRepository,
                       QuantityTypeRepository quantityTypeRepository, TruckRepository truckRepository){
        this.itemRepository = itemRepository;
        this.warehouseRepository = warehouseRepository;
        this.itemSumRepository = itemSumRepository;
        this.quantityTypeRepository = quantityTypeRepository;
        this.truckRepository = truckRepository;
    }

    public ItemDto saveItem(ItemDto itemDto){

        Optional<Warehouse> warehouseOptional = warehouseRepository.findByIdWithItems(itemDto.getIdWarehouse());
        Optional<Warehouse> warehouseOptional2 = warehouseRepository.findByIdWithItemSums(itemDto.getIdWarehouse());
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
            List<Item> possibleItem = items.stream().filter(itemFilter ->
                    itemFilter.getName().equalsIgnoreCase(itemDto.getName())).toList();

            if(possibleItem.size() == 1){

                item = possibleItem.get(0);
                item.setQuantity(item.getQuantity() + itemDto.getQuantity());

            }else {

                item = new Item();
                item.setName(itemDto.getName());

                item.setQuantityType(quantityType);
                item.setQuantity(itemDto.getQuantity());
                item.setWarehouse(warehouseWithItems);

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

//    public ResponseEntity deteleItem(Integer idItem){
//        Optional<Item> optionalItem = itemRepository.findById(idItem);
//        if(optionalItem.isEmpty())
//            throw new RuntimeException("brak takiego przedmiotu");
//        Item item = optionalItem.get();
//
//        Optional<ItemSum> optionalItemSum = itemSumRepository.findByNameWithWarehouses(item.getName());
//
//        if(optionalItemSum.isEmpty())
//            throw new RuntimeException("cos poszlo nie tak");
//
//        ItemSum itemSum = optionalItemSum.get();
//
//        Optional<Warehouse> optionalWarehouseWithItems = warehouseRepository.findByNameWithItems(item.getWarehouse().getName());
//        Optional<Warehouse> optionalWarehouseWithItemSums = warehouseRepository.findByNameWithItemSums(item.getWarehouse().getName());
//
//        if(optionalWarehouseWithItems.isEmpty() || optionalWarehouseWithItemSums.isEmpty())
//            throw new RuntimeException("cos nie tak ze sciaganiem magazynow");
//
//        itemSum.removeWarehouse(optionalWarehouseWithItemSums.get());
//        item.removeWarehouse(optionalWarehouseWithItems.get());
//
//        itemSum.setQuantity(itemSum.getQuantity() - item.getQuantity());
//
//        itemRepository.delete(item);
//
//        if(itemSum.getQuantity() == 0)
//            itemSumRepository.delete(itemSum);
//
//        return ResponseEntity.ok().build();
//
//    }

    public List<ItemSumDto> getListOfItemSum() {
        return itemSumRepository.findAll().stream().map(itemSum -> ItemSumDto.of(itemSum)).toList();
    }

    public ItemDto getItemById(Integer idItem){
        Optional<Item> optionalItem = itemRepository.findById(idItem);
        if (optionalItem.isEmpty())
            throw new RuntimeException("nie ma takiego itemu");
        return ItemDto.of(optionalItem.get());
    }

    public TransportDto getTransportDetails(Integer idItem) {

        Optional<Item> optionalItem = itemRepository.findById(idItem);

        if (optionalItem.isEmpty())
            throw new RuntimeException("blad przedmiotu");

        List<Truck> trucks = truckRepository.findAll();

        return TransportDto.of(optionalItem.get(), trucks);

    }

    public ResponseEntity transportItem(TransportItemDto transportItemDto) {

        Optional<Item> optionalItem = itemRepository.findById(transportItemDto.getIdItem());
        if (optionalItem.isEmpty())
            throw new RuntimeException("nie znaleziono itemu o id: " + transportItemDto.getIdItem());

        Item item = optionalItem.get();

        Optional<Warehouse> optionalOldWarehouseItems = warehouseRepository.findByIdWithItems(item.getWarehouse().getIdWarehouse());
        Optional<Warehouse> optionalOldWarehouseItemsSum = warehouseRepository.findByIdWithItemSums(item.getWarehouse().getIdWarehouse());
        Optional<Warehouse> newOptionalWarehouseItems = warehouseRepository.findByIdWithItems(transportItemDto.getNewWarehouseId());
        Optional<Warehouse> newOptionalWarehouseItemsSum = warehouseRepository.findByIdWithItemSums(transportItemDto.getNewWarehouseId());
        if (optionalOldWarehouseItems.isEmpty() || optionalOldWarehouseItemsSum.isEmpty() ||
                newOptionalWarehouseItemsSum.isEmpty() || newOptionalWarehouseItems.isEmpty())
            throw new RuntimeException("blad magazynu");

        Warehouse oldWarehouseItems = optionalOldWarehouseItems.get();
        Warehouse oldWarehouseItemsSum = optionalOldWarehouseItemsSum.get();
        Warehouse newWarehouseItems = newOptionalWarehouseItems.get();
        Warehouse newWarehouseItemsSum = newOptionalWarehouseItemsSum.get();

        Optional<ItemSum> optionalItemSum = itemSumRepository.findByNameWithWarehouses(item.getName());
        //TODO by Name && QuantityType
        if (optionalItemSum.isEmpty())
            throw new RuntimeException("blad ItemSum");

        ItemSum itemSum = optionalItemSum.get();

        //powyzej dane przygotowane do dzialania

        if (transportItemDto.getQuantityToSend() == item.getQuantity()) {
            item.removeWarehouse(oldWarehouseItems);
            itemSum.removeWarehouse(oldWarehouseItemsSum);
            itemRepository.delete(item);

        } else
            item.setQuantity(item.getQuantity() - transportItemDto.getQuantityToSend());

        if(transportItemDto.getTransportationType().equalsIgnoreCase("SELL"))
            return ResponseEntity.ok().build();
        //jesli item sprzedajemy to koniec dzialania

        List<Item> filterItems = newWarehouseItems.getItems().stream().filter(filterItem -> filterItem.getName().equalsIgnoreCase(item.getName())
                && filterItem.getQuantityType().equals(item.getQuantityType())).toList();

        if (filterItems.size() == 1) {
            Item foundItem = filterItems.get(0);
            Optional<Item> optionalExistingItem = itemRepository.findById(foundItem.getIdItem());
            if(optionalExistingItem.isEmpty())
                throw new RuntimeException("blad itemu");

            Item existingItem = optionalExistingItem.get();
            existingItem.setQuantity(existingItem.getQuantity() + transportItemDto.getQuantityToSend());

        } else if (filterItems.size() == 0) {

            Item newItem = new Item();
            newItem.setName(itemSum.getName());
            newItem.setQuantityType(itemSum.getQuantityType());
            newItem.setQuantity(transportItemDto.getQuantityToSend());
            newItem.setWarehouse(newWarehouseItems);
            itemSum.addWarehouse(newWarehouseItemsSum);

            itemRepository.save(newItem);

        } else
            throw new RuntimeException("wiecej niz 1 item o tej samej nazwie w jednym magazynie");

        return ResponseEntity.ok().build();
    }
}
