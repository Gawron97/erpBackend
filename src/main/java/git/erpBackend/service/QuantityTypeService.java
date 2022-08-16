package git.erpBackend.service;

import git.erpBackend.dto.QuantityTypeDto;
import git.erpBackend.entity.QuantityType;
import git.erpBackend.repository.QuantityTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class QuantityTypeService {

    QuantityTypeRepository quantityTypeRepository;

    @Autowired
    public QuantityTypeService(QuantityTypeRepository quantityTypeRepository){
        this.quantityTypeRepository = quantityTypeRepository;
    }

    public List<QuantityTypeDto> getQuantityTypeList(){
        return quantityTypeRepository.findAll().stream().map(quantityType -> QuantityTypeDto.of(quantityType)).collect(Collectors.toList());

    }

}
