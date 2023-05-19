package git.erpBackend.service;

import git.erpBackend.dto.ItemDto;
import git.erpBackend.dto.ItemSumDto;
import git.erpBackend.dto.TransportDto;
import git.erpBackend.dto.TransportItemDto;
import git.erpBackend.entity.*;
import git.erpBackend.repository.*;
import git.erpBackend.utils.exception.item.DuplicateItemException;
import git.erpBackend.utils.exception.item.ItemNotFoundException;
import git.erpBackend.utils.exception.item.ItemSumNotFoundException;
import git.erpBackend.utils.exception.item.NotEnoughItemQuantityException;
import git.erpBackend.utils.exception.quantityType.QuantityTypeNotFound;
import git.erpBackend.utils.exception.truck.TruckCapacityException;
import git.erpBackend.utils.exception.truck.TruckNotFoundException;
import git.erpBackend.utils.exception.warehouse.IdWarehouseMissingException;
import git.erpBackend.utils.exception.warehouse.WarehouseNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
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

        Warehouse warehouse = warehouseRepository.findById(itemDto.getIdWarehouse()).orElseThrow(() ->
                new WarehouseNotFoundException());

        Optional<ItemSum> itemSumOptional = itemSumRepository.findByName(itemDto.getName());
        ItemSum itemSum;
        Item item;

        QuantityType quantityType = quantityTypeRepository.findById(itemDto.getQuantityTypeDto().getIdQuantityType())
                .orElseThrow(() -> new QuantityTypeNotFound());

        //koniec inicjalizacji zmiennych
        //czesc wykonawcza

        if(itemSumOptional.isPresent()) {
            itemSum = itemSumOptional.get();
            itemSum.setQuantity(itemSum.getQuantity() + itemDto.getQuantity());

            List<Item> items = warehouse.getItems();
            List<Item> possibleItem = items.stream().filter(itemFilter ->
                    itemFilter.getName().equalsIgnoreCase(itemDto.getName())).toList();

            if(possibleItem.size() == 1) {//przypadek gdzie przedmiot w magazynie juz byl

                item = possibleItem.get(0);
                double averagePrice = calculateAveragePrice(item.getQuantity(), item.getPrice(),
                        itemDto.getQuantity(), itemDto.getPrice());

                item.setQuantity(item.getQuantity() + itemDto.getQuantity());
                item.setPrice(averagePrice);

            }else {//pzypadek gdzie przedmiotu w magazynie nie bylo

                item = new Item();
                item.setName(itemDto.getName());

                item.setQuantityType(quantityType);
                item.setQuantity(itemDto.getQuantity());
                item.setPrice(itemDto.getPrice());
                item.setWarehouse(warehouse);

                itemSum.addWarehouse(warehouse);
            }

        }else { // przypadek gdzie przedmiot nie wystepowal w zadnym magazynie

            item = new Item();
            item.setName(itemDto.getName());

            item.setQuantityType(quantityType);
            item.setQuantity(itemDto.getQuantity());
            item.setPrice(itemDto.getPrice());
            item.setWarehouse(warehouse);

            itemSum = new ItemSum();
            itemSum.setName(item.getName());
            itemSum.setQuantityType(item.getQuantityType());
            itemSum.setQuantity(item.getQuantity());
            itemSum.addWarehouse(warehouse);

        }

        itemRepository.save(item);
        itemSumRepository.save(itemSum);


        return itemDto;
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

    public ItemDto getItemById(Integer idItem){
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

    public ResponseEntity transportItem(TransportItemDto transportItemDto) {

//        ResponseEntity checkTruck = checkTruckCapacity(transportItemDto);
//        if(!checkTruck.equals(HttpStatus.OK))
//            return checkTruck;
        //TODO odblokowac slasze

        Warehouse newWarehouse = null;

        if(transportItemDto.getTransportationType().equalsIgnoreCase("TRANSPORT_TO_WAREHOUSE")) {
            Integer idNewWarehouse = transportItemDto.getNewWarehouseId().orElseThrow(() -> {
                throw new IdWarehouseMissingException();
            });
            Optional<Warehouse> optionalNewWarehouse = warehouseRepository.findById(idNewWarehouse);
            newWarehouse = optionalNewWarehouse.orElseThrow(() -> {
                throw new WarehouseNotFoundException();
            });

        }

        Optional<Item> optionalItem = itemRepository.findById(transportItemDto.getIdItem());
        Item item = optionalItem.orElseThrow(() -> {
            throw new ItemNotFoundException();
        });

        Optional<Warehouse> optionalOldWarehouse = warehouseRepository.findById(item.getWarehouse().getIdWarehouse());
        Warehouse oldWarehouse = optionalOldWarehouse.orElseThrow(() -> {
            throw new WarehouseNotFoundException();
        });

        Optional<ItemSum> optionalItemSum = itemSumRepository.findByName(item.getName());//TODO by Name && QuantityType
        ItemSum itemSum = optionalItemSum.orElseThrow(() -> {
            throw new ItemNotFoundException();
        });

        if(transportItemDto.getQuantityToSend() > item.getQuantity()) {
            throw new NotEnoughItemQuantityException();
        }

        //powyzej dane przygotowane do dzialania

        if (transportItemDto.getQuantityToSend() == item.getQuantity()) {
            item.removeWarehouse();
            itemSum.removeWarehouse(oldWarehouse);
            oldWarehouse.removeItemSum(itemSum);
            oldWarehouse.removeItem(item);
            itemRepository.delete(item);

        } else {
            item.setQuantity(item.getQuantity() - transportItemDto.getQuantityToSend());
        }

        if(transportItemDto.getTransportationType().equalsIgnoreCase("SELL")) {
            itemSum.setQuantity(itemSum.getQuantity() - transportItemDto.getQuantityToSend());
            if(itemSum.getQuantity() == 0) {
                itemSumRepository.delete(itemSum);
            }
            return ResponseEntity.ok().build();
        }
        //jesli item sprzedajemy to koniec dzialania

        List<Item> filterItems = newWarehouse.getItems().stream().filter(filterItem ->
                filterItem.getName().equalsIgnoreCase(item.getName())
                && filterItem.getQuantityType().equals(item.getQuantityType())).toList();


        if (filterItems.size() == 1) { //jesli item juz byl w magazynie
            Item existingItem = filterItems.get(0);

            double averagePrice = calculateAveragePrice(existingItem.getQuantity(), existingItem.getPrice(),
                    transportItemDto.getQuantityToSend(), item.getPrice());

            existingItem.setQuantity(existingItem.getQuantity() + transportItemDto.getQuantityToSend());
            existingItem.setPrice(averagePrice);

        } else if (filterItems.size() == 0) { // jesli itemu w magazynie nie bylo

            Item newItem = new Item();
            newItem.setName(itemSum.getName());
            newItem.setQuantityType(itemSum.getQuantityType());
            newItem.setQuantity(transportItemDto.getQuantityToSend());
            newItem.setPrice(item.getPrice());
            newItem.setWarehouse(newWarehouse);
            itemSum.addWarehouse(newWarehouse);

            itemRepository.save(newItem);

        } else {
            throw new DuplicateItemException();
        }

        return ResponseEntity.ok().build();
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
