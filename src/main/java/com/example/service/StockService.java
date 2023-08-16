package com.example.service;

import java.util.ArrayList;
import java.util.Map;

public interface StockService {

	void save(String key, String name, Object data);

	Map<String, String> getCompanyMap(ArrayList<String> arrayList);

	void getStockInfo(Map<String, String> map);

}
