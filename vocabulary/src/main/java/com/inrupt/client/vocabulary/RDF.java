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
package com.inrupt.client.vocabulary;

import java.net.URI;

/**
 * URI constants from the RDF vocabulary.
 *
 * @see <a href="http://www.w3.org/1999/02/22-rdf-syntax-ns#">RDF Vocabulary</a>
 */
public final class RDF {

    private static String namespace = "http://www.w3.org/1999/02/22-rdf-syntax-ns#";

    // Properties
    /**
     * The rdf:type URI.
     */
    public static final URI type = URI.create(namespace + "type");

    /**
     * Get the RDF namespace URI.
     *
     * @return the RDF namespace
     */
    public static URI getNamespace() {
        return URI.create(namespace);
    }

    private RDF() {
        // Prevent instantiation
    }
}
