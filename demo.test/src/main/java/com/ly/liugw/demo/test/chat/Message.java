package com.ly.liugw.demo.test.chat;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * 消息
 */
@Data
public class Message implements Serializable {
    private Integer index;
    private Integer type;
    private String msg;

    public Message(int index, int type, String msg) {
        this.index = index;
        this.type = type;
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "Message{" +
                "index=" + index +
                ", type=" + type +
                ", msg='" + msg + '\'' +
                '}';
    }
}