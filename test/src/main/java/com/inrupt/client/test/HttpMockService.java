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
package com.inrupt.client.test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.common.ClasspathFileSource;
import com.github.tomakehurst.wiremock.common.FileSource;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;

import java.util.Collections;
import java.util.Map;

/**
 * A {@link WireMockServer}-based HTTP service used for testing HTTP services.
 */
class HttpMockService {

    private static final String CONTENT_TYPE = "Content-Type";
    private static final String TEXT_TURTLE = "text/turtle";

    private final WireMockServer wireMockServer;

    public HttpMockService() {
        wireMockServer = new WireMockServer(WireMockConfiguration.options()
                .dynamicPort()
                .fileSource(new ClasspathFileSourceWithoutLeadingSlash()));
    }

    /*
    * Without this class Wiremock tries to find the mappings directory under /__files
    * and the classloader will not find this directory because of the leading slash.
    * This class removes the leading slash and as a consequence the classloader will
    * find the mappings directory.
    * See: https://github.com/wiremock/wiremock/issues/504#issuecomment-383869098
    */
    class ClasspathFileSourceWithoutLeadingSlash extends ClasspathFileSource {

        ClasspathFileSourceWithoutLeadingSlash() {
            super("");
        }

        @Override
        public FileSource child(final String subDirectoryName) {
            return new ClasspathFileSource(subDirectoryName);
        }
    }

    public int getPort() {
        return wireMockServer.port();
    }

    private void setupMocks() {

        wireMockServer.stubFor(get(urlEqualTo("/file"))
                    .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader(CONTENT_TYPE, "text/plain")
                        .withBodyFile("clarissa-sample.txt")));

        wireMockServer.stubFor(get(urlEqualTo("/example"))
                    .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader(CONTENT_TYPE, TEXT_TURTLE)
                        .withBodyFile("profileExample.ttl")));

        wireMockServer.stubFor(post(urlEqualTo("/rdf/"))
                    .withRequestBody(equalTo(
                            "<http://example.test/s> " +
                            "<http://example.test/p> \"object\" ."))
                    .withHeader(CONTENT_TYPE, containing(TEXT_TURTLE))
                    .willReturn(aResponse()
                        .withStatus(201)));

        wireMockServer.stubFor(get(urlEqualTo("/solid.png"))
                    .willReturn(aResponse()
                        .withHeader(CONTENT_TYPE, "image/png")
                        .withBodyFile("SolidOS.png")
                        .withStatus(200)));

        wireMockServer.stubFor(patch(urlEqualTo("/rdf"))
                    .withRequestBody(equalTo(
                            "INSERT DATA { " +
                            "<http://example.test/s> " +
                            "<http://example.test/p> \"data\" . }"))
                    .withHeader(CONTENT_TYPE, containing("application/sparql-update"))
                    .willReturn(aResponse()
                        .withStatus(204)));

        wireMockServer.stubFor(delete(urlEqualTo("/rdf"))
                    .willReturn(aResponse()
                        .withStatus(204)));
    }

    public Map<String, String> start() {
        wireMockServer.start();

        setupMocks();
        return Collections.singletonMap("http_uri", wireMockServer.baseUrl());
    }

    public void stop() {
        wireMockServer.stop();
    }

}
