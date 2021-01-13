package com.jds.model.BackResponse;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class BackResponse {
    private Boolean success;
    private String message;

    public BackResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public BackResponse() {
    }

}
