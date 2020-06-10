package pt.up.hs.linguistics.nlp.serializers;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;

public final class JavaTypeHandler {

    static <T> JavaType getJavaType(Class<T> clazz) {
        return TypeFactory.defaultInstance().constructType(clazz);
    }
}
