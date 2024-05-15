package com.raghad.LibraryManagementSystem.DTOs;

import java.util.List;

public record ErrorResponse(String description,
                            List<ErrorDetail> errorDetails) {
}

