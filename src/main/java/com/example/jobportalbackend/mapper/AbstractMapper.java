package com.example.jobportalbackend.mapper;

public abstract class AbstractMapper <Entity, DTO> {
public abstract Entity toEntity(DTO dto);
public abstract DTO toDto(Entity entity);
}
