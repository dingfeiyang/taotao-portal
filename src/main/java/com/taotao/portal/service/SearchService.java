package com.taotao.portal.service;

import com.taotao.portal.pojo.SearchResult;

public interface SearchService {

	SearchResult seacher(String queryString,Integer page);
}
