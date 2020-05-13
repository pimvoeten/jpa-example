package com.github.pimvoeten.jpa.example.exceptions;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
public class ErrorMessage {
    @JsonUnwrapped
    List<String> messages = new ArrayList<>();

    public ErrorMessage(String errormessage) {
        this.messages.add(errormessage);
    }

    public ErrorMessage(List<String> errormessages) {
        this.messages.addAll(errormessages);
    }

    public ErrorMessage(String[] errormessages) {
        this.messages.addAll(Arrays.asList(errormessages));
    }
}
