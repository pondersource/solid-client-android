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
/**
 *
 * <h2>WebID Profile support for the Inrupt Java Client Libraries.</h2>
 *
 * <p>This module contains a BodyHandler which consumes the actual response body bytes
 *  and converts them into a {@link WebIdProfile} Java object.
 *
 * <p>The following example reads a Solid WebID profile and presents it as a {@link WebIdProfile} Java object:
 *
 * <pre>{@code
        try (final WebIdProfile profile = client.read(URI.create("https://id.example/username"), WebIdProfile.class)) {
            System.out.println("WebID URI: " + profile.getIdentifier());
            for (final URI type : profile.getType()) {
                System.out.println("WebID Types: " + type);
            }
        }}
 * </pre>
 */
package com.inrupt.client.webid;
