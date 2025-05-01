package com.innervoice.conversation.domain;

import lombok.Data;

@Data
public class SignalingMessage {

    private String type;
    private Long roomId;
    private Object payload;
}