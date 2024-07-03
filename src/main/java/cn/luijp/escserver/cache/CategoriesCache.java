package cn.luijp.escserver.cache;

import cn.luijp.escserver.model.entity.Categories;
import cn.luijp.escserver.service.CategoriesControllerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategoriesCache {

    public static Map<Integer, Categories> CategoriesMap = new HashMap<>();

}
