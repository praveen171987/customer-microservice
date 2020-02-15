package com.ashraya.customer.domain;

import java.io.Serializable;

/**
 * 
 * @author SeekADegree
 *
 */
public class NotificationRequestModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9166590570767004726L;
	private NotificationData notification;
	private String to;

	/**
	 * @return the notification
	 */
	public NotificationData getNotification() {
		return notification;
	}

	/**
	 * @param notification the notification to set
	 */
	public void setNotification(NotificationData notification) {
		this.notification = notification;
	}

	/**
	 * @return the to
	 */
	public String getTo() {
		return to;
	}

	/**
	 * @param to the to to set
	 */
	public void setTo(String to) {
		this.to = to;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((notification == null) ? 0 : notification.hashCode());
		result = prime * result + ((to == null) ? 0 : to.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NotificationRequestModel other = (NotificationRequestModel) obj;
		if (notification == null) {
			if (other.notification != null)
				return false;
		} else if (!notification.equals(other.notification))
			return false;
		if (to == null) {
			if (other.to != null)
				return false;
		} else if (!to.equals(other.to))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("NotificationRequestModel [notification=").append(notification).append(", to=").append(to)
				.append("]");
		return builder.toString();
	}

}
