/**
 * Copyright 2016 NAVER Corp.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.navercorp.pinpoint.bootstrap.interceptor;

/**
 * @author jaehong.kim
 */
public class ExceptionHandleAroundInterceptor implements AroundInterceptor {

    private final AroundInterceptor delegate;

    public ExceptionHandleAroundInterceptor(AroundInterceptor delegate) {
        if (delegate == null) {
            throw new NullPointerException("delegate must not be null");
        }

        this.delegate = delegate;
    }

    @Override
    public void before(Object target, Object[] args) {
        try {
            this.delegate.before(target, args);
        } catch (Throwable t) {
            InterceptorInvokerHelper.handleException(t);
        }
    }

    @Override
    public void after(Object target, Object[] args, Object result, Throwable throwable) {
        try {
            this.delegate.after(target, args, result, throwable);
        } catch (Throwable t) {
            InterceptorInvokerHelper.handleException(t);
        }
    }
}