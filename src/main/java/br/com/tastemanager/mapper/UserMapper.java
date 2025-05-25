package br.com.tastemanager.mapper;

import br.com.tastemanager.dto.request.UserRequestDTO;
import br.com.tastemanager.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

//    @Mapping(target = "id", ignore = true)
//    @Mapping(target = "createdAt",  expression = "java(new java.util.Date())")
//    @Mapping(target = "lastUpdate", ignore = true)
//    User toEntity(UserRequestDTO dto);


}
