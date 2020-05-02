package com.github.pimvoeten.jpa.example.controllers.mappers;

import org.mapstruct.Mapper;

import java.util.UUID;

@Mapper(componentModel = "spring")
public class UUIDMapper {

    public String asString(UUID uuid) {
        return uuid.toString();
    }

    public UUID asUUID(String uuid) {
        return UUID.fromString(uuid);
    }
}
