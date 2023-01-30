package com.qy.easyes.controller;

import cn.easyes.core.biz.EsPageInfo;
import cn.easyes.core.conditions.LambdaEsUpdateWrapper;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.qy.easyes.model.Document;
import com.qy.easyes.service.IEasyEsService;
import com.qy.easyes.util.LambdaUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * EasyEsController
 *
 * @author qiaoyan
 * @date 2022-12-29 10:30:05
 */
@Slf4j
@RestController
//@RequiredArgsConstructor(onConstructor = @__(@Autowired)) 配合 final注入
public class EasyEsController {

    @Resource
    private IEasyEsService easyEsService;

    /**
     * 文档插入
     * @param document
     * @author qiaoyan
     * @date 2022年12月29日 10:58:16
     * @return java.lang.Integer
     */
    @PostMapping(value = "/insert")
    public Integer insertDocument(@Validated @RequestBody Document document) {
        return easyEsService.insertDocument(document);
    }

    @PostMapping(value = "/selectByPage")
    public EsPageInfo<Document> selectByPage(@RequestBody Map<String,Object> map) {
        EsPageInfo pageInfo = BeanUtil.mapToBean(
                (LinkedHashMap)map.get("pageInfo"),
                EsPageInfo.class,
                false,
                CopyOptions.create().setIgnoreNullValue(true).setIgnoreError(true)
                );
        Document document = BeanUtil.mapToBean(
                (LinkedHashMap)map.get("document"),
                Document.class,
                false,
                CopyOptions.create().setIgnoreNullValue(true).setIgnoreError(true)
        );
        return easyEsService.selectByPage(document,pageInfo);
    }

    /**
     * 根据title查询
     * @param title
     * @author qiaoyan
     * @date 2022年12月29日 10:59:02
     * @return java.util.List<com.qy.easyes.model.Document>
     */
    @GetMapping("/search/{title}")
    public List<Document> selectDocumentList(@PathVariable String title) {
        return easyEsService.selectDocumentList(title);
    }

    @GetMapping("/selectById/{id}")
    public Document selectById(@PathVariable String id) {
        return easyEsService.selectById(id);
    }

    @PostMapping("updateById")
    public Integer updateById(@RequestBody Document document) {
        return easyEsService.updateById(document);
    }

    @PostMapping("update")
    public Integer update(@RequestBody List<Document> documents) {
        if(CollectionUtils.isEmpty(documents) || documents.size()!=2){
            log.error("入参错误: {}",documents);
            throw new RuntimeException("入参错误");
        }
        LambdaEsUpdateWrapper wrapper = new LambdaEsUpdateWrapper();
        LambdaUtils.buildByEntity(wrapper,documents.get(1),(wra,fieldName,value)->{
            if(Objects.nonNull(value)){
                wra.like(fieldName,value);
            }
        });
        return easyEsService.update(documents.get(0),wrapper);
    }
}
