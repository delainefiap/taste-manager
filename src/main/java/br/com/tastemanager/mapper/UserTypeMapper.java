package br.com.tastemanager.mapper;

import br.com.tastemanager.dto.request.UserTypeRequestDTO;
import br.com.tastemanager.dto.response.UserTypeResponseDTO;
import br.com.tastemanager.entity.UserType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserTypeMapper {

    @Mapping(target = "name", source = "name")
    UserType toEntity(UserTypeRequestDTO dto);

    UserTypeRequestDTO toRequestDTO(UserType entity);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    UserTypeResponseDTO toResponseDTO(UserType entity);
}
