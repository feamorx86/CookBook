package com.feamorx.cookebook.parsers;

/**
 * Created by x86 on 08.01.2017.
 */

public interface Serializer {
    Object save(Object model, Object ... args);
    Object load(Object data, Object ...args);
}
