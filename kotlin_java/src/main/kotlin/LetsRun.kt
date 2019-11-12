import org.jsoup.Jsoup

fun main() {
    val doc = Jsoup.connect("https://letsrun.com/forum").get()
    val threads = doc.select("div.threads_container div.thread_list_container ul.thread_list li.row")
    val replies = Jsoup.connect("https://www.letsrun.com/forum/flat_read.php?thread=9467966").get()
        .select("div.page_content div.thread_list_container ul.thread > a")

    val id = replies[0].attr("name")
    val text = Jsoup.connect("https://www.letsrun.com/forum/posts/$id/reply").get().toString()
    println(text)
//    val regex = Regex("\\{.*?}")
    val regex = Regex("window\\.App\\.state\\.reply = (\\{.*?});\\s*\$", RegexOption.MULTILINE)
    println()
    println(regex.pattern)
    println()
//    println(regex.find("{\"foo\": 3}")?.value)
//    regex.find(text)?.groups?.forEach(::println)
    println(regex.find(text)?.groups?.get(1)?.value)
//    val json = regex.find(text)?.value
//        ?: error("Reply not found in post")

    // 7/3/2019 3:08pm
//    val formatter = DateTimeFormatter.ofPattern("M/d/yyyy h:mma").withZone(ZoneOffset.ofHours(-3))
//    threads.subList(3, threads.size).forEachIndexed { index, row ->
//        println(row)
//        println(
//            LocalDateTime.parse(
//                row.select(".timestamp").text().toUpperCase(),
//                formatter
//            ).atZone(ZoneId.systemDefault()).format(formatter)
//        )
//        println(row.select(".timestamp").text())
//        println(row.select(".post_title").text())
//        println(row.select(".post_author").text())
//        println(row.select(".post_count").text())
//        println("=================================")
//    }

}
