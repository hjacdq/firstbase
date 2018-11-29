package com.hj.model;

import java.io.Serializable;

/**
 * 返回数据
 */
public class ResultMap implements Serializable, Cloneable {

	private static final long serialVersionUID = -8438869231059830817L;
	
	private Object data;
    private String message;
    private boolean success;

    public static ResultMap failure(String errorMessage) {
    	ResultMap result = new ResultMap();
        result.message = errorMessage;
        result.success = false;
        return result;
    }
    
    public static ResultMap success(Object data) {
    	ResultMap result = new ResultMap();
        result.message = "";
        result.data = data;
        result.success = true;
        return result;
    }

    public static ResultMap success() {
    	ResultMap result = new ResultMap();
        result.message = "";
        result.data = "success";
        result.success = true;
        return result;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
