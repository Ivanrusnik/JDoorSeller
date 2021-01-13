package com.jds.model.BackResponse;

import com.jds.dao.entity.DoorsОrder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class OrderResponse extends BackResponse {
    private DoorsОrder data;

    public OrderResponse(DoorsОrder order) {
        this.data = order;
        setSuccess(true);
        setMessage("Ok");
    }

    public OrderResponse(boolean success, String message) {
        super(success, message);
    }

    public OrderResponse(){

    }

}
