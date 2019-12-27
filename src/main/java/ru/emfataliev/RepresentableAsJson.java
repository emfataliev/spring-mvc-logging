package ru.emfataliev;

import com.google.gson.JsonElement;

/**
 * @author e.fataliev
 * @since 28.12.2019
 */
public interface RepresentableAsJson {

    JsonElement asJson();
}
