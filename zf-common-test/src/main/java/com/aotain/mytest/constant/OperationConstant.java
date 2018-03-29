package com.aotain.mytest.constant;

/**
 * 操作类型枚举常量
 *
 * @author HP
 * @date 2018/03/29
 */
public enum OperationConstant {
    SAVE(1),
    UPDATE(2),
    DELETE(3);

    private int operationType;

    private OperationConstant(int operationType){
        this.operationType = operationType;
    }

    public int getOperationType(){
        return operationType;
    }

    public static void main(String[] args) {
        System.out.println(OperationConstant.SAVE.getOperationType());
    }
}
