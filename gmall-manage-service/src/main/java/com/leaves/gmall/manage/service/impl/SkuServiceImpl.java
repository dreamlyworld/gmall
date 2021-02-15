package com.leaves.gmall.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.leaves.gmall.manage.dao.PmsSkuAttrValueMapper;
import com.leaves.gmall.manage.dao.PmsSkuImageMapper;
import com.leaves.gmall.manage.dao.PmsSkuInfoMapper;
import com.leaves.gmall.manage.dao.PmsSkuSaleAttrValueMapper;
import com.leaves.gmall.model.PmsSkuAttrValue;
import com.leaves.gmall.model.PmsSkuImage;
import com.leaves.gmall.model.PmsSkuInfo;
import com.leaves.gmall.model.PmsSkuSaleAttrValue;
import com.leaves.gmall.service.SkuService;
import com.leaves.gmall.util.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;

import javax.persistence.Transient;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * @Author Chenweiwei
 * @Date 2021/1/27 15:50
 * @Version 1.0
 */

@Service
public class SkuServiceImpl implements SkuService {

    @Autowired
    PmsSkuInfoMapper pmsSkuInfoMapper;

    @Autowired
    PmsSkuAttrValueMapper pmsSkuAttrValueMapper;

    @Autowired
    PmsSkuSaleAttrValueMapper pmsSkuSaleAttrValueMapper;

    @Autowired
    PmsSkuImageMapper pmsSkuImageMapper;

    @Autowired
    RedisUtil redisUtil;

    @Override
    public void saveSkuInfo(PmsSkuInfo pmsProductInfo) {
        pmsSkuInfoMapper.insert(pmsProductInfo);
        String skuId = pmsProductInfo.getId();
        //插入图片关联
        List<PmsSkuImage> skuImageList = pmsProductInfo.getSkuImageList();
        for (PmsSkuImage pmsSkuImage : skuImageList) {
            pmsSkuImage.setSkuId(skuId);
            pmsSkuImageMapper.insertSelective(pmsSkuImage);
        }

        //插入平台属性关联
        List<PmsSkuAttrValue> skuAttrValueList = pmsProductInfo.getSkuAttrValueList();
        for (PmsSkuAttrValue pmsSkuAttrValue : skuAttrValueList) {
            pmsSkuAttrValue.setSkuId(skuId);
            pmsSkuAttrValueMapper.insertSelective(pmsSkuAttrValue);
        }


        //插入销售属性关联
        List<PmsSkuSaleAttrValue> skuSaleAttrValueList = pmsProductInfo.getSkuSaleAttrValueList();
        for (PmsSkuSaleAttrValue pmsSkuSaleAttrValue : skuSaleAttrValueList) {
            pmsSkuSaleAttrValue.setSkuId(skuId);
            pmsSkuSaleAttrValueMapper.insertSelective(pmsSkuSaleAttrValue);
        }
    }

    @Override
    public PmsSkuInfo getSkuByIdFromDB(String skuId) {
        PmsSkuInfo pmsSkuInfo = new PmsSkuInfo();
        pmsSkuInfo.setId(skuId);
        PmsSkuInfo pmsSkuInfo1 = pmsSkuInfoMapper.selectOne(pmsSkuInfo);

        PmsSkuImage pmsSkuImage = new PmsSkuImage();
        pmsSkuImage.setSkuId(skuId);
        List<PmsSkuImage> imageList = pmsSkuImageMapper.select(pmsSkuImage);
        pmsSkuInfo1.setSkuImageList(imageList);

        PmsSkuAttrValue pmsSkuAttrValue = new PmsSkuAttrValue();
        pmsSkuAttrValue.setSkuId(skuId);
        List<PmsSkuAttrValue>  pmsSkuAttrValueList = pmsSkuAttrValueMapper.select(pmsSkuAttrValue);
        pmsSkuInfo1.setSkuAttrValueList(pmsSkuAttrValueList);

        PmsSkuSaleAttrValue pmsSkuSaleAttrValue = new PmsSkuSaleAttrValue();
        pmsSkuSaleAttrValue.setSkuId(skuId);

        List<PmsSkuSaleAttrValue> skuSaleAttrValueList = pmsSkuSaleAttrValueMapper.select(pmsSkuSaleAttrValue);
        pmsSkuInfo1.setSkuSaleAttrValueList(skuSaleAttrValueList);

        return pmsSkuInfo1;
    }

    @Override
    public PmsSkuInfo getSkuById(String skuId,String remoteAddr) {
        String skuInfoKey = "sku-"+skuId+"info";
        String skuInfoLockKey= "sku-"+skuId+"info:lock";
        PmsSkuInfo pmsSkuInfo1 = new PmsSkuInfo();

        Jedis jedis = redisUtil.getJedis();
        if (jedis.exists(skuInfoKey)){
            String skuInfoString = jedis.get(skuInfoKey);
             pmsSkuInfo1 = JSON.parseObject(skuInfoString, PmsSkuInfo.class);

        }else {
            //设置分布式锁,过期时间十秒
            //设置token,使得用户查询时间超出过期时间时删锁进行验证,
            String token = UUID.randomUUID().toString();
            String OK = jedis.set(skuInfoLockKey,token,"nx","ex",10);
            if (StringUtils.isNotBlank(OK) && "OK".equals(OK)){
                System.out.println("拿到锁的ip："+remoteAddr);
                pmsSkuInfo1 = getSkuByIdFromDB(skuId);
                if (null != pmsSkuInfo1){
                    jedis.set(skuInfoKey,JSON.toJSONString(pmsSkuInfo1));
                }else {
                    //防止缓存穿透
                    jedis.setex(skuInfoKey,60*3,JSON.toJSONString(""));
                }
                //释放锁
                String lockTaken = jedis.get(skuInfoLockKey);
                String script ="if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
                jedis.eval(script, Collections.singletonList("lock"),Collections.singletonList(token));

                if (StringUtils.isNotBlank(lockTaken ) && token.equals(lockTaken)){
                    jedis.del(skuInfoLockKey);
                }
            }else {
                //设置失败，自旋，睡眠几秒后，再次在本线程中调用该方法
                System.out.println(remoteAddr);
                try {
                    Thread.sleep(3000l);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //错误代码
                //getSkuById(skuId);没有使用return
                return getSkuById(skuId,remoteAddr);
            }


        }
        jedis.close();
        return pmsSkuInfo1;
    }

    @Override
    public List<PmsSkuInfo> SkuSaleAttrValueListBySpu(String productId) {
        return pmsSkuInfoMapper.selectSkuSaleAttrValueListBySpu(productId);
    }
}
