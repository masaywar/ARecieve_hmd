/* This software was developed by employees of the National Institute of
 * Standards and Technology (NIST), an agency of the Federal Government.
 * Pursuant to title 15 United States Code Section 105, works of NIST
 * employees are not subject to copyright protection in the United States
 * and are considered to be in the public domain.  As a result, a formal
 * license is not needed to use the software.
 * 
 * This software is provided by NIST as a service and is expressly
 * provided "AS IS".  NIST MAKES NO WARRANTY OF ANY KIND, EXPRESS, IMPLIED
 * OR STATUTORY, INCLUDING, WITHOUT LIMITATION, THE IMPLIED WARRANTY OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE, NON-INFRINGEMENT
 * AND DATA ACCURACY.  NIST does not warrant or make any representations
 * regarding the use of the software or the results thereof including, but
 * not limited to, the correctness, accuracy, reliability or usefulness of
 * the software.
 * 
 * Permission to use this software is contingent upon your acceptance
 * of the terms of this agreement.
 */
package com.nist.jrtp;

import java.util.EventObject;

/**
 * An abstract base class for all RTP events.
 * 
 * @author mranga@nist.gov
 * @version $Revision: 1.1 $, $Date: 2007-05-10 13:22:37 $
 * @since 1.5
 */
public abstract class RtpEvent extends EventObject {

	/***************************************************************************
	 * Variables
	 **************************************************************************/

	/** A description of this event. */
	private String description = "";

	/***************************************************************************
	 * Constructors
	 **************************************************************************/

	/**
	 * Construct an RTP event.
	 * 
	 * @param rtpSession
	 *            The RTP session.
	 * @param description
	 *            A description of this event.
	 */
	public RtpEvent(com.nist.jrtp.RtpSession rtpSession, String description) {

		super(rtpSession);
		this.description = description;

	}

	/***************************************************************************
	 * Methods
	 **************************************************************************/

	/**
	 * Get a String representation of this event.
	 * 
	 * @return A String representation of this event.
	 */
	public String toString() {

		return description;

	}

}
