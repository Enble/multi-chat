package com.enble.auth.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum IdGeneratorHolder {

    MEMBER(IdGenerator.newInstance()),
    ;

    private final IdGenerator idGenerator;
}
