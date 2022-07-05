package com.decathlon.sports.dto;

import java.util.List;

public class SportFullDataDTO {

    private List<SportDTO> data;

    public SportFullDataDTO() {
    }

    public SportFullDataDTO(List<SportDTO> data) {
        this.data = data;
    }

    public List<SportDTO> getData() {
        return data;
    }

    public void setData(List<SportDTO> data) {
        this.data = data;
    }


}
