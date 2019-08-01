package com.comarch.danielkurosz.service;

import com.comarch.danielkurosz.data.ClientTagEntity;
import com.comarch.danielkurosz.data.Tag;
import com.comarch.danielkurosz.dto.ClientTagDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import java.lang.reflect.Type;
import java.util.List;
import java.util.UUID;

public class Mapper {

    private ModelMapper modelMapper;

    public Mapper(){
        modelMapper = new ModelMapper();
    }

    public List<ClientTagDTO> mapToDTOList(List<ClientTagEntity> entityList){
        Type listType = new TypeToken<List<ClientTagDTO>>() {}.getType();
        return modelMapper.map(entityList,listType);
    }

    public List<Tag> mapToTagList(List<ClientTagEntity> entityList){
        Type listType = new TypeToken<List<Tag>>() {}.getType();
        return modelMapper.map(entityList,listType);
    }




}
