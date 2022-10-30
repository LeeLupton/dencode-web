<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
%><h3>О преобразовании клавиатуры JIS (преобразование Микака)</h3>
<p>Преобразование клавиатуры JIS (преобразование Микака) - это один из единых подстановочных шифров, который шифрует путем замены символов в тексте на другие символы. В основном, он используется для жаргона и обфускации в японском Интернете.</p>
<p>Замена символов осуществляется путем преобразования между английскими символами и японскими символами, напечатанными на японской клавиатуре JIS (JIS X4064 / OADG109A).</p>

<p>
<a title="StuartBrady (the first version) and others., GFDL &lt;http://www.gnu.org/copyleft/fdl.html&gt;, via Wikimedia Commons" href="https://commons.wikimedia.org/wiki/File:KB_Japanese.svg"><img width="512" alt="KB Japanese" src="https://upload.wikimedia.org/wikipedia/commons/thumb/b/bc/KB_Japanese.svg/512px-KB_Japanese.svg.png"></a>
</p>

<p>Например, "n" шифруется в "み", а "t" - в "か".</p>

<pre>Text before encryption: this is a secret message
Text after encryption : かくにと にと ち といそすいか もいととちきい</pre>

<p>Первоначально он происходит от интернет-сленга под названием "みかか" (Микака), который является результатом набора "NTT", одного из японских телекоммуникационных операторов, на клавиатуре JIS.</p>

<p>Японская буква "を" не конвертируется, поскольку не имеет буквенного аналога на клавиатуре JIS.</p>

<p>"\" (обратная косая черта в английских шрифтах, символ йены в японских шрифтах) существует в двух местах на клавиатуре JIS: "ー" и "ろ". Обратите внимание, что эти отображения: "ろ" &lt;-&gt; "\", "ー" &lt;-&gt; "|". На клавиатуре Mac JIS единственным английским символом для японской клавиши "ろ" является "_", поэтому следует использовать "ろ" &lt;-&gt; "_", "ー" &lt;-&gt; "\" лучше, но это соответствует спецификациям существующих инструментов преобразования.</p>

<p>DenCode конвертирует английские и японские символы одновременно, поэтому шифрование и расшифровка имеют одинаковый смысл. Таким образом, преобразование "nttはみかか" становится "みかかfntt", и наоборот, преобразование "みかかfntt" становится "nttはみかか".</p>

<p>Предусмотрены два дополнительных режима преобразования: строгий и мягкий. Строгий режим не конвертирует алфавитные символы верхнего регистра, кроме Z и E, а также японские символы катаканы, а мягкий режим конвертирует и эти символы. Используйте их в зависимости от того, нужно ли разрешить не возвращаться к исходному символу, например, "N -> み -> n" или "ミ -> n -> み" при повторном преобразовании.</p>
