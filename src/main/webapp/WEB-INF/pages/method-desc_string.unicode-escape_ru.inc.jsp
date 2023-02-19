<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
%><h3>О эскейп-последовательность Unicode</h3>
<p>Эскейп-последовательность Unicode преобразуют один символ в формат 4-значного шестнадцатеричного кода, например \uXXXX. Например, «A» станет «\u0041».</p>

<p>В DenCode, помимо формата \uXXXX, также поддерживаются следующие форматы обозначений.</p>

<div class="table-responsive">
	<table class="table table-sm table-fit">
		<tr><th scope="col">Формат</th><th scope="col">Результат преобразования «ABC»</th><th scope="col">Описание / Язык программирования</th></tr>
		<tr><td>\uXXXX</td><td>\u0041\u0042\u0043</td><td>Общие эскейп-последовательность Unicode</td></tr>
		<tr><td>\u{X}</td><td>\u{41}\u{42}\u{43}</td><td>Lua</td></tr>
		<tr><td>\x{X}</td><td>\x{41}\x{42}\x{43}</td><td>Perl</td></tr>
		<tr><td>\X</td><td>\41\42\43</td><td>CSS</td></tr>
		<tr><td>&amp;#xX;</td><td>&amp;#x41;&amp;#x42;&amp;#x43;</td><td>HTML, XML</td></tr>
		<tr><td>%uXXXX</td><td>%u0041%u0042%u0043</td><td>Процентное кодирование (Нестандартный)</td></tr>
		<tr><td>U+XXXX</td><td>U+0041 U+0042 U+0043</td><td>Стандартная запись Unicode для кодовых точек (разделенные пробелами)</td></tr>
		<tr><td>0xX</td><td>0x41 0x42 0x43</td><td>Шестнадцатеричная запись кодовых точек (разделенные пробелами)</td></tr>
	</table>
</div>

<p>Некоторые из этих форматов упоминаются в <a href="https://www.rfc-editor.org/rfc/rfc5137" target="_blank">RFC 5137 (ASCII Escaping of Unicode Characters)</a> как BEST CURRENT PRACTICE, но не существует международного стандарта и т.д.</p>
<p>Формат %uXXXX поддерживается Microsoft IIS, но является нестандартным. Его можно закодировать в формате %u с помощью <a href="https://learn.microsoft.com/en-us/dotnet/api/system.web.httputility.urlencodeunicode" target="_blank">System.Web.HttpUtility.UrlEncodeUnicode</a> в C#, но этот метод устарел с версии .NET Framework 4.5.</p>
<p>Обратите внимание, что в формате \X пробел рассматривается как разделитель и игнорируется при декодировании, <a href="https://www.w3.org/International/questions/qa-escapes" target="_blank">как указано в CSS</a>. В форматах U+XXXX и 0xX при кодировании каждый символ отделяется одним пробелом, а при декодировании, как и в формате \X, последующий подряд один пробел игнорируется.</p>


<h4>Экранирование по имени Unicode</h4>

<p>В качестве управляющих последовательностей Unicode также поддерживается экранирование по имени Unicode.</p>

<div class="table-responsive">
	<table class="table table-sm table-fit">
		<tr><th scope="col">Формат</th><th scope="col">Результат преобразования «A»</th><th scope="col">Описание / Язык программирования</th></tr>
		<tr><td>\N{name}</td><td>\N{LATIN CAPITAL LETTER A}</td><td>C++23, Python, Perl</td></tr>
	</table>
</div>

<p>Имена Unicode можно найти в <a href="https://unicode.org/charts/nameslist/" target="_blank">Names List Charts - Unicode</a> или <a href="https://www.unicode.org/Public/15.0.0/ucd/NamesList.txt" target="_blank">NamesList.txt - Unicode</a>.</p>


<h4>Символы Unicode, отличные от BMP, в эскейп-последовательность Unicode</h4>

<p>Для символов Unicode, не относящихся к BMP, кодовые точки не умещаются в четыре цифры, поэтому они выражаются в следующей форме обозначения для каждого языка программирования.</p>
<p>Результат преобразования «😀» (U+01F600), который является символом Unicode, отличным от BMP, выглядит следующим образом.</p>

<div class="table-responsive">
	<table class="table table-sm table-fit">
		<tr><th scope="col">Формат</th><th scope="col">Результат преобразования «😀» (U+01F600)</th><th scope="col">Язык программирования</th></tr>
		<tr><td>\uXXXX</td><td>\uD83D\uDE00</td><td>Java, Kotlin, Scala</td></tr>
		<tr><td>\u{X}</td><td>\u{1F600}</td><td>C++23, Rust, Swift, JavaScript, PHP, Ruby, Dart, Lua</td></tr>
		<tr><td>\U00XXXXXX</td><td>\U0001F600</td><td>C, C++, Objective-C, C#, Go, Python, R</td></tr>
		<tr><td>\x{X}</td><td>\x{1F600}</td><td>Perl</td></tr>
		<tr><td>\X</td><td>\1F600</td><td>CSS</td></tr>
		<tr><td>&amp;#xX;</td><td>&amp;#x1F600;</td><td>HTML, XML</td></tr>
		<tr><td>%uXXXX</td><td>%uD83D%uDE00</td><td>-</td></tr>
		<tr><td>U+XXXX</td><td>U+1F600</td><td>-</td></tr>
		<tr><td>0xX</td><td>0x1F600</td><td>-</td></tr>
		<tr><td>\N{name}</td><td>\N{GRINNING FACE}</td><td>C++23, Python, Perl</td></tr>
	</table>
</div>

<p>В форматах \uXXXX и %uXXXX не-BMP символы представлены двумя кодовыми единицами в виде суррогатных пар UTF-16. В других форматах символ представлен одной кодовой точкой.</p>
