package com.qy.easyes.service.impl;

import cn.easyes.core.biz.EsPageInfo;
import cn.easyes.core.conditions.LambdaEsQueryWrapper;
import cn.easyes.core.conditions.LambdaEsUpdateWrapper;
import com.qy.easyes.mapper.DocumentMapper;
import com.qy.easyes.model.Document;
import com.qy.easyes.service.IEasyEsService;
import com.qy.easyes.util.LambdaUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * EasyEsServiceImpl
 *
 * @author qiaoyan
 * @date 2022-12-29 14:05:08
 */
@Slf4j
@Service
public class EasyEsServiceImpl implements IEasyEsService {

    @Resource
    private DocumentMapper documentMapper;

    @Override
    public Document selectById(String id) {
        return documentMapper.selectById(id);
    }

    @Override
    public EsPageInfo<Document> selectByPage(Document document, EsPageInfo pageInfo) {
        LambdaEsQueryWrapper<Document> wrapper = new LambdaEsQueryWrapper<>();
        LambdaUtils.buildByEntity(wrapper,document);
//        wrapper.match(Document::getContent,document.getContent());
//        wrapper.match(Document::getTitle,document.getTitle());
        return documentMapper.pageQuery(wrapper,pageInfo.getPageNum(),pageInfo.getPageSize());
    }

    @Override
    public List<Document> selectDocumentList(String title) {
        LambdaEsQueryWrapper<Document> wrapper = new LambdaEsQueryWrapper<>();
        wrapper.eq(Document::getTitle, title);
        return documentMapper.selectList(wrapper);
    }

    @Override
    public Integer insertDocument(Document document) {
        return documentMapper.insert(document);
    }

    @Override
    public Integer deleteById(String id) {
        return documentMapper.deleteById(id);
    }

    @Override
    public Integer updateById(Document document) {
        return documentMapper.updateById(document);
    }

    @Override
    public Integer update(Document document, LambdaEsUpdateWrapper updateWrapper) {
        return documentMapper.update(document,updateWrapper);
    }
}
