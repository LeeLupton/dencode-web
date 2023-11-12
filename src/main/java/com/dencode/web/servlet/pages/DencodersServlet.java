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

import java.io.IOException;
import java.io.OutputStream;
import java.util.LinkedHashMap;
import java.util.Map;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletResponse;

import org.mifmi.commons4j.web.servlet.MifmiServletException;

import com.dencode.logic.DencodeMapper;
import com.dencode.logic.dencoder.annotation.Dencoder;
import com.dencode.web.servlet.AbstractDencodeHttpServlet;

@Deprecated
@WebServlet("/dencoders/*")
public class DencodersServlet extends AbstractDencodeHttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet() throws Exception {
		String[] params = reqres().pathParams();
		if (params == null || params.length != 2) {
			return;
		}
		
		String type = params[0];
		String method = params[1];
		
		Dencoder dencoder = DencodeMapper.getDencoderDefinition(type, method);
		if (dencoder == null) {
			return;
		}
		
		Map<String, Object> v = new LinkedHashMap<>() {
			private static final long serialVersionUID = 1L;
			{
				put("hasEncoder", Boolean.valueOf(dencoder.hasEncoder()));
				put("hasDecoder", Boolean.valueOf(dencoder.hasDecoder()));
				put("useOe", Boolean.valueOf(dencoder.useOe()));
				put("useNl", Boolean.valueOf(dencoder.useNl()));
				put("useTz", Boolean.valueOf(dencoder.useTz()));
			}
		};
		
		HttpServletResponse res = response();
		res.setStatus(HttpServletResponse.SC_OK);
		res.setHeader("Content-Type", "application/json; charset=UTF-8");
		
		try (OutputStream out = res.getOutputStream()) {
			writeAsJson(out, v);
			out.flush();
		} catch (IOException e) {
			throw new MifmiServletException(e);
		}
	}
}
