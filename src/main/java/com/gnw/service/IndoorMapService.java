package com.gnw.service;

import com.gnw.pojo.IndoorMap;

import java.util.List;

public interface IndoorMapService {
    public List<IndoorMap> selIndoorMap(String mapName);
    public void deleteIndoorMap(String mapName);
    public void insertIndoorMap(IndoorMap indoorMap);
}
