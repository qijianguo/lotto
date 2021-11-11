package com.qijianguo.game.model.vo;

import lombok.Data;

import java.util.Map;

@Data
public class GameStatResp {

    private Integer maxNum;
    private Integer maxCredit;

    private Integer minNum;
    private Integer minCredit;


    public static GameStatResp create(Map<Integer, Integer> numberCreditMap) {
        GameStatResp resp = new GameStatResp();
        int maxNum = 0, minNum = 0, maxCredit = 0, minCredit = 0;
        for (Integer num : numberCreditMap.keySet()) {
            Integer credit = numberCreditMap.get(num);
            if (maxCredit < credit) {
                maxNum = num;
                maxCredit += credit;
            } else if (minCredit > credit){
                minNum = num;
                minCredit = credit;
            }
        }
        resp.setMaxNum(maxNum);
        resp.setMaxCredit(maxCredit);
        resp.setMinNum(minNum);
        resp.setMinCredit(minCredit);
        return resp;
    }
}
