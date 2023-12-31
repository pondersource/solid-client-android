/*
 * Copyright Inrupt Inc.
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

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;

/**
 * A JSON handling abstraction.
 */
public interface JsonService {

    /**
     * Write object data into JSON.
     *
     * @param <T> the object type
     * @param object the object to serialize
     * @param output the output stream
     * @throws IOException when there is a serialization error
     */
    <T> void toJson(T object, OutputStream output) throws IOException;

    /**
     * Read JSON into a java object.
     *
     * @param <T> the object type
     * @param input the input stream
     * @param clazz the object class
     * @return the newly created object
     * @throws IOException when there is a parsing error
     */
    <T> T fromJson(InputStream input, Class<T> clazz) throws IOException;

    /**
     * Read JSON into a java object.
     *
     * @param <T> the object type
     * @param input the input stream
     * @param type the runtime type of the object
     * @return the newly created object
     * @throws IOException when there is a parsing error
     */
    <T> T fromJson(InputStream input, Type type) throws IOException;

}
