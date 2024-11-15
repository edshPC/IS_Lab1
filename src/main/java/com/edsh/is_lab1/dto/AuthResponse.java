package com.edsh.is_lab1.dto;

import com.edsh.is_lab1.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse extends BaseResponse<AuthResponse> {
    private String token;
    private User logged_as;
}
