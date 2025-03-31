package com.fawry.user_api.dto.user;

import com.fawry.user_api.annotations.ValidPassword;
import jakarta.validation.constraints.NotNull;

public record PasswordChangeRequest(

                                    @NotNull
                                    String oldPassword,

                                    @NotNull
                                     @ValidPassword
                                     String newPassword) {
}
