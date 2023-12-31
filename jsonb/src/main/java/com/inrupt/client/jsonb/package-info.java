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
 * <h2>JakartaEE JSON Bind support for the Inrupt Java Client Libraries</h2>
 *
 * <p>Many of the high-level APIs in the Inrupt Java Client Libraries make use of a
 * JSON parser. In the Java ecosystem, there are several widely used JSON
 * parsing libraries. This package adds support for the JakartaEE 8 JSON APIs.
 *
 * <p>This module depends on the JakartaEE APIs. A user of the {@code JsonbService} should
 * ensure that the relevant JakartaEE JSON implementations are available on the
 * classpath in addition to Jsonb.
 * Example:
 *
 * <pre>
 *     &lt;dependency&gt;
 *            &lt;groupId&gt;com.inrupt&lt;/groupId&gt;
 *            &lt;artifactId&gt;inrupt-client-jsonb&lt;/artifactId&gt;
 *            &lt;version&gt;${project.version}&lt;/version&gt;
 *     &lt;/dependency&gt;
 * </pre>
 *
 * <h3>Example of using the JSON service fromJson() method to deserialize data into a custom type:</h3>
 *
 * <pre>{@code
    JsonService service = ServiceProvider.getJsonService();
    try (InputStream is = Test.class.getResourceAsStream("customType.json")) {
        CustomType obj = service.fromJson(is, CustomType.class);
        System.out.println("Custom Type Id is: " + obj.id);
    }
 * }</pre>
 */
package com.inrupt.client.jsonb;
