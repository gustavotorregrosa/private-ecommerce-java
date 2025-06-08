package dev.torregrosa.app.shared;

public interface IService <DTO, ID> {

    DTO save(DTO dto);

    DTO findById(ID id);

    Iterable<DTO> findAll();

    void deleteById(ID id);
}
