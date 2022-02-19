package fr.adbonnin.kumoko.web.http

import fr.adbonnin.kumoko.web.html.ParseHtmlResponseHandler
import org.apache.hc.client5.http.classic.HttpClient
import org.apache.hc.core5.http.ClassicHttpRequest
import org.apache.hc.core5.http.HttpHost
import org.apache.hc.core5.http.protocol.HttpContext
import org.jsoup.nodes.Document
import java.io.File
import java.io.OutputStream

fun HttpClient.parseHtml(request: ClassicHttpRequest): Document {
    return execute(request, ParseHtmlResponseHandler(request))
}

fun HttpClient.parseHtml(request: ClassicHttpRequest, context: HttpContext?): Document {
    return execute(request, context, ParseHtmlResponseHandler(request))
}

fun HttpClient.parseHtml(target: HttpHost, request: ClassicHttpRequest): Document {
    return execute(target, request, ParseHtmlResponseHandler(request))
}

fun HttpClient.parseHtml(target: HttpHost, request: ClassicHttpRequest, context: HttpContext?): Document {
    return execute(target, request, context, ParseHtmlResponseHandler(request))
}

fun HttpClient.download(request: ClassicHttpRequest, output: OutputStream) {
    execute(request, DownloadResponseHandler(output))
}

fun HttpClient.download(request: ClassicHttpRequest, context: HttpContext?, output: OutputStream) {
    execute(request, context, DownloadResponseHandler(output))
}

fun HttpClient.download(target: HttpHost, request: ClassicHttpRequest, output: OutputStream) {
    execute(target, request, DownloadResponseHandler(output))
}

fun HttpClient.download(target: HttpHost, request: ClassicHttpRequest, context: HttpContext?, output: OutputStream) {
    execute(target, request, context, DownloadResponseHandler(output))
}

fun HttpClient.newFileDownloader(): FileDownloader {
    return FileDownloader()
}

fun HttpClient.downloadFile(request: ClassicHttpRequest, file: File, redownloadMode: RedownloadMode): File {
    return newFileDownloader().download(file, redownloadMode) { handler -> execute(request, handler) }
}

fun HttpClient.downloadFile(request: ClassicHttpRequest, context: HttpContext?, file: File, redownloadMode: RedownloadMode): File {
    return newFileDownloader().download(file, redownloadMode) { handler -> execute(request, context, handler) }
}

fun HttpClient.downloadFile(target: HttpHost, request: ClassicHttpRequest, file: File, redownloadMode: RedownloadMode): File {
    return newFileDownloader().download(file, redownloadMode) { handler -> execute(target, request, handler) }
}

fun HttpClient.downloadFile(target: HttpHost, request: ClassicHttpRequest, context: HttpContext?, file: File, redownloadMode: RedownloadMode): File {
    return newFileDownloader().download(file, redownloadMode) { handler -> execute(target, request, context, handler) }
}

fun HttpClient.downloadToDir(request: ClassicHttpRequest, dir: File, emptyFilename: String, redownloadMode: RedownloadMode): File {
    return newFileDownloader().downloadToDir(dir, request.path, emptyFilename, redownloadMode) { handler -> execute(request, handler) }
}

fun HttpClient.downloadToDir(request: ClassicHttpRequest, context: HttpContext?, dir: File, emptyFilename: String, redownloadMode: RedownloadMode): File {
    return newFileDownloader().downloadToDir(dir, request.path, emptyFilename, redownloadMode) { handler -> execute(request, context, handler) }
}

fun HttpClient.downloadToDir(target: HttpHost, request: ClassicHttpRequest, dir: File, emptyFilename: String, redownloadMode: RedownloadMode): File {
    return newFileDownloader().downloadToDir(dir, request.path, emptyFilename, redownloadMode) { handler -> execute(target, request, handler) }
}

fun HttpClient.downloadToDir(target: HttpHost, request: ClassicHttpRequest, context: HttpContext?, dir: File, emptyFilename: String, redownloadMode: RedownloadMode): File {
    return newFileDownloader().downloadToDir(dir, request.path, emptyFilename, redownloadMode) { handler -> execute(target, request, context, handler) }
}
