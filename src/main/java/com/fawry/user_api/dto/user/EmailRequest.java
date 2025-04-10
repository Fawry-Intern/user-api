package com.fawry.user_api.dto.user;

import jakarta.validation.constraints.Email;

public record EmailRequest(@Email String email) {
}
