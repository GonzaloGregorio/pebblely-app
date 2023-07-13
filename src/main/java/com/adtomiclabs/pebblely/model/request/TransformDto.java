package com.adtomiclabs.pebblely.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransformDto {

    private Double scaleX;

    private Double scaleY;

    private Integer x;

    private Integer y;

    private Double angle;

}
