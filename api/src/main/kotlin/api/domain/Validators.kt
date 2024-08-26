package api.domain
import kotlin.random.Random
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Suppress("DEPRECATION")
fun String.isValidCPF(): Boolean {
    if (!this.matches(Regex("\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}"))) return false

    val numbers = this.filter { it.isDigit() }.map { it.toString().toInt() }
    if (numbers.distinct().size == 1) return false

    val firstVerifier = (0..8).sumBy { (10 - it) * numbers[it] } % 11
    val firstDigit = if (firstVerifier < 2) 0 else 11 - firstVerifier

    val secondVerifier = (0..9).sumBy { (11 - it) * numbers[it] } % 11
    val secondDigit = if (secondVerifier < 2) 0 else 11 - secondVerifier

    return firstDigit == numbers[9] && secondDigit == numbers[10]
}

fun generateRandomId(): Long {
    return Random.nextLong(Long.MIN_VALUE, Long.MAX_VALUE)
}

fun generateRandomSixDigit(): String {
    return Random.nextInt(1, 1000000).toString().padStart(6, '0')
}

fun generateRandomFourDigit(): String {
    return Random.nextInt(1, 10000).toString().padStart(4, '0')
}

fun getCurrentDateTime(): String {
    val currentDateTime = LocalDateTime.now()
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    return currentDateTime.format(formatter)
}