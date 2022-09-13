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
package com.inrupt.client.rdf4j;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.eclipse.rdf4j.http.client.SPARQLProtocolSession;
import org.eclipse.rdf4j.model.util.ModelBuilder;
import org.eclipse.rdf4j.repository.sail.SailRepository;
import org.eclipse.rdf4j.repository.sparql.query.SPARQLUpdate;
import org.eclipse.rdf4j.sail.memory.MemoryStore;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RDF4JBodyPublishersTest {

    private static final RDF4JMockHttpService mockHttpService = new RDF4JMockHttpService();
    private static final Map<String, String> config = new HashMap<>();
    private static final HttpClient client = HttpClient.newHttpClient();

    @BeforeAll
    static void setup() {
        config.putAll(mockHttpService.start());
    }

    @AfterAll
    static void teardown() {
        mockHttpService.stop();
    }

    @Test
    void testOfModelPublisher() throws IOException, InterruptedException {

        final var builder = new ModelBuilder();
        builder.namedGraph(RDF4JTestModel.G_RDF4J)
                .subject(RDF4JTestModel.S_VALUE)
                    .add(RDF4JTestModel.P_VALUE, RDF4JTestModel.O_VALUE);
        builder.defaultGraph().subject(RDF4JTestModel.S1_VALUE).add(RDF4JTestModel.P_VALUE, RDF4JTestModel.O1_VALUE);
        final var model = builder.build();

        final HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(config.get("rdf4j_uri") + "/postOneTriple"))
                .header("Content-Type", "text/turtle")
                .POST(RDF4JBodyPublishers.ofModel(model))
                .version(HttpClient.Version.HTTP_1_1)
                .build();

        final var response = client.send(request, HttpResponse.BodyHandlers.discarding());

        assertEquals(204, response.statusCode());
    }

    @Test
    void testOfRepositoryPublisher() throws IOException, InterruptedException {

        final var st = RDF4JTestModel.VF.createStatement(
            RDF4JTestModel.S_RDF4J,
            RDF4JTestModel.P_RDF4J,
            RDF4JTestModel.O_RDF4J,
            RDF4JTestModel.G_RDF4J
        );
        final var st1 = RDF4JTestModel.VF.createStatement(
            RDF4JTestModel.S1_RDF4J,
            RDF4JTestModel.P1_RDF4J,
            RDF4JTestModel.O1_RDF4J
        );
        final var repository = new SailRepository(new MemoryStore());
        try (final var conn = repository.getConnection()) {
            conn.add(st);
            conn.add(st1);
        }

        final HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(config.get("rdf4j_uri") + "/postOneTriple"))
                .header("Content-Type", "text/turtle")
                .POST(RDF4JBodyPublishers.ofRepository(repository))
                .version(HttpClient.Version.HTTP_1_1)
                .build();

        final var response = client.send(request, HttpResponse.BodyHandlers.discarding());

        assertEquals(204, response.statusCode());
    }

    @Test
    void testOfSPARQLUpdatePublisher() throws IOException, InterruptedException {

        final var updateString =
            "INSERT DATA { <http://example.test/s1> <http://example.test/p1> <http://example.test/o1> .}";

        final var executorService = Executors.newFixedThreadPool(2);

        try (final CloseableHttpClient httpclient = HttpClients.createDefault()) {
            final var sparqlProtocolSession = new SPARQLProtocolSession(httpclient, executorService);
            final SPARQLUpdate sU = new SPARQLUpdate(
                sparqlProtocolSession,
                "http://example.test",
                updateString
            );
            final HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(config.get("rdf4j_uri") + "/sparqlUpdate"))
                    .header("Content-Type", "application/sparql-update")
                    .method("PATCH", RDF4JBodyPublishers.ofSparqlUpdate(sU))
                    .build();
            final var response = client.send(request, HttpResponse.BodyHandlers.discarding());
            assertEquals(204, response.statusCode());
        }
    }

}