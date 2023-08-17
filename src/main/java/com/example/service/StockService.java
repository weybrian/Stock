package com.example.service;

import java.util.ArrayList;
import java.util.Map;

public interface StockService {

	Map<String, String> getCompanyMap(ArrayList<String> arrayList);

	ArrayList<Map<String, Object>> getStockInfo(Map<String, String> map);

	void save(String key, String name, Object data);

}
