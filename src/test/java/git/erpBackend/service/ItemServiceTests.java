package git.erpBackend.service;

import git.erpBackend.dto.ItemDto;
import git.erpBackend.dto.QuantityTypeDto;
import git.erpBackend.dto.TransportItemDto;
import git.erpBackend.entity.Item;
import git.erpBackend.entity.ItemSum;
import git.erpBackend.entity.QuantityType;
import git.erpBackend.entity.Warehouse;
import git.erpBackend.enums.QuantityEnum;
import git.erpBackend.repository.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ItemServiceTests {

    private ItemRepository itemRepository;
    private static WarehouseRepository warehouseRepository;
    private ItemSumRepository itemSumRepository;
    private static QuantityTypeRepository quantityTypeRepository;
    private ItemService itemService;
    private static TruckRepository truckRepository;
    private Warehouse warehouse;

    @BeforeAll
    public static void init(){
        warehouseRepository = mock(WarehouseRepository.class);
        quantityTypeRepository = mock(QuantityTypeRepository.class);
        truckRepository = mock(TruckRepository.class);
    }

    @BeforeEach
    public void prepare(){
        itemRepository = mock(ItemRepository.class);
        itemSumRepository = mock(ItemSumRepository.class);
        itemService = new ItemService(itemRepository, warehouseRepository, itemSumRepository, quantityTypeRepository,
                truckRepository);

        warehouse = new Warehouse();
        warehouse.setIdWarehouse(1);
        warehouse.setName("Warsaw Warehouse");
    }

    @Test
    public void givenSomeItemsInDatabaseWhenTakeAllThenOk(){

        //Given
        Item i1 = new Item();
        Item i2 = new Item();
        Item i3 = new Item();

        List<Item> items = List.of(i1, i2, i3);

        Mockito.mockStatic(ItemDto.class);
        when(ItemDto.of(i1)).thenReturn(new ItemDto());
        when(ItemDto.of(i2)).thenReturn(new ItemDto());
        when(ItemDto.of(i3)).thenReturn(new ItemDto());
        when(itemRepository.findAll()).thenReturn(items);


        //When
        List<ItemDto> takenItems = itemService.getListOfItems();

        //Then
        assertEquals(items.size(), takenItems.size());
    }

    @Test
    public void givenEmptyOneWarehouseWhenSaveOneItemThenOneItemInWarehouseAndOneInItemSum(){

        //Given
        QuantityType quantityType = new QuantityType();
        quantityType.setIdQuantityType(1);
        quantityType.setQuantityType(QuantityEnum.KILOGRAMS);

        QuantityTypeDto quantityTypeDto = new QuantityTypeDto();
        quantityTypeDto.setIdQuantityType(1);
        quantityTypeDto.setName("kilo");

        //When
        ItemDto itemDto = new ItemDto();
        itemDto.setName("grain");
        itemDto.setQuantityTypeDto(quantityTypeDto);
        itemDto.setQuantity(50);
        itemDto.setPrice(20);
        itemDto.setIdWarehouse(1);

        when(warehouseRepository.findById(itemDto.getIdWarehouse())).thenReturn(Optional.of(warehouse));
        when(itemSumRepository.findByName(itemDto.getName())).thenReturn(Optional.ofNullable(null));
        when(quantityTypeRepository.findById(itemDto.getQuantityTypeDto().getIdQuantityType())).thenReturn(Optional.of(quantityType));

        itemService.addItem(itemDto);

        //Then
        Item expectedItem = new Item();
        expectedItem.setName("grain");
        expectedItem.setQuantity(50);
        expectedItem.setPrice(20);
        expectedItem.setQuantityType(quantityType);
        expectedItem.setWarehouse(warehouse);

        ItemSum expectedItemSum = new ItemSum();
        expectedItemSum.setName("grain");
        expectedItemSum.setQuantityType(quantityType);
        expectedItemSum.setQuantity(50);
        expectedItemSum.addWarehouse(warehouse);

        verify(itemRepository).save(expectedItem);
        verify(itemSumRepository).save(expectedItemSum);
    }

    @Test
    public void givenTwoWarehousesOneItemExistInOneOfThemWhenAddingSameNamedItemWithSameQuantityTypeToOtherWarehouseThenItemSumIsSumOfTheseTwoItemsAndContainsTwoWarehouses(){

        //Given
        Warehouse wroclawWarehouse = new Warehouse();
        wroclawWarehouse.setIdWarehouse(2);
        wroclawWarehouse.setName("Wroclaw Warehouse");

        QuantityType quantityType = new QuantityType();
        quantityType.setIdQuantityType(1);
        quantityType.setQuantityType(QuantityEnum.KILOGRAMS);

        QuantityTypeDto quantityTypeDto = new QuantityTypeDto();
        quantityTypeDto.setIdQuantityType(1);
        quantityTypeDto.setName("kilo");

        Item item = new Item();
        item.setIdItem(1);
        item.setName("grain");
        item.setQuantityType(quantityType);
        item.setQuantity(50);
        item.setPrice(80);
        item.setWarehouse(warehouse);

        ItemSum itemSum = new ItemSum();
        itemSum.setName("grain");
        itemSum.setIdItemSum(1);
        itemSum.setQuantity(50);
        itemSum.setQuantityType(quantityType);
        itemSum.addWarehouse(warehouse);

        //When
        ItemDto itemDto = new ItemDto();
        itemDto.setName("grain");
        itemDto.setQuantityTypeDto(quantityTypeDto);
        itemDto.setQuantity(120);
        itemDto.setPrice(30);
        itemDto.setIdWarehouse(2);

        when(warehouseRepository.findById(itemDto.getIdWarehouse())).thenReturn(Optional.of(wroclawWarehouse));
        when(itemSumRepository.findByName(itemDto.getName())).thenReturn(Optional.of(itemSum));
        when(quantityTypeRepository.findById(itemDto.getQuantityTypeDto().getIdQuantityType())).thenReturn(Optional.of(quantityType));

        itemService.addItem(itemDto);

        //Then
        Item expectedItem = new Item();
        expectedItem.setName("grain");
        expectedItem.setQuantity(120);
        expectedItem.setPrice(30);
        expectedItem.setQuantityType(quantityType);
        expectedItem.setWarehouse(wroclawWarehouse);

        ItemSum expectedItemSum = new ItemSum();
        expectedItemSum.setIdItemSum(1);
        expectedItemSum.setName("grain");
        expectedItemSum.setQuantityType(quantityType);
        expectedItemSum.setQuantity(170);
        expectedItemSum.addWarehouse(warehouse);
        expectedItemSum.addWarehouse(wroclawWarehouse);

        verify(itemRepository).save(expectedItem);
        verify(itemSumRepository).save(expectedItemSum);

    }

    @Test
    public void givenTwoWarehousesOneItemExistInOneOfThemWhenAddingSameNamedItemWithSameQuantityTypeToOtherWarehouseThenTwoWarehousesEachIncludesOneItem(){

        //Given
        Warehouse wroclawWarehouse = new Warehouse();
        wroclawWarehouse.setIdWarehouse(2);
        wroclawWarehouse.setName("Wroclaw Warehouse");

        QuantityType quantityType = new QuantityType();
        quantityType.setIdQuantityType(1);
        quantityType.setQuantityType(QuantityEnum.KILOGRAMS);

        QuantityTypeDto quantityTypeDto = new QuantityTypeDto();
        quantityTypeDto.setIdQuantityType(1);
        quantityTypeDto.setName("kilo");

        Item item = new Item();
        item.setIdItem(1);
        item.setName("grain");
        item.setQuantityType(quantityType);
        item.setQuantity(50);
        item.setPrice(20);
        item.setWarehouse(warehouse);

        ItemSum itemSum = new ItemSum();
        itemSum.setName("grain");
        itemSum.setIdItemSum(1);
        itemSum.setQuantity(50);
        itemSum.setQuantityType(quantityType);
        itemSum.addWarehouse(warehouse);

        //When
        ItemDto itemDto = new ItemDto();
        itemDto.setName("grain");
        itemDto.setQuantityTypeDto(quantityTypeDto);
        itemDto.setQuantity(120);
        itemDto.setPrice(30);
        itemDto.setIdWarehouse(2);

        when(warehouseRepository.findById(itemDto.getIdWarehouse())).thenReturn(Optional.of(wroclawWarehouse));
        when(itemSumRepository.findByName(itemDto.getName())).thenReturn(Optional.of(itemSum));
        when(quantityTypeRepository.findById(itemDto.getQuantityTypeDto().getIdQuantityType())).thenReturn(Optional.of(quantityType));

        itemService.addItem(itemDto);

        //Then
        assertEquals(1, warehouse.getItems().size());
        assertEquals(1, wroclawWarehouse.getItems().size());

    }

    @Test
    public void givenOneWarehouseWithOneItemWhenAddToTheWarehouseTheSameItemThenItemsSplitIntoOne(){

        //Given
        QuantityType quantityType = new QuantityType();
        quantityType.setIdQuantityType(1);
        quantityType.setQuantityType(QuantityEnum.KILOGRAMS);

        QuantityTypeDto quantityTypeDto = new QuantityTypeDto();
        quantityTypeDto.setIdQuantityType(1);
        quantityTypeDto.setName("kilo");

        Item item = new Item();
        item.setIdItem(1);
        item.setName("grain");
        item.setQuantityType(quantityType);
        item.setQuantity(50);
        item.setPrice(20);
        item.setWarehouse(warehouse);

        ItemSum itemSum = new ItemSum();
        itemSum.setName("grain");
        itemSum.setIdItemSum(1);
        itemSum.setQuantity(50);
        itemSum.setQuantityType(quantityType);
        itemSum.addWarehouse(warehouse);

        //When
        ItemDto itemDto = new ItemDto();
        itemDto.setName("grain");
        itemDto.setQuantityTypeDto(quantityTypeDto);
        itemDto.setQuantity(120);
        itemDto.setPrice(10);
        itemDto.setIdWarehouse(1);

        when(warehouseRepository.findById(itemDto.getIdWarehouse())).thenReturn(Optional.of(warehouse));
        when(itemSumRepository.findByName(itemDto.getName())).thenReturn(Optional.of(itemSum));
        when(quantityTypeRepository.findById(itemDto.getQuantityTypeDto().getIdQuantityType())).thenReturn(Optional.of(quantityType));
        when(itemRepository.findItemByNameAndWarehouse(itemDto.getName(), warehouse)).thenReturn(Optional.of(item));

        itemService.addItem(itemDto);

        //Then
        Item expectedItem = new Item();
        expectedItem.setIdItem(1);
        expectedItem.setName("grain");
        expectedItem.setQuantityType(quantityType);
        expectedItem.setQuantity(170);
        expectedItem.setPrice(12.94);
        expectedItem.setWarehouse(warehouse);

        ItemSum expectedItemSum = new ItemSum();
        expectedItemSum.setIdItemSum(1);
        expectedItemSum.setName("grain");
        expectedItemSum.setQuantityType(quantityType);
        expectedItemSum.setQuantity(170);
        expectedItemSum.addWarehouse(warehouse);

        verify(itemRepository).save(expectedItem);
        verify(itemSumRepository).save(expectedItemSum);

    }

    @Test
    public void givenTwoWarehousesEachWithOneItemWithSameNameAndSameQuantityTypeWhenAddToOneWarehouseTheSameItemThenItemsSplitIntoOneAndItemSumChanged(){

        //Given
        Warehouse wroclawWarehouse = new Warehouse();
        wroclawWarehouse.setIdWarehouse(2);
        wroclawWarehouse.setName("Wroclaw Warehouse");

        QuantityType quantityType = new QuantityType();
        quantityType.setIdQuantityType(1);
        quantityType.setQuantityType(QuantityEnum.KILOGRAMS);

        QuantityTypeDto quantityTypeDto = new QuantityTypeDto();
        quantityTypeDto.setIdQuantityType(1);
        quantityTypeDto.setName("kilo");

        Item item = new Item();
        item.setIdItem(1);
        item.setName("wheat");
        item.setQuantityType(quantityType);
        item.setQuantity(50);
        item.setPrice(20);
        item.setWarehouse(warehouse);

        Item item2 = new Item();
        item2.setIdItem(2);
        item2.setName("wheat");
        item2.setQuantityType(quantityType);
        item2.setQuantity(60);
        item2.setPrice(21);
        item2.setWarehouse(wroclawWarehouse);

        ItemSum itemSum = new ItemSum();
        itemSum.setName("wheat");
        itemSum.setIdItemSum(1);
        itemSum.setQuantity(110);
        itemSum.setQuantityType(quantityType);
        itemSum.addWarehouse(warehouse);
        itemSum.addWarehouse(wroclawWarehouse);

        //When
        ItemDto itemDto = new ItemDto();
        itemDto.setName("wheat");
        itemDto.setQuantityTypeDto(quantityTypeDto);
        itemDto.setQuantity(130);
        itemDto.setPrice(15);
        itemDto.setIdWarehouse(1);

        when(warehouseRepository.findById(itemDto.getIdWarehouse())).thenReturn(Optional.of(warehouse));
        when(itemSumRepository.findByName(itemDto.getName())).thenReturn(Optional.of(itemSum));
        when(quantityTypeRepository.findById(itemDto.getQuantityTypeDto().getIdQuantityType())).thenReturn(Optional.of(quantityType));
        when(itemRepository.findItemByNameAndWarehouse(itemDto.getName(), warehouse)).thenReturn(Optional.of(item));

        itemService.addItem(itemDto);

        //Then
        Item expectedItem = new Item();
        expectedItem.setIdItem(1);
        expectedItem.setName("wheat");
        expectedItem.setQuantityType(quantityType);
        expectedItem.setQuantity(180);
        expectedItem.setPrice(16.39);
        expectedItem.setWarehouse(warehouse);

        ItemSum expectedItemSum = new ItemSum();
        expectedItemSum.setIdItemSum(1);
        expectedItemSum.setName("wheat");
        expectedItemSum.setQuantityType(quantityType);
        expectedItemSum.setQuantity(240);
        expectedItemSum.addWarehouse(warehouse);
        expectedItemSum.addWarehouse(wroclawWarehouse);

        verify(itemRepository).save(expectedItem);
        verify(itemSumRepository).save(expectedItemSum);

    }

    @Test
    public void givenTwoWarehousesFirstWithOneItemWhenSendAllItemToSecondWarehouseThenItemRemovedFromFirstAndAddedToSecond(){

        //Given
        Warehouse wroclawWarehouse = new Warehouse();
        wroclawWarehouse.setIdWarehouse(2);
        wroclawWarehouse.setName("Wroclaw Warehouse");

        QuantityType quantityType = new QuantityType();
        quantityType.setIdQuantityType(1);
        quantityType.setQuantityType(QuantityEnum.KILOGRAMS);

        Item item = new Item();
        item.setIdItem(1);
        item.setName("corn");
        item.setQuantityType(quantityType);
        item.setQuantity(50);
        item.setPrice(20);
        item.setWarehouse(warehouse);

        ItemSum itemSum = new ItemSum();
        itemSum.setIdItemSum(1);
        itemSum.setName("corn");
        itemSum.setQuantity(50);
        itemSum.setQuantityType(quantityType);
        itemSum.addWarehouse(warehouse);


        //When
        TransportItemDto transportItemDto = new TransportItemDto();
        transportItemDto.setIdItem(1);
        transportItemDto.setIdTruck(1);
        transportItemDto.setOldWarehouseId(1);
        transportItemDto.setNewWarehouseId(2);
        transportItemDto.setQuantityToSend(50);

        when(itemRepository.findById(1)).thenReturn(Optional.of(item));
        when(warehouseRepository.findById(1)).thenReturn(Optional.of(warehouse));
        when(warehouseRepository.findById(2)).thenReturn(Optional.of(wroclawWarehouse));
        when(itemSumRepository.findByName("corn")).thenReturn(Optional.of(itemSum));

        itemService.transportItem(transportItemDto);

        //Then
        Item expectedItem = new Item();
        expectedItem.setName("corn");
        expectedItem.setQuantityType(quantityType);
        expectedItem.setQuantity(50);
        expectedItem.setPrice(20);
        expectedItem.setWarehouse(wroclawWarehouse);

        ItemSum expectedItemSum = new ItemSum();
        expectedItemSum.setIdItemSum(1);
        expectedItemSum.setName("corn");
        expectedItemSum.setQuantity(50);
        expectedItemSum.setQuantityType(quantityType);
        expectedItemSum.addWarehouse(wroclawWarehouse);

        verify(itemRepository).delete(item);
        verify(itemRepository).save(expectedItem);

        assertEquals(expectedItemSum, itemSum);

    }

    @Test
    public void givenTwoWarehousesFirstWithOneItemWhenSendPartOfItemToSecondWarehouseThenItemExistInBothWithCorrectQuantity(){

        //Given
        Warehouse wroclawWarehouse = new Warehouse();
        wroclawWarehouse.setIdWarehouse(2);
        wroclawWarehouse.setName("Wroclaw Warehouse");

        QuantityType quantityType = new QuantityType();
        quantityType.setIdQuantityType(1);
        quantityType.setQuantityType(QuantityEnum.KILOGRAMS);

        Item item = new Item();
        item.setIdItem(1);
        item.setName("corn");
        item.setQuantityType(quantityType);
        item.setQuantity(50);
        item.setPrice(40);
        item.setWarehouse(warehouse);

        ItemSum itemSum = new ItemSum();
        itemSum.setIdItemSum(1);
        itemSum.setName("corn");
        itemSum.setQuantity(50);
        itemSum.setQuantityType(quantityType);
        itemSum.addWarehouse(warehouse);

        //When
        TransportItemDto transportItemDto = new TransportItemDto();
        transportItemDto.setIdItem(1);
        transportItemDto.setIdTruck(1);
        transportItemDto.setOldWarehouseId(1);
        transportItemDto.setNewWarehouseId(2);
        transportItemDto.setQuantityToSend(30);

        when(itemRepository.findById(1)).thenReturn(Optional.of(item));
        when(warehouseRepository.findById(1)).thenReturn(Optional.of(warehouse));
        when(warehouseRepository.findById(2)).thenReturn(Optional.of(wroclawWarehouse));
        when(itemSumRepository.findByName("corn")).thenReturn(Optional.of(itemSum));

        itemService.transportItem(transportItemDto);

        //Then
        Item expectedSaveItem = new Item();
        expectedSaveItem.setName("corn");
        expectedSaveItem.setQuantityType(quantityType);
        expectedSaveItem.setQuantity(30);
        expectedSaveItem.setPrice(40);
        expectedSaveItem.setWarehouse(wroclawWarehouse);

        Item expectedOldItem = new Item();
        expectedOldItem.setIdItem(1);
        expectedOldItem.setName("corn");
        expectedOldItem.setQuantityType(quantityType);
        expectedOldItem.setQuantity(20);
        expectedOldItem.setPrice(40);
        expectedOldItem.setWarehouse(warehouse);

        ItemSum expectedItemSum = new ItemSum();
        expectedItemSum.setIdItemSum(1);
        expectedItemSum.setName("corn");
        expectedItemSum.setQuantity(50);
        expectedItemSum.setQuantityType(quantityType);
        expectedItemSum.addWarehouse(wroclawWarehouse);
        expectedItemSum.addWarehouse(warehouse);

        verify(itemRepository, never()).delete(item);
        verify(itemRepository).save(expectedSaveItem);

        assertEquals(expectedOldItem, item);
        assertEquals(expectedItemSum, itemSum);
    }

    @Test
    public void givenTwoWarehousesWithSameItemsWhenOneItemSendToSecondWarehouseThenItemInSecondModifiedAndFromFirstRemoved(){

        //Given
        Warehouse wroclawWarehouse = new Warehouse();
        wroclawWarehouse.setIdWarehouse(2);
        wroclawWarehouse.setName("Wroclaw Warehouse");

        QuantityType quantityType = new QuantityType();
        quantityType.setIdQuantityType(1);
        quantityType.setQuantityType(QuantityEnum.KILOGRAMS);

        Item firstItem = new Item();
        firstItem.setIdItem(1);
        firstItem.setName("corn");
        firstItem.setQuantityType(quantityType);
        firstItem.setQuantity(50);
        firstItem.setPrice(20);
        firstItem.setWarehouse(warehouse);

        Item secondItem = new Item();
        secondItem.setIdItem(2);
        secondItem.setName("corn");
        secondItem.setQuantityType(quantityType);
        secondItem.setQuantity(70);
        secondItem.setPrice(10);
        secondItem.setWarehouse(wroclawWarehouse);

        ItemSum itemSum = new ItemSum();
        itemSum.setIdItemSum(1);
        itemSum.setName("corn");
        itemSum.setQuantity(120);
        itemSum.setQuantityType(quantityType);
        itemSum.addWarehouse(warehouse);
        itemSum.addWarehouse(wroclawWarehouse);


        //When
        TransportItemDto transportItemDto = new TransportItemDto();
        transportItemDto.setIdItem(1);
        transportItemDto.setIdTruck(1);
        transportItemDto.setOldWarehouseId(1);
        transportItemDto.setNewWarehouseId(2);
        transportItemDto.setQuantityToSend(50);

        when(itemRepository.findById(1)).thenReturn(Optional.of(firstItem));
        when(warehouseRepository.findById(1)).thenReturn(Optional.of(warehouse));
        when(warehouseRepository.findById(2)).thenReturn(Optional.of(wroclawWarehouse));
        when(itemSumRepository.findByName("corn")).thenReturn(Optional.of(itemSum));
        when(itemRepository.findById(2)).thenReturn(Optional.of(secondItem));

        itemService.transportItem(transportItemDto);

        //Then
        Item expectedSecondItem = new Item();
        expectedSecondItem.setIdItem(2);
        expectedSecondItem.setName("corn");
        expectedSecondItem.setQuantityType(quantityType);
        expectedSecondItem.setQuantity(120);
        expectedSecondItem.setPrice(14.17);
        expectedSecondItem.setWarehouse(wroclawWarehouse);

        ItemSum expectedItemSum = new ItemSum();
        expectedItemSum.setIdItemSum(1);
        expectedItemSum.setName("corn");
        expectedItemSum.setQuantity(120);
        expectedItemSum.setQuantityType(quantityType);
        expectedItemSum.addWarehouse(wroclawWarehouse);

        verify(itemRepository).delete(firstItem);
        verify(itemRepository, never()).save(any());

        assertEquals(expectedSecondItem, secondItem);
        assertEquals(expectedItemSum, itemSum);
    }

    @Test
    public void givenTwoWarehousesWithSameItemsWhenPartOfOneItemSendToSecondWarehouseThenBothItemsModified(){

        //Given
        Warehouse wroclawWarehouse = new Warehouse();
        wroclawWarehouse.setIdWarehouse(2);
        wroclawWarehouse.setName("Wroclaw Warehouse");

        QuantityType quantityType = new QuantityType();
        quantityType.setIdQuantityType(1);
        quantityType.setQuantityType(QuantityEnum.KILOGRAMS);

        Item firstItem = new Item();
        firstItem.setIdItem(1);
        firstItem.setName("corn");
        firstItem.setQuantityType(quantityType);
        firstItem.setQuantity(50);
        firstItem.setPrice(20);
        firstItem.setWarehouse(warehouse);

        Item secondItem = new Item();
        secondItem.setIdItem(2);
        secondItem.setName("corn");
        secondItem.setQuantityType(quantityType);
        secondItem.setQuantity(70);
        secondItem.setPrice(30);
        secondItem.setWarehouse(wroclawWarehouse);

        ItemSum itemSum = new ItemSum();
        itemSum.setIdItemSum(1);
        itemSum.setName("corn");
        itemSum.setQuantity(120);
        itemSum.setQuantityType(quantityType);
        itemSum.addWarehouse(warehouse);
        itemSum.addWarehouse(wroclawWarehouse);

        //When
        TransportItemDto transportItemDto = new TransportItemDto();
        transportItemDto.setIdItem(1);
        transportItemDto.setIdTruck(1);
        transportItemDto.setOldWarehouseId(1);
        transportItemDto.setNewWarehouseId(2);
        transportItemDto.setQuantityToSend(20);

        when(itemRepository.findById(1)).thenReturn(Optional.of(firstItem));
        when(warehouseRepository.findById(1)).thenReturn(Optional.of(warehouse));
        when(warehouseRepository.findById(2)).thenReturn(Optional.of(wroclawWarehouse));
        when(itemSumRepository.findByName("corn")).thenReturn(Optional.of(itemSum));
        when(itemRepository.findById(2)).thenReturn(Optional.of(secondItem));
        when(quantityTypeRepository.findById(1)).thenReturn(Optional.of(quantityType));

        itemService.transportItem(transportItemDto);

        //Then
        Item expectedFirstItem = new Item();
        expectedFirstItem.setIdItem(1);
        expectedFirstItem.setName("corn");
        expectedFirstItem.setQuantityType(quantityType);
        expectedFirstItem.setQuantity(30);
        expectedFirstItem.setPrice(20);
        expectedFirstItem.setWarehouse(warehouse);

        Item expectedSecondItem = new Item();
        expectedSecondItem.setIdItem(2);
        expectedSecondItem.setName("corn");
        expectedSecondItem.setQuantityType(quantityType);
        expectedSecondItem.setQuantity(90);
        expectedSecondItem.setPrice(27.78);
        expectedSecondItem.setWarehouse(wroclawWarehouse);

        ItemSum expectedItemSum = new ItemSum();
        expectedItemSum.setIdItemSum(1);
        expectedItemSum.setName("corn");
        expectedItemSum.setQuantity(120);
        expectedItemSum.setQuantityType(quantityType);
        expectedItemSum.addWarehouse(wroclawWarehouse);
        expectedItemSum.addWarehouse(warehouse);

    }

}
