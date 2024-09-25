package com.tp.hair_salon_app.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class ApiResponse<T>{
    private int statusCode;
    private T data;
}
