package com.example.controller;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.service.StockService;

@RestController
public class StockController {

	@Autowired
	StockService stockService;

//	@RequestMapping("/stock/{date}") TODO post 日期、想儲存的類別
	@RequestMapping("/stock")
	public String putStock() {

		// 抓取公司代號、名稱 (本國上市證券 有價證券代號及名稱)
		ArrayList<String> arrayList = new ArrayList<String>();
//		arrayList.add("股票"); TODO 想儲存的類別改可以前端輸入
//		arrayList.add("ETF");
		arrayList.add("受益證券-不動產投資信託");
		Map<String, String> map = stockService.getCompanyMap(arrayList);

		// 依公司名稱 取得當月資訊
		ArrayList<Map<String, Object>> stockInfo = stockService.getStockInfo(map);

		// TODO 放入資料庫，資料庫怎麼設計 代號 名稱 data日期, 成交股數, 成交金額, 開盤價, 最高價, 最低價, 收盤價, 漲跌價差, 成交筆數
		for (int i = 0; i < stockInfo.size(); i++) {
			System.out.println(stockInfo.get(i));
		}

		return null;
	}
}
