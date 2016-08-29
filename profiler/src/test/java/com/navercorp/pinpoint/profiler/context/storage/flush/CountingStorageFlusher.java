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

package com.navercorp.pinpoint.profiler.context.storage.flush;

import com.navercorp.pinpoint.profiler.context.Span;
import com.navercorp.pinpoint.profiler.context.SpanChunk;
import com.navercorp.pinpoint.profiler.context.SpanEvent;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Taejin Koo
 */
public class CountingStorageFlusher implements StorageFlusher {

    private final AtomicInteger spanEventCounter = new AtomicInteger();
    private final AtomicInteger spanChunkCounter = new AtomicInteger();
    private final AtomicInteger spanCounter = new AtomicInteger();


    @Override
    public void flush(SpanEvent spanEvent) {
        spanEventCounter.incrementAndGet();
    }

    @Override
    public void flush(SpanChunk spanChunk) {
        spanChunkCounter.incrementAndGet();
    }

    @Override
    public void flush(Span span) {
        spanCounter.incrementAndGet();
    }

    public int getSpanEventCount() {
        return spanEventCounter.get();
    }

    public int getSpanChunkCount() {
        return spanChunkCounter.get();
    }

    public int getSpanCount() {
        return spanCounter.get();
    }

    @Override
    public String toString() {
        return "CountingStorageFlusher{" +
                "spanEventCount=" + spanEventCounter.get() +
                ", spanChunkCount=" + spanChunkCounter.get() +
                ", spanCount=" + spanCounter.get() +
                '}';
    }

}
