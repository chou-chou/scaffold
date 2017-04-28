package com.hrp.utils.net;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.atmosphere.cpr.AtmosphereResource;
import org.atmosphere.cpr.BroadcasterFactory;
import org.atmosphere.cpr.DefaultBroadcasterFactory;

/**
 * AtmosphereBroadcasterHelper
 */
public class AtmosphereBroadcasterHelper {

	/**
	 * broadcastToUsers
	 */
	public static void broadcastToUsers(final Object msg, final String userIds, final Map<String, AtmosphereResource> allRs) {
		Set<AtmosphereResource> toRs = new HashSet<AtmosphereResource>();
		String[] ids = jodd.util.StringUtil.split(userIds, ",");
		for (int i = 0, length = ids.length; i < length; i++) {
			AtmosphereResource rs = allRs.get(ids[i]);
			if (rs != null && rs.isSuspended()) {
				toRs.add(rs);
			}
		}
		BroadcasterFactory factory = new DefaultBroadcasterFactory();
		factory.lookup("/*").broadcast(msg, toRs);
	}
}
