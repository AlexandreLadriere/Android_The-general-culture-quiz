package alexandre.ladriere.thegeneralculturequiz

internal fun removeSpecialCharFromString(string: String): String {
    var outStr = string.replace("&#039;", "'")
    outStr = outStr.replace("&quot;", "\"")
    outStr = outStr.replace("&ouml;", "ö")
    outStr = outStr.replace("&deg;", "°")
    outStr = outStr.replace("&ldquo;", "“")
    outStr = outStr.replace("&rdquo;", "”")
    outStr = outStr.replace("&amp;", "&")
    outStr = outStr.replace("&divide;", "÷")
    outStr = outStr.replace("&ograve;", "ò")
    outStr = outStr.replace("&Ouml;", "Ö")
    outStr = outStr.replace("&ouml;", "ö")
    outStr = outStr.replace("&uuml;", "ü")
    outStr = outStr.replace("&pi;", "π")
    return outStr
}