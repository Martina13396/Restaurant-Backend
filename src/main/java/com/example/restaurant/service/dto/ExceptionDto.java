package com.example.restaurant.service.dto;

import com.example.restaurant.helper.BundleMessage;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "Exception Dto" , description = "Exception Dto to handle bundle message")
public class ExceptionDto {
    @Schema(name = "Bundle Message", description = "Arabic and English messages" , example = "products not found")
    private BundleMessage  bundleMessage;
}
