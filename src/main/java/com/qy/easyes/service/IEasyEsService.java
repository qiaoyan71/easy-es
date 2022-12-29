package com.qy.easyes.service;

import cn.easyes.core.biz.EsPageInfo;
import cn.easyes.core.conditions.LambdaEsUpdateWrapper;
import com.qy.easyes.model.Document;

import java.util.List;

public interface IEasyEsService {

    Document selectById(String id);

    EsPageInfo<Document> selectByPage(Document document, EsPageInfo pageInfo);

    List<Document> selectDocumentList(String title);

    Integer insertDocument(Document document);

    Integer deleteById(String id);

    Integer updateById(Document document);

    /**
     * 根据动态条件 更新记录 (先根据匹配条件查询id,再用id修改)
     * @param document      更新内容
     * @param updateWrapper 更新匹配条件
     * @return {@code Integer}
     */
    Integer update(Document document, LambdaEsUpdateWrapper updateWrapper);
}
