package com.AsadBabayev.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiError<T> {

    private String id;

    private Date errorDate;

    private T errors;
}
