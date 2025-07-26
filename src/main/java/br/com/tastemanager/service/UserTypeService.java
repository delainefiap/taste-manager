package br.com.tastemanager.service;

import br.com.tastemanager.dto.request.UserTypeRequestDTO;
import br.com.tastemanager.dto.response.UserTypeResponseDTO;
import br.com.tastemanager.entity.UserType;
import br.com.tastemanager.mapper.UserTypeMapper;
import br.com.tastemanager.repository.UserRepository;
import br.com.tastemanager.repository.UserTypeRepository;
import br.com.tastemanager.validator.UserTypeValidator;
import br.com.tastemanager.validator.UserValidator;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class UserTypeService {

    private final UserTypeRepository userTypeRepository;

    private final UserRepository userRepository;

    private final UserTypeMapper userTypeMapper;

    private final UserTypeValidator userTypeValidator;

    public UserTypeService(UserTypeRepository userTypeRepository, UserRepository userRepository, UserTypeMapper userTypeMapper, UserTypeValidator userTypeValidation) {
        this.userTypeRepository = userTypeRepository;
        this.userRepository = userRepository;
        this.userTypeMapper = userTypeMapper;
        this.userTypeValidator = userTypeValidation;
    }

    public UserTypeResponseDTO createUserType(UserTypeRequestDTO userTypeRequestDTO) {
        userTypeValidator.validateUserTypeName(userTypeRequestDTO.getName());
        UserType userType = new UserType();
        userType.setName(userTypeRequestDTO.getName());
        userTypeRepository.save(userType);
        return userTypeMapper.toResponseDTO(userType);
    }

    public List<UserType> findAllUserTypes(int page, int size) {
        int offset = (page - 1) * size;
        return userTypeRepository.findAll(size, offset);
    }

    public UserTypeResponseDTO updateUserType(Long id, UserTypeRequestDTO userTypeRequestDTO) {
        UserType userType = userTypeMapper.toEntity(userTypeRequestDTO);
        userTypeRepository.updateUserType(id, userType);
        return userTypeMapper.toResponseDTO(userType);
    }

    public String deleteUserType(Long id) {
        userTypeValidator.validateUserTypeIsInUse(id);
        userTypeRepository.deleteUserType(id);
        return "User type deleted successfully";
    }

    public UserTypeResponseDTO findUserTypeById(Long id) {
        UserType userType = userTypeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("UserType não encontrado"));
        return userTypeMapper.toResponseDTO(userType);
    }
}