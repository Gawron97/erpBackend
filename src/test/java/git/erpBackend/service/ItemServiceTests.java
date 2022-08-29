package git.erpBackend.service;

import git.erpBackend.dto.ItemDto;
import git.erpBackend.dto.QuantityTypeDto;
import git.erpBackend.entity.*;
import git.erpBackend.enums.QuantityEnum;
import git.erpBackend.repository.ItemRepository;
import git.erpBackend.repository.ItemSumRepository;
import git.erpBackend.repository.QuantityTypeRepository;
import git.erpBackend.repository.WarehouseRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ItemServiceTests {

    private ItemRepository itemRepository;
    private static WarehouseRepository warehouseRepository;
    private ItemSumRepository itemSumRepository;
    private static QuantityTypeRepository quantityTypeRepository;
    private static ItemService itemService;

    @BeforeAll
    public static void init(){
        warehouseRepository = mock(WarehouseRepository.class);
        quantityTypeRepository = mock(QuantityTypeRepository.class);
    }

    @BeforeEach
    public void prepare(){
        itemRepository = mock(ItemRepository.class);
        itemSumRepository = mock(ItemSumRepository.class);
        itemService = new ItemService(itemRepository, warehouseRepository, itemSumRepository, quantityTypeRepository);
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
        Warehouse warehouse = new Warehouse();
        warehouse.setName("Warsaw Warehouse");

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
        itemDto.setWarehouseName("Warsaw Warehouse");

        when(warehouseRepository.findByNameWithItems(itemDto.getWarehouseName())).thenReturn(Optional.of(warehouse));
        when(warehouseRepository.findByNameWithItemSums(itemDto.getWarehouseName())).thenReturn(Optional.of(warehouse));
        when(itemSumRepository.findByNameWithWarehouses(itemDto.getName())).thenReturn(Optional.ofNullable(null));
        when(quantityTypeRepository.findById(itemDto.getQuantityTypeDto().getIdQuantityType())).thenReturn(Optional.of(quantityType));

        itemService.saveItem(itemDto);

        //Then
        Item expectedItem = new Item();
        expectedItem.setName("grain");
        expectedItem.setQuantity(50);
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
        Warehouse warsawWarehouse = new Warehouse();
        warsawWarehouse.setIdWarehouse(1);
        warsawWarehouse.setName("Warsaw Warehouse");

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
        item.setWarehouse(warsawWarehouse);

        ItemSum itemSum = new ItemSum();
        itemSum.setName("grain");
        itemSum.setId(1);
        itemSum.setQuantity(50);
        itemSum.setQuantityType(quantityType);
        itemSum.addWarehouse(warsawWarehouse);

        //When
        ItemDto itemDto = new ItemDto();
        itemDto.setName("grain");
        itemDto.setQuantityTypeDto(quantityTypeDto);
        itemDto.setQuantity(120);
        itemDto.setWarehouseName("Wroclaw Warehouse");

        when(warehouseRepository.findByNameWithItems(itemDto.getWarehouseName())).thenReturn(Optional.of(wroclawWarehouse));
        when(warehouseRepository.findByNameWithItemSums(itemDto.getWarehouseName())).thenReturn(Optional.of(wroclawWarehouse));
        when(itemSumRepository.findByNameWithWarehouses(itemDto.getName())).thenReturn(Optional.of(itemSum));
        when(quantityTypeRepository.findById(itemDto.getQuantityTypeDto().getIdQuantityType())).thenReturn(Optional.of(quantityType));

        itemService.saveItem(itemDto);

        //Then
        Item expectedItem = new Item();
        expectedItem.setName("grain");
        expectedItem.setQuantity(120);
        expectedItem.setQuantityType(quantityType);
        expectedItem.setWarehouse(wroclawWarehouse);

        ItemSum expectedItemSum = new ItemSum();
        expectedItemSum.setId(1);
        expectedItemSum.setName("grain");
        expectedItemSum.setQuantityType(quantityType);
        expectedItemSum.setQuantity(170);
        expectedItemSum.addWarehouse(warsawWarehouse);
        expectedItemSum.addWarehouse(wroclawWarehouse);

        verify(itemRepository).save(expectedItem);
        verify(itemSumRepository).save(expectedItemSum);

    }

    @Test
    public void givenTwoWarehousesOneItemExistInOneOfThemWhenAddingSameNamedItemWithSameQuantityTypeToOtherWarehouseThenTwoWarehousesEachIncludesOneItem(){

        //Given
        Warehouse warsawWarehouse = new Warehouse();
        warsawWarehouse.setIdWarehouse(1);
        warsawWarehouse.setName("Warsaw Warehouse");

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
        item.setWarehouse(warsawWarehouse);

        ItemSum itemSum = new ItemSum();
        itemSum.setName("grain");
        itemSum.setId(1);
        itemSum.setQuantity(50);
        itemSum.setQuantityType(quantityType);
        itemSum.addWarehouse(warsawWarehouse);

        //When
        ItemDto itemDto = new ItemDto();
        itemDto.setName("grain");
        itemDto.setQuantityTypeDto(quantityTypeDto);
        itemDto.setQuantity(120);
        itemDto.setWarehouseName("Wroclaw Warehouse");

        when(warehouseRepository.findByNameWithItems(itemDto.getWarehouseName())).thenReturn(Optional.of(wroclawWarehouse));
        when(warehouseRepository.findByNameWithItemSums(itemDto.getWarehouseName())).thenReturn(Optional.of(wroclawWarehouse));
        when(itemSumRepository.findByNameWithWarehouses(itemDto.getName())).thenReturn(Optional.of(itemSum));
        when(quantityTypeRepository.findById(itemDto.getQuantityTypeDto().getIdQuantityType())).thenReturn(Optional.of(quantityType));

        itemService.saveItem(itemDto);

        //Then
        assertEquals(warsawWarehouse.getItems().size(), 1);
        assertEquals(wroclawWarehouse.getItems().size(), 1);

    }

    @Test
    public void givenOneWarehouseWithOneItemWhenAddToTheWarehouseTheSameItemThenItemsSplitIntoOne(){

        //Given
        Warehouse warsawWarehouse = new Warehouse();
        warsawWarehouse.setIdWarehouse(1);
        warsawWarehouse.setName("Warsaw Warehouse");

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
        item.setWarehouse(warsawWarehouse);

        ItemSum itemSum = new ItemSum();
        itemSum.setName("grain");
        itemSum.setId(1);
        itemSum.setQuantity(50);
        itemSum.setQuantityType(quantityType);
        itemSum.addWarehouse(warsawWarehouse);

        //When
        ItemDto itemDto = new ItemDto();
        itemDto.setName("grain");
        itemDto.setQuantityTypeDto(quantityTypeDto);
        itemDto.setQuantity(120);
        itemDto.setWarehouseName("Warsaw Warehouse");

        when(warehouseRepository.findByNameWithItems(itemDto.getWarehouseName())).thenReturn(Optional.of(warsawWarehouse));
        when(warehouseRepository.findByNameWithItemSums(itemDto.getWarehouseName())).thenReturn(Optional.of(warsawWarehouse));
        when(itemSumRepository.findByNameWithWarehouses(itemDto.getName())).thenReturn(Optional.of(itemSum));
        when(quantityTypeRepository.findById(itemDto.getQuantityTypeDto().getIdQuantityType())).thenReturn(Optional.of(quantityType));

        itemService.saveItem(itemDto);

        //Then
        Item expectedItem = new Item();
        expectedItem.setIdItem(1);
        expectedItem.setName("grain");
        expectedItem.setQuantityType(quantityType);
        expectedItem.setQuantity(170);
        expectedItem.setWarehouse(warsawWarehouse);

        ItemSum expectedItemSum = new ItemSum();
        expectedItemSum.setId(1);
        expectedItemSum.setName("grain");
        expectedItemSum.setQuantityType(quantityType);
        expectedItemSum.setQuantity(170);
        expectedItemSum.addWarehouse(warsawWarehouse);

        verify(itemRepository).save(expectedItem);
        verify(itemSumRepository).save(expectedItemSum);

    }

    @Test
    public void givenTwoWarehousesEachWithOneItemWithSameNameAndSameQuantityTypeWhenAddToOneWarehouseTheSameItemThenItemsSplitIntoOneAndItemSumChanged(){

        //Given
        Warehouse warsawWarehouse = new Warehouse();
        warsawWarehouse.setIdWarehouse(1);
        warsawWarehouse.setName("Warsaw Warehouse");

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
        item.setWarehouse(warsawWarehouse);

        Item item2 = new Item();
        item2.setIdItem(2);
        item2.setName("wheat");
        item2.setQuantityType(quantityType);
        item2.setQuantity(60);
        item2.setWarehouse(wroclawWarehouse);

        ItemSum itemSum = new ItemSum();
        itemSum.setName("wheat");
        itemSum.setId(1);
        itemSum.setQuantity(110);
        itemSum.setQuantityType(quantityType);
        itemSum.addWarehouse(warsawWarehouse);
        itemSum.addWarehouse(wroclawWarehouse);

        //When
        ItemDto itemDto = new ItemDto();
        itemDto.setName("wheat");
        itemDto.setQuantityTypeDto(quantityTypeDto);
        itemDto.setQuantity(130);
        itemDto.setWarehouseName("Warsaw Warehouse");

        when(warehouseRepository.findByNameWithItems(itemDto.getWarehouseName())).thenReturn(Optional.of(warsawWarehouse));
        when(warehouseRepository.findByNameWithItemSums(itemDto.getWarehouseName())).thenReturn(Optional.of(warsawWarehouse));
        when(itemSumRepository.findByNameWithWarehouses(itemDto.getName())).thenReturn(Optional.of(itemSum));
        when(quantityTypeRepository.findById(itemDto.getQuantityTypeDto().getIdQuantityType())).thenReturn(Optional.of(quantityType));

        itemService.saveItem(itemDto);

        //Then
        Item expectedItem = new Item();
        expectedItem.setIdItem(1);
        expectedItem.setName("wheat");
        expectedItem.setQuantityType(quantityType);
        expectedItem.setQuantity(180);
        expectedItem.setWarehouse(warsawWarehouse);

        ItemSum expectedItemSum = new ItemSum();
        expectedItemSum.setId(1);
        expectedItemSum.setName("wheat");
        expectedItemSum.setQuantityType(quantityType);
        expectedItemSum.setQuantity(240);
        expectedItemSum.addWarehouse(warsawWarehouse);
        expectedItemSum.addWarehouse(wroclawWarehouse);

        verify(itemRepository).save(expectedItem);
        verify(itemSumRepository).save(expectedItemSum);


    }

}
