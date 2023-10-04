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
package com.dencode.logic.dencoder;

import static com.dencode.logic.dencoder.DencodeUtils.appendRoundString;

import java.math.RoundingMode;
import java.util.List;

import org.mifmi.commons4j.graphics.color.RGBColor;

import com.dencode.logic.dencoder.annotation.Dencoder;
import com.dencode.logic.dencoder.annotation.DencoderFunction;
import com.dencode.logic.model.DencodeCondition;

@Dencoder(type="color", method="color.rgb", hasEncoder=true, hasDecoder=false)
public class ColorRGBDencoder {
	
	private ColorRGBDencoder() {
		// NOP
	}
	
	
	@DencoderFunction
	public static String encColorRGBHex(DencodeCondition cond) {
		return encColorRGBHex(cond.valueAsColors());
	}
	
	@DencoderFunction
	public static String encColorRGBFn(DencodeCondition cond) {
		return encColorRGBFn(
				cond.valueAsColors(),
				DencodeUtils.getOption(cond.options(), "encColorRGBFnNotation", "percentage")
				);
	}
	
	
	private static String encColorRGBHex(List<RGBColor> vals) {
		return DencodeUtils.dencodeLines(vals, (rgb) -> {
			if (rgb == null) {
				return null;
			}
			
			boolean hasAlpha = (Double.compare(rgb.getA(), 1.0) != 0);
			
			StringBuilder sb = new StringBuilder();
			sb.append('#');
			
			if (rgb.getR8() <= 0xf) {
				sb.append('0');
			}
			sb.append(Integer.toHexString(rgb.getR8()));
			
			if (rgb.getG8() <= 0xf) {
				sb.append('0');
			}
			sb.append(Integer.toHexString(rgb.getG8()));
			
			if (rgb.getB8() <= 0xf) {
				sb.append('0');
			}
			sb.append(Integer.toHexString(rgb.getB8()));
			
			if (hasAlpha) {
				int a8 = (int) (255.0 * rgb.getA());
				if (a8 <= 0xf) {
					sb.append('0');
				}
				sb.append(Integer.toHexString(a8));
			}
			
			return sb.toString();
		});
	}
	
	private static String encColorRGBFn(List<RGBColor> vals, String notation) {
		return DencodeUtils.dencodeLines(vals, (rgb) -> {
			if (rgb == null) {
				return null;
			}
			
			boolean hasAlpha = (Double.compare(rgb.getA(), 1.0) != 0);
			
			StringBuilder sb = new StringBuilder();
			sb.append("rgb(");
			
			if (notation.equals("number")) {
				// number
				sb.append(rgb.getR8());
				sb.append(' ');
				sb.append(rgb.getG8());
				sb.append(' ');
				sb.append(rgb.getB8());
			} else {
				// percentage
				appendRoundString(sb, rgb.getR() * 100, 2, RoundingMode.HALF_UP);
				sb.append('%');
				sb.append(' ');
				appendRoundString(sb, rgb.getG() * 100, 2, RoundingMode.HALF_UP);
				sb.append('%');
				sb.append(' ');
				appendRoundString(sb, rgb.getB() * 100, 2, RoundingMode.HALF_UP);
				sb.append('%');
			}
			
			if (hasAlpha) {
				sb.append(" / ");
				appendRoundString(sb, rgb.getA(), 2, RoundingMode.HALF_UP);
			}
			sb.append(')');
			
			return sb.toString();
		});
	}
}
