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
package com.inrupt.client.httpclient;

import com.inrupt.client.Headers;
import com.inrupt.client.Response.ResponseInfo;

import java.net.URI;
import java.net.http.HttpResponse;
import java.nio.ByteBuffer;

class HttpClientResponseInfo implements ResponseInfo {

    private final HttpResponse<byte[]> response;
    private final ByteBuffer responseBody;

    public HttpClientResponseInfo(final HttpResponse<byte[]> response, final ByteBuffer body) {
        this.response = response;
        this.responseBody = body;
    }

    @Override
    public Headers headers() {
        return Headers.of(response.headers().map());
    }

    @Override
    public URI uri() {
        return response.uri();
    }

    @Override
    public int statusCode() {
        return response.statusCode();
    }

    @Override
    public ByteBuffer body() {
        return responseBody;
    }
}
