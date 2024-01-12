<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
%><h3>Base32について</h3>
<p>Base32は、印字可能なASCII文字を使用した符号化方式です。</p>
<p>Base32では、データを5ビットずつに分割し、それらを英数字(A-Z, 2-7)の文字に変換して表します。8文字ごとに変換し、最後が8文字に満たない場合はイコール記号(=)でパディングします。</p>

<p>Base32の文字への変換表は以下のとおりです。</p>

<div class="table-responsive">
	<table class="table table-sm table-fit">
		<tr><th>5ビットデータ</th><th>Base32文字</th></tr>
		<tr><td>00000</td><td>A</td></tr>
		<tr><td>00001</td><td>B</td></tr>
		<tr><td>00010</td><td>C</td></tr>
		<tr><td>00011</td><td>D</td></tr>
		<tr><td>00100</td><td>E</td></tr>
		<tr><td>00101</td><td>F</td></tr>
		<tr><td>00110</td><td>G</td></tr>
		<tr><td>00111</td><td>H</td></tr>
		<tr><td>01000</td><td>I</td></tr>
		<tr><td>01001</td><td>J</td></tr>
		<tr><td>01010</td><td>K</td></tr>
		<tr><td>01011</td><td>L</td></tr>
		<tr><td>01100</td><td>M</td></tr>
		<tr><td>01101</td><td>N</td></tr>
		<tr><td>01110</td><td>O</td></tr>
		<tr><td>01111</td><td>P</td></tr>
		<tr><td>10000</td><td>Q</td></tr>
		<tr><td>10001</td><td>R</td></tr>
		<tr><td>10010</td><td>S</td></tr>
		<tr><td>10011</td><td>T</td></tr>
		<tr><td>10100</td><td>U</td></tr>
		<tr><td>10101</td><td>V</td></tr>
		<tr><td>10110</td><td>W</td></tr>
		<tr><td>10111</td><td>X</td></tr>
		<tr><td>11000</td><td>Y</td></tr>
		<tr><td>11001</td><td>Z</td></tr>
		<tr><td>11010</td><td>2</td></tr>
		<tr><td>11011</td><td>3</td></tr>
		<tr><td>11100</td><td>4</td></tr>
		<tr><td>11101</td><td>5</td></tr>
		<tr><td>11110</td><td>6</td></tr>
		<tr><td>11111</td><td>7</td></tr>
	</table>
</div>

<p>例えば、「Hello!」をBase32で変換すると以下のようになります。</p>

<p>1. 2進数表現にする。</p>

<pre>01001000 01100101 01101100 01101100 01101111 00100001  (US-ASCII / UTF-8の場合)</pre>

<p>2. 5ビットごとに区切る。5ビットに満たない場合は末尾を「0」でパディングする。</p>

<pre>01001 00001 10010 10110 11000 11011 00011 01111 00100 00100</pre>

<p>3. 変換表を使用して文字に変換する。8文字ごとに変換し、8文字に満たない場合は末尾を「=」でパディングする。</p>

<pre>JBSWY3DP EE======</pre>

<p>4. 文字を全て繋げてBase32の変換結果とする。</p>

<pre>JBSWY3DPEE======</pre>
