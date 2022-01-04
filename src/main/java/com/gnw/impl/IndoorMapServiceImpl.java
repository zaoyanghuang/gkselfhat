package com.gnw.impl;

import com.gnw.mapper.IndoorMapMapper;
import com.gnw.pojo.IndoorMap;
import com.gnw.service.IndoorMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class IndoorMapServiceImpl implements IndoorMapService {
    @Autowired
    private IndoorMapMapper indoorMapMapper;
    @Override
    public List<IndoorMap> selIndoorMap(String mapName) {
        return indoorMapMapper.selIndoorMap(mapName);
    }

    @Override
    public void deleteIndoorMap(String mapName) {
        indoorMapMapper.deleteIndoorMap(mapName);
    }

    @Override
    public void insertIndoorMap(IndoorMap indoorMap) {
        indoorMapMapper.insertIndoorMap(indoorMap);
    }
}
