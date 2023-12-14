package git.erpBackend.service;

import git.erpBackend.dto.*;
import git.erpBackend.entity.*;
import git.erpBackend.repository.*;
import git.erpBackend.utils.exception.item.ItemNotFoundException;
import git.erpBackend.utils.exception.item.ItemSumNotFoundException;
import git.erpBackend.utils.exception.item.NotEnoughItemQuantityException;
import git.erpBackend.utils.exception.quantityType.QuantityTypeNotFound;
import git.erpBackend.utils.exception.truck.TruckCapacityException;
import git.erpBackend.utils.exception.truck.TruckNotFoundException;
import git.erpBackend.utils.exception.warehouse.WarehouseNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final WarehouseRepository warehouseRepository;
    private final ItemSumRepository itemSumRepository;
    private final QuantityTypeRepository quantityTypeRepository;
    private final TruckRepository truckRepository;

    private Warehouse getWarehouseById(Integer id) {
        return warehouseRepository.findById(id).orElseThrow(WarehouseNotFoundException::new);
    }

    private QuantityType getQuantityTypeById(Integer id) {
        return quantityTypeRepository.findById(id).orElseThrow(QuantityTypeNotFound::new);
    }

    private ItemSum getItemSumByName(String name) {
        return itemSumRepository.findByName(name).orElseThrow(ItemSumNotFoundException::new);
    }

    private Item getItemById(Integer id) {
        return itemRepository.findById(id).orElseThrow(ItemNotFoundException::new);
    }

    private void validateIfItemExistsInWarehouse(Item item, Warehouse warehouse) {
        if(!item.getWarehouse().equals(warehouse)) {
            throw new RuntimeException();
        }
    }

    public ItemDto editItem(ItemDto itemDto, Integer itemId) {

        Warehouse warehouse = getWarehouseById(itemDto.getIdWarehouse());
        Item item = getItemById(itemId);
        validateIfItemExistsInWarehouse(item, warehouse);
        ItemSum itemSum = getItemSumByName(item.getName());
        itemSum.setQuantity(itemSum.getQuantity() + itemDto.getQuantity() - item.getQuantity());

        item.setQuantity(itemDto.getQuantity());
        item.setPrice(itemDto.getPrice());

        Item savedItem = itemRepository.save(item);
        itemSumRepository.save(itemSum);
        return ItemDto.of(savedItem);
    }

    public ItemDto addItem(ItemDto itemDto){

        Warehouse warehouse = getWarehouseById(itemDto.getIdWarehouse());
        QuantityType quantityType = getQuantityTypeById(itemDto.getQuantityTypeDto().getIdQuantityType());

        Optional<ItemSum> itemSumOptional = itemSumRepository.findByName(itemDto.getName());
        Item item;

        if(itemSumOptional.isPresent()) {
            item = handleExistingItem(itemDto, warehouse, itemSumOptional.get(), quantityType);
        }else {
            item = handleNewItem(itemDto, warehouse, quantityType);
        }
        Item savedItem = itemRepository.save(item);
        return ItemDto.of(savedItem);
    }

    private Item handleNewItem(ItemDto itemDto, Warehouse warehouse, QuantityType quantityType) {
        Item item = createNewItemToWarehouse(itemDto, warehouse, quantityType);
        warehouse.addItem(item);

        ItemSum itemSum = createNewItemSum(item);
        itemSum.addWarehouse(item.getWarehouse());

        itemSumRepository.save(itemSum);
        return item;
    }

    private ItemSum createNewItemSum(Item item) {
        return ItemSum.builder()
                .name(item.getName())
                .quantity(item.getQuantity())
                .quantityType(item.getQuantityType())
                .warehouses(new ArrayList<>())
                .build();
    }

    private Item handleExistingItem(ItemDto itemDto, Warehouse warehouse, ItemSum itemSum, QuantityType quantityType) {
        Optional<Item> optionalItem = itemRepository.findItemByNameAndWarehouse(itemDto.getName(), warehouse);
        Item item;
        if(optionalItem.isPresent()) {
            item = updateExistingItemInWarehouse(itemDto, optionalItem.get());
        } else {
            item = createNewItemToWarehouse(itemDto, warehouse, quantityType);
            warehouse.addItem(item);
            itemSum.addWarehouse(warehouse);
        }

        itemSum.setQuantity(itemSum.getQuantity() + itemDto.getQuantity());
        itemSumRepository.save(itemSum);
        return item;
    }

    private Item createNewItemToWarehouse(ItemDto itemDto, Warehouse warehouse, QuantityType quantityType) {

        return Item.builder()
                .name(itemDto.getName())
                .quantityType(quantityType)
                .quantity(itemDto.getQuantity())
                .price(itemDto.getPrice())
                .warehouse(warehouse)
                .build();
    }

    private Item updateExistingItemInWarehouse(ItemDto itemDto, Item item) {
        double averagePrice = calculateAveragePrice(item.getQuantity(), item.getPrice(),
                itemDto.getQuantity(), itemDto.getPrice());
        item.setQuantity(item.getQuantity() + itemDto.getQuantity());
        item.setPrice(averagePrice);
        return item;
    }

    private double calculateAveragePrice(double beforeItemQuantity, double beforeItemPrice,
                                         double purchaseItemQuantity, double purchaseItemPrice) {

        double priceForAllBefore = beforeItemQuantity * beforeItemPrice;
        double priceForNewPurchase = purchaseItemQuantity * purchaseItemPrice;

        double averagePrice = (priceForAllBefore + priceForNewPurchase) / (beforeItemQuantity + purchaseItemQuantity);
        return (double) Math.round(averagePrice * 100) / 100;
    }

    public List<ItemDto> getListOfItems(){
        List<ItemDto> collect = itemRepository.findAll().stream().map(item -> ItemDto.of(item)).collect(Collectors.toList());
        return collect;
    }

    public List<ItemSumDto> getListOfItemSum() {
        return itemSumRepository.findAll().stream().map(itemSum -> ItemSumDto.of(itemSum)).toList();
    }

    public ItemDto getItem(Integer idItem){
        Optional<Item> optionalItem = itemRepository.findById(idItem);
        if (optionalItem.isEmpty())
            throw new ItemNotFoundException();
        return ItemDto.of(optionalItem.get());
    }

    public TransportDto getTransportDetails(Integer idItem) {

        Optional<Item> optionalItem = itemRepository.findById(idItem);

        if (optionalItem.isEmpty())
            throw new ItemNotFoundException();

        List<Truck> trucks = truckRepository.findAll();

        return TransportDto.of(optionalItem.get(), trucks);

    }

    private void validateIfItIsEnoughItemInWarehouse(double quantityToSend, Item item) {
        if(quantityToSend > item.getQuantity()) {
            throw new NotEnoughItemQuantityException();
        }
    }

    public ItemDto transportItem(TransportItemDto transportItem) {

        Item item = getItemById(transportItem.getIdItem());
        Warehouse oldWarehouse = getWarehouseById(transportItem.getOldWarehouseId());
        validateIfItemExistsInWarehouse(item, oldWarehouse);
        validateIfItIsEnoughItemInWarehouse(transportItem.getQuantityToSend(), item);

        Warehouse newWarehouse = getWarehouseById(transportItem.getNewWarehouseId());

        ItemDto itemToTransport = ItemDto.of(item);
        if(item.getQuantity() == transportItem.getQuantityToSend()) {
            itemRepository.delete(item);
        } else {
            item.setQuantity(item.getQuantity() - transportItem.getQuantityToSend());
        }

        itemToTransport.setQuantity(transportItem.getQuantityToSend());
        itemToTransport.setIdWarehouse(newWarehouse.getIdWarehouse());
        return addItem(itemToTransport);

    }

    public ItemDto sellItem(SellItemDto sellItem) {

        Item item = getItemById(sellItem.getIdItem());
        Warehouse warehouse = getWarehouseById(sellItem.getOldWarehouseId());
        validateIfItemExistsInWarehouse(item, warehouse);
        validateIfItIsEnoughItemInWarehouse(sellItem.getQuantityToSell(), item);

        if(sellItem.getQuantityToSell() == item.getQuantity()) {
            return deleteItem(item, warehouse.getIdWarehouse());
        } else {
            item.setQuantity(item.getQuantity() - sellItem.getQuantityToSell());
            itemRepository.save(item);
            return ItemDto.of(item);
        }

    }

    private ItemDto deleteItem(Item item, Integer warehouseId) {
        Warehouse warehouse = getWarehouseById(warehouseId);
        ItemSum itemSum = getItemSumByName(item.getName());
        item.removeWarehouse();
        itemSum.removeWarehouse(warehouse);
        warehouse.removeItemSum(itemSum);
        warehouse.removeItem(item);
        itemRepository.delete(item);
        return ItemDto.of(item);
    }


    private ResponseEntity checkTruckCapacity(TransportItemDto transportItemDto){

        Optional<Truck> optionalTruck = truckRepository.findById(transportItemDto.getIdTruck());
        if(optionalTruck.isEmpty())
            throw new TruckNotFoundException();
        Truck truck = optionalTruck.get();

        if(transportItemDto.getQuantityToSend() > truck.getCapacity()) {
            throw new TruckCapacityException();
        }
        return ResponseEntity.ok().build();

    }

    public ItemSumDto getItemSumById(Integer idItemSum) {
        Optional<ItemSum> optionalItemSum = itemSumRepository.findById(idItemSum);
        if(optionalItemSum.isEmpty())
            throw new ItemSumNotFoundException();

        return ItemSumDto.of(optionalItemSum.get());
    }
}
