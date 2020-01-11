package com.isapsw.project.common;

import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;

@Component
public class TimeProvider implements Serializable {
    private static final long serialVersionUID = 0;

    public Date now() {
        return new Date();
    }
}
