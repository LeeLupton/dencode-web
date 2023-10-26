/*!
 * dencode-web
 * Copyright 2016 Mozq
 *
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
 */
package com.dencode.web.servlet.pages;

import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.DateTimeException;
import java.time.ZoneId;
import java.util.Map;

import jakarta.servlet.annotation.WebServlet;

import com.dencode.logic.DencodeMapper;
import com.dencode.logic.model.DencodeCondition;
import com.dencode.web.servlet.AbstractDencodeHttpServlet;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebServlet("/dencode")
public class DencodeServlet extends AbstractDencodeHttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doPost() throws Exception {
		String type;
		String method;
		String val;
		String oe;
		String nl;
		String tz;
		Map<String, String> options;

		try (InputStream is = reqres().request().getInputStream()) {
			ObjectMapper mapper = new ObjectMapper();
			Map<String, Object> reqmap = mapper.readValue(is, new TypeReference<Map<String, Object>>() {});
			
			type = reqmap.getOrDefault("type", "all").toString();
			method = reqmap.getOrDefault("method", "all.all").toString();
			val = reqmap.getOrDefault("value", "").toString();
			oe = reqmap.getOrDefault("oe", "UTF-8").toString();
			nl = reqmap.getOrDefault("nl", "crlf").toString();
			tz = reqmap.getOrDefault("tz", "UTC").toString();
			
			options = (Map<String, String>)reqmap.get("options");
		}
		
		Charset charset = toCharset(oe, StandardCharsets.UTF_8);
		String lineBreak = toLineBreakString(nl);
		ZoneId zone = toZoneId(tz, "UTC");
		
		DencodeCondition cond = new DencodeCondition(val, charset, lineBreak, zone, options);
		
		try {
			Map<String, Object> dencodeResult = DencodeMapper.dencode(type, method, cond);
			
			responseAsJson(dencodeResult);
		} catch (OutOfMemoryError e) {
			throw new IllegalArgumentException("Method: " + method + ", Value length: " + cond.value().length(), e);
		}
	}

	private static String toCharsetName(String oe) {
		if (oe == null || oe.isEmpty()) {
			return "UTF-8";
		}
		
		switch (oe) {
			case "Shift_JIS": return "windows-31j";
			case "ISO-2022-JP": return "x-windows-iso2022jp";
			case "windows-874": return "x-windows-874";
		}
		
		if (!Charset.isSupported(oe)) {
			return "UTF-8";
		}
		
		return oe;
	}
	
	private static Charset toCharset(String oe, Charset defaultCharset) {
		
		Charset charset;
		try {
			charset = Charset.forName(toCharsetName(oe));
		} catch (IllegalArgumentException e) {
			charset = defaultCharset;
		}
		
		return charset;
	}
	
	private static String toLineBreakString(String nl) {
		
		String lineBreak;
		if ("lf".equals(nl)) {
			lineBreak = "\n";
		} else if ("cr".equals(nl)) {
			lineBreak = "\r";
		} else {
			// crlf
			lineBreak = "\r\n";
		}
		
		return lineBreak;
	}
	
	private static ZoneId toZoneId(String tz, String defaultTz) {
		
		ZoneId zone;
		try {
			zone = ZoneId.of(tz);
		} catch (DateTimeException | NullPointerException e) {
			zone = ZoneId.of(defaultTz);
		}
		
		return zone;
	}
}
