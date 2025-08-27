package it.poli.controller.mapper;

public interface CommonModelEntityMapper<M, E> {

  M toModel(E entity);

  E toEntity(M model);
}
