package com.qy.easyes.util;

import cn.easyes.core.conditions.AbstractWrapper;
import cn.hutool.core.util.ReflectUtil;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.Objects;


/**
 * LambdaUtils
 * @author qiaoyan
 * @date 2022-12-29 17:10:56
 */
@Slf4j
public class LambdaUtils {

    /**
     * 根据实体(非null数据)构建wrapper
     * @param wrapper       要构建的wrapper
     * @param entity        实体对象
     * @param functions     自定义处理,默认为非空属性设置match条件
     * @author qiaoyan
     * @date 2022年12月29日 16:40:04
     */
    public static <T> void buildByEntity(AbstractWrapper wrapper, T entity, WaFunction<AbstractWrapper,String,Object> ... functions){
        try {
            Class<?> clazz = entity.getClass();
            Field[] fields = ReflectUtil.getFields(clazz);
            for (Field field : fields) {
                field.setAccessible(true);
                Object o = field.get(entity);
                String fieldName = field.getName();
                if(functions.length == 0){
                    if(Objects.nonNull(o)){
                        wrapper.match(fieldName,o);
                    }
                }else{
                    functions[0].apply(wrapper,fieldName,o);
                }
            }
        } catch (IllegalAccessException e) {
            log.error("buildByEntity >> IllegalAccessException:",e);
            throw new RuntimeException("buildByEntity >> IllegalAccessException");
        }
    }

    public interface WaFunction<AbstractWrapper,String,Object>{
        void apply(AbstractWrapper a, String fieldName, Object value);
    }

}
