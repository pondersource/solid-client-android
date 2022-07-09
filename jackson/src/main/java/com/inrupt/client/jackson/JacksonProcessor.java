/*
 * Copyright 2022 Inrupt Inc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to use,
 * copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the
 * Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
 * SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.inrupt.client.spi;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UncheckedIOException;

/**
 * A {@link JsonProcessor} using the Jackson JSON library.
 */
public class JacksonProcessor implements JsonProcessor {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Override
    public <T> void toJson(final T object, final OutputStream output) {
        try {
            MAPPER.writeValue(output, object);
        } catch (final IOException ex) {
            throw new UncheckedIOException("Error writing JSON", ex);
        }
    }

    @Override
    public <T> T fromJson(final InputStream input, final Class<T> clazz) {
        try {
            return MAPPER.readValue(input, clazz);
        } catch (final IOException ex) {
            throw new UncheckedIOException("Error reading from JSON", ex);
        }
    }
}

