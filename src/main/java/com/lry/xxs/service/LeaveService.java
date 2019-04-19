package com.lry.xxs.service;

import com.lry.xxs.mapper.LeaveMapper;
import com.lry.xxs.utils.PageData;
import com.lry.xxs.utils.UuidUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class LeaveService {

    @Autowired
    private LeaveMapper leaveMapper;

    public void add(PageData pd) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        pd.put("updata_time", sdf.format(date));
        pd.put("leave_id", UuidUtil.get32UUID());
        pd.put("delect_is", 0);
        leaveMapper.add(pd);
    }

    public void delete(PageData pd) {
        pd.put("delect_is", 1);
        leaveMapper.updateById(pd);
    }

    public List<PageData> select(PageData pd) throws Exception {
        List<PageData> list = leaveMapper.select1(pd);
        for (PageData kar : list) {
            kar.put("user_name", new String(Base64.decodeBase64(kar.getString("user_name")), "UTF-8"));
            kar.put("u1name", new String(Base64.decodeBase64(kar.getString("u1name")), "UTF-8"));
            Date date = (Date) kar.get("updata_time");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            kar.put("updata_time",sdf.format(date));
            List<PageData> tempList = new ArrayList<>();
            PageData hf = new PageData();
            hf.put("leave_id", kar.getString("leave_id"));
            while (true) {
                PageData aiyawei = new PageData();
                aiyawei.put("link_leave_id", hf.getString("leave_id"));
                List<PageData> sgl = leaveMapper.select(aiyawei);
                if (sgl.size() == 0) {
                    break;
                } else {
                    hf = sgl.get(0);
                    hf.put("user_name", new String(Base64.decodeBase64(hf.getString("user_name")), "UTF-8"));
                    hf.put("u1name", new String(Base64.decodeBase64(hf.getString("u1name")), "UTF-8"));
                    date = (Date) hf.get("updata_time");
                    hf.put("updata_time",sdf.format(date));
                    tempList.add(hf);
                }
            }
            kar.put("children", tempList);
        }
        return list;
    }
}
