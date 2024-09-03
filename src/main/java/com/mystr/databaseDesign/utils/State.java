package com.mystr.databaseDesign.utils;

public enum State {
    /**
     * 账号状态
     */
    //可用
    ACCOUNT_ACTIVE(100),
    //冻结
    ACCOUNT_FROZEN(101),

    /**
     * 航线状态
     */
    //启用
    AIRLINE_ACTIVE(200),
    //停用
    AIRLINE_INACTIVE(201),

    /**
     * 航班状态
     */
    //正常到达
    FLIGHT_ARRIVED(300),
    //正在检票
    FLIGHT_CHECKING(301),
    //正在途中
    FLIGHT_RUNNING(302),
    //延误
    FLIGHT_DELAYED(303),
    //取消
    FLIGHT_CANCELED(304),

    /**
     * 班机状态
     */
    //正常运行
    PLANE_NORMAL(400),
    //检修
    PLANE_FIX(401),
    //已报废
    PLANE_DESERTED(402),

    /**
     * 订单状态
     */
    //已提交
    ORDER_SUBMITTED(500),
    //已完成
    ORDER_FINISHED(501),
    //已取消
    ORDER_CANCELED(502),
    //已过期
    ORDER_OVERTIME(503);

    public int code;

    State(int code) {
        this.code = code;
    }
}