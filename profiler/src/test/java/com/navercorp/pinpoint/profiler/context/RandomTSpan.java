/*
 * Copyright 2016 NAVER Corp.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.navercorp.pinpoint.profiler.context;

import com.navercorp.pinpoint.common.util.TransactionIdUtils;
import com.navercorp.pinpoint.thrift.dto.TAnnotation;
import com.navercorp.pinpoint.thrift.dto.TAnnotationValue;
import com.navercorp.pinpoint.thrift.dto.TIntStringValue;
import com.navercorp.pinpoint.thrift.dto.TSpanEvent;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomTSpan {

    private final Random random = new Random();

    public Span randomSpan() {
        final Span span = new Span();
        span.setAgentId("agentId");
        span.setApplicationName("appName");
        span.setAgentStartTime(System.currentTimeMillis());

        span.setTransactionId(TransactionIdUtils.formatByteBuffer("agent", System.currentTimeMillis(), RandomUtils.nextLong(0, Long.MAX_VALUE)));
        span.setSpanId(random.nextLong());
        span.setParentSpanId(RandomUtils.nextInt(0, 100000));
        span.setStartTime(System.currentTimeMillis() + RandomUtils.nextInt(0, 1000));
        span.setElapsed(RandomUtils.nextInt(0, 2000));
        span.setRpc(RandomStringUtils.random(10));

        span.setServiceType(randomServerServiceType());
        span.setEndPoint(RandomStringUtils.random(20));
        span.setRemoteAddr(RandomStringUtils.random(20));

        List<TAnnotation> tAnnotationList = randomTAnnotationList();
        if (CollectionUtils.isNotEmpty(tAnnotationList)) {
            span.setAnnotations(tAnnotationList);
        }
        span.setFlag((short) RandomUtils.nextInt(0, 4));
        span.setErr((short) RandomUtils.nextInt(0, 2));
//        tSpan.setSpanEventList()
        span.setParentApplicationName("parentApp");
        span.setParentApplicationType(randomServerServiceType());
        span.setAcceptorHost("acceptHost");
        span.setApiId(RandomUtils.nextInt(0, 5000));
        if (random.nextBoolean()) {
            span.setApplicationServiceType(randomServerServiceType());
        } else {
            span.setApplicationServiceType(span.getServiceType());
        }
        if (random.nextBoolean()) {
            TIntStringValue exceptionInfo = new TIntStringValue();
            exceptionInfo.setIntValue(RandomUtils.nextInt(0, 5000));
            exceptionInfo.setStringValue(RandomStringUtils.random(100));
            span.setExceptionInfo(exceptionInfo);
        }
        span.setLoggingTransactionInfo((byte) RandomUtils.nextInt(0, 256));
        return span;
    }

    private short randomServerServiceType() {
        //        Server (1000 ~ 1899)
        return (short) RandomUtils.nextInt(1000, 1899);
    }

    public List<TAnnotation> randomTAnnotationList() {
        int annotationSize = RandomUtils.nextInt(0, 3);
        List<TAnnotation> result = new ArrayList<TAnnotation>();
        for (int i = 0; i < annotationSize; i++) {
            result.add(randomTAnnotation(i));
        }
        return result;
    }

    public TAnnotation randomTAnnotation(int key) {
        TAnnotation tAnnotation = new TAnnotation();
        // sort order
        tAnnotation.setKey(key);
        TAnnotationValue tAnnotationValue = new TAnnotationValue();
        tAnnotationValue.setStringValue(RandomStringUtils.random(10));
        tAnnotation.setValue(tAnnotationValue);
        return tAnnotation;
    }

    public TSpanEvent randomTSpanEvent(short sequence) {
        TSpanEvent tSpanEvent = new TSpanEvent();
//        @deprecated
//        tSpanEvent.setSpanId();
        tSpanEvent.setSequence(sequence);
        tSpanEvent.setStartElapsed(RandomUtils.nextInt(0, 1000));
        tSpanEvent.setEndElapsed(RandomUtils.nextInt(0, 1000));
        tSpanEvent.setRpc(RandomStringUtils.random(10));
//         Database (2000 ~ 2899)
        tSpanEvent.setServiceType((short) RandomUtils.nextInt(2000, 2889));
        tSpanEvent.setEndPoint(RandomStringUtils.random(10));

        List<TAnnotation> tAnnotationList = randomTAnnotationList();
        if (CollectionUtils.isNotEmpty(tAnnotationList)) {
            tSpanEvent.setAnnotations(tAnnotationList);
        }
        tSpanEvent.setDepth(RandomUtils.nextInt(0, 256));
        tSpanEvent.setNextSpanId(random.nextLong());

        tSpanEvent.setDestinationId(RandomStringUtils.random(20));
        tSpanEvent.setApiId(RandomUtils.nextInt(0, 65535));

        tSpanEvent.setAsyncId(randomNegative(RandomUtils.nextInt(0, 10)));
        tSpanEvent.setNextAsyncId(random.nextInt());
        tSpanEvent.setAsyncSequence((short) RandomUtils.nextInt(0, Short.MAX_VALUE));

        if (random.nextBoolean()) {
            TIntStringValue exceptionInfo = new TIntStringValue();
            exceptionInfo.setIntValue(RandomUtils.nextInt(0, 5000));
            exceptionInfo.setStringValue(RandomStringUtils.random(100));
            tSpanEvent.setExceptionInfo(exceptionInfo);
        }

        return tSpanEvent;
    }

    private int randomNegative(int value) {
        if (random.nextBoolean()) {
            return -value;
        }
        return value;
    }

    public SpanChunk randomSpanChunk() {
        final SpanChunk tSpanChunk = new SpanChunk(new ArrayList<SpanEvent>());
        tSpanChunk.setAgentId("agentId");
        tSpanChunk.setApplicationName("appName");
        tSpanChunk.setAgentStartTime(System.currentTimeMillis());

        tSpanChunk.setTransactionId(TransactionIdUtils.formatByteBuffer("agent", System.currentTimeMillis(), RandomUtils.nextLong(0, Long.MAX_VALUE)));
        tSpanChunk.setSpanId(random.nextLong());

        tSpanChunk.setServiceType(randomServerServiceType());
        tSpanChunk.setEndPoint(RandomStringUtils.random(20));

//        tSpanChunk.setSpanEventList()
        tSpanChunk.setApplicationServiceType(randomServerServiceType());
        return tSpanChunk;
    }


    public Span createSpan(int spanEventSize) {
        Span span = randomSpan();

        List<TSpanEvent> spanEventList = new ArrayList<TSpanEvent>(spanEventSize);
        for (int i = 0; i < spanEventSize; i++) {
            spanEventList.add(randomTSpanEvent((short) i));
        }

        span.setSpanEventList(spanEventList);
        return span;
    }

    public SpanChunk createSpanChunk(int spanEventSize) {
        SpanChunk spanChunk = randomSpanChunk();

        List<TSpanEvent> spanEventList = new ArrayList<TSpanEvent>(spanEventSize);
        for (int i = 0; i < spanEventSize; i++) {
            spanEventList.add(randomTSpanEvent((short) i));
        }

        spanChunk.setSpanEventList(spanEventList);
        return spanChunk;
    }


}
