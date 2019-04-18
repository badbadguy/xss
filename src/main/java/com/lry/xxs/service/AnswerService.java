package com.lry.xxs.service;

import com.lry.xxs.mapper.AnswerMapper;
import com.lry.xxs.utils.PageData;
import org.apache.commons.lang.StringUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class AnswerService {

    @Autowired
    private AnswerMapper answerMapper;
    @Autowired
    private UserService userService;

    public void add(PageData pd) {
        answerMapper.add(pd);
    }

    public void deleteById(String id) {
        answerMapper.deleteById(id);
    }

    public void updateById(PageData pd) {
        answerMapper.updateById(pd);
    }

    public List<PageData> select(PageData pd) {
        return answerMapper.select(pd);
    }

    public List<PageData> selectRanking(PageData pd) throws Exception {
        List<PageData> returnList = answerMapper.selectRanking();
        List<PageData> tempList = new ArrayList<>();
        PageData tiaojian = new PageData();
        tiaojian.put("user_type", 3);
        List<PageData> userList = userService.select(tiaojian);
        for (PageData tempPD : returnList) {
            if (StringUtils.isNotBlank(tempPD.getString("subject_id")) && tempPD.getString("subject_id").equals(pd.getString("subject_id"))) {
                tempList.add(tempPD);
            }
        }
        for (PageData tempPD : tempList) {
            if (tempPD.containsKey("s") && StringUtils.isNotBlank(tempPD.getString("s"))) {
                String tempName = new String(Base64.decodeBase64(tempPD.getString("s")), "UTF-8");
                tempPD.put("s", tempName);
            }
        }
        for (PageData fenglba : userList) {
            for (PageData tempPD : tempList) {
                if (fenglba.getString("user_id").equals(tempPD.getString("user_id"))) {
                    fenglba.putAll(tempPD);
                }
            }
            if (!fenglba.containsKey("c")) {
                String wdmy = fenglba.getString("user_name");
                fenglba.put("s", wdmy);
                fenglba.put("c", 0L);
            }
        }
        Collections.sort(userList, new Comparator<PageData>() {
            @Override
            public int compare(PageData o1, PageData o2) {
                Long temp1 = (Long) o1.get("c");
                Long temp2 = (Long) o2.get("c");
                Long temp = temp1 - temp2;
                return temp.intValue();
            }
        });
        return userList;
    }
}
