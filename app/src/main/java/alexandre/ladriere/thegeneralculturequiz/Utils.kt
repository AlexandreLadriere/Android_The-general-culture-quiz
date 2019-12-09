package alexandre.ladriere.thegeneralculturequiz

internal fun removeSpecialCharFromString(string: String): String {
    var outStr = string.replace("&#039;", "'")
    outStr = outStr.replace("&quot;", "\"")
    outStr = outStr.replace("&ouml;", "ö")
    return outStr
}