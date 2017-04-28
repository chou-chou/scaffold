/*
 * Copyright 2002-2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hrp.utils.net;

import java.util.concurrent.CountDownLatch;
import javax.servlet.http.HttpServletRequest;
import org.atmosphere.cpr.AtmosphereResource;
import org.atmosphere.cpr.AtmosphereResourceEvent;
import org.atmosphere.cpr.AtmosphereResourceEventListenerAdapter;
import org.atmosphere.cpr.Meteor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * atmosphere
 */
public final class AtmosphereUtils {
	public static final Logger LOG = LoggerFactory.getLogger(AtmosphereUtils.class);

	/**
	 * AtmosphereResource
	 */
	public static AtmosphereResource getAtmosphereResource(final HttpServletRequest request) {
		return getMeteor(request).getAtmosphereResource();
	}

	/**
	 * getMeteor
	 */
	public static Meteor getMeteor(final HttpServletRequest request) {
		return Meteor.build(request);
	}

	/**
	 * suspend
	 */
	public static void suspend(final AtmosphereResource resource) {
		final CountDownLatch countDownLatch = new CountDownLatch(1);
		resource.addEventListener(new AtmosphereResourceEventListenerAdapter() {
			@Override
			public void onSuspend(final AtmosphereResourceEvent event) {
				countDownLatch.countDown();
				resource.removeEventListener(this);
			}
		});
		resource.suspend();
		// If a BroadcasterCache is used, the resource may not have been suspended when transport == long-polling
		if (resource.isSuspended()) {
			try {
				countDownLatch.await();
			} catch (InterruptedException e) {
				LOG.error("Interrupted while trying to suspend resource {}", resource);
			}
		}
	}
}
