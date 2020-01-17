/**
 * 
 */
package com.omantel.restapi.bean;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Data;

/**
 * @author Dhiraj Gour
 * @date 22 August 2019
 *
 */
@Data
@JsonSerialize
@JsonInclude(Include.NON_NULL)
public class Log {
	
	private String sessionId = null;
	private String requestId = null;
	private String url = null;
	private String userId = null;
	private String app = null;
	private String appUserId = null;
	private String externalId = null;
	private String accountNo = null;
	private String payload = null;
}
