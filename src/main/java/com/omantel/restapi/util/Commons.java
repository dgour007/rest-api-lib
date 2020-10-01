/**
 * 
 */
package com.omantel.restapi.util;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omantel.restapi.bean.ApiError;

/**
 * @author Dhiraj Gour
 * @date 29 Sep 2020
 *
 */
public class Commons {

	public static int stringToInt(String val) throws Exception {

		if (val == null || "".equalsIgnoreCase(val)) {
			return 0;
		}
		try {
			return Integer.valueOf(val);
		} catch (Exception e) {
			return 0;
		}
	}
	
	public static ApiError mapApiErrorResponse(String responseBodyStr) {
    	
    	ApiError error = null;
    	
		try {
			ObjectMapper mapper = new ObjectMapper();
			error = mapper.readValue(responseBodyStr, ApiError.class);
			//error.setCode(Constants.REST_ERROR);
		} catch (Exception e){
			error = new ApiError();
			error.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			error.setMessage("Technical Problem Occurred while API response read");
		}
		return error;
	}
}
