/*
 * Copyright 2023 Inrupt Inc.
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
package com.inrupt.client.jsonb;

import com.inrupt.client.VerifiableCredential;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonString;
import javax.json.JsonValue;
import javax.json.bind.adapter.JsonbAdapter;

public class VCAdapter implements JsonbAdapter<VerifiableCredential, JsonObject> {

    private static final String CONTEXT = "@context";
    private static final String TYPE = "type";
    private static final String ID = "id";
    private static final String ISSUER = "issuer";
    private static final String ISSUANCE_DATE = "issuanceDate";
    private static final String EXPIRATION_DATE = "expirationDate";
    private static final String CREDENTIAL_SUBJECT = "credentialSubject";
    private static final String CREDENTIAL_STATUS = "credentialStatus";
    private static final String PROOF = "proof";

    @Override
    public JsonObject adaptToJson(final VerifiableCredential vc) throws Exception {

        final var result = Json.createObjectBuilder();

        if (vc.context != null) {
            final var arrayBuilder = Json.createArrayBuilder();
            vc.context.forEach(arrayBuilder::add);
            result.add(CONTEXT, arrayBuilder.build());
        }

        if (vc.type != null) {
            final var arrayBuilder = Json.createArrayBuilder();
            vc.type.forEach(arrayBuilder::add);
            result.add(TYPE, arrayBuilder.build());
        }

        if (vc.id != null) {
            result.add(ID, vc.id);
        }

        if (vc.issuer != null) {
            result.add(ISSUER, vc.issuer);
        }

        if (vc.issuanceDate != null) {
            result.add(ISSUANCE_DATE, vc.issuanceDate.toString());
        }

        if (vc.expirationDate != null) {
            result.add(EXPIRATION_DATE, vc.expirationDate.toString());
        }

        if (vc.credentialSubject != null) {
            final var objectBuilder = Json.createObjectBuilder();
            for (final var oneCS : vc.credentialSubject.entrySet()) {
                addRightJsonType(objectBuilder, oneCS);
            }
            result.add(CREDENTIAL_SUBJECT, objectBuilder.build());
        }

        if (vc.credentialStatus != null) {
            final var objectBuilder = Json.createObjectBuilder();
            for (final var oneCS : vc.credentialStatus.entrySet()) {
                addRightJsonType(objectBuilder, oneCS);
            }
            result.add(CREDENTIAL_STATUS, objectBuilder.build());
        }

        if (vc.proof != null) {
            final var objectBuilder = Json.createObjectBuilder();
            for (final var oneProof : vc.proof.entrySet()) {
                addRightJsonType(objectBuilder, oneProof);
            }
            result.add(PROOF, objectBuilder.build());
        }

        return result.build();
    }

    @Override
    public VerifiableCredential adaptFromJson(final JsonObject adapted) throws Exception {
        final var vc = new VerifiableCredential();

        if (adapted.containsKey(CONTEXT)) {
            vc.context = new ArrayList<String>();
            adapted.getJsonArray(CONTEXT).forEach(value -> vc.context.add(((JsonString) value).getString()));
        }

        if (adapted.containsKey(ID)) {
            vc.id = adapted.getString(ID);
        }

        if (adapted.containsKey(TYPE)) {
            vc.type = new ArrayList<String>();
            adapted.getJsonArray(TYPE).forEach(value -> vc.type.add(((JsonString) value).getString()));
        }

        if (adapted.containsKey(ISSUER)) {
            vc.issuer = adapted.getString(ISSUER);
        }

        if (adapted.containsKey(ISSUANCE_DATE)) {
            vc.issuanceDate = Instant.parse(adapted.getString(ISSUANCE_DATE));
        }

        if (adapted.containsKey(EXPIRATION_DATE)) {
            vc.expirationDate = Instant.parse(adapted.getString(EXPIRATION_DATE));
        }

        if (adapted.containsKey(CREDENTIAL_SUBJECT)) {
            vc.credentialSubject = new HashMap<String, Object>();
            vc.credentialSubject.putAll(adapted.getJsonObject(CREDENTIAL_SUBJECT));
        }

        if (adapted.containsKey(CREDENTIAL_STATUS)) {
            vc.credentialStatus = new HashMap<String, Object>();
            vc.credentialStatus.putAll(adapted.getJsonObject(CREDENTIAL_STATUS));
        }

        if (adapted.containsKey(PROOF)) {
            vc.proof = new HashMap<String, Object>();
            vc.proof.putAll(adapted.getJsonObject(PROOF));
        }

        return vc;
    }

    private <T> void addRightJsonType(final JsonObjectBuilder objectBuilder, final Entry<String, T> entry) {
        if (entry.getValue() instanceof JsonString) {
            objectBuilder.add(entry.getKey(), (JsonString) entry.getValue());
        }
        if (entry.getValue() instanceof JsonNumber) {
            objectBuilder.add(entry.getKey(), (JsonNumber) entry.getValue());
        }
        if (entry.getValue() instanceof JsonArray) {
            final var arrayBuilder = Json.createArrayBuilder();
            ((JsonArray) entry.getValue()).forEach(arrayBuilder::add);
            objectBuilder.add(entry.getKey(), arrayBuilder.build());
        }
        if (entry.getValue() instanceof JsonValue) {
            objectBuilder.add(entry.getKey(), (JsonValue) entry.getValue());
        }
        if (entry.getValue() instanceof JsonObject) {
            final var object = Json.createObjectBuilder();
            for (final var it : ((JsonObject)entry.getValue()).entrySet()) {
                addRightJsonType(object, it);
            }
            objectBuilder.add(entry.getKey(), object);
        }
    }

}
