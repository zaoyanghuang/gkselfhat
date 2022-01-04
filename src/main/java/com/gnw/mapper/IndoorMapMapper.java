package com.gnw.mapper;

import com.gnw.pojo.IndoorMap;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface IndoorMapMapper {
    public List<IndoorMap> selIndoorMap(String mapName);
    public void deleteIndoorMap(String mapName);
    public void insertIndoorMap(IndoorMap indoorMap);
}
